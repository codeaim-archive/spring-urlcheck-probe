package com.codeaim.urlcheck.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "com.codeaim.urlcheck.probe")
public class ProbeConfiguration
{
    private String name = "Standalone";
    private long candidatePoolSize = 25;
    private int resultExpirySize = 10000;
    private boolean clustered;
    private boolean scheduleDisabled;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getCandidatePoolSize()
    {
        return candidatePoolSize;
    }

    public void setCandidatePoolSize(long candidatePoolSize)
    {
        this.candidatePoolSize = candidatePoolSize;
    }

    public int getResultExpirySize()
    {
        return resultExpirySize;
    }

    public void setResultExpirySize(int resultExpirySize)
    {
        this.resultExpirySize = resultExpirySize;
    }

    public boolean isClustered()
    {
        return clustered;
    }

    public void setClustered(boolean clustered)
    {
        this.clustered = clustered;
    }

    public boolean isScheduleDisabled()
    {
        return scheduleDisabled;
    }

    public void setScheduleDisabled(boolean scheduleDisabled)
    {
        this.scheduleDisabled = scheduleDisabled;
    }
}
