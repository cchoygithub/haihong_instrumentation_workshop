package com.nr.workshop.orderservice.service;

import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.CompletableFuture;

@Service
public class CouponService {
    @Autowired
    CustomDnsResolver customDnsResolver;

    @Value(value = "${CouponService.URL}")
    private String CouponServiceURL;


    public String getCoupon(String vendor) {
        String url = CouponServiceURL+"/getCoupon/" + vendor;

        HttpClient httpClient = HttpClients.custom().setDnsResolver(customDnsResolver).build();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "no coupon";
    }

//###WORKSHOP_LAB8-1 multi thread (uncomment the following block)
//    public String getCoupon(String vendor, Token token) {
//        token.link();
//        String url = CouponServiceURL+"/getCoupon/" + vendor;

//        HttpClient httpClient = HttpClients.custom().setDnsResolver(customDnsResolver).build();
//        HttpGet httpGet = new HttpGet(url);

//        try {
//            HttpResponse response = httpClient.execute(httpGet);
//            int statusCode = response.getStatusLine().getStatusCode();
//            if (statusCode == 200) {
//                HttpEntity entity = response.getEntity();
//                return EntityUtils.toString(entity);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "no coupon";
//    }

    public String checkCoupon(String vendor, String coupon) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(CouponServiceURL + "/checkCoupon/" + vendor)
                .queryParam("coupon", coupon);

        String url = builder.toUriString();

        HttpClient httpClient = HttpClients.custom().setDnsResolver(customDnsResolver).build();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Coupon Service unavailable!";
    }
}
