package com.nchhr.mall.Controller;

/*
Created by Jacy 2018/10/05
Serving coupon functions
 */

import com.nchhr.mall.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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
    public String chooseCoupon(HttpSession httpSession, String couponId) {
        //此处为从优惠券页面传上来选择的优惠券
        System.out.println(couponId);
        httpSession.setAttribute("coupon", couponId);
        return "-OK-";
    }
}
