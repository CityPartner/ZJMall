package com.nchhr.mall.Entity;


public class OrderEntity{

    private String O_id;            //订单编号
    private String Re_id;           //收货人id
    private String M_id;            //商城用户id（发货人）
    private double price;           //总价
    private double original_price; //原价
    private String order_time;      //下单时间
    private String self_lifting;    //是否自提
    private String status;          //订单状态      0-未支付  1-已支付  2-待发货  3-已发货  4-已完成  5-订单已取消  6-补录 7-删除
    private String OFid;           //优惠券id

    public String getO_id() {
        return O_id;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public OrderEntity(String o_id, String re_id, String m_id, double price, double original_price, String order_time, String self_lifting, String status, String OFid) {
        O_id = o_id;
        Re_id = re_id;
        M_id = m_id;
        this.price = price;
        this.original_price = original_price;
        this.order_time = order_time;
        this.self_lifting = self_lifting;
        this.status = status;
        this.OFid = OFid;
    }

    public void setO_id(String o_id) {
        O_id = o_id;
    }

    public String getRe_id() {
        return Re_id;
    }

    public void setRe_id(String re_id) {
        Re_id = re_id;
    }

    public String getM_id() {
        return M_id;
    }

    public void setM_id(String m_id) {
        M_id = m_id;
    }

    public String getOFid() {
        return OFid;
    }

    public void setOFid(String OFid) {
        this.OFid = OFid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getSelf_lifting() {
        return self_lifting;
    }

    public void setSelf_lifting(String self_lifting) {
        this.self_lifting = self_lifting;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderEntity() {
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "O_id='" + O_id + '\'' +
                ", Re_id='" + Re_id + '\'' +
                ", M_id='" + M_id + '\'' +
                ", price=" + price +
                ", original_price=" + original_price +
                ", order_time='" + order_time + '\'' +
                ", self_lifting='" + self_lifting + '\'' +
                ", status='" + status + '\'' +
                ", OFid='" + OFid + '\'' +
                '}';
    }
}
