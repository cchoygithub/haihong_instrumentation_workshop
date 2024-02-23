package com.nr.workshop.fulfilmentservice.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String vendor;
    private String product;
    private Integer quantity;
    private String username;
//    private Date targetDate;
    private String coupon;
    private String rating;
}