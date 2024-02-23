package com.nr.workshop.orderservice.controller;

import com.nr.workshop.orderservice.data.Order;
import com.nr.workshop.orderservice.exception.OrderUnsuccessfulException410;
import com.nr.workshop.orderservice.exception.OrderUnsuccessfulException501;
import com.nr.workshop.orderservice.kafka.KafkaService;
import com.nr.workshop.orderservice.service.CouponService;
import com.nr.workshop.orderservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.nr.workshop.orderservice.utility.HelperUtility.delay;

@RestController
@RequestMapping("/restapi")
@Slf4j
public class OrderController {

    private final KafkaService kafkaService;
    private final InventoryService inventoryService;
    private final CouponService couponService;

    public OrderController(KafkaService kafkaService, InventoryService inventoryService, CouponService couponService) {
        this.kafkaService = kafkaService;
        this.inventoryService = inventoryService;
        this.couponService = couponService;
    }

    @PostMapping("/order")
    public ResponseEntity<String> postOrder(@RequestBody Order order,@RequestParam(value = "coupon", required = false, defaultValue = "") String coupon)   {
        String vendor = order.getVendor();
        Integer quantity = order.getQuantity();


        order.setUsername(getCurrentUsername());
//        order.setTargetDate(new Date());

        Integer inventoryStatus = inventoryService.checkInventory(vendor, quantity);
        String couponStatus = couponService.checkCoupon(vendor, coupon);

        delay(601);
        if (inventoryStatus==200) {
            return ResponseEntity.ok(kafkaService.sendOrder(order)+ "\n"+couponStatus);
        }
        if (inventoryStatus>=400 && inventoryStatus<500) {
            throw new OrderUnsuccessfulException410("Inventory check failed!" +quantity );
        }
        if (inventoryStatus>=500) {
            throw new OrderUnsuccessfulException501("Inventory check failed!"+quantity);
        }

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("==== Inventory check failed! Unable to submit order.");
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return "anonymousUser";
    }

    public String concatHeaders(HttpHeaders headers){
        StringBuilder headerString = new StringBuilder();

        // Iterate through all the request headers
        headers.forEach((key, values) -> {
            headerString.append(key).append(": ");
            values.forEach(value -> headerString.append(value).append(", "));
            headerString.append("\n");
        });
        return headerString.toString();
    }
}
