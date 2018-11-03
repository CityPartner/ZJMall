package com.nchhr.mall.Service;

import com.nchhr.mall.Dao.WalletDao;
import com.nchhr.mall.Entity.ProjectWallet;
import com.nchhr.mall.Entity.ProjectWalletIncome;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class WalletService {

    @Resource
    private WalletDao walletDao;

    /**
     * 插入一个收入信息
     * @param projectWalletIncome
     * @return
     * HWG
     */
    public boolean insertIntoPWI(ProjectWalletIncome projectWalletIncome){
        try{
            walletDao.insertIntoPWI(projectWalletIncome);
        }catch (Exception e32){
            System.out.println(e32.getMessage());
            return false;
        }
        return true;
    }


    /**
     * 更新钱包余额
     * @param user_id
     * @param project_id
     * @param wallet_amount
     * @return
     * HWG
     */
    public boolean updateWallet(String user_id,String project_id,double wallet_amount)
    {
        ProjectWallet wallet = walletDao.getPWbyUidAndPid(user_id, project_id);
        double oldAmount = Double.parseDouble(wallet.getWalletAmount());
        double newAmount=oldAmount+wallet_amount;
        try {
            walletDao.updateWallet(String.valueOf(newAmount),wallet.getWalletId());
        }catch (Exception e321){
            return  false;
        }
        return true;
    }
}
