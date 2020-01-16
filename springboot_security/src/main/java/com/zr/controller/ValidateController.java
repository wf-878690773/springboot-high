package com.zr.controller;

import com.zr.domain.ImageCode;
import com.zr.utils.ImageCodeUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 用于处理生成验证码请求
 */
/*@RestController*/
public class ValidateController {

    public final static String SESSION_KEY_IMAGE_CODE = "SESSION_KEY_IMAGE_CODE";

    //HttpSessionSessionStrategy对象封装了一些处理Session的方法
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ImageCode imageCode = ImageCodeUtils.createImageCode();

        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_IMAGE_CODE, imageCode);

        ImageIO.write(imageCode.getImage(), "jpeg", response.getOutputStream());
    }




}