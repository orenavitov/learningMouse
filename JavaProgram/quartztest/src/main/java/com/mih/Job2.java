package com.mih;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Job2 implements Job{
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("job2");
    }
}
