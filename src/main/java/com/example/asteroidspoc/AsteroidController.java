package com.example.asteroidspoc;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AsteroidController {

    AsteroidSearch search;

    public AsteroidController() {
        search = new AsteroidSearch();
    }
    
    @GetMapping("/hello")
    String helloWorld(){
        return "Helloworld, Welcome to github cicd action with heroku demo!";
    }

    @RequestMapping(value = "/closestAsteroids.json")
    public @ResponseBody List<Asteroid> searchByDates(@RequestParam(value="startDate") String startDate, @RequestParam(value="endDate") String endDate) {
        return search.searchByDates(startDate,endDate);
    }

    @RequestMapping(value = "/largestAsteroid.json")
    public @ResponseBody List<Asteroid> largestAsteroidByYear(@RequestParam(value="year") String year) {
        return search.largestAsteroidByYear(year);
    }
}
