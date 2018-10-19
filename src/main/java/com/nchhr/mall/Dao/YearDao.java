package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.Year;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface YearDao {

    @Select("SELECT * from `year` ORDER BY `year`.year")
    List<Year> loadYear();


    @Insert("INSERT INTO `year` VALUES( #{Y_id}, #{year})")
    boolean addYear(@Param("Y_id") String Y_id, @Param("year") String year);

    @Delete("DELETE FROM `year` WHERE `year`.Y_id = #{Y_id}  ")
    boolean delete(@Param("Y_id") String y_id);

    @Update("UPDATE  `year` SET `year`.year = #{name} WHERE `year`.Y_id = #{Y_id};")
    boolean update(@Param("Y_id") String yid, @Param("name") String name);

    //通过id 得到规格
    @Select("SELECT * FROM `year` WHERE `year`.Y_id = #{0}")
    Year selectByYearid(@Param("0") String yid);
}
