##Survey API
Survey API implementation.

## Technology stack
* Java 11
* Maven
* Spring boot
* JPA
* H2database- for end to end test
* PostgreSQL - used as a testcontainers for integration test 
* Junit 5
* Springdoc(swagger) - for API documentation
* Docker - Integration test needs testcontainers
* Docker-compose (Optional)


##Setup
* Install docker if not install yet, because PostgreSQL testcontainers used for integration test
* Test and build: 
```
$ mvn clean install
```
* Run application:
```
$ mvn clean spring-boot:run
```
* Swagger API documentation available at:
  
    http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config


* End to End test 
    * Go to above swagger API docs
    * Create patient with following endpoint 
    ```
    /api/v1/patient
    ```
    * Create survey with following endpoint
    ```
    /api/v1/survey
    ```
    * Take part in survey with following endpoint
        * Use the above patientId & surveyId in request body
        * Provides question(s) and answer(s) in request body" 
    ```
    /api/v1/survey/take-part
    ```
* Run API and PostgreSQL as a microservice together using Docker-compose (Optional)
    * Build the project
    ```
    $ mvn clean install
    ```
    ```
    $ docker-compose up -d --build
    ```
    * Check service logs
    ```
    $ docker-compose logs -f
    ```
    * Stop docker-compose services
    ```
    $ docker-compose stop
    ```
    