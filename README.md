# Gaggle Candidate Assignment
## Requirements
Assume there is a database of your choice (can be relational or non-relational) with records that have the following fields:
* Unique identifier 
* First name 
* Last name

Write the code for a single AWS Lambda function in (TypeScript, Java or Python) that provides two possible operations for a client to call:
* Get a record by ID 
* Search for records by name
  * **Example**: If the database contains the full name of "Bruce Wayne", then it should be reasonable for the function to return this result given any of the following search strings: "bru", "Bruce", "Wayne", "Bruce Wayne", etc.

* Both the input and output to and from the function should be formatted in JSON. 
* The connection information for the database is passed into the Lambda function using environment variables, which can be named according to your choosing. The Lambda function will be configured using the (Node.js 14, Java 8 or Python 3.8) runtime environment. 
* The following components are **NOT** in scope for this exercise:
  * You do **NOT** have to create a "live" demo (i.e. you don't need to deploy the code to a real AWS Lambda function, and you don't need to configure a real database)
  * You do **NOT** need to provide any "infrastructure as code" implementation.
* **Bonus**: Leverage dependency injection

## Instructions
### Building
* Assumptions
  1. Java (8+) is installed (tested with openjdk version "1.8.0_275")
  2. Maven (3+) is installed (tested with Apache Maven 3.6.3)

* Compile, Unit Test (with Code Coverage)
    ```commandline
    % mvn clean verify
    ```
### Code Coverage Reports
You can find code coverage reports at `target/site/jacoco/index.html`
