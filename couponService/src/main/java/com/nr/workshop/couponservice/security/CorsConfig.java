package com.nr.workshop.inventoryservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false); // Allow credentials
        config.addAllowedOriginPattern("*"); // Allow all origins
        config.addAllowedMethod("*"); // Allow all HTTP methods
        config.addAllowedHeader("workshopusername");
        config.addAllowedHeader("content-type");
        config.addAllowedHeader("authorization");

//###WORKSHOP_LAB3-2 CORS headers (uncomment the following block to allow DT headers adding by browser agent - when it is configured)
        // config.addAllowedHeader("newrelic");
        // config.addAllowedHeader("traceparent");
        // config.addAllowedHeader("tracestate");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}