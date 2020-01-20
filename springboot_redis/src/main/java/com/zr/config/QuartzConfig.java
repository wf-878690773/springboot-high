package com.zr.config;

import com.zr.entity.LikeTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    private static final String LIKE_TASK_IDENTITY = "likeTaskQuartz";

    /**
     * 任务数据
     * @return
     */
    @Bean
    public JobDetail quartzDetail() throws Exception{
        return JobBuilder.newJob(LikeTask.class).withIdentity(LIKE_TASK_IDENTITY).storeDurably().build();
    }

    /**
     * 触发器
     */
    @Bean
    public Trigger quartzTrigger() throws Exception {
        //任务调度器
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(30)
//                .withIntervalInHours(2)
                .repeatForever();

        return TriggerBuilder.newTrigger().forJob(quartzDetail())
                .withIdentity(LIKE_TASK_IDENTITY)
                .withSchedule(builder)
                .build();
    }
}
