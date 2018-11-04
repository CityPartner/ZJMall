package com.nchhr.mall.Controller;


import com.nchhr.mall.Dao.MallUserDao;
import com.nchhr.mall.Entity.MallUserEntity;
import com.nchhr.mall.Enum.ExceptionEnum;
import com.nchhr.mall.RsultModel.R_data;
import com.nchhr.mall.Service.CookiesService;
import com.nchhr.mall.Service.LoginService;
import com.nchhr.mall.Utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Resource
    MallUserDao mallUserDao;

    @Autowired
    CookiesService cookiesService;

    @RequestMapping("")
    public String index(HttpServletRequest request,HttpServletResponse response,HttpSession session) {

        //有cookie cookie保存了很多信息
        // return "index"

        //没有cookie
        System.out.println("检查cookie");

        Cookie[] cookies = request.getCookies();
        String ss = "";

        if (cookies == null){
            return "redirect:"+"/index";
        }
        for (Cookie cookie : cookies) {
            System.out.println(cookie.toString());
            if (cookie.getName().equals("MID")) {
                ss = cookie.getValue();
            }
        }
        System.out.println("mid:"+ss);
        if ("".equals(ss) && ss == null){
            return "redirect:"+"/index";
        }
        else {
            MallUserEntity mallUser = mallUserDao.loadByMid(ss);
            if (mallUser == null){
//                cookiesService.clear(response,request);
                for (Cookie cookie : cookies) {
//                    System.out.println(cookie.toString());
                    if (cookie.getName().equals("MID")) {
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                    }
                }
                return  "redirect:/login.html";
            }
            System.out.println(mallUser.toString());
            session.setAttribute("MallUserInfo",mallUser);

            return "redirect:/index";
        }


    }


    @RequestMapping("/login")
    @ResponseBody
    public R_data login(String phone, String pwd, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception{

        return ResultUtils.success(loginService.login(phone, pwd,response,request,session),ExceptionEnum.SUCCESS);
    }
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        loginService.loginOut(request,response,session);
        return "redirect:/login.html";
    }

}
