### Generate traffic and validate transactions in New Relic UI
- Generate some order traffic including errors from browser app `just-order-app`
- Generate CRUD traffic by adding/updating/deleting products in `just-order-app`

### Tips: 
#### Check distributed tracing UI 
- Is fulfilmentService(Kafka Consumer) part of the order DT? 
- Is just-order-app(the frontend) part of the DT? 

#### Check transaction traces
- Recall when "getCoupon" button is clicked, orderSerivce calls "getCoupon" to couponService and "checkRating" to vendorServie concurrently to get coupon and rating info
- Is "getCoupon" and "checkRating" calls captured in transaction trace or DT? 
- Is Browse app just-order-app(the frontend) part of any DT? 
- If you want to capture the coupon value, how to do that? 
- If you want to capture the login user info, how to do that? 

#### Check errors inbox
- Check order410 error and order501 error are capatured
- Do they have stack trace? 
- If you would like to know impacted users, how to do that?

