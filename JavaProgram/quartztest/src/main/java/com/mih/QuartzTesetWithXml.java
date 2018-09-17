package com.mih;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class QuartzTesetWithXml {
    public static void main(String[] args)
    {
        QuartzTesetWithXml simple = new QuartzTesetWithXml();
        try
        {
            // Create a Scheduler and schedule the Job
            Scheduler scheduler = simple.createScheduler();
            // Jobs can be scheduled after Scheduler is running
            scheduler.start();
        }
        catch (SchedulerException ex) {
        }
    }
    public Scheduler createScheduler() throws SchedulerException
    {//创建调度器
        return StdSchedulerFactory.getDefaultScheduler();
    }
}
