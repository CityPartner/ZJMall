package com.nchhr.mall.Entity;

public class MallUserEntity {
    private String M_id;
    private String R_id;
    private String SC_id;
    private String phone;
    private String nickname;
    private String ID_card;
    private String name;
    private String WX_id; // 登录标识
    private String paaword;

    public String getM_id() {
        return M_id;
    }

    public void setM_id(String m_id) {
        M_id = m_id;
    }

    public String getR_id() {
        return R_id;
    }

    public void setR_id(String r_id) {
        R_id = r_id;
    }

    public String getSC_id() {
        return SC_id;
    }

    public void setSC_id(String SC_id) {
        this.SC_id = SC_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getID_card() {
        return ID_card;
    }

    public void setID_card(String ID_card) {
        this.ID_card = ID_card;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWX_id() {
        return WX_id;
    }

    public void setWX_id(String WX_id) {
        this.WX_id = WX_id;
    }

    public String getPaaword() {
        return paaword;
    }

    public void setPaaword(String paaword) {
        this.paaword = paaword;
    }

    @Override
    public String toString() {
        return "MallUser{" +
                "M_id='" + M_id + '\'' +
                ", R_id='" + R_id + '\'' +
                ", SC_id='" + SC_id + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", ID_card='" + ID_card + '\'' +
                ", name='" + name + '\'' +
                ", WX_id='" + WX_id + '\'' +
                ", paaword='" + paaword + '\'' +
                '}';
    }
}
