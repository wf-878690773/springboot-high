package com.zr.controller;

import com.zr.domain.MyUser;
import com.zr.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 控制器BrowserSecurityController
 */
@RestController
public class BrowserSecurityController {


    //Spring Security提供的用于缓存请求的对象
    private RequestCache requestCache = new HttpSessionRequestCache();

    //Spring Security提供的用于处理重定向的方法。
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();



    @GetMapping("/authentication/require")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)   //HTTP状态码为401（HttpStatus.UNAUTHORIZED）。
    public String requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //getRequest方法可以获取到本次请求的HTTP信息
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        System.out.println(savedRequest);

        if (savedRequest != null) {

            String targetUrl = savedRequest.getRedirectUrl();

            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html"))
                //sendRedirect为Spring Security提供的用于处理重定向的方法。
                redirectStrategy.sendRedirect(request, response, "/login.html");
        }
        return "访问的资源需要身份认证！";
    }




    @GetMapping("hello")
    public String hello() {
        return "hello spring security";
    }


    /**
     * 定义义一个处理该成功登陆请求的方法
     * @return
     */
    @GetMapping("index")
    public Object index(){

        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Session失效后跳转到/session/invalid，并且将这个URL添加到了免认证路径
     * @return
     */
    @GetMapping("session/invalid")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String sessionInvalid(){
        return "session已失效，请重新认证";
    }


}
