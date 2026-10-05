package com.example.main.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.http.client.JdkClientHttpRequestFactory;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClientCustomizer restClientCustomizer() {

        return builder -> {

            JdkClientHttpRequestFactory factory =
                    new JdkClientHttpRequestFactory();

            factory.setReadTimeout(Duration.ofMinutes(5));

            builder.requestFactory(factory);
        };
    }
}