
### Troubleshooting 

#### Docker containers used in the lab 

Defined in `docker/docker-compose.yml`

```
kafka
mysql8
zookeeper
```

#####  Docker Container status 
  In `instrumentation_workshop` folder, make sure the containes are running. 
```
 docker-compose -f docker/docker-compose.yml ps
```

```
  Name                Command             State                              Ports
--------------------------------------------------------------------------------------------------------------
kafka       /etc/confluent/docker/run     Up      0.0.0.0:9092->9092/tcp,:::9092->9092/tcp
mysql8      docker-entrypoint.sh mysqld   Up      0.0.0.0:3306->3306/tcp,:::3306->3306/tcp, 33060/tcp
zookeeper   /etc/confluent/docker/run     Up      0.0.0.0:2181->2181/tcp,:::2181->2181/tcp, 2888/tcp, 3888/tcp

```
##### Stop all containers 
```
 docker-compose -f docker/docker-compose.yml down
```

##### Start all containers

```
 docker-compose -f docker/docker-compose.yml up -d
```


#### Java 17+ Installed

```
java --versoin 
```

```
java 17.0.8 2023-07-18 LTS
Java(TM) SE Runtime Environment (build 17.0.8+9-LTS-211)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.8+9-LTS-211, mixed mode, sharing)
```


#### NodeJS 18+ Installed

```
node --version
```

#### Your New Relic license key in env file

```
more env 
```
```
export NEW_RELIC_LICENSE_KEY="<YOUR KEY>"
```

#### Check mysql database

```
docker exec mysql8 bash -c 'mysql -unruser -pabc123 -e "SHOW DATABASES;"'
```

#### Check orderService REST API

##### Post `order phone` to orderService continuously

```
for ((i=1;i<=1999099;i++)); do echo "\n"; curl -X POST 'http://localhost:8080/restapi/order?coupon=%22HUAWEI_3E3EBC8972%22' -H 'Content-Type: application/json' -H 'Authorization: Basic bnJ1c2VyOmFiYzEyMw=='  -d '{ "id": 10003, "vendor": "xiaomi", "price": 2024, "quantityavailable": 10000, "coupon": "XIAOMI_3E3EBC8972", "rating": 7, "product": "Redmi Note 13", "quantity": 1 }'; echo "\n"; done

```
##### Request `getCoupon` `checkRating` to orderService continuously
```
for ((i=1;i<=1999099;i++)); do echo "\n"; curl -X GET '127.0.0.1:8080/restapi/vendor-details/Samsung' -H 'Content-Type: application/json' -H 'Authorization: Basic bnJ1c2VyOmFiYzEyMw==' ; done

```

#### Kafka Utility Command 

##### list kafka topics 
```
docker exec kafka bash -c 'kafka-topics --list --bootstrap-server localhost:9092'
```

##### console consumer 
```
docker exec kafka bash -c 'kafka-console-consumer --topic=order-topic --from-beginning --bootstrap-server localhost:9092'
 
```

##### console producer
```
docker exec kafka bash -c 'kafka-console-producer --topic=order-topic  --bootstrap-server localhost:9092'
```