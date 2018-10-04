package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.ShopCartEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ShopCartDao {

    @Select("select * from shoppingcart_commodity where SC_id=#{SC_id} and C_id = #{C_id}")
    ShopCartEntity findBySCidAndCid(@Param("SC_id") String SC_id, @Param("C_id") String C_id);

    @Select("select * from shoppingcart_commodity where SC_id =#{SC_id}")
    List<ShopCartEntity> findBySCID(@Param("SC_id")String SC_id);

    @Insert("insert into shoppingcart_commodity values(#{SC_id},#{C_id},#{number},#{add_time})")
    void save(ShopCartEntity shopCartEntity);

    @Update("update shoppingcart_commodity set number=#{number} , add_time=#{add_time} where SC_id=#{SC_id} and C_id = #{C_id}")
    void update(ShopCartEntity shopCartEntity);
}
