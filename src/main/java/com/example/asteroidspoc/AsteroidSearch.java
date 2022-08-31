package com.example.asteroidspoc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class AsteroidSearch {
    RestTemplate restTemplate;
    public List<Asteroid> searchByDates (String startDate, String endDate)
    {
        List<Asteroid> asteroids = new ArrayList<>();
        restTemplate = new RestTemplate();
        final String baseUrl = "https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=DEMO_KEY";
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

            Iterator<Asteroid> asteroidIterator = asteroids.iterator();
            int i = 0;
            while(asteroidIterator.hasNext() && i < 10) {
                ++i;
                Asteroid asteroid = asteroidIterator.next();
                System.out.println(asteroid.getName() + " " + asteroid.getDistance());
            }

        } catch (URISyntaxException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return asteroids;
    }
}
