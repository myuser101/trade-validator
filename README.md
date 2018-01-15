Recruitment task - Trade Validator
=====

Various comments related to given solution:
* Publishing log messages should be done to centralized systems and log events viewed in common UI like Grafana.
* Similarly to log events, performance metrics shall be also centralized (e.g. in Graphite).
* JBoss logging library enforces unique identifiers for every log message and moves text to annotations instead of business logic.
* Enabled caching of successful Fixer AIP replies to improve performance.
* Implemented circuit-breaker pattern using Hystrix on call to Fixer API.
* Client-side load balancing is more robust then traditional HTTP LB (F5, Apache HTTP Server). I have skipped Eureka client setup, because Eureka server is needed in runtime (simplifying recruitment task).
* Swagger documentation available.
* Split integration and unit tests to speed-up quick evaluation for developers. Build still requires integration tests to pass.
* I am used to Hibernate formatting style (https://github.com/hibernate/hibernate-ide-codestyles), but obviously can follow any established conventions. Hope the code is readable for you. Once you get used to it, you will never return to Sun conventions :).
* Would suggest every trade request to have unique UUID. While revoking invalid trade, service could just specify UUID of the request, and not complete trade object.
* Value date not present in Vanilla Option trade type in sample input, so I assumed it is only available for Spot and Forward options.
* Applied URL style service versioning (_/v1/..._).


##### Q&A
> 1. Expose performance metrics of the application including: number of requests processed, processing time (min, max, average quantile95).

I would suggest to export metrics asynchronously to Graphite store. Graphite calculates execution count, percentiles, minimum and maximum values by default. Graphite client shall be plugged into clas similar to _LoggingTrace_. Graphite can act as a data store for Grafana which enabled users to create on-demand dashboards. Since Graphite requires server to run, I skipped coding in recruitment task.

> 2. Describe and present the approach for providing high availability of the service and its scalability.

Spring Boot applications can be easily deployed in container environment. Since all services are stateless, we could run active-active between multiple data centers without any additional effort. The only open topics is service discovery and load balancing. Suggest to setup client-side load balancing with Eureka as service discovery implementation. Since Eureka requires server to start, I have skipped coding in recruitment task.
 
> 3. Create online documentation of the REST API exposed by the service.

Done using Swagger.

> 4. Provide a simple HTML5 GUI where one could enter trade information, do the validation and display the status of it.

I am focused on back-end services, distributed algorithms, data structures and low latency solutions. UI is not my strong part.

##### Sample request
    [
      {
        "id": "5c6ea698-533c-41e5-9491-7fffa53ba2fa",
        "customer": "PLUTO1",
        "ccyPair": "EURUSD",
        "type": "Spot",
        "direction": "BUY",
        "tradeDate": "2016-08-11",
        "amount1": 1000000,
        "amount2": 1120000,
        "rate": 1.12,
        "valueDate": "2016-08-15",
        "legalEntity": "CS Zurich",
        "trader": "Johann Baumfiddler"
      },
      {
        "id": "c80c5d73-354f-4d9a-a0e7-2c4d1cc8094b",
        "customer": "PLUTO3",
        "ccyPair": "EURUSD",
        "type": "VanillaOption",
        "style": "AMERICAN",
        "direction": "SELL",
        "strategy": "CALL",
        "tradeDate": "2016-08-11",
        "amount1": 1000000,
        "amount2": 1120000,
        "rate": 1.12,
        "deliveryDate": "2016-08-22",
        "expiryDate": "2016-08-19",
        "exerciseStartDate": "2016-08-10",
        "payCcy": "USD",
        "premium": 0.2,
        "premiumCcy": "USD",
        "premiumType": "%USD",
        "premiumDate": "2016-08-12",
        "legalEntity": "CS Zurich",
        "trader": "Johann Baumfiddler"
      }
    ]

##### Sample reply
    [
      {
        "id": "5c6ea698-533c-41e5-9491-7fffa53ba2fa",
        "status": "OK"
      },
      {
        "id": "c80c5d73-354f-4d9a-a0e7-2c4d1cc8094b",
        "status": "ERROR",
        "reasons": [
          {
            "code": "VAL-004",
            "message": "Unsupported customer."
          },
          {
            "code": "VAL-006",
            "message": "Exercise start date not between trade and expiry dates."
          }
        ]
      }
    ]
