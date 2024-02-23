package com.nr.workshop.orderservice.dns;

import org.apache.http.conn.DnsResolver;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomDnsResolver implements DnsResolver {
    
    private final Map<String, String> hostMappings;

    public CustomDnsResolver() {
        this.hostMappings = new HashMap<>();
        // Map inventoryservice and couponservice to localhost
        this.hostMappings.put("inventoryservice", "127.0.0.1");
        this.hostMappings.put("couponservice", "127.0.0.1");
        this.hostMappings.put("vendorservice", "127.0.0.1");

    }

    @Override
    public InetAddress[] resolve(String host) throws UnknownHostException {
        String mappedAddress = hostMappings.get(host);
        if (mappedAddress != null) {
            return new InetAddress[]{InetAddress.getByName(mappedAddress)};
        } else {
            throw new UnknownHostException("Host not found: " + host);
        }
    }
}
