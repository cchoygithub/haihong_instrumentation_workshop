### New Relic DT trace header 
- The `newrelic` trace header is injected after the flags enabled  in newrelic yml.

### Tips: 
#### DT tracer headers
- where are the following W3C headers?

    - traceparent
    - tracestate 

- How to enable W3C headers? 
- How to disable `newrelic` header? 

#### Manual API Kafka consumer instrumentation
- any other ways to instrument and achieve the same result? 
- why the Kafka `producer` does not need manual instrumentation? 
- With `@Trace(dispatcher = true)` for `listenOrder`(in this lab) what type of transaction does it start? 

#### DT header processing
- Do you need a transation context? 




