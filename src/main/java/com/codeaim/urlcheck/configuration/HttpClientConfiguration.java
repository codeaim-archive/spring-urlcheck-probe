package com.codeaim.urlcheck.configuration;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class HttpClientConfiguration
{
    @Autowired
    ProbeConfiguration probeConfiguration;

    @Bean
    public OkHttpClient getHttpClient()
    {
        return new OkHttpClient().newBuilder()
                .followRedirects(true)
                .followSslRedirects(true)
                .build();
    }

    @Bean
    public ExecutorService getExecutorService()
    {
        return Executors.newFixedThreadPool((int) probeConfiguration.getCandidatePoolSize());
    }
}
