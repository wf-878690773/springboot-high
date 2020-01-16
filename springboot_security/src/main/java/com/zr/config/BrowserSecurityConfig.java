package com.zr.config;

import com.zr.Handler.MyAuthenticationFailureHandler;
import com.zr.Handler.MyAuthenticationSuccessHandler;
import com.zr.Handler.MySessionExpiredStrategy;
import com.zr.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义成功请求
     */
    @Autowired
    private MyAuthenticationSuccessHandler successHandler;

    /**
     * 自定义失败请求
     */
    @Autowired
    private MyAuthenticationFailureHandler failureHandler;
    /**
     *  注入了PasswordEncoder对象，该对象用于密码加密，注入前需要手动配置。
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        //PasswordEncoder是一个密码加密接口，而BCryptPasswordEncoder是Spring Security提供的一个实现方法，我们也可以自己实现PasswordEncoder。
        return new BCryptPasswordEncoder();
    }

    /**
     * 验证码校验
     */
 /*   @Autowired*/
    private ValidateCodeFilter validateCodeFilter;

    /**
     *
     */
    @Autowired
    private MySessionExpiredStrategy sessionExpiredStrategy;


    /**
     * Spring Security跳转到我们自己定义的登录页面呢
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

       /* http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class); */// 添加验证码校验过滤器

        http.formLogin() // 表单登录
                // http.httpBasic() // HTTP Basic
                //在未登录的情况下，当用户访问html资源的时候跳转到登录页，否则返回JSON格式数据，状态码为401。
                //要实现这个功能我们将loginPage的URL改为/authentication/require，并且在antMatchers方法中加入该URL，让其免拦截:
                .loginPage("/authentication/require") // 登录跳转 URL
                .loginProcessingUrl("/login") // 处理表单登录 URL
                .successHandler(successHandler) // 处理登录成功
                .failureHandler(failureHandler) // 处理登录失败
                .and()
                .authorizeRequests() // 授权配置
                .antMatchers("/authentication/require", "/login.html","/code/image").permitAll() // 登录跳转 URL 无需认证
                .anyRequest()  // 所有请求
                .authenticated() // 都需要认证
               /* .and().csrf().disable();*/
                /*.and().sessionManagement().invalidSessionUrl("/session/invalid");*/
                .and().sessionManagement()
                .invalidSessionUrl("/session/invalid")
                .maximumSessions(1)
                .expiredSessionStrategy(sessionExpiredStrategy);
    }

}
