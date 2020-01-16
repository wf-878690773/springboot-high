package com.zr.mapper;

import com.zr.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select( "select id , username , password from user where username = #{username}" )
    User loadUserByUsername(@Param("username") String username);

}