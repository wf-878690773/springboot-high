package com.zr.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功逻辑
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {



    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {


        /**
         *       response.setContentType("application/json;charset=utf-8");
         *       response.getWriter().write(mapper.writeValueAsString(authentication));
         *       Authentication参数既包含了认证请求的一些信息，比如IP，请求的SessionId等，
         *       也包含了用户信息，即前面提到的User对象。通过上面这个配置，用户登录成功后页面将打印出Authentication对象的信息。
         */
        redirectStrategy.sendRedirect(request, response, "/index");

    }
}
