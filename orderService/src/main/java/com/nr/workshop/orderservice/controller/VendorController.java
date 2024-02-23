package com.nr.workshop.orderservice.controller;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.nr.workshop.orderservice.service.CouponService;
import com.nr.workshop.orderservice.service.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.nr.workshop.orderservice.utility.HelperUtility.delay;
@RestController
@RequestMapping("/restapi")
public class VendorController {
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private final CouponService couponService;
    private final VendorService vendorService ;

    public VendorController(CouponService couponService, VendorService vendorService) {
        this.couponService = couponService;
        this.vendorService = vendorService;
    }

    @GetMapping("/vendor-details/{vendor}")
    public ResponseEntity<Map<String, Object>> getVendorDetails(@PathVariable String vendor) {
        delay(602);

        List<Future<?>> futures = new ArrayList<Future<?>>();

//###WORKSHOP_LAB8-1 multi thread (uncomment out following block)
    //    Token token = NewRelic.getAgent().getTransaction().getToken();
    //    var couponFuture = executorService.submit(() -> couponService.getCoupon(vendor,token));
    //    var ratingFuture = executorService.submit(() -> vendorService.checkRating(vendor,token));

//###WORKSHOP_LAB8-1 multi thread (comment out following block)
        var couponFuture = executorService.submit(() -> couponService.getCoupon(vendor));
        var ratingFuture = executorService.submit(() -> vendorService.checkRating(vendor));


        futures.add(couponFuture);
        futures.add(ratingFuture);
        Map<String, Object> responseData = new HashMap<>();
        try {
            var couponResult = couponFuture.get(); // This will block until the result is available
            var ratingResult = ratingFuture.get(); // This will block until the result is available

            responseData.put("Coupon", couponResult);
            responseData.put("Rating", ratingResult);

//###WORKSHOP_LAB8-1 multi thread (uncomment out following block)
        //    token.expire();

            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(responseData);
    }

}
