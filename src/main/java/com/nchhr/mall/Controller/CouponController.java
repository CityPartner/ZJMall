package com.nchhr.mall.Controller;

/*
Created by Jacy 2018/10/05
Serving coupon functions
 */

import com.nchhr.mall.Entity.CouponEntity;
import com.nchhr.mall.Service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @RequestMapping("")
    public String coupon(Map<String, Object> map) {
        // TODO 此处的查询优惠券所有者的userId是临时定义的
        String userId = "#123";
        map.put("unusedCoupons", couponService.getCoupons(userId, "0"));
        map.put("usedCoupons", couponService.getCoupons(userId, "1"));
        return "coupon";
    }

    @RequestMapping("/choose")
    @ResponseBody
    public String chooseCoupon(HttpServletRequest request, String couponId) {

        try{

            //此处为从优惠券页面传上来选择的优惠券
//        System.out.println(couponId);
            CouponEntity couponEntity = couponService.getCouponByOfid(couponId);

//            System.out.println("totalAmount:" + request.getSession().getAttribute("totalAmount").toString());
            String totalAmount = request.getSession().getAttribute("totalAmount").toString();

            Float f_Condition_use = Float.parseFloat(couponEntity.getCondition_use());
            Float f_totalAmount = Float.parseFloat(totalAmount);

            //type == 1  折扣 ; type == 2  满减
            if (couponEntity.getType() == "1"){
                request.getSession().setAttribute("OFid",couponId);
                return "-OK-";
            }else if (couponEntity.getType() == "2"){
                if (f_totalAmount > f_Condition_use){
                    return "-OK-";
                }else {
                    return "-Error-";
                }
            }else {
                System.out.println("error: coupon type !!!");
                return "-Error-";
            }

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
