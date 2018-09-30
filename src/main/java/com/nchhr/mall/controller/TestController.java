package com.nchhr.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class TestController {

    // TODO 此控制类仅用作测试静态资源 切勿在此添加代码逻辑

    @RequestMapping(value = "/{resourseName}")
    public ModelAndView index(@PathVariable("resourseName") String resourseName) {
        ModelAndView modelAndView = new ModelAndView(resourseName);
        return modelAndView;
    }

}
