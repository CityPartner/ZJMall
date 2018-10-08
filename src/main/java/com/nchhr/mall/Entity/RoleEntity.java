package com.nchhr.mall.Entity;

public class RoleEntity {

    private String R_id;
    private String name;

    public String getR_id() {
        return R_id;
    }

    public void setR_id(String r_id) {
        R_id = r_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "R_id='" + R_id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
