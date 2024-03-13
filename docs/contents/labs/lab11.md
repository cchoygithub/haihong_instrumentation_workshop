### Using Weaver to instrument inventoryService -> RatingController.checkRating

- Your team does not own `inventoryService` application source code. However, the business has the following requirements:    

  > 1. to keep track of the `vendor` info for which the `checkRating` being called   
  > 2. to keep track how many times the `checkRating` being called per vendor  

    ![Lab Diagram](../assets/images/classToWeave.png)

- NR Weaver instrumentation allows you to create a mirrored class which will be merged into application's(e.g. `inventoryService`) class (e.g. `RatingController`) during runtime. 

- A Weaver project(`workshop_weaver`) is created to meet the above requirements.   
  
  In the `workshop_weaver` project, a dedicate module `inventoryService_Instrumentation` is created to do weaver instrumentation for `inventoryService` application. 

  > - A mirrored class **com.nr.workshop.inventoryservice.controller.**`RatingController` is created with `@Weave` annotation   
  > - `checkRating` method in the mirrored class has the same class signature as the application's class  
  > - `@Trace` annotation added to `checkRating` method  
  > - The following code is added to achieve the requirements  
  >  ```
  >      NewRelic.addCustomParameter("vendor",vendor);
  >      NewRelic.incrementCounter("Custom/CheckingRating/"+vendor);
  >  ```
  > - The `return Weaver.callOriginal();` indicates the original `checkRating` code is called after the above code. 

    ![Lab Diagram](../assets/images/weaverProject.png)


- Side by side comparision of the class to be instrumented(**left**) vs Weaver Class(**right**) does the instrumentation

    ![Lab Diagram](../assets/images/classVSweaverclass.png)

- The merged(weaved) class (e.g. `RatingController`)  during runtime is sort of like the following:   
  > The code in the red box comes from weaver class   
  > The code in the blue box comes from the original application class   

    ![Lab Diagram](../assets/images/weavedClass.png)

- Run Gradle build to build the instrumentation module jar file

    **In `instrumentation_workshop/workshop_weaver` folder**
    ```
    ./gradlew clean build

    ```

     ![Lab Diagram](../assets/images/weaverGradleBuild.png)


- Copy the `build/libs/inventoryService_Instrumentation.jar` instrumentation module to <newrelic-agent/extensions> folder. 
  
    ![Lab Diagram](../assets/images/agentExtensions.png)

  The Java agent should pick up the instrumention automatically. 
  
  You can also restart apps manaully to force the agent to fresh load the extension. 

- Generate `getCoupon` activities, validate the metrics are being

    ![Lab Diagram](../assets/images/getCoupon.png)

     ![Lab Diagram](../assets/images/weaverResult.png)

---
## The END 


