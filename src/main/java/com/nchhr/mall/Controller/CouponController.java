package com.nchhr.mall.Controller;

/*
Created by Jacy 2018/10/05
Serving coupon functions
 */

import com.nchhr.mall.Entity.CouponEntity;
import com.nchhr.mall.Entity.MallUserEntity;
import com.nchhr.mall.Service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @RequestMapping("/coupon")
    public String coupon(Map<String, Object> map,HttpSession session) {
        MallUserEntity mallUserEntity= (MallUserEntity) session.getAttribute("MallUserInfo");
        //TODO 手机号当做USERID
        String userId=mallUserEntity.getPhone();
        map.put("unusedCoupons", couponService.getCoupons(userId, "0"));
        map.put("usedCoupons", couponService.getCoupons(userId, "1"));
        return "coupon";
    }

    @RequestMapping("/myCoupon")
    public String myCoupon(Map<String, Object> map, HttpSession session) {
        MallUserEntity mallUserEntity= (MallUserEntity) session.getAttribute("MallUserInfo");
        //TODO 手机号当做USERID
        String userId=mallUserEntity.getPhone();
        map.put("unusedCoupons", couponService.getCoupons(userId, "0"));
        map.put("usedCoupons", couponService.getCoupons(userId, "1"));
        return "myCoupon";
    }

    @RequestMapping("/coupon/choose")
    @ResponseBody
    public String chooseCoupon(HttpServletRequest request, String couponId) {
        try{
            //此处为从优惠券页面传上来选择的优惠券
            CouponEntity couponEntity = couponService.getCouponByOfid(couponId);
            System.out.println(couponEntity.toString());
            String totalAmount = request.getSession().getAttribute("totalAmount").toString();

            Float f_Condition_use = Float.parseFloat(couponEntity.getCondition_use());
            Float f_totalAmount = Float.parseFloat(totalAmount);
            //type == 1  折扣 ; type == 2  满减
            if ("1".equals(couponEntity.getType())){
                request.getSession().setAttribute("OFid",couponId);
                return "-OK-";
            }else if ("2".equals(couponEntity.getType())){
                if (f_totalAmount > f_Condition_use){
                    request.getSession().setAttribute("OFid",couponId);
                    return "-OK-";
                }else {
                    return "-Error1-";
                }
            }else {
                System.out.println("error: coupon type !!!");
                return "-Error2-";
            }
        }catch (Exception e){
            System.out.println(e.getMessage());;
        }
        return "-Error2-";
    }
}
