package com.nchhr.mall.Service;

import com.nchhr.mall.Dao.CommodityDao;
import com.nchhr.mall.Entity.CommodityEntity;
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
