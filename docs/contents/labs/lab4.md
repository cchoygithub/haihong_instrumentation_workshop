#### lab4-1 Light up `Kafka` nerdlet in APM UI  
This lab will be quick. 

- To light up `Kafka` nerdlet for both producer(orderService) and consumer(fulfilmentService)  

 ![Lab Diagram](../assets/images/lightUpProducer.png)

 ![Lab Diagram](../assets/images/lightUpConsumer.png)

- Configure the following in newrelic yml on both producer and consumer  

 ![Lab Diagram](../assets/images/lightUpKafka.png)


- Restart producer `orderService` app and consumer `fulfilmentService`  
    or simply restart all the apps for the change to take effect
    ```
    ./inst_apps.sh restart orderService
    ./inst_apps.sh restart fulfilmentService    
    or
    ./inst_apps.sh restart all    
    ```

- Generate new order traffic
   Wait for a few minutes, the Kafka metrics will be collected and the Kafka Nerdlet UI will be populated. 





