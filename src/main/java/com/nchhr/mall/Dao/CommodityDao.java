package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.CommodityEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CommodityDao {

    @Select("select * from commodity")
    List<CommodityEntity> findAll();

    @Select("select * from commodity where C_id=#{C_id}")
    CommodityEntity findById(@Param("C_id") String C_id);

    @Update("update commodity set stock = #{stock} where C_id=${C_id}")
    void updateStock(CommodityEntity commodityEntity);

    @Select("select * from commodity where Y_id=#{Y_id}")
    List<CommodityEntity> findByYid(@Param("Y_id")String Y_id);
}
