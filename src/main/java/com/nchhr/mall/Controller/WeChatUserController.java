package com.nchhr.mall.Controller;

import com.nchhr.mall.entity.WeChatOAuth2Token;
import com.nchhr.mall.entity.WeChatUserEntity;
import com.nchhr.mall.service.WeChatUserService;
import com.nchhr.mall.util.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/wechatuser")
public class WeChatUserController {

    @Autowired
    private WeChatUserService weChatUserService;

    @RequestMapping("")
    private String weChatAuthorize() {
        //跳转授权验证请求页
        return "redirect:"+weChatUserService.getWeChatRequestURL();
    }

    @RequestMapping("/wechat_redirect")
    @ResponseBody
    public String weChatRedirect(String code, String state) {
        weChatUserService.getWeChatOAuth2Token(code, state);


        //获取用户微信openid
        String openid = weChatUserService.getOpenid();
        //获取用户微信信息
        WeChatUserEntity weChatUser = weChatUserService.getUser();

        return "USER-TEST";
    }
}
