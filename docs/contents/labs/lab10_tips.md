### Tips

- You can generate a thread profile and instrument the method and class from the profile from New Relic UI
- `nameTransaction` in xml instrumentation overrides the transaction name up this point of time of the transaction pipeline.
    ```
            <!-- By adding the <nameTransaction/> element, the class and method specified 
                in this pointcut will be used to name transactions which hit this pointcut. 
            -->
            <pointcut transactionStartPoint="false">
                <nameTransaction />
                <className>com.sample.SampleString</className>
                <!-- Instruments the method boolean startsWith(String prefix) -->
                <method>
                    <name>startsWith</name>
                    <parameters>
                        <type>java.lang.String</type>
                    </parameters>
                </method>
            </pointcut>
    ```
