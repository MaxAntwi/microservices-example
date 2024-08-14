package org.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                //Product Service Route
                .route("product-service", r -> r.path("/api/v1/products/**").uri("lb://product-service"))
                //Order Service Route
                .route("order-service", r -> r.path("/api/v1/orders/**").uri("lb://order-service"))
                //Discovery Service Route
                .route("discovery-server", r -> r.path("/eureka/web").filters(f -> f.setPath("/")).uri("http://localhost:8761"))
                .route("discovery-server-static", r -> r.path("/eureka/**").uri("http://localhost:8761"))
                .build();
    }
}
