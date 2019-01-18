package com.nchhr.mall.Filter;

import com.nchhr.mall.Dao.MallUserDao;
import com.nchhr.mall.Entity.MallUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class LoginFilter implements Filter {



    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        //如果没有登录
//        String requestURI = req.getRequestURI().substring(req.getRequestURI().indexOf("/", 1), req.getRequestURI().length());
        String requestURI = req.getRequestURI();
        System.out.println("requestURI=" + requestURI);
//        System.out.println("路径："+req.getContextPath());
////        boolean ss = requestURI.contains("**/index**");
//        System.out.println("80:"+requestURI);
        //访问除login.jsp（登录页面）和验证码servlet之外的jsp/servlet都要进行验证
        if (    !(req.getContextPath()+"/").contains(requestURI)
                && requestURI.contains(req.getContextPath())
                && !requestURI.contains("/404.html")
                && !requestURI.contains("/login.html")
                && !requestURI.contains("/images")
                && !requestURI.contains("/lib")
                && !requestURI.contains("/js")
                && !requestURI.contains("/css")
                && !requestURI.contains("/wechat_redirect")
                && !requestURI.contains("/images")
                && !requestURI.contains("/custom-js")
                && !requestURI.contains("/custom-css")
                && !requestURI.contains("/goods")
                && !requestURI.contains("/wechatuser")
                && !requestURI.contains("/registered")
                && !requestURI.contains("/index")
                && !requestURI.contains("/getMallUserInfo")
                && !requestURI.contains("/login")
                && !requestURI.contains("/MallUserRegistered")
                && !requestURI.contains("/deleteCode")
                && !requestURI.contains("/pro_info")
                && !requestURI.contains("/usr/local/upload/")
                && !requestURI.contains("/indexImg")
                && !requestURI.contains("/regist.html")
                && !requestURI.contains("/psd_chage.html")
                && !requestURI.contains("/ResetPassword")
                && !requestURI.contains("/ChangePassword")
                && !requestURI.contains("/RegistLogin")
                && !requestURI.contains("/wechat/pay")
//                && !requestURI.contains("/test")



//                && !requestURI.contains("/print")
                ) {
            //判断cookies中是否有用户信息，如果没有则重定向到登录页面
            String MID = "" ;
            Cookie[] cookies = req.getCookies();
            HttpSession session = req.getSession();
            if (cookies == null ){
                res.sendRedirect( req.getContextPath()+"/login.html");
                return;
            }
            else {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("MID")) {
                        MID = cookie.getValue();
                    }
                }
            }

//            System.out.println("MID:"+MID);
            if ( MID == null || MID.equals("")) {
                res.sendRedirect( req.getContextPath()+"/login.html");
                return;
            }else {
                if (session.getAttribute("MallUserInfo") == null) {
                    System.out.println(req.getContextPath());
                    res.sendRedirect( req.getContextPath()+"/getMallUserInfo");
                }
            }


        }

        chain.doFilter(request, response);

    }

    public void destroy() {

    }




}
