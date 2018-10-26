package com.nchhr.mall.Controller;

import com.nchhr.mall.Entity.TemporaryloginEntity;
import com.nchhr.mall.Entity.WeChatUserEntity;
import com.nchhr.mall.Service.LoginService;
import com.nchhr.mall.Service.WeChatUserService;
import com.nchhr.mall.util.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/wechatuser")
public class WeChatUserController {

    @Autowired
    private WeChatUserService weChatUserService;

    @Autowired
    LoginService loginService;

    @RequestMapping("")
    private String weChatAuthorize(String userPhone, String codess, String pwd, HttpSession session) {
        TemporaryloginEntity temporaryloginEntity = new TemporaryloginEntity();
        temporaryloginEntity.setCode(codess);
        temporaryloginEntity.setPhone(userPhone);
        temporaryloginEntity.setPwd(pwd);
        System.out.println(temporaryloginEntity);
        if (session.getAttribute("temporaryloginEntity") != null){
            session.removeAttribute("temporaryloginEntity");
        }
        session.setAttribute("temporaryloginEntity",temporaryloginEntity);
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

        return "redirect:"+"/RegistLogin";

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
