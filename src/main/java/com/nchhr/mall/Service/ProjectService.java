package com.nchhr.mall.Service;

import com.nchhr.mall.Dao.ProjectDao;
import com.nchhr.mall.Entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


//平台项目相关业务层
@Service
public class ProjectService {

    @Resource
    private ProjectDao projectDao;

    public ProjectEntity getProByPid(String project_id){
        return projectDao.getProjectByPid(project_id);
    }

}
