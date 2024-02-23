package com.nr.workshop.orderservice.kafka;

import com.newrelic.api.agent.Trace;
import com.nr.workshop.orderservice.data.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.nr.workshop.orderservice.utility.HelperUtility.delay;

@Service
@Slf4j
public class KafkaService {
    private final KafkaTemplate<String, Order> orderKafkaTemplate;

    public KafkaService(KafkaTemplate<String, Order> orderKafkaTemplate) {
        this.orderKafkaTemplate = orderKafkaTemplate;
    }

    @Value(value = "${message.topic.name}")
    private String topicName;

    @Trace(dispatcher = true)
    public String sendOrder(Order order)  {

        CompletableFuture<SendResult<String, Order>> sendResult = orderKafkaTemplate.send(topicName, order);

        try {
            SendResult<String, Order> stringOrderSendResult = sendResult.get();
            Headers headers = stringOrderSendResult.getProducerRecord().headers();
            StringBuilder sb = new StringBuilder();

            for (Header header : headers) {
                String key = header.key();
                String value = new String(header.value());
                sb.append("Key: " + key + ", Value: " + value +"\n");
            }
            delay(601);
            return "==== Order successfully submitted! \n" + order +"\n"
                    +"\n==== Kafka ProducerRecord Headers: [look for DT header 'newrelic','traceparent','tracestate'] ===== \n"+ sb;

        } catch (InterruptedException|ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
