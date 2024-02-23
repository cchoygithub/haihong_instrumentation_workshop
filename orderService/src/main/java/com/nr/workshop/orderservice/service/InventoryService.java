package com.nr.workshop.orderservice.service;

import com.newrelic.api.agent.Trace;
import com.nr.workshop.orderservice.dns.CustomDnsResolver;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    CustomDnsResolver customDnsResolver;

    @Value(value = "${InventoryService.URL}")
    private String InventoryServiceURL;

    public Integer checkInventory(String vendor, int quantity) {
        String url = InventoryServiceURL+"/checkInventory/" + vendor + "?quantity=" + quantity;

        HttpClient httpClient = HttpClients.custom().setDnsResolver(customDnsResolver).build();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            return statusCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
