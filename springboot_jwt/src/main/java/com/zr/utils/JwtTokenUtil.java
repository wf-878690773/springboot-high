package com.zr.utils;

import com.zr.entity.User;
import io.jsonwebtoken.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * JWT 工具类
 * <p>
 * jwt含有三部分：头部（header）、载荷（payload）、签证（signature）
 * (1)头部一般有两部分信息：声明类型、声明加密的算法（通常使用HMAC SHA256）
 * (2)载荷该部分一般存放一些有效的信息。jwt的标准定义包含五个字段：
 * - iss：该JWT的签发者
 * - sub: 该JWT所面向的用户
 * - aud: 接收该JWT的一方
 * - exp(expires): 什么时候过期，这里是一个Unix时间戳
 * - iat(issued at): 在什么时候签发的
 * (3)签证（signature） JWT最后一个部分。该部分是使用了HS256加密后的数据；包含三个部分
 *
 * @author kou
 */

@Component
public class JwtTokenUtil implements Serializable {
    private static final String CLAIM_KEY_USERNAME = "sub";
    /**
     * 5天(毫秒)
     */
    private static final long EXPIRATION_TIME = 60000;
    /**
     * JWT密码
     */
    private static final String SECRET = "secret";
    /**
     * 签发JWT
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put( CLAIM_KEY_USERNAME, userDetails.getUsername() );
        return Jwts.builder()
                .setClaims( claims )
                .setExpiration( new Date( Instant.now().toEpochMilli() + EXPIRATION_TIME ) )
                .signWith( SignatureAlgorithm.HS512, SECRET )
                .compact();
    }
    /**
     * 验证JWT
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        String username = getUsernameFromToken( token );
        return (username.equals( user.getUsername() ) && !isTokenExpired( token ));
    }
    /**
     * 获取token是否过期
     */
    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken( token );
        return expiration.before( new Date() );
    }
    /**
     * 根据token获取username
     */
    public String getUsernameFromToken(String token) {
        String username = getClaimsFromToken( token ).getSubject();
        return username;
    }
    /**
     * 获取token的过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration = getClaimsFromToken( token ).getExpiration();
        return expiration;
    }
    /**
     * 解析JWT
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey( SECRET )
                .parseClaimsJws( token )
                .getBody();
        return claims;
    }




}
