package com.mih;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.util.Date;

public class mihSchedule {

    private static final String TRIGGER_NAME = "mihTrigger";
    private static final int REPEAT_COUNT = 10;
    private static final long REPEAT_INTERVAL = 1000;

    /**
     *
     * @param triggerName 必须存在且唯一
     * @param jobGroupName
     * @param jobName 必须存在且与注册的Job的名字相同
     * @param repeatCount
     * @param repeatInterval
     * @return
     */
    private SimpleTriggerImpl getTrigger(String triggerName, String jobGroupName, String jobName, int repeatCount, long repeatInterval) {
        SimpleTriggerImpl trigger = new SimpleTriggerImpl();
        trigger.setStartTime(new Date());
        trigger.setName(triggerName);
        trigger.setJobGroup(jobGroupName);
        trigger.setJobName(jobName);
        trigger.setRepeatCount(repeatCount);
        trigger.setRepeatInterval(repeatInterval);
        return trigger;
    }

    private JobDetailImpl getJobDetial(String jobId, String jobGroupId, Class jobClass) {
        JobDetailImpl jobDetail = new JobDetailImpl();
        JobKey jobKey = new JobKey(jobId);
        jobDetail.setKey(jobKey);
        jobDetail.setName(jobId);
        jobDetail.setGroup(jobGroupId);
        jobDetail.setJobClass(jobClass);
        return jobDetail;
    }

    public static void main(String args[]) {
        mihSchedule mih = new mihSchedule();
        StdSchedulerFactory factory = new StdSchedulerFactory();

        JobDetailImpl jobDetail_1 = mih.getJobDetial("job_1", "mihJob", Job1.class);
        JobDetailImpl jobDetail_2 = mih.getJobDetial("job_2", "mihJob", Job2.class);
        JobDetailImpl jobDetail_3 = mih.getJobDetial("job_3", "mihJob", Job3.class);
        try {
            Scheduler scheduler = factory.getScheduler();
            if (scheduler != null) {
                scheduler.addJob(jobDetail_1, false, true);
                scheduler.addJob(jobDetail_2, false, true);
                scheduler.addJob(jobDetail_3, false, true);
                /*
                 *每个trigger必须有个唯一的name，并且每个trigger的name不能重复
                 */
                SimpleTrigger trigger_1 = mih.getTrigger(TRIGGER_NAME + "1", "mihJob", "job_1", REPEAT_COUNT, REPEAT_INTERVAL);
                SimpleTrigger trigger_2 = mih.getTrigger(TRIGGER_NAME + "2", "mihJob", "job_2", REPEAT_COUNT, REPEAT_INTERVAL);
                SimpleTrigger trigger_3 = mih.getTrigger(TRIGGER_NAME + "3", "mihJob", "job_3", REPEAT_COUNT, REPEAT_INTERVAL);
                scheduler.scheduleJob(trigger_1);

                scheduler.scheduleJob(trigger_2);
                scheduler.scheduleJob(trigger_3);
            }
            scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
