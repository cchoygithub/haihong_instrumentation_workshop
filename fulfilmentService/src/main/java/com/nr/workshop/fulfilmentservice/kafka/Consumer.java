package com.nr.workshop.fulfilmentservice.kafka;

import com.newrelic.api.agent.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import static com.nr.workshop.fulfilmentservice.utility.HelperUtility.delay;

@Slf4j
@Service
public class Consumer {
    private static final String W3C_TRACE_PARENT_HEADER = "traceparent";
    private static final String W3C_TRACE_STATE_HEADER = "tracestate";
    private static final String NEWRELIC_HEADER = "newrelic";


//###WORKSHOP_LAB2-2 Kafka consumer
    // @Trace(dispatcher = true)
    @KafkaListener(topics = "${message.topic.name}", containerFactory = "headersKafkaListenerContainerFactory")
    public void listenOrder(String order, ConsumerRecord<String, String> consumerRecord) {

//###WORKSHOP_LAB2-2 Kafka DT accept header
        // acceptDistributedTraceHeadersFromKafkaRecord(consumerRecord);

        log.info("WORKSHOP_LOG: Received Order: "+ order);
        delay(505);
        processOrder(order);
    }
    @Trace
    public void processOrder(String order){
        delay(705);
        log.info("WORKSHOP_LOG: Order processed and fulfilled: "+ order);
    }

    @Trace
    private static void acceptDistributedTraceHeadersFromKafkaRecord(ConsumerRecord<String, String> record) {
        // ConcurrentHashMapHeaders provides a concrete implementation of com.newrelic.api.agent.Headers
        Headers distributedTraceHeaders = ConcurrentHashMapHeaders.build(HeaderType.MESSAGE);

        // Iterate through each Kafka record header and insert the W3C Trace Context headers into the distributedTraceHeaders map
        for (Header kafkaRecordHeader : record.headers()) {
            String kafkaRecordHeaderValue = new String(kafkaRecordHeader.value(), StandardCharsets.UTF_8);


            if (kafkaRecordHeader.key().equals(NEWRELIC_HEADER)) {
                log.info("WORKSHOP_LOG: Kafka record header: {} {} ",kafkaRecordHeader.key(), kafkaRecordHeaderValue);
                distributedTraceHeaders.addHeader(NEWRELIC_HEADER, kafkaRecordHeaderValue);
            }

            if (kafkaRecordHeader.key().equals(W3C_TRACE_PARENT_HEADER)) {
                log.info("WORKSHOP_LOG: Kafka record header: {} {} ",kafkaRecordHeader.key(), kafkaRecordHeaderValue);
                distributedTraceHeaders.addHeader(W3C_TRACE_PARENT_HEADER, kafkaRecordHeaderValue);
            }

            if (kafkaRecordHeader.key().equals(W3C_TRACE_STATE_HEADER)) {
                log.info("WORKSHOP_LOG: Kafka record header: {} {} ",kafkaRecordHeader.key(), kafkaRecordHeaderValue);
                distributedTraceHeaders.addHeader(W3C_TRACE_STATE_HEADER, kafkaRecordHeaderValue);
            }

            // Accept distributed tracing headers to link this request to the originating request
            NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Kafka, distributedTraceHeaders);
        }
    }
}