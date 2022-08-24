# LoadBalancer
An load balancer that contains implementations to access providers randomly and other that the providers will be accessed in a sequential mode.

## Running tests

```shell
mvn test
```

## User simulations
It's possible to simulate multiple users, using the code inside the Main class, that you can change and run using the following command:

```shell
mvn clean compile exec:java -Dexec.mainClass="com.iptiq.Main"
```

## Requirements

* JDK 17