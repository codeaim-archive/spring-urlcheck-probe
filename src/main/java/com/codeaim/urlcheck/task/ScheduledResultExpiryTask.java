package com.codeaim.urlcheck.task;

import com.codeaim.urlcheck.configuration.ProbeConfiguration;
import com.codeaim.urlcheck.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduledResultExpiryTask implements ResultExpiryTask
{
    private ResultRepository resultRepository;
    private ProbeConfiguration probeConfiguration;

    @Autowired
    public ScheduledResultExpiryTask(
            ResultRepository resultRepository,
            ProbeConfiguration probeConfiguration
    )
    {
        this.resultRepository = resultRepository;
        this.probeConfiguration = probeConfiguration;
    }

    @Override
    public void run()
    {
        int expiredResultCount = resultRepository.expireResults(this.probeConfiguration.getResultExpirySize());
        System.out.println("Expired results " + expiredResultCount);
    }
}
