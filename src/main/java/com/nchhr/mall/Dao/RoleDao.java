package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.RoleEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RoleDao {

    @Select("SELECT * FROM role WHERE R_id = #{0}")
    RoleEntity loadByRid(@Param("0") String rid);
}
