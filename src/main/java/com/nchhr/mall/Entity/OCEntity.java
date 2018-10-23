package com.nchhr.mall.Entity;

public class OCEntity {
    private String C_id;
    private String name;//商品名
    private String image;
    private float price;//售价
    private int number;


    @Override
    public String toString() {
        return "OCEntity{" +
                "C_id='" + C_id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", number=" + number +
                '}';
    }

    public String getC_id() {
        return C_id;
    }

    public void setC_id(String c_id) {
        C_id = c_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public OCEntity() {
    }
}
