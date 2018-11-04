package com.nchhr.mall.Service;

import com.nchhr.mall.Dao.CommodityDao;
import com.nchhr.mall.Dao.YearDao;
import com.nchhr.mall.Entity.CommodityEntity;
import com.nchhr.mall.EntityVo.CommodityVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommodityService {
    @Resource
    private CommodityDao commodityDao;

    @Resource
    private YearDao yearDao;

    public List<CommodityVo> findAllCommodity(){
        List<CommodityVo> commodityVos=new ArrayList<>();
        List<CommodityEntity> commodities = commodityDao.findAll();

        for (CommodityEntity commodity:commodities) {
            commodityVos.add(new CommodityVo(commodity,yearDao.selectByYearid(commodity.getY_id())));
        }
        return  commodityVos;
    }

    public CommodityEntity findCommodityById(String C_id){
        return commodityDao.findById(C_id);
    }

    public CommodityVo findCommodityVoById(String C_id){
        CommodityEntity byId = commodityDao.findById(C_id);
        return new CommodityVo(byId,yearDao.selectByYearid(byId.getY_id()));
    }

    public void updateStock(CommodityEntity commodityEntity){
        commodityDao.updateStock(commodityEntity);
    }

    public int buyCommodity(String C_id,String Stock){
        CommodityEntity commodityEntity=commodityDao.findById(C_id);

        Integer stock=((Integer.parseInt(commodityEntity.getStock()))-(Integer.parseInt(Stock)));
        commodityEntity.setStock(stock.toString());
        updateStock(commodityEntity);
        return Integer.parseInt(Stock);
    }

    public List<CommodityEntity> findCommodityByYid(String Y_id){
        return commodityDao.findByYid(Y_id);
    }

}
