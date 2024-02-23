# Lab Application Architecture
#### User Journey 1
 User login -> Order -> Inventory Check -> Kafka producer -> Kafka Consumer

![Lab Diagram](assets/images/userjourney1.png)

#### User Journey 2
 User login -> Order Phone -> getCoupon( call couponService, call RatingService) -> Order -> Inventory Check -> Kafka Producer -> Kafka Consumer
 
![Lab Diagram](assets/images/userjourney2.png)

#### User Journey 3
 User login -> Order Phone -> Add New Product 
![Lab Diagram](<assets/images/userjourney 3.png>)