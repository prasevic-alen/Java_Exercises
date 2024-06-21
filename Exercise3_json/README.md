## University of Luxembourg - Java for Enterprise Applications projects

### Exercise 3 - Building a GraphQL or REST API with SpringBoot
    * The application can cloned from https://github.com/prasevic-alen/Java_Exercises.git .
    * The application is in the folder Exercise3_json folder.
    * Folder structure is presented in the Info/projectStructure.txt.
    * Sample screen cast on functionality is present in the exercise2/Info/ folder.


## Running the application

    Building and running application
    $ gradle build [ This step can be skiped ]
    $ gradle bootRun

## Using the application
    You can use the GraphiQL interface to test and execute GraphQL queries.
    - Access the GraphiQL interface for testing GraphQL queries at:
      http://localhost:8080/Exercise3

## Example queries, that could be as well
    Example queries can be found in Info/*.sql

## Logging
    I added loging during the developemtn, but still left them to see how data exchange flows
    
## Application is tested
    * Tested on the Java 21.0.1, and Gradle 8.8.

## Some useful paths for the example 
    “http://localhost:8080/”                - for the home page.            (home.html)
    “http://localhost:8080/not_exing/error” – for errors not existing path  (error.html)
    “http://localhost:8080/api/refetch”     - for the re-fetching data      (refetch.html)
    "http://localhost:8080/Exercise3"       - for the GraphiQL page


## Verified on other functional Linux machine with 
Gradle 8.8
------------------------------------------------------------

Build time:   2024-05-31 21:46:56 UTC
Revision:     4bd1b3d3fc3f31db5a26eecb416a165b8cc36082

Kotlin:       1.9.22
Groovy:       3.0.21
Ant:          Apache Ant(TM) version 1.10.13 compiled on January 4 2023
JVM:          21.0.3 (Ubuntu 21.0.3+9-Ubuntu-1ubuntu122.04.1)
OS:           Linux 5.15.0-107-generic amd64
