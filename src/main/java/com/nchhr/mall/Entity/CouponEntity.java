package com.nchhr.mall.entity;


import java.sql.Timestamp;

public class CouponEntity {

    private String OFid;
    private String offe_user;
    private String Receiver;
    private String COid;
    private Timestamp pickTime;
    private String state;
    private Timestamp useTime;

    public CouponEntity(String OFid, String offe_user, String receiver, String COid, Timestamp pickTime, String state, Timestamp useTime) {
        this.OFid = OFid;
        this.offe_user = offe_user;
        Receiver = receiver;
        this.COid = COid;
        this.pickTime = pickTime;
        this.state = state;
        this.useTime = useTime;
    }

    public CouponEntity(String OFid, String offe_user, String COid) {
        this.OFid = OFid;
        this.offe_user = offe_user;
        this.COid = COid;
    }

    public CouponEntity(String OFid, String receiver, Timestamp pickTime, String state) {
        this.OFid = OFid;
        Receiver = receiver;
        this.pickTime = pickTime;
        this.state = state;
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

    public String getCOid() {
        return COid;
    }

    public void setCOid(String COid) {
        this.COid = COid;
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

    @Override
    public String toString() {
        return "Coupon{" +
                "OFid='" + OFid + '\'' +
                ", offe_user='" + offe_user + '\'' +
                ", Receiver='" + Receiver + '\'' +
                ", COid='" + COid + '\'' +
                ", pickTime=" + pickTime +
                ", state='" + state + '\'' +
                ", useTime=" + useTime +
                '}';
    }
}
