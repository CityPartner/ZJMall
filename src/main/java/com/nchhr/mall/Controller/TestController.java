package com.nchhr.mall.Controller;

import com.nchhr.mall.Entity.MallUserEntity;
import com.nchhr.mall.Utils.OrderSmsUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class TestController {
    @RequestMapping("/test")
    @ResponseBody
    public String test(HttpSession session) {

        OrderSmsUtils.orderSend(((MallUserEntity)session.getAttribute("MallUserInfo")).getPhone(),"测试短信");
        return "这是接口调用测试";
    }
}
