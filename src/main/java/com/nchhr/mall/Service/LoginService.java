package com.nchhr.mall.Service;


import com.nchhr.mall.Dao.MallUserDao;
import com.nchhr.mall.Dao.MenuDao;
import com.nchhr.mall.Dao.RoleDao;
import com.nchhr.mall.Entity.MallUserEntity;
import com.nchhr.mall.Entity.MenuEntity;
import com.nchhr.mall.Entity.RoleEntity;
import com.nchhr.mall.EntityVo.MenuVo;
import com.nchhr.mall.EntityVo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {

    @Resource
    MallUserDao mallUserDao;
    @Resource
    MenuDao menuDao;
    @Resource
    RoleDao roleDao;

   @Autowired
   CookiesService cookiesService;

    public String login(String phone, String pwd, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
        MallUserEntity mallUserEntity = mallUserDao.loadByID(phone);
        System.out.println("现在denglu ::"+mallUserEntity);
        System.out.println(pwd + "pwd");
        if (mallUserEntity == null) {
            //该用户不存在
            return "2";
        }
        if (mallUserEntity != null) {
            if (pwd.equals(mallUserEntity.getPassword())) {

                UserVo uvo = new UserVo();
                uvo.setUser(mallUserEntity);
                //核对密码
                // if (mallUser.get().equals(pwd)) {
                //获取菜单（权限）信息
                List<MenuEntity> menus = menuDao.loadByid(mallUserEntity.getR_id());
                //获取个人权限名
                RoleEntity role = roleDao.loadByRid(mallUserEntity.getR_id());
                uvo.setRole(role);
//            System.out.println("全部菜单："+menus);
                List<MenuVo> menuVos = new ArrayList<>();
                //一级菜单
                List<MenuEntity> FirstMenu = new ArrayList<>();
                for (MenuEntity menu : menus) {
                    if (menu.getMenu_sort().equals("0")) {
                        FirstMenu.add(menu);
                    }
                }

//            System.out.println("FirstMenu" + FirstMenu);

                for (MenuEntity firstMenu : FirstMenu){
                    MenuVo menuVo = new MenuVo();
                    menuVo.setMenu(firstMenu);
                    List<MenuEntity> menuList = new ArrayList<>();
                    for (MenuEntity sortMenu : menus){

                        if (firstMenu.getParents().equals(sortMenu.getParents()) && Integer.parseInt(sortMenu.getMenu_sort()) != 0){
                            menuList.add(sortMenu);
                        }

                    }
                    menuVo.setMenus(menuList);
                    menuVos.add(menuVo);
                }

                uvo.setMenuVos(menuVos);
                cookiesService.clear(response,request);
                System.out.println("login:Mid"+mallUserEntity.getM_id());
                cookiesService.saveCookies(mallUserEntity.getM_id(),response,request);
                session.setAttribute("UserVo",uvo);
                session.setAttribute("MallUserInfo", mallUserEntity);
                //1登录成功
                return "1";
            }
            else {
                //3密码错误
                return "3";
            }

        }else {
            //4系统异常
            return "4";
        }

    }

    public boolean loginByOpenid(String openid , HttpServletResponse response,HttpServletRequest request,HttpSession session) {

        MallUserEntity mallUserEntity=mallUserDao.loadByOpenid(openid);
        if (mallUserEntity != null) {
                cookiesService.saveCookies(mallUserEntity.getM_id(),response,request);
                UserVo uvo = new UserVo();
                uvo.setUser(mallUserEntity);
                //核对密码
                // if (mallUser.get().equals(pwd)) {
                //获取菜单（权限）信息
                List<MenuEntity> menus = menuDao.loadByid(mallUserEntity.getR_id());
                //获取个人权限名
                RoleEntity role = roleDao.loadByRid(mallUserEntity.getR_id());
                uvo.setRole(role);
//            System.out.println("全部菜单："+menus);
                List<MenuVo> menuVos = new ArrayList<>();
                //一级菜单
                List<MenuEntity> FirstMenu = new ArrayList<>();
                for (MenuEntity menu : menus) {
                    if (menu.getMenu_sort().equals("0")) {
                        FirstMenu.add(menu);
                    }
                }

//            System.out.println("FirstMenu" + FirstMenu);

                for (MenuEntity firstMenu : FirstMenu){
                    MenuVo menuVo = new MenuVo();
                    menuVo.setMenu(firstMenu);
                    List<MenuEntity> menuList = new ArrayList<>();
                    for (MenuEntity sortMenu : menus){
                        if (firstMenu.getParents().equals(sortMenu.getParents()) && Integer.parseInt(sortMenu.getMenu_sort()) != 0){
                            menuList.add(sortMenu);
                        }
                    }
                    menuVo.setMenus(menuList);
                    menuVos.add(menuVo);
                }

                uvo.setMenuVos(menuVos);
                cookiesService.saveCookies(mallUserEntity.getM_id(),response,request);
                session.setAttribute("UserVo",uvo);
                session.setAttribute("MallUserInfo", mallUserEntity);
                //1登录成功
                return true;

        }else {
            //4系统异常
            return false;
        }
    }

    public boolean loginOut(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        cookiesService.clear(response,request);
        if (session.getAttribute("MallUserInfo") == null){
            return true;
        }else {
            session.removeAttribute("MallUserInfo");
        }
        return true;
    }
}
