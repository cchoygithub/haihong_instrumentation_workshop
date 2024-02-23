package com.nr.workshop.couponservice.controller;

import com.nr.workshop.couponservice.exception.InvalidCouponException;
import com.nr.workshop.couponservice.exception.NoCouponAttachedException;
import com.nr.workshop.couponservice.exception.NoCouponOfferException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class CouponController {

    @GetMapping("/getCoupon/{vendor}")
    public ResponseEntity<String> generateCoupon(@PathVariable String vendor) {
        if ("apple".equalsIgnoreCase(vendor)) {
            throw new NoCouponOfferException("==== CouponCheck:"+vendor+":vendor do not offer coupon!");
        }

        // Generate a random coupon code using UUID
        String couponCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10); // Adjust the length as needed

        return ResponseEntity.ok((vendor+"_"+couponCode).toUpperCase());
    }
    @GetMapping("/checkCoupon/{vendor}")
    public ResponseEntity<String> checkCoupon(@PathVariable String vendor,@RequestParam(required = false, defaultValue = "_NONE_") String coupon) {
        if ("_NONE_".equalsIgnoreCase(coupon) || "no coupon".equalsIgnoreCase(coupon)) {
            throw new NoCouponAttachedException("==== CouponCheck:"+vendor+":vendor no coupon Attached");
        }
        if ("samsung".equalsIgnoreCase(vendor) ) {

            throw new InvalidCouponException("==== CouponCheck:"+vendor+":vendor all coupons invalidated!");
        }
        return ResponseEntity.ok((" ==== CouponCheck:" + coupon+":valid").toUpperCase());
    }
}
