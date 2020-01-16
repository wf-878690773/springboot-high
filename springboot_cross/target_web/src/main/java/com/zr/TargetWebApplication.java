package com.zr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 跨域接收者
 */
@SpringBootApplication
public class TargetWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TargetWebApplication.class, args);
    }
}