package com.zr.mapper;

import com.zr.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    //映射注解
    @Results({
            @Result(column="id" ,property="id" ),
            @Result(column="username", property="userName" ),
            @Result(column="password" ,property="password"),
            @Result(column="create_time" ,property="createTime"),
            @Result(column="status" ,property="status"),
    })
    @Select("select * from t_user where username = #{userName}")
    User findByUserName(String username);
}
