package com.codeaim.urlcheck.task;

import com.codeaim.urlcheck.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ScheduledResultExpiryTask implements ResultExpiryTask
{
    private ResultRepository resultRepository;
    private int resultExpiryLimit;

    @Autowired
    public ScheduledResultExpiryTask(
            ResultRepository resultRepository,
            @Value("${com.codeaim.urlcheck.resultExpiryLimit:10000}")
            int resultExpiryLimit
    )
    {
        this.resultRepository = resultRepository;
        this.resultExpiryLimit = resultExpiryLimit;
    }

    @Override
    public void run()
    {
        int expiredResultCount = resultRepository.expireResults(this.resultExpiryLimit);
        System.out.println("Expired results " + expiredResultCount);
    }
}
