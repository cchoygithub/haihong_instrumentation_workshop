package com.nr.workshop.inventoryservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

import static com.nr.workshop.inventoryservice.utility.HelperUtility.delay;

@RestController
public class RatingController {
    @GetMapping("/checkRating/{vendor}")
    public ResponseEntity<Integer> checkRating(@PathVariable String vendor) {
        
        
        
        delay(1501);
        Random random = new Random();
        int rating = random.nextInt(4) + 7;
        return ResponseEntity.ok(rating);
    }
}
