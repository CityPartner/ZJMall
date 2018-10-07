package com.nchhr.mall.Dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface OrdersDao {


    @Insert("insert into orders(O_id,Re_id,M_id,price,order_time,self_lifting,status,OFid)" +
            " values(#{O_id},#{Re_id},#{M_id},#{price},#{order_time},#{self_lifting},#{status},#{OFid})")
    boolean insertOrder(@Param("O_id") String O_id,@Param("Re_id") String Re_id,@Param("M_id") String M_id,
                        @Param("price") double price, @Param("order_time")String  order_time,
                        @Param("self_lifting") String self_lifting,@Param("status") String status,@Param("OFid") String OFid);
}
