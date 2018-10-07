package com.nchhr.mall.Entity;

public class OrderCommodityEntity {

    String O_id;
    String C_id;
    int number;

    @Override
    public String toString() {
        return "OrderCommodityEntity{" +
                "O_id='" + O_id + '\'' +
                ", C_id='" + C_id + '\'' +
                ", number=" + number +
                '}';
    }

    public String getO_id() {
        return O_id;
    }

    public void setO_id(String o_id) {
        O_id = o_id;
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

    public OrderCommodityEntity() {
    }

    public OrderCommodityEntity(String o_id, String c_id, int number) {
        O_id = o_id;
        C_id = c_id;
        this.number = number;
    }
}
