package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.OrderCommodityEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface OrderCommodityDao {

    @Insert("insert into order_commodity(O_id,C_id,number) values(#{0_id},#{C_id},#{number})")
    boolean save(OrderCommodityEntity orderCommodityEntity);

    @Insert("insert into order_commodity(O_id,C_id,number) values(#{O_id},#{C_id},#{number})")
    void saveByHand(@Param("O_id")String O_id,@Param("C_id")String C_id,@Param("number")int number);
}
