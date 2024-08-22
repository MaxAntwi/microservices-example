//package org.example.orderservice.config;
//
//import com.netflix.discovery.EurekaClientConfig;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
//import org.springframework.cloud.netflix.eureka.http.RestTemplateTransportClientFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//
//@Configuration
//@RequiredArgsConstructor
//public class CustomEurekaClientConfig {
//    private final RestTemplate eurekaRestTemplate;
//
//    @Bean
//    public EurekaClientConfig eurekaClientConfig() {
//        EurekaClientConfigBean config = new EurekaClientConfigBean();
//        // Customize the EurekaClientConfig here if needed
//        return config;
//    }
//
//    @Bean
//    public RestTemplateTransportClientFactory restTemplateTransportClientFactory() {
//        return new RestTemplateTransportClientFactory(eurekaRestTemplate);
//    }
//}
