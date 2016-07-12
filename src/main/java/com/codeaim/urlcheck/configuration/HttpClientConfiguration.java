package com.codeaim.urlcheck.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class HttpClientConfiguration
{
    @Value("${com.codeaim.urlcheck.candidatePoolSize:25}")
    long candidatePoolSize;

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
        return Executors.newFixedThreadPool((int) candidatePoolSize);
    }
}
