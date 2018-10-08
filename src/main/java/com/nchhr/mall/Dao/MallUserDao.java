package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.MallUserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface MallUserDao {


    @Select("select * from mall_user where phone = #{0}")
    MallUserEntity loadByID(@Param("0") String phone);

    @Select("select * from mall_user where M_id = #{0}")
    MallUserEntity loadByMid(@Param("0") String mid);

    //插入用户
    @Insert("insert into mall_user(M_id,R_id,phone,password,nickname,addTime) values(#{0},'3',#{1},#{2},'商城用户',#{3})")
    boolean RegistLogin( @Param("0") String mid, @Param("1") String userPhone, @Param("2") String pwd,@Param("3") String addtime);

    //插入购物车
    @Insert("insert into shopping_cart values(#{0})")
    boolean addShop(@Param("0") String sid);

    //在用户里面添加购物车
    @Update("update mall_user set SC_id = #{1} where M_id = #{0}")
    boolean updateSCat(@Param("0") String mid, @Param("1") String sid);

    //更新用户密码
    @Update("update mall_user set password = #{1} where M_id = #{0} ")
    boolean updatePwd(@Param("0") String m_id, @Param("1") String newpwd);
}
