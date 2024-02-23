## Tips

- Why `external services` error metrics are being capture 
- Where did we capture the error metric for non external serivces?
- What libraries does the repo instrument: 

    [custom-external-service-error extension ](https://github.com/haihongren/custom-external-service-error)

- Developer can use the following New Relic API to report error metric in their external call error handling code. 

    ```
    NewRelic.incrementCounter("Custom/ExternalError/"+ remoteHost);
    ```