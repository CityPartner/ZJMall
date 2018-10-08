package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.WeChatUserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface WeChatUserDao {


    @Insert("insert into WeChatMallUser values(" +
            "#{openid}," +
            "#{nickname}," +
            "#{sex}," +
            "#{language}," +
            "#{city}," +
            "#{province}," +
            "#{country}," +
            "#{headimgurl}," +
            "#{privilege}," +
            "#{unionid})")
    boolean addWeCharUser( WeChatUserEntity weChatUserEntity);
}
