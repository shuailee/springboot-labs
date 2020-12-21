package com.shuailee.springboot.study.api.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: springboot-study
 * @description: ScheduledTask springboot的调度器
 * @author: shuai.li
 * @create: 2020-05-21 11:08
 **/
@Component
public class ScheduledTask {
    @Scheduled(fixedRate = 2000)
    public void task2() {
        System.out.println("每2000毫秒执行一次！");
    }

    @Scheduled(initialDelay=1000, fixedDelay = 1000)
    public void task1() {
        System.out.println("延迟1000毫秒后执行，任务执行完1000毫秒之后执行！");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "*/2 * * * * ?")
    public void task3() {
        System.out.println("每2秒执行一次！");
    }

}
