package com.nr.workshop.orderservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BasicAuthController {
    @GetMapping(path = "/basicauth")
    public String basicAuthCheck() {
        return "Success";
    }
}
