package com.nr.workshop.inventoryservice.controller;

import com.nr.workshop.inventoryservice.exception.OutOfStockException;
import com.nr.workshop.inventoryservice.exception.ProductDiscontinuedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.nr.workshop.inventoryservice.utility.HelperUtility.delay;


@RestController
public class InventoryController {

    @GetMapping("/checkInventory/{vendor}")
    public ResponseEntity<String> checkInventory(@PathVariable String vendor, @RequestParam(required = false, defaultValue = "1") int quantity) {
        delay(601);
        String body=vendor + ": " + quantity;
        if (quantity < 300) {
            // Return 200 response code with vendor + quantity
            return ResponseEntity.ok(vendor + " " + quantity);
        } else if (quantity >= 300 && quantity < 400) {
            // Return 303 response code
            return ResponseEntity.status(HttpStatus.SEE_OTHER).body(body);
        } else if (quantity >= 400 && quantity < 500) {
            // Return 410 response code
            throw new OutOfStockException("product out of stock!");

//            return ResponseEntity.status(HttpStatus.GONE).body(body);
        } else if (quantity >= 500) {
            // Return 501 response code
            throw new ProductDiscontinuedException("product discontinued!");
        } else {
            // Invalid quantity
            return ResponseEntity.badRequest().body("Invalid quantity");
        }
    }



}
