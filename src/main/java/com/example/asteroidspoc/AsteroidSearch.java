package com.example.asteroidspoc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class AsteroidSearch {
    RestTemplate restTemplate;
    URI uri;

    @Cacheable
    public List<Asteroid> largestAsteroidByYear(String year)
    {
        List<Asteroid> asteroids = new ArrayList<>();
        restTemplate = new RestTemplate();
        final String baseUrl = "https://api.nasa.gov/neo/rest/v1/neo/browse?api_key=DEMO_KEY";
        try {
            uri = new URI(baseUrl);
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode objects = root.get("near_earth_objects");

            Iterator<JsonNode> objIterator = objects.iterator();
            while (objIterator.hasNext()) {
                JsonNode rawAsteroid = objIterator.next();
                JsonNode approachData = rawAsteroid.findValue("close_approach_data");
                Iterator<JsonNode> approaches = approachData.iterator();
                while (approaches.hasNext()) {
                    JsonNode approach = approaches.next();
                    //System.out.println(approach.findValue("close_approach_date").asText() + " " + rawAsteroid.findValue("close_approach_date").asText().substring(0,4) + " " + year);
                    if(approach.findValue("close_approach_date").asText().substring(0,4).equals(year)){
                        asteroids.add(new Asteroid(rawAsteroid.findValue("name").asText(),rawAsteroid.findValue("astronomical").asDouble(),rawAsteroid.findValue("estimated_diameter_min").asDouble(),rawAsteroid.findValue("absolute_magnitude_h").asDouble()));
                    }
                }
            }

            Comparator<Asteroid> byDiameter = Comparator.comparing(Asteroid::getDiameter);
            Collections.sort(asteroids, byDiameter);

            if (asteroids.size() > 1){
                asteroids.subList(1, asteroids.size()).clear();
            }

        } catch (URISyntaxException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return asteroids;
    }
    @Cacheable
    public List<Asteroid> searchByDates (String startDate, String endDate)
    {
        List<Asteroid> asteroids = new ArrayList<>();
        restTemplate = new RestTemplate();
        final String baseUrl = "https://api.nasa.gov/neo/rest/v1/feed?start_date="+startDate+"&end_date="+endDate+"&api_key=DEMO_KEY";
        URI uri = null;
        try {
            uri = new URI(baseUrl);
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode objects = root.get("near_earth_objects");

            Iterator<JsonNode> objIterator = objects.iterator();
            while (objIterator.hasNext()) {
                JsonNode object = objIterator.next();
                Iterator<JsonNode> rawAsteroids = object.iterator();
                while (rawAsteroids.hasNext()) {
                    JsonNode rawAsteroid = rawAsteroids.next();
                    asteroids.add(new Asteroid(rawAsteroid.findValue("name").asText(),rawAsteroid.findValue("astronomical").asDouble()));
                }
            }

            Comparator<Asteroid> byDistance = Comparator.comparing(Asteroid::getDistance);
            Collections.sort(asteroids, byDistance);

            if (asteroids.size() > 10){
                asteroids.subList(10, asteroids.size()).clear();
            }

        } catch (URISyntaxException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return asteroids;
    }
}
