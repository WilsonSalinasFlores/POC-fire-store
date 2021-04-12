package com.example.pocfirestoregcp.model;

import java.util.List;

public class City {

    private String name;
    private  String state;
    private String country;
    private Boolean capital;
    private Long population;
    private List<String> regions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getCapital() {
        return capital;
    }

    public void setCapital(Boolean capital) {
        this.capital = capital;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }
    public City()
    {
        this.name = "";
        this.state = "";
        this.country = "";
        this.capital = false;
        this.population = 0L;
        this.regions = null;

    }
    public City(String name, String state, String country,
                Boolean capital, Long population, List<String> regions) {
        this.name = name;
        this.state = state;
        this.country = country;
        this.capital = capital;
        this.population = population;
        this.regions = regions;
    }

}
