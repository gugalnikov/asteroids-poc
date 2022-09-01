This is a tool which provides data on nearby asteroids using [NASA Asteroids API](https://api.nasa.gov/)

# Technology Stack

- Java 8
- Spring Boot  
- Maven  

# Design Considerations

Though this is only a PoC, here are some considerations for an MVP: 

# Features

- closestAsteroids: Shows 10 asteroids that passed the closest to Earth between two user-provided dates  
- largestAsteroid: Shows characteristics of the largest asteroid (estimated diameter) passing Earth during a user-provided year  

# Development

- The original development was done using IntelliJ IDEA 2022.2.1 (Ultimate Edition), but any Java-compatible IDE will do.  
- This is basically a Spring Boot web application created by using Spring Initializr  
- APIs are published and consumed by leveraging the RestTemplate built-in class  
- No 3rd party dependencies are currently being used  
- Basic response caching has been enabled and added by annotating methods to use the built-in Spring Boot Starter Cache  
- Some further development suggestions:  
  - Add error handling  
  - Use of developer API key instead of DEMO_KEY  
  - Create generic handler for API calls to reduce code  
  - Externalize configuration  
  - Add more robust caching solution such as Redis, Couchbase, etc.  

# Usage

## Running the application

The application can be started both from an IDE of your choice or from the command line:  

`./mvnw spring-boot:run`  

You can also package and run it using a docker container:  

`./mvnw package`  
`docker build -t springio/gs-spring-boot-docker .`  
`docker run -p 8080:8080 springio/gs-spring-boot-docker`  

## Retrieving Data

The following 2 endpoints are available:  

`http://<host>:<port>/closestAsteroids.json?startDate=<startDate>&endDate=<endDate>`  
`http://<host>:<port>/largestAsteroid.json?year=<year>`  

Considerations:

- Response is a JSON document  
- Date format is always: YYYY-MM-DD  
- The NASA API has a hard limit of 7 days for retrieving data from it  
- This PoC uses the DEMO_KEY to retrieve data, for further use you need to replace this with a [NASA developer key](https://api.nasa.gov/#signUp)   

# CI / CD

This repo includes GitHub Actions configuration for deploying to Heroku Cloud. This will happen automatically when merging into master.   
Sample endpoints:    

`https://young-wave-56035.herokuapp.com/closestAsteroids.json?startDate=2016-09-07&endDate=2016-09-10`    
`https://young-wave-56035.herokuapp.com/largestAsteroid.json?year=2003`. 

