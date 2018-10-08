package com.nchhr.mall.Dao;

import com.nchhr.mall.Entity.MenuEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuDao {

    /**
     * 通过r_id 找到相对应那中角色的菜单
     * @param rid
     * @return
     */
    @Select("SELECT\n" +
            "menu.M_id,\n" +
            "menu.title,\n" +
            "menu.url,\n" +
            "menu.menu_sort,\n" +
            "menu.visible,\n" +
            "menu.parents,\n" +
            "menu.css\n"+
            "FROM\n" +
            "menu\n" +
            "INNER JOIN Menu_Role ON Menu_Role.M_id = menu.M_id\n" +
            "INNER JOIN role ON Menu_Role.R_id = role.R_id\n" +
            "WHERE Menu_Role.R_id = #{0}" +
            "ORDER BY\n" +
            "menu.parents ASC,\n" +
            "menu.menu_sort ASC ")
    List<MenuEntity> loadByid(@Param("0") String rid);

    //

}
