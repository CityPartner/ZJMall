package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.WeChatUserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("select * from WeChatMallUser where openid=#{openid}")
    WeChatUserEntity getUserByOpenid(@Param("openid")String openid);

}
