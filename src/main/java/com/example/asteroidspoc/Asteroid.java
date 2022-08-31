package com.example.asteroidspoc;

public class Asteroid {
    private String name;
    private double distance;
    private double diameter;
    private double magnitude;

    public Asteroid(String name, double distance, double diameter, double magnitude) {
        this.name = name;
        this.distance = distance;
        this.diameter = diameter;
        this.magnitude = magnitude;
    }

    public Asteroid(String name, double distance) {
        this.name = name;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
}
