//package com.nr.workshop.orderservice.dns;
//
//import com.nr.workshop.orderservice.dns.CustomDnsResolver;
//import org.apache.http.conn.DnsResolver;
//import org.apache.http.conn.HttpClientConnectionManager;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class HttpClientConfig {
//
//    @Bean
//    public CloseableHttpClient customHttpClient() {
//        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
//
//        // Configure custom DNS resolver
//        DnsResolver dnsResolver = new CustomDnsResolver();
//
//        HttpClientBuilder httpClientBuilder = HttpClients.custom()
//                .setConnectionManager(connectionManager)
//                .setDnsResolver(dnsResolver);
//
//        return httpClientBuilder.build();
//    }
//}