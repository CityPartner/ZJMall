package com.nchhr.mall.EntityVo;

import com.nchhr.mall.Entity.MallUserEntity;
import com.nchhr.mall.Entity.RoleEntity;

import java.util.List;

public class UserVo {
    private MallUserEntity user;
    private List<MenuVo> menuVos;
    private RoleEntity role;

    public MallUserEntity getUser() {
        return user;
    }

    public void setUser(MallUserEntity user) {
        this.user = user;
    }

    public List<MenuVo> getMenuVos() {
        return menuVos;
    }

    public void setMenuVos(List<MenuVo> menuVos) {
        this.menuVos = menuVos;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "user=" + user +
                ", menuVos=" + menuVos +
                ", role=" + role +
                '}';
    }
}

