### Using Java Agent built-in JMX to collect GC metrics 

- Identify the JMX metrics in `Jconsole`(comes with JDK) or other similar tools
    ```
    #start Jconsole from terminal
    jconsole 
    ```

     ![Lab Diagram](../assets/images/connectJconsole.png)

 
- Take note of the ObjectName and attribute name

     ![Lab Diagram](../assets/images/gc_objectName.png)


- Create a yml file (e.g. GC_JMX_example.yml), save it to `<newrelic agent>/extensions` folder. 
    - wildcard(*) can be used
    - {name} property reference 
    - `root_metric_name` prefix

    ![Lab Diagram](../assets/images/jmxConfig.png)


- Validate the metrics are being collected.

    ![Lab Diagram](../assets/images/jmxCollectedMetrics.png)
