package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrdersDao {


    @Insert("insert into orders(O_id,Re_id,M_id,price,order_time,self_lifting,status,OFid)" +
            " values(#{O_id},#{Re_id},#{M_id},#{price},#{order_time},#{self_lifting},#{status},#{OFid})")
    boolean insertOrder(@Param("O_id") String O_id,@Param("Re_id") String Re_id,@Param("M_id") String M_id,
                        @Param("price") double price, @Param("order_time")String  order_time,
                        @Param("self_lifting") String self_lifting,@Param("status") String status,@Param("OFid") String OFid);


    @Select("select * from orders where M_id=#{M_id} and status=#{status}")
    List<OrderEntity> getOrderByMid(@Param("M_id")String M_id, @Param("status")String status);


}
