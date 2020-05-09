package com.dgut.community.Quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    private static final String LIKE_TASK_IDENTITY = "LikeTaskQuartz";
    private static final String MAIL_TASK_IDENTITY = "MailTaskQuartz";

    @Bean
    public JobDetail quartzDetail(){
        return JobBuilder.newJob(LikeTask.class).withIdentity(LIKE_TASK_IDENTITY).storeDurably().build();
    }

    @Bean
    public JobDetail quartzDetail2(){
        return JobBuilder.newJob(MailTask.class).withIdentity(MAIL_TASK_IDENTITY).storeDurably().build();
    }

    @Bean
    public Trigger quartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                //.withIntervalInSeconds(60)  //设置时间周期单位秒
                .withIntervalInHours(2)  //两个小时执行一次
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(quartzDetail())
                .withIdentity(LIKE_TASK_IDENTITY)
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public Trigger quartzTrigger2(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                //.withIntervalInSeconds(60)  //设置时间周期单位秒
                .withIntervalInHours(72)  //三天执行一次
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(quartzDetail2())
                .withIdentity(MAIL_TASK_IDENTITY)
                .withSchedule(scheduleBuilder)
                .build();
    }
}

