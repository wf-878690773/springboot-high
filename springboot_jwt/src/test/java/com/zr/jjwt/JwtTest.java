package com.zr.jjwt;

import com.zr.entity.User;
import com.zr.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



public class JwtTest {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void test(){
        User user = new User();
        user.setUsername("小明");
        user.setPassword("123456");
        String token = jwtTokenUtil.generateToken(user);
        System.out.println(token);

    }
    @Test
    public void test1(){
        User user = new User();

        String tokens = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLlsI_mmI4iLCJleHAiOjE1Nzc5NDIwNDZ9.bmy4y5brBh_XkeD3uFjVKqOWPVfEip2MbfyYRmOIhAYNBqZDCCelP8xFJ2YE_vmq3f4UBAxtdaHIhaTO-bBN0w";

        Claims claimsFromToken = jwtTokenUtil.getClaimsFromToken(tokens);

        System.out.println(claimsFromToken);

    }

}

