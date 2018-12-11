package com.nchhr.mall.Controller;

import com.nchhr.mall.Entity.WeChatUserEntity;
import com.nchhr.mall.Service.LoginService;
import com.nchhr.mall.Service.WeChatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/wechatuser")
public class WeChatUserController {

    @Autowired
    private WeChatUserService weChatUserService;
    @Autowired
    LoginService loginService;

    @RequestMapping("")
    private String weChatAuthorize() {
        //跳转授权验证请求页
        return "redirect:"+weChatUserService.getWeChatRequestURL();
    }

    @RequestMapping("/wechat_redirect")
    public String weChatRedirect(String code, String state, HttpServletRequest request, HttpServletResponse  response, HttpSession session) {
        weChatUserService.getWeChatOAuth2Token(code, state);

        //获取用户微信openid
        String openid = weChatUserService.getOpenid();
        //获取用户微信信息
        WeChatUserEntity weChatUser = weChatUserService.getUser();
        if (session.getAttribute("weChatUser") != null){
            session.removeAttribute("weChatUser");
        }
        session.setAttribute("weChatUser",weChatUser);
        System.out.println(weChatUser);
        return "redirect:"+"/regist.html";

//        if (loginService.loginByOpenid(openid,response,request,session)){
//
//
//            return "redirect:/index";
//        }
//        else {
//            return "redirect:/regist.html";
//        }

            //微信id存在 有账号 return“login”

            //不存在 没有账号 return “register”
//            return "register";
    }
}
