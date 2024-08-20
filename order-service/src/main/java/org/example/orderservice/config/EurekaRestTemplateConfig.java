package org.example.orderservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

@Configuration
public class EurekaRestTemplateConfig {
    @Value("${eureka.user.name}")
    private String username;

    @Value("${eureka.user.password}")
    private String password;

    @Bean
    public RestTemplate eurekaRestTemplate() {
        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Combine username and password in the "username:password" format
        String auth = username + ":" + password;

        // Encode the combined string using Base64 encoding
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        // Create the "Authorization" header with the value "Basic <encodedAuth>"
        String authHeader = "Basic " + encodedAuth;

        // Create a ClientHttpRequestInterceptor that adds the "Authorization" header to every request
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            request.getHeaders().set("Authorization", authHeader);
            return execution.execute(request, body);
        };

        // Register the interceptor with the RestTemplate
        restTemplate.setInterceptors(Collections.singletonList(interceptor));

        return restTemplate;
    }

}
