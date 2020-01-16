package com.zr.config;

import com.zr.job.HelloJob;
import org.quartz.*;
import org.quartz.spi.MutableTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;


public class QuartzJobConfigure {

    /**
     * 任务的细节，表达一个具体的可执行的调度程序，job在这里执行
     * 使用JobDetailFactoryBean进行配置，自定义任务需要实现Job接口
     * @param helloJob 自定义任务
     * @return
     */
    @Bean("jobDetail")
    public JobDetailFactoryBean jobDetailFactoryBean(HelloJob helloJob) {

        JobDetailFactoryBean bean = new JobDetailFactoryBean();

        bean.setJobClass(helloJob.getClass());

        bean.setDurability(true);

        //额外参数
        JobDataMap jobDataMap = new JobDataMap();


          jobDataMap.put("name","Quartz");


        bean.setJobDataMap(jobDataMap);

        return bean;

    }

    /**
     * 普通的触发器，此处也可用Cron储发器实现，执行任务的规则
     * @param jobDetail
     * @return
     */
    @Bean("simpleTrigger")
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(JobDetailFactoryBean jobDetail) {

        SimpleTriggerFactoryBean bean = new SimpleTriggerFactoryBean();

        bean.setJobDetail(jobDetail.getObject());

        bean.setStartDelay(0);

        bean.setRepeatInterval(2000);


        return bean;

    }
    @Bean("cronTrigger")
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetail) {

        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();

        MutableTrigger build = CronScheduleBuilder.cronSchedule("0/5 * * * * ?").build();

        bean.setJobDetail(jobDetail.getObject());

        bean.setStartDelay(0);



        return bean;

    }



    /**
     * 配置调度容器
     * @param simpleTrigger
     * @return
     */
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactoryBean(Trigger simpleTrigger) {

        SchedulerFactoryBean  bean = new SchedulerFactoryBean();
        //用于Quartz集群，QuartzScheduler启动时会更新已存在的Job
        bean.setOverwriteExistingJobs(true);
        //延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        //注册触发器
        bean.setTriggers(simpleTrigger);

        return bean;

    }

}
