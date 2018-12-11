package com.nchhr.mall.Controller;

import com.nchhr.mall.Configure.WechatConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class MainController {
    @RequestMapping("/MP_verify_"+ WechatConfig.MP_verify +".txt")
    @ResponseBody
    public String verify() {
        return WechatConfig.MP_verify;
    }
}
