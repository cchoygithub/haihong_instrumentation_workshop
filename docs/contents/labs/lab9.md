### Using Java Agent built-in JMX to collect GC metrics 

- Identify the JMX metrics in `Jconsole`(comes with JDK) or other similar tools
    ```
    #start Jconsole from terminal
    jconsole 
    ```

     ![Lab Diagram](../assets/images/connectJconsole.png)

 
- Take note of the ObjectName and attribute name

     ![Lab Diagram](../assets/images/gc_objectName.png)


- Create a yml file (e.g. GC_JMX_example.yml) shown in the screenshot, save it to `<newrelic agent>/extensions` folder
    > filename can be any name with `.yml` extension
    > create `extensions` subfolder if it does exist   
    > sample `GC_JMX_example.yml` is provded in the package (review and copy it to the `extensions` folder)
    

    - wildcard(*) can be used   
        - In the following example, `java.lang:name=*` has `*`. It matches any name (including `PS MarkSweep`)
    - {name} property reference 
    - `root_metric_name` prefix

    ![Lab Diagram](../assets/images/jmxConfig.png)


- Validate the metrics are being collected.  

    > In NR UI, click SPAServer Application  
    > Click Metrics Explorer  
    > Filter the metric name configured in above configuration (e.g. `itsCustomMetrics`)

    ![Lab Diagram](../assets/images/jmxCollectedMetrics.png)
