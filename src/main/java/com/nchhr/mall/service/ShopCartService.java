package com.nchhr.mall.service;

import com.nchhr.mall.dao.ShopCartDao;
import com.nchhr.mall.entity.ShopCartEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShopCartService {
    @Resource
    private ShopCartDao shopCartDao;

    public ShopCartEntity findBySCidAndCid(String SC_id, String C_id){
        return shopCartDao.findBySCidAndCid(SC_id,C_id);
    }

    public List<ShopCartEntity> findBySCid(String SC_id){
        return shopCartDao.findBySCID(SC_id);
    }

    public void save(ShopCartEntity shopCartEntity){
        shopCartDao.save(shopCartEntity);
    }

    public void update(ShopCartEntity shopCartEntity){
        shopCartDao.update(shopCartEntity);
    }
}
