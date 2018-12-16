package com.nchhr.mall.Service;

import com.nchhr.mall.Dao.OrderCommodityDao;
import com.nchhr.mall.Dao.OrdersDao;
import com.nchhr.mall.Entity.CommodityEntity;
import com.nchhr.mall.Entity.MallUserEntity;
import com.nchhr.mall.Entity.OrderEntity;
import com.nchhr.mall.EntityVo.OrderCommodityVo;
import com.nchhr.mall.Utils.Generate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public boolean setOrderStatus(String o_id,String status){
        try {
            ordersDao.setOrderStatusByOid(o_id,status);
        }catch (Exception se){
            System.out.println(se.getMessage());
            return false;
        }
        return  true;
    }
}
