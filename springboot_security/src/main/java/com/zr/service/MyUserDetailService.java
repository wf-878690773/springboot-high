package com.zr.service;

import com.zr.domain.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 描述用户信息的方法
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /**
         * 模拟数据库信息
         */
        MyUser myUser = new MyUser();
        myUser.setUserName(username);
        myUser.setPassword(passwordEncoder.encode("123"));
        System.out.println("编译后密码"+myUser.getPassword());

        User user = new User(username,myUser.getPassword(),
                true,true,
                true,true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return user ;
    }
}
