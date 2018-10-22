package com.nchhr.mall.Filter;

import org.springframework.stereotype.Component;

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
        String requestURI = req.getServletPath();
        System.out.println("requestURI=" + requestURI);
////        boolean ss = requestURI.contains("**/index**");
//        System.out.println("80:"+requestURI);
        //访问除login.jsp（登录页面）和验证码servlet之外的jsp/servlet都要进行验证
        if (    requestURI.contains(req.getContextPath())
                && requestURI.contains("/shopCart")
                && requestURI.contains("/mine")
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
//                && !requestURI.contains("/save")
                && !requestURI.contains("/index")
                && !requestURI.contains("/MP_verify_6NRD3VognOOIx0WG.txt")
//                && !requestURI.contains("/print")
                ) {
            //判断cookies中是否有用户信息，如果没有则重定向到登录页面
            String MID = "" ;
            Cookie[] cookies = req.getCookies();
            HttpSession session = req.getSession();
            if (cookies == null && session.getAttribute("MallUserInfo") == null ){
                res.sendRedirect( req.getContextPath()+"/login.html");
                return;
            }
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("MID")) {
                        MID = cookie.getValue();
                    }
                }
            }

            System.out.println("MID:"+MID);
            if ( MID == null || MID.equals("") && session.getAttribute("MallUserInfo") == null) {
                res.sendRedirect( req.getContextPath()+"/login.html");
                return;
            }


        }

        chain.doFilter(request, response);

    }

    public void destroy() {

    }




}
