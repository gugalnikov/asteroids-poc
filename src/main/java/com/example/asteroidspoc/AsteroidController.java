package com.example.asteroidspoc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AsteroidController {

    AsteroidSearch search;

    public AsteroidController() {
        search = new AsteroidSearch();
    }

    @RequestMapping(value = "/closestAsteroids.json")
    public @ResponseBody List<Asteroid> searchByDates(@RequestParam(value="startDate") String startDate, @RequestParam(value="endDate") String endDate) {
        return search.searchByDates(startDate,endDate);
    }
}
