package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.ProjectWallet;
import com.nchhr.mall.Entity.ProjectWalletIncome;
import org.apache.ibatis.annotations.*;

public interface WalletDao {

    /**
     * 插入一条收入记录
     * @param projectWalletIncome
     * HWG
     */
    @Insert("insert into project_wallet_income(" +
            "income_id,user_id,project_id,income_amount,income_time,income_type,attach_info) " +
            " values(" +
            "#{incomeId},#{userId},#{projectId},#{incomeAmount}," +
            "#{incomeTime},#{incomeType},#{attachInfo})")
    void insertIntoPWI(ProjectWalletIncome projectWalletIncome);

    /**
     * 通过userid和projectid获取余额
     * @param user_id
     * @param project_id
     * @return
     * HWG
     */
    @Select("select * from project_wallet where user_id=#{user_id} and project_id=#{project_id}")
    @Results({
            @Result(property = "walletId",column = "wallet_id"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "projectId",column = "project_id"),
            @Result(property = "walletAmount",column = "wallet_amount"),
    })
    ProjectWallet getPWbyUidAndPid(@Param("user_id")String user_id,@Param("project_id")String project_id);

    /**
     * 通过钱包id更新
     * @param wallet_amount
     * @param wallet_id
     * HWG
     */
    @Update("update project_wallet set wallet_amount=#{wallet_amount} where wallet_id=#{wallet_id}")
    void updateWallet(@Param("wallet_amount")String wallet_amount,@Param("wallet_id")String wallet_id);
}
