package com.nchhr.mall.EntityVo;

import com.nchhr.mall.Entity.OCEntity;
import com.nchhr.mall.Entity.OrderEntity;

import java.util.List;

public class OrderCommodityVo {
    OrderEntity orderEntity;
    List<OCEntity> commodities;

    public OrderCommodityVo() {
    }

    public OrderCommodityVo(OrderEntity orderEntity, List<OCEntity> commodities) {
        this.orderEntity = orderEntity;
        this.commodities = commodities;
    }

    @Override
    public String toString() {
        return "OrderCommodityVo{" +
                "orderEntity=" + orderEntity +
                ", commodities=" + commodities +
                '}';
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public List<OCEntity> getCommodities() {
        return commodities;
    }

    public void setCommodities(List<OCEntity> commodities) {
        this.commodities = commodities;
    }
}
