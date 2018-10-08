package com.nchhr.mall.EntityVo;

import com.nchhr.mall.Entity.MenuEntity;

import java.util.List;

public class MenuVo {
    MenuEntity menu;
    List<MenuEntity> menus;

    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }

    public List<MenuEntity> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuEntity> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "MenuVo{" +
                "menu=" + menu +
                ", menus=" + menus +
                '}';
    }
}
