package com.codeaim.urlcheck.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Schedule implements SchedulingConfigurer
{
    boolean isDisabled;
    CheckTask checkTask;
    ResultExpiryTask resultExpiryTask;


    @Autowired
    public Schedule(
            @Value("${com.codeaim.urlcheck.schedule.isDisabled:false}")
            boolean isDisabled,
            CheckTask checkTask,
            ResultExpiryTask resultExpiryTask
    )
    {
        this.isDisabled = isDisabled;
        this.checkTask = checkTask;
        this.resultExpiryTask = resultExpiryTask;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar)
    {
        if (!isDisabled)
        {
            taskRegistrar.addFixedDelayTask(() -> this.checkTask.run(), 2000);
            taskRegistrar.addFixedDelayTask(() -> this.resultExpiryTask.run(), 300000);
        }
    }
}
