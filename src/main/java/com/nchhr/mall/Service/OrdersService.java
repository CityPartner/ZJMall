package com.nchhr.mall.Service;

import com.nchhr.mall.Dao.OrderCommodityDao;
import com.nchhr.mall.Dao.OrdersDao;
import com.nchhr.mall.Utils.Generate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    public int insertOrder(HttpServletRequest request) {

        try{

//            产生订单号
            Object re_id1 = request.getSession().getAttribute("Re_id");
            if(re_id1==null)
                return 2;
            String re_id = request.getSession().getAttribute("Re_id").toString();
//            String m_id = request.getSession().getAttribute("M_id").toString();
            String m_id="#123";
            String OFid = null;
            Object oFid = request.getSession().getAttribute("OFid");
            if(oFid!=null)
                OFid=oFid.toString();
            String price = request.getSession().getAttribute("totalAmount").toString();
//            System.out.println(price);
            String o_id = "D"+Generate.getTime()+m_id;
            String order_time = Generate.getTimeByFormat("yyyy-MM-dd HH:mm:ss");
            String self_lifting = "否";
            String status = "0";
            boolean b1 = ordersDao.insertOrder(o_id,re_id,m_id,Double.parseDouble(price),order_time,self_lifting,status,OFid);

//            插入商品
            String CommodityData=request.getSession().getAttribute("CommodityData").toString();
            System.out.println(CommodityData);
            String[] commoditys=CommodityData.split(",");
            System.out.println(commoditys.toString());

//        读取商品信息
            boolean b2=false;
            try{
                for (String codata:commoditys) {
                    System.out.println(codata);
                    if(codata!=null) {
                        String[] temp = codata.split(":");
//                        b2 = orderCommodityDao.save(new OrderCommodityEntity(o_id, temp[0], Integer.parseInt(temp[1])));
                        System.out.println(o_id+" "+temp[0]+"  "+temp[1]);
                        commodityService.buyCommodity(temp[0],temp[1]);
                        orderCommodityDao.saveByHand(o_id,temp[0],Integer.parseInt(temp[1]));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                return 3;
            }

//            使用优惠券
            if(OFid!=null){
                couponService.useCoupon(OFid);
            }




            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return  4;
        }

    }
}
