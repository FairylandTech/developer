/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2025-08-24 16:45:39 UTC+08:00
 ****************************************************/
package com.fairyland.mapper;

import com.fairyland.entity.MyBatisUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Beau Dean
 */
@Mapper
public interface MyBatisUserMapper {
    @Select("select * from user;")
    public List<MyBatisUser> list();
}
