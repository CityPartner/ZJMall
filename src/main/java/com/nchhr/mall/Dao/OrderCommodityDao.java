package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.OCEntity;
import com.nchhr.mall.Entity.OrderCommodityEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderCommodityDao {

    @Insert("insert into order_commodity(O_id,C_id,number) values(#{0_id},#{C_id},#{number})")
    boolean save(OrderCommodityEntity orderCommodityEntity);

    @Insert("insert into order_commodity(O_id,C_id,number) values(#{O_id},#{C_id},#{number})")
    void saveByHand(@Param("O_id")String O_id,@Param("C_id")String C_id,@Param("number")int number);


    @Select("select c.C_id,c.name,c.price,c.image,o.number from commodity as c join " +
            " (SELECT C_id,number  FROM  order_commodity where O_id=#{Oid} ) " +
            " as o on c.C_id=o.C_id ;")
    List<OCEntity> getOrderCommoditiesByOid(@Param("Oid")String Oid);
}
