package com.codeaim.urlcheck.configuration;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfiguration
{
    @Bean
    public OkHttpClient getHttpClient()
    {
        return new OkHttpClient().newBuilder()
                .followRedirects(true)
                .followSslRedirects(true)
                .build();
    }
}
