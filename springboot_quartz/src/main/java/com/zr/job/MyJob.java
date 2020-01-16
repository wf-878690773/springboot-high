package com.zr.job;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

/*@Component*/
public class MyJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("我开始干活啦"+new Date(System.currentTimeMillis()));
    }

    @Bean
    public JobDetail createMyJobDetail() {
        return JobBuilder.newJob(MyJob.class).withIdentity("myJob").storeDurably().build();
    }
    @Bean
    public Trigger createMyJobTrigger() {
        return TriggerBuilder.newTrigger().forJob(createMyJobDetail()).withIdentity("myJob").
                withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?").
                        withMisfireHandlingInstructionFireAndProceed()).build();
    }

}
