package com.nchhr.mall.Controller;


import com.nchhr.mall.Dao.MallUserDao;
import com.nchhr.mall.Entity.MallUserEntity;
import com.nchhr.mall.Enum.ExceptionEnum;
import com.nchhr.mall.RsultModel.R_data;
import com.nchhr.mall.Service.LoginService;
import com.nchhr.mall.Utils.ResultUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @Resource
    MallUserDao mallUserDao;

    @RequestMapping("")
    public String index(HttpServletRequest request,HttpServletResponse response,HttpSession session) {

        //有cookie cookie保存了很多信息
        // return "index"

        //没有cookie

        Cookie[] cookies = request.getCookies();
        String ss = "";

        if (cookies == null){
            return "redirect:"+"/mall/wechatuser";
        }
        for (Cookie cookie : cookies) {
            System.out.println(cookie.toString());
            if (cookie.getName().equals("MID")) {
                ss = cookie.getValue();

            }
        }
        if (ss == ""){
            return "redirect:"+"/mall/wechatuser";
        }
        else {
            MallUserEntity mallUser = mallUserDao.loadByMid(ss);
            session.setAttribute("MallUserInfo",mallUser);

            return "redirect:/mall/index";
        }


    }


    @RequestMapping("login")
    @ResponseBody
    public R_data login(String phone, String pwd, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception{

        return ResultUtils.success(loginService.login(phone, pwd,response,request,session),ExceptionEnum.SUCCESS);
    }
}
