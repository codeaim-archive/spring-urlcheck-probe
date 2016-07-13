package com.codeaim.urlcheck.task;

import com.codeaim.urlcheck.configuration.ProbeConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Schedule implements SchedulingConfigurer
{
    ProbeConfiguration probeConfiguration;
    CheckTask checkTask;
    ResultExpiryTask resultExpiryTask;


    @Autowired
    public Schedule(
            ProbeConfiguration probeConfiguration,
            CheckTask checkTask,
            ResultExpiryTask resultExpiryTask
    )
    {
        this.probeConfiguration = probeConfiguration;
        this.checkTask = checkTask;
        this.resultExpiryTask = resultExpiryTask;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar)
    {
        if (!probeConfiguration.isScheduleDisabled())
        {
            taskRegistrar.addFixedDelayTask(() -> this.checkTask.run(), 2000);
            taskRegistrar.addFixedDelayTask(() -> this.resultExpiryTask.run(), 300000);
        }
    }
}
