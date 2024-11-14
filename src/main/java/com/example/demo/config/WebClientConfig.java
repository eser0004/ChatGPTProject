package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient(webClient.Builder builder) {
            return builder.baseUrl("https://api.openai.com/v1").build();
        }

        //placeholder
}
