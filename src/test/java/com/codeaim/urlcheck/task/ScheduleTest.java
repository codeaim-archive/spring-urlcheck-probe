package com.codeaim.urlcheck.task;

import com.codeaim.urlcheck.Application;
import com.codeaim.urlcheck.configuration.ProbeConfiguration;
import com.codeaim.urlcheck.task.mock.CheckTaskMock;
import com.codeaim.urlcheck.task.mock.ResultExpiryTaskMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class ScheduleTest
{
    @Autowired
    ProbeConfiguration probeConfiguration;

    @Test
    public void configureTasks()
    {
        new Schedule(
                probeConfiguration,
                new CheckTaskMock(),
                new ResultExpiryTaskMock())
                .configureTasks(new ScheduledTaskRegistrar());
    }
}
