package com.mih;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleTest {

    public static void main(String args[]) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(6);
        service.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("mi");
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }
}
