package com.nchhr.mall.Entity;

public class AddressEntity {
    String Re_id;
    String name;
    String phone;
    String acquiescence;
    String province;
    String city;
    String county;
    String address_details;
    String M_id;

    public AddressEntity() {
    }

    public AddressEntity(String re_id, String name, String phone, String acquiescence, String province, String city, String county, String address_details, String m_id) {
        Re_id = re_id;
        this.name = name;
        this.phone = phone;
        this.acquiescence = acquiescence;
        this.province = province;
        this.city = city;
        this.county = county;
        this.address_details = address_details;
        M_id = m_id;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "Re_id='" + Re_id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", acquiescence='" + acquiescence + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", address_details='" + address_details + '\'' +
                ", M_id='" + M_id + '\'' +
                '}';
    }

    public String getRe_id() {
        return Re_id;
    }

    public void setRe_id(String re_id) {
        Re_id = re_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAcquiescence() {
        return acquiescence;
    }

    public void setAcquiescence(String acquiescence) {
        this.acquiescence = acquiescence;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress_details() {
        return address_details;
    }

    public void setAddress_details(String address_details) {
        this.address_details = address_details;
    }

    public String getM_id() {
        return M_id;
    }

    public void setM_id(String m_id) {
        M_id = m_id;
    }
}
