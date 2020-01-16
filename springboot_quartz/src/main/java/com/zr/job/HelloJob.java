package com.zr.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/*@Component*/
public class HelloJob implements Job {

    private String name;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap jdm = context.getJobDetail().getJobDataMap();

        name = jdm.getString("name");

        System.out.println("hello: " + name);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
