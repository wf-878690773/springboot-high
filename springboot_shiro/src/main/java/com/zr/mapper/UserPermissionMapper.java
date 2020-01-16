package com.zr.mapper;

import com.zr.domain.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface UserPermissionMapper {

    @Select(" select p.id,p.url,p.name from t_role r " +
            " left join t_user_role ur on(r.id = ur.rid)  " +
            " left join t_user u on(u.id = ur.user_id) " +
            " left join t_role_permission rp on(rp.rid = r.id)  " +
            " left join t_permission p on(p.id = rp.pid ) " +
            " where u.username = #{userName}")
    List<Permission> findByUserName(String userName);


}
