package com.nchhr.mall.entity;

import java.sql.Date;

public class ShopCartEntity {
    private String SC_id;
    private  String C_id;
    private int number;
    private Date add_time;

    public ShopCartEntity() {
    }

    @Override
    public String toString() {
        return "ShopCartEntity{" +
                "SC_id='" + SC_id + '\'' +
                ", C_id='" + C_id + '\'' +
                ", number=" + number +
                ", add_time=" + add_time +
                '}';
    }

    public String getSC_id() {
        return SC_id;
    }

    public void setSC_id(String SC_id) {
        this.SC_id = SC_id;
    }

    public String getC_id() {
        return C_id;
    }

    public void setC_id(String c_id) {
        C_id = c_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }
}
