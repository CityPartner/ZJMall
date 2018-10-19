package com.nchhr.mall.EntityVo;


import com.nchhr.mall.Entity.CommodityEntity;
import com.nchhr.mall.Entity.Year;

public class CommodityVo {
    private CommodityEntity commodityEntity;
    private Year year;



    public CommodityVo() {
    }

    @Override
    public String toString() {
        return "CommodityVo{" +
                "commodityEntity=" + commodityEntity.toString() +
                ", year=" + year.toString() +
                '}';
    }

    public CommodityEntity getCommodityEntity() {
        return commodityEntity;
    }

    public void setCommodityEntity(CommodityEntity commodityEntity) {
        this.commodityEntity = commodityEntity;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public CommodityVo(CommodityEntity commodityEntity, Year year) {
        this.commodityEntity = commodityEntity;
        this.year = year;
    }
}
