package com.codeaim.urlcheck.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeaim.urlcheck.task.mock.ScheduledTaskMock;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class ScheduleTest
{
    @Test
    public void configureTasks()
    {
        new Schedule(false, new ScheduledTaskMock()).configureTasks(new ScheduledTaskRegistrar());
    }
}
