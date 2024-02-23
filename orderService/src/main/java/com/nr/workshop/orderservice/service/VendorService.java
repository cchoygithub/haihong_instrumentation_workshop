package com.nr.workshop.orderservice.service;

import com.newrelic.api.agent.Token;
import com.nr.workshop.orderservice.dns.CustomDnsResolver;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class VendorService {
    @Autowired
    CustomDnsResolver customDnsResolver;

    @Value(value = "${VendorService.URL}")
    private String VendorServiceURL;

    public Integer checkRating(String vendor) {

        String url = VendorServiceURL+"/checkRating/" + vendor ;

        HttpClient httpClient = HttpClients.custom().setDnsResolver(customDnsResolver).build();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity);
                    // Parse the response body to an Integer
                    Integer rating = Integer.parseInt(responseBody);
                    return rating;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

//###WORKSHOP_LAB8-1 multi thread
//    public Integer checkRating(String vendor, Token token) {
//        token.link();

//        String url = VendorServiceURL+"/checkRating/" + vendor ;

//        HttpClient httpClient = HttpClients.custom().setDnsResolver(customDnsResolver).build();
//        HttpGet httpGet = new HttpGet(url);

//        try {
//            HttpResponse response = httpClient.execute(httpGet);
//            int statusCode = response.getStatusLine().getStatusCode();

//            if (statusCode == 200) {
//                HttpEntity entity = response.getEntity();
//                if (entity != null) {
//                    String responseBody = EntityUtils.toString(entity);
//                    // Parse the response body to an Integer
//                    Integer rating = Integer.parseInt(responseBody);
//                    return rating;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
}
