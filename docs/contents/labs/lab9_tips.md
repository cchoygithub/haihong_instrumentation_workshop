### Using JMX to collect GC metrics
- [Infrastructure JMX integration](https://docs.newrelic.com/docs/infrastructure/host-integrations/host-integrations-list/jmx-monitoring-install/) is an alternative option
- Flex supports JMX integration as well
- Both the above requires `nrjmx` - a Java based utility bundle from NR
- With [nrjmx tool](https://github.com/newrelic/nrjmx), you can do a jmx dump using the following command line
    ```
    echo '*:*' | java -jar ./nrjmx/nrjmx.jar -hostname 127.0.0.1 -port 9191 -user myuser1 -password abcd1234
    ```
