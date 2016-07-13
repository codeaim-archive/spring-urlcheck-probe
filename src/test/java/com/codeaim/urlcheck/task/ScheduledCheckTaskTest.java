package com.codeaim.urlcheck.task;

import com.codeaim.urlcheck.configuration.ProbeConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeaim.urlcheck.Application;
import com.codeaim.urlcheck.repository.mock.CheckRepositoryMock;
import com.codeaim.urlcheck.repository.mock.ResultRepositoryMock;

import okhttp3.OkHttpClient;

import java.util.concurrent.ExecutorService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class ScheduledCheckTaskTest
{
    @Autowired
    OkHttpClient httpClient;

    @Autowired
    ExecutorService executorService;

    @Autowired
    ProbeConfiguration probeConfiguration;

    @Test
    public void run()
    {
        new ScheduledCheckTask(httpClient, executorService,new CheckRepositoryMock(), new ResultRepositoryMock(), probeConfiguration).run();
    }

    @Test
    public void runError()
    {
        new ScheduledCheckTask(null, executorService, new CheckRepositoryMock(), new ResultRepositoryMock(), probeConfiguration).run();
    }
}
