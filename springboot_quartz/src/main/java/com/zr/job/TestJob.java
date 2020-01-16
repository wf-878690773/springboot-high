package com.zr.job;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class TestJob {

    @Scheduled(cron = "0/2 * * * * ?")
    protected void job() {

        System.out.println("我开始干活啦"+new Date(System.currentTimeMillis()));
    }

}
