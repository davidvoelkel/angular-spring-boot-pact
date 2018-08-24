# Spring Boot Pact JVM back end provider example

This project contains an example on how [Pact JVM](https://github.com/DiUS/pact-jvm) can be used for testing your [Spring Boot Controllers](https://spring.io/guides/gs/serving-web-content/). Because most of the files were only generated for a Spring Boot base project, here are the ones you should have a look at: 

## Config
* [pom.xml](pom.xml): contains besides all spring boot dependencies the pact-jvm test scoped dependency (au.com.dius:pact-jvm-provider_2.12) required for running Pact JVM.

## Tests
Run `./mvnw test` to execute the unit tests via [Maven](https://maven.apache.org/)
* [pacts/*](pacts/): contains all JSON Pact files that are used by the pact tests
* [UserController.java](src/main/java/de/codecentric/cdc/demo/user/UserController.java): contains the UserController under test
* [UserPactTest.java](src/test/java/de/codecentric/cdc/demo/user/UserPactTest.java): contains the **actual Pact test**, that takes a pact file, fires it against the controller under test and verifies the expected response
* [PactTestSetup.java](src/test/java/de/codecentric/cdc/demo/PactTestSetup.java): contains some test helper stuff to make the actual Pact tests more crisp

