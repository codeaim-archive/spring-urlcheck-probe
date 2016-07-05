package com.codeaim.urlcheck.configuration;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration
{
    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate(getClientHttpRequestFactory());
    }


    public ClientHttpRequestFactory getClientHttpRequestFactory()
    {

        return new OkHttp3ClientHttpRequestFactory(getHttpClient());
    }

    private OkHttpClient getHttpClient()
    {
        return new OkHttpClient().newBuilder()
                .followRedirects(true)
                .followSslRedirects(true)
                .build();
    }
}
