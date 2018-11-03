package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.IncomeEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IncomeDao {

    /**
     * 从income中获取数据
     * @return
     */
    @Select("SELECT * FROM income ORDER BY time DESC")
    List<IncomeEntity> loadList();

    /**
     * 插入一条数据
     * HWG
     */

    @Insert("insert into income(In_id,M_id,time,income_amount,unallocated_amount) values" +
            "(#{In_id},#{M_id},#{time},#{income_amount},#{unallocated_amount})")
    void insertIntoIncome(IncomeEntity incomeEntity);

}
