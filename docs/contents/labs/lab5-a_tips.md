## Tips

- By default, the agent does not capture request parameters. [Docs](https://docs.newrelic.com/docs/apm/agents/php-agent/attributes/attribute-examples/)

    You can use the following configuration to turn on parameter capture for these default destinations: transaction_tracer, transaction_events, and error_collector.   

    **Capture all request parameters**
    ```
    newrelic.attributes.include = request.parameters.*
    ```

    **Capture only specific request parameters**
    ```
    newrelic.attributes.include = request.parameters.user_id request.parameters.product_id
    ```
