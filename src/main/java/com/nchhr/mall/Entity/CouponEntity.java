package com.nchhr.mall.Entity;


import java.sql.Timestamp;

//优惠券实体（多表）
public class CouponEntity {

    //优惠券具体信息
    private String OFid;
    private String offe_user;
    private String Receiver;
    private Timestamp pickTime;
    private String state;
    private Timestamp useTime;
    //优惠券类型信息
    private String amount;
    private String discount;
    private String condition_use;
    private String type;

    public CouponEntity() {
    }

    public String getOFid() {
        return OFid;
    }

    public void setOFid(String OFid) {
        this.OFid = OFid;
    }

    public String getOffe_user() {
        return offe_user;
    }

    public void setOffe_user(String offe_user) {
        this.offe_user = offe_user;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public Timestamp getPickTime() {
        return pickTime;
    }

    public void setPickTime(Timestamp pickTime) {
        this.pickTime = pickTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getUseTime() {
        return useTime;
    }

    public void setUseTime(Timestamp useTime) {
        this.useTime = useTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCondition_use() {
        return condition_use;
    }

    public void setCondition_use(String condition_use) {
        this.condition_use = condition_use;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CouponEntity{" +
                "OFid='" + OFid + '\'' +
                ", offe_user='" + offe_user + '\'' +
                ", Receiver='" + Receiver + '\'' +
                ", pickTime=" + pickTime +
                ", state='" + state + '\'' +
                ", useTime=" + useTime +
                ", amount='" + amount + '\'' +
                ", discount='" + discount + '\'' +
                ", condition_use='" + condition_use + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
