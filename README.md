# JSONPlaceholder API Test Framework

This is a maven project

https://maven.apache.org/guides/getting-started/#:~:text=groupId%20This%20element%20indicates%20the,maven


**1. Download, install and setup software dependencies**

- Latest version of Maven

- Java SE Development Kit


**2. Setup environment variables on your system**

Add **JAVA_HOME** environment variable
```
JAVA_HOME=C:\Program Files\Java\jdk-19
```

Add **JAVA_HOME\bin** to your Path environment variable
```
PATH=...;%JAVA_HOME%\bin
```

Add **MAVEN_HOME** environment variable
```
MAVEN_HOME=C:\Program Files\apache-maven-3.8.7
```

Add **MAVEN_HOME\bin** to your Path environment variable
```
PATH=...;%MAVEN_HOME%\bin
```


**3. Clone this repository onto your file system**


**4. Running the API tests**

Change directory to the repository on your file system:

```
cd jsonplaceholder-api-test-framework
```

Run the following command in a terminal, this command will run feature files that are tagged with **@complete**

```
mvn -Dcucumber.filter.tags="@complete" test
```


**Tagged Scenarios**

The scenarios are all tagged individually so that you can conveniently run one scenario or a feature file of scenarios. This gives the developer flexibility to run whatever test they wish.

**@complete** when running the scenarios with this tag, it will run all the feature files tagged with **@complete**.

**@posts** when using this tag it will run all the scenarios in the `posts.feature` file. You can also run each post scenario individually, e.g. @posts1 to run the first scenario, @posts2 will run the second scenario.

**@comments** when using this tag it will run all the scenarios in the `comments.feature` file. You can also run each comment scenario individually, e.g. @comments1 to run the first scenario, @comments2 will run the second scenario.


**Config Variables**

These config varaibles allow us to parameterize our framework and give us more flexibility in how we run them when we integrate on the CI


**Tags**

Specifying this config variable will run all feature files with the associated tag, **E.g. @posts1**

**Examples:**

```
-Dcucumber.filter.tags="@posts1"
```


**Maven Dependencies**

- JUnit - Java unit test runner
- Cucumber Gherkin - Parses gherkin feature files and matches the steps with the glue code
- Cucumber Java - Java flavour of the Cucumber BDD tool
- Cucumber JUnit - Cucumber's extension of the Java unit test runner
- Cucumber Picocontainer - Lighweight dependency injection tool
- Rest Assured - Java API testing library to perform API testing
- Gson - Java Library from Google used for parsing and manipulating JSON 

- Maven Compiler Plugin - Maven plugin for compiling maven projects
- Maven Surefire Plugin - Maven plugin that allows us to run the unit tests in command line


**Folder Structure**

**- com.api.test**

This package contains the classes that abstract the logic of the REST API endpoints. Each class contains API methods that are used in the tests

**- com.api.test.config**

In this package you will find the Config class. This class retrieves the configuration values from property files to bootstrap the tests.

**- com.api.test.data.objects**

This package is where the data access objects live. Th

**- com.api.test.runner**

This package contains the Cucumber configuration for running all the Cucumber tests. This file is the starting point and is required so that you can run your entire test suite in JUnit.

**- com.api.test.stepdefs**

This package contains the step definition classes. Cucumber will parse the gherkin feature files in the `features` folder and check in the `com.api.test.stepdefs` package for the corresponding steps.

**- com.api.test.utils.json**

This package contains the `JsonUtils` utility class for parsing the JSON and binding it to `JsonObject` objects. So that we can get assert values from the JSON in the tests.


**Dependency Injection**

Cucumber Picocontainer is a small, easy to use and lightweight dependency injection library that is used in this test framework. Picocontainer is easy to setup because it does not use glue code or configuration. You just need to define your dependencies in the class constructor.

How it works is that Cucumber will scan all the step definition classes and pass them to Picocontainer. Picocontainer will then use dependency injection to instantiate the dependencies defined in the step definition class constructor. You will notice that in the step definition constructors there is a test context object. The purpose of the test context object is used to save test state (response) between steps which is needed because we are performing API requests and we need to assert the status code and the JSON response payload. Picocontainer will create a new instance of the test context object every time a scenario is run. The test context object is wiped clean after a scenario is run, which means we do not need to worry about state being shared across scenarios. This also gives us the benefit of asserting state from one step definition class to another. E.g. Calling an API call in the step definition `PostStepDefs` class and we can assert the state of the response in `CommentStepDefs` class.


**Observations of the System Under Test**


**DISCLAIMER:**
In the guide page of JSONPlaceholder there is a disclaimer that states:

"**Important**: resource will not be really updated on the server but it will be faked as if."

I'm aware that this is a test API and the behaviour I have observed from the system under test is that the state is not actually being updated/saved in the backend therefore some of the tests I have written are going to fail and this is expected. In an ideal world I would have written the tests with appropriate test setup and test cleanup in mind to ensure that before the test starts, the system under test has a clean state and the state is cleaned up after the tests have run.


**Examples:**

- **POST** endpoints do not create resources in the backend. When I executed a **POST** request to create a post it will return a successful 201 status code. However when I execute a **GET** request to get back all the posts I am not able to retrieve the post I just created. This is not expected behaviour as I should be able to create a new resource.


- **DELETE** endpoints also do not remove resources in the backend. I have executed a successful **DELETE** request for a post resource but when I executed a **GET** request to get back all the posts I can still retrieve it. This is not the expected behaviour as I should not be able to fetch a resource I just deleted.

