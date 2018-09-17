package com.mih;

import mih.testInterface;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
@Component(immediate = true)
public class Job1 implements Job {
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected testInterface test;
    @Activate
    public void activate() {
        test.sayHello();
        System.out.println("Job1 started!");
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("job1");
    }
}
