package com.zr.controller;

import com.zr.domain.ResponseBo;
import com.zr.domain.User;
import com.zr.utils.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }



    /**
     *    2. subject.login(token)提交认证
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseBo login(String username, String password,Boolean rememberMe) {
        // 1.用MD5对密码进行加密
        password = MD5Utils.encrypt(username, password);

        System.out.println("加密的密码"+password);
        //2.将加密后的数据封装在UsernamePasswordToken对象中
        UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);

        // 获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        try {

            //提交认证
            subject.login(token);

            return ResponseBo.ok();

        } catch (UnknownAccountException e) {

            return ResponseBo.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {

            return ResponseBo.error(e.getMessage());
        } catch (LockedAccountException e) {

            return ResponseBo.error(e.getMessage());
        } catch (AuthenticationException e) {

            return ResponseBo.error("认证失败！");
        }
    }

    @RequestMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        // 登录成后，即可通过Subject获取登录的用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/403")
    public String forbid() {
        return "403";
    }
}
