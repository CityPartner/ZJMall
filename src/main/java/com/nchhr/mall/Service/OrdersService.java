package com.nchhr.mall.Service;

import com.nchhr.mall.Dao.OrderCommodityDao;
import com.nchhr.mall.Dao.OrdersDao;
import com.nchhr.mall.Entity.*;
import com.nchhr.mall.EntityVo.OrderCommodityVo;
import com.nchhr.mall.Utils.Generate;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrdersService {

    @Resource
    private OrdersDao ordersDao;

    @Resource
    private OrderCommodityDao orderCommodityDao;

    @Resource
    private CommodityService commodityService;

    @Resource
    private CouponService couponService;

    @Resource
    private ShopCartService shopCartService;

    @Autowired
    MallUserService mallUserService;
    @Autowired
    ProjectService projectService;
    @Autowired
    IncomeService incomeService;
    @Autowired
    WalletService walletService;

    /**产生并写入订单
     *库存相应减少
     * 优惠券使用
     * HWG
     */
    public int insertOrder(HttpSession httpSession, HttpServletRequest request) {
        MallUserEntity mallUserEntity= (MallUserEntity) request.getSession().getAttribute("MallUserInfo");

        try{
//            产生订单号
            Object re_id1 = request.getSession().getAttribute("Re_id");
            if(re_id1==null)
                return 2;
            String re_id = request.getSession().getAttribute("Re_id").toString();
//            String m_id = request.getSession().getAttribute("M_id").toString();
            String m_id=mallUserEntity.getM_id();
            String OFid = null;
            Object oFid = request.getSession().getAttribute("OFid");
            if(oFid!=null)
                OFid=oFid.toString();
            String price = request.getSession().getAttribute("DisAmount").toString();
            String original_price = request.getSession().getAttribute("totalAmount").toString();
            System.out.println("OrderPrice:"+price);
            String o_id = "D"+Generate.getTime()+Generate.getRandomNumStr(6);
//            System.out.println(o_id);
            String order_time = Generate.getTimeByFormat("yyyy-MM-dd HH:mm:ss");
            String self_lifting = "否";
            String status = "0";
            boolean b1 = ordersDao.insertOrder(o_id,re_id,m_id,Double.parseDouble(price),Double.parseDouble(original_price),order_time,self_lifting,status,OFid);

//            插入商品
            String CommodityData=request.getSession().getAttribute("CommodityData").toString();
            System.out.println(CommodityData);
            String[] commoditys=CommodityData.split(",");
            System.out.println(commoditys.toString());

//        读取商品信息
            boolean b2=false;
            String SC_id=mallUserEntity.getSC_id();
            List<CommodityEntity> coms= (List<CommodityEntity>) request.getSession().getAttribute("RealComs");
            try{
//                for (String codata:commoditys) {
//                    System.out.println(codata);
//                    if(codata!=null) {
//                        String[] temp = codata.split(":");
//                        System.out.println(o_id+" "+temp[0]+"  "+temp[1]);
//                        commodityService.buyCommodity(temp[0],temp[1]);
//                        orderCommodityDao.saveByHand(o_id,temp[0],Integer.parseInt(temp[1]));
//                        shopCartService.deleteBySCidAndCid(SC_id,temp[0]);
//                    }
//                }
                for (CommodityEntity codata:coms) {
                    System.out.println(codata);
                    if(codata!=null) {
                        commodityService.buyCommodity(codata.getC_id(),codata.getStock());
                        orderCommodityDao.saveByHand(o_id,codata.getC_id(),Integer.parseInt(codata.getStock()));
                        shopCartService.deleteBySCidAndCid(SC_id,codata.getC_id());
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
                return 3;
            }

//            使用优惠券
            if(OFid!=null){
                couponService.useCoupon(OFid);
                request.getSession().removeAttribute("OFid");
            }
            httpSession.setAttribute("orderId", o_id);
            String orderFee = (int)(Float.parseFloat(price)*100) + "";
            httpSession.setAttribute("orderFee", orderFee);

            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return  4;
        }

    }


    /**
     * HWG
     * @param M_id
     * @param status
     * @return
     */
    public List<OrderCommodityVo> getOrdersByMid(String M_id, String status){
        List<OrderCommodityVo> orderCommodityVo=new ArrayList<>();
        List<OrderEntity> orders = ordersDao.getOrderByMid(M_id, status);

        for (OrderEntity order:orders) {
            orderCommodityVo.add(new OrderCommodityVo(order,orderCommodityDao.getOrderCommoditiesByOid(order.getO_id())));
        }


        return orderCommodityVo;

    }

    /**
     *通过O_id 获取订单
     * HWG
     */
    public OrderEntity getOrderById(String O_id){
        return ordersDao.getOrderById(O_id);
    }

    /**
     * HWG
     * @param o_id
     * @param status
     * @return
     */
    public boolean setOrderStatus(String o_id,String status){
        try {
            ordersDao.setOrderStatusByOid(o_id,status);
        }catch (Exception se){
            System.out.println(se.getMessage());
            return false;
        }
        return  true;
    }

    /**
     * HWG
     * @param O_id
     * @return
     */
    public String orderBonus(String O_id){
        OrderEntity order = ordersDao.getOrderById(O_id);


        if(order==null)
            return "Wrong Order!";
        String m_id = order.getM_id();
        String oFid = order.getOFid();
        MallUserEntity user = mallUserService.getUserByMid(m_id);
        ProjectEntity project = projectService.getProByPid("PmA1bP2PAVSUItWEZsLjeTTQAD1NFpktz");
        int discount_lowest = (int) (project.getDiscount_lowest()*10);
        String r_id = user.getR_id();
        String moneyReceiver=null;
        boolean flag=false;
        double price=order.getPrice();
        double original_price = order.getOriginal_price();
        double project_income=price;
        double person_income=0.0;

//        //项目发起人或投资人下单
//        if ("1".equals(r_id)||"2".equals(r_id)){
//            project_income=order.getPrice()-(original_price*discount_lowest/10);
//        }
        if(oFid!=null)
        {
            flag=true;
            CouponEntity coupon = couponService.getCouponByOfid(oFid);
            moneyReceiver=coupon.getOffe_user();
//            System.out.println("original_price:"+original_price+
//                    "\ndis_lowest:"+discount_lowest+
//                    "\noXd:"+original_price*discount_lowest+
//                    "\n(original_price*discount_lowest)*0.01="+(original_price*discount_lowest)*0.01);
            project_income=(original_price*discount_lowest)*0.01;
            //System.out.println("price="+price+"\nprice-project_income="+(price-project_income));
            person_income=(price*100-project_income*100)*0.01;
        }
        else
        {
            flag=false;
            project_income=price;
        }
        System.out.println("project"+project_income+"----person"+person_income);
        //插入到income表
        IncomeEntity incomeEntity=new IncomeEntity();
        incomeEntity.setIn_id("I"+Generate.getTime()+Generate.getRandomNumStr(3));
        incomeEntity.setIncome_amount(String.valueOf(price));
        incomeEntity.setM_id(m_id);
        incomeEntity.setUnallocated_amount(String.valueOf(project_income));
        if(!(incomeService.insertIntoIncome(incomeEntity))){
            return "Failed insert into table income!";
        }

        //插入到投资人钱包、收入（如果用户使用了优惠券）
        if(flag){
            //收入表更新
            ProjectWalletIncome pwi=new ProjectWalletIncome();
            pwi.setAttachInfo(O_id);
            pwi.setIncomeAmount(String.valueOf(person_income));
            pwi.setIncomeId("PI"+Generate.getTime()+Generate.getRandomNumStr(4));
            pwi.setIncomeTime(new Timestamp(new Date().getTime()));
            pwi.setIncomeType("0");
            pwi.setProjectId("PmA1bP2PAVSUItWEZsLjeTTQAD1NFpktz");
            pwi.setUserId(moneyReceiver);
            if(!(walletService.insertIntoPWI(pwi)))
                return "Failed to insert into project_wallet_income";
            //钱包余额更新
            if(!(walletService.updateWallet(moneyReceiver,"PmA1bP2PAVSUItWEZsLjeTTQAD1NFpktz",person_income)))
                return "Failed to update walllet_amount!";
        }



        return "success";

    }

    public boolean toPay(String o_id, HttpSession httpSession){
        try {
            System.out.println("oid:"+o_id);
            OrderEntity orderById = ordersDao.getOrderById(o_id);
            double price = orderById.getPrice();
            String orderFee = price * 100 + "";
            httpSession.setAttribute("orderId", o_id);
            httpSession.setAttribute("orderFee", orderFee);
            return true;
        }catch (Exception eee){
            System.out.println(eee.getMessage());
            return false;
        }
    }
}
