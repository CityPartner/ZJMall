package com.nchhr.mall.Entity;

public class TemporaryloginEntity {
    private String phone;
    private String code;
    private String pwd;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "TemporaryloginEntity{" +
                "phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
