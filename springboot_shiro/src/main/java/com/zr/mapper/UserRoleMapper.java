package com.zr.mapper;

import com.zr.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRoleMapper {


    @Select(" select r.id,r.name,r.memo from t_role r " +
            " left join t_user_role ur on(r.id = ur.rid) " +
            " left join t_user u on(u.id = ur.user_id) " +
            " where u.username = #{userName}")
    List<Role> findByUserName(String userName);
}
