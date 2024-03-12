
### Generate traffic automatically (optional)

- If you prefer to generate traffic to the apps automatically,  use the following simple shell command line. 
- During the labs, it is preferable to do everything manually. 

##### Post `order phone` to orderService continuously

```
for ((i=1;i<=1999099;i++)); do echo "\n"; curl -X POST 'http://localhost:8080/restapi/order?coupon=%22HUAWEI_3E3EBC8972%22' -H 'Content-Type: application/json' -H 'Authorization: Basic bnJ1c2VyOmFiYzEyMw=='  -d '{ "id": 10003, "vendor": "xiaomi", "price": 2024, "quantityavailable": 10000, "coupon": "XIAOMI_3E3EBC8972", "rating": 7, "product": "Redmi Note 13", "quantity": 1 }'; echo "\n"; done

```
##### Request `getCoupon` `checkRating` to orderService continuously
```
for ((i=1;i<=1999099;i++)); do echo "\n"; curl -X GET '127.0.0.1:8080/restapi/vendor-details/Samsung' -H 'Content-Type: application/json' -H 'Authorization: Basic bnJ1c2VyOmFiYzEyMw==' ; done

```