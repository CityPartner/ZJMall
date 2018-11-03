package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.ProjectEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//平台项目相关dao层
public interface ProjectDao {

    @Select("select * from project where project_id=#{project_id}")
    ProjectEntity getProjectByPid(@Param("project_id")String project_id);

}
