package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrdersDao {

    /*
     *插入订单
     * XY
     */
    @Insert("insert into orders(O_id,Re_id,M_id,price,original_price,order_time,self_lifting,status,OFid)" +
            " values(#{O_id},#{Re_id},#{M_id},#{price},#{original_price},#{order_time},#{self_lifting},#{status},#{OFid})")
    boolean insertOrder(@Param("O_id") String O_id,@Param("Re_id") String Re_id,@Param("M_id") String M_id,
                        @Param("price") double price,@Param("original_price")Double original_price, @Param("order_time")String  order_time,
                        @Param("self_lifting") String self_lifting,@Param("status") String status,@Param("OFid") String OFid);


    /*
     *查看一个用户的所有订单通过status
     * HWG
     */
    @Select("select * from orders where M_id=#{M_id} and status=#{status} order by order_time desc")
    List<OrderEntity> getOrderByMid(@Param("M_id")String M_id, @Param("status")String status);

    /*
     *通过O_id 获取订单
     * HWG
     */
    @Select("select * from orders where O_id=#{O_id}")
    OrderEntity getOrderById(@Param("O_id")String O_id);

    @Update("update orders set status=#{status} where O_id = #{o_id}")
    void setOrderStatusByOid(@Param("o_id")String  o_id,@Param("status")String status);
}
