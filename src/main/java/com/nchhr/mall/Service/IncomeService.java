package com.nchhr.mall.Service;

import com.nchhr.mall.Dao.IncomeDao;
import com.nchhr.mall.Entity.IncomeEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IncomeService {
    @Resource
    private IncomeDao incomeDao;

    /**
     * 插入
     * HWG
     */
    public boolean insertIntoIncome(IncomeEntity incomeEntity){
        try {
            incomeDao.insertIntoIncome(incomeEntity);
        }catch (Exception e12){
            return false;
        }
        return true;
    }
}
