package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.AddressEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AddressDao {

    @Select("select * from receiver where M_id=#{M_id} order by acquiescence desc")
    List<AddressEntity> getAllAddressByMid(@Param("M_id") String M_id);

    @Select("select * from receiver where Re_id=#{Re_id}")
    AddressEntity getByReid(@Param("Re_id") String Re_id);

    @Select("select count(*) from receiver where M_id=#{M_id}")
    int getAddressCountByMid(@Param("M_id") String M_id);

    @Select("select * from receiver where M_id=#{M_id} order by acquiescence desc limit 1")
    AddressEntity getDefaultAddress(@Param("M_id") String M_id);

    @Delete("delete from receiver where Re_id =#{Re_id}")
    void deleteAddByReid(@Param("Re_id") String Re_id);

    @Update("update receiver set acquiescence='是' where Re_id=#{Re_id}")
    void setDefaultAdd(@Param("Re_id") String Re_id);

    @Insert("insert into receiver(Re_id,name,phone,acquiescence,province,city,county,address_details,M_id)" +
            " values(#{Re_id},#{name},#{phone},#{acquiescence},#{province},#{city},#{county},#{address_details},#{M_id})")
    void addAddress(AddressEntity addressEntity);

    @Update("update receiver set acquiescence='否' where M_id=#{M_id}")
    void setAllNotDefaust(@Param("M_id") String M_id);

    @Update("update receiver set name=#{name},phone=#{phone},acquiescence=#{acquiescence}," +
            "province=#{province},city=#{city},county=#{county},address_details=#{address_details},M_id=#{M_id}" +
            " where Re_id=#{Re_id}")
    void updateAddress(AddressEntity addressEntity);



}
