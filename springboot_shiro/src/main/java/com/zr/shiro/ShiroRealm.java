package com.zr.shiro;

import com.zr.domain.Permission;
import com.zr.domain.Role;
import com.zr.domain.User;
import com.zr.mapper.UserMapper;
import com.zr.mapper.UserPermissionMapper;
import com.zr.mapper.UserRoleMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * realm：域，领域，相当于数据源，通过realm存取认证、授权相关数据。
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserPermissionMapper userPermissionMapper;

    /**
     * 获取用户角色和权限(
     * )
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userName = user.getUserName();

        System.out.println("用户" + userName + "获取权限-----ShiroRealm.doGetAuthorizationInfo");

        //管理用户角色和权限的对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();


        // 获取用户角色集
        List<Role> roleList = userRoleMapper.findByUserName(userName);

        Set<String> roleSet = new HashSet<String>();
        for (Role r : roleList) {
            roleSet.add(r.getName());
        }
        //将用户角色集保存到SimpleAuthorizationInfo对象
        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
        List<Permission> permissionList = userPermissionMapper.findByUserName(userName);

        Set<String> permissionSet = new HashSet<String>();
        for (Permission p : permissionList) {
            permissionSet.add(p.getName());
        }
        //将用户权限集保存到SimpleAuthorizationInfo对象
        simpleAuthorizationInfo.setStringPermissions(permissionSet);

        return simpleAuthorizationInfo;

    }

    /**
     * 登录认证(用户认证)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        // 获取用户输入的用户名和密码
        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        System.out.println("用户" + userName + "认证-----ShiroRealm.doGetAuthenticationInfo"+"密码"+password);

        // 通过用户名到数据库查询用户信息
        User user = userMapper.findByUserName(userName);

        if (user == null) {

            //UnknownAccountException等异常为Shiro自带异常,并捕捉任何你希望的异常，并作出相应的反应
            throw new UnknownAccountException("用户名或密码错误！");
        }
        if (!password.equals(user.getPassword())) {

            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if (user.getStatus().equals("0")) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());

        return info;
    }
}
