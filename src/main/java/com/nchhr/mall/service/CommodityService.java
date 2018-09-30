package com.nchhr.mall.service;

import com.nchhr.mall.dao.CommodityDao;
import com.nchhr.mall.entity.CommodityEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommodityService {
    @Resource
    private CommodityDao commodityDao;

    public List<CommodityEntity> findAllCommodity(){
        return  commodityDao.findAll();
    }

    public CommodityEntity findCommodityById(String C_id){
        return commodityDao.findById(C_id);
    }

    public void updateStock(CommodityEntity commodityEntity){
        commodityDao.updateStock(commodityEntity);
    }

    public List<CommodityEntity> findCommodityByYid(String Y_id){
        return commodityDao.findByYid(Y_id);
    }

}
