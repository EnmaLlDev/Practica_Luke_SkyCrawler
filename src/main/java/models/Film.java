package models;

import java.util.List;

public class Film {
    private String title;
    private int episode_id;
    private String opening_crawl;
    private String director;
    private String producer;
    private String release_date;
    private List<String> characters;
    private List<String> planets;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> species;
    private String url;

    // Getters
    public String getTitle() { return title; }
    public int getEpisode_id() { return episode_id; }
    public String getOpening_crawl() { return opening_crawl; }
    public String getDirector() { return director; }
    public String getProducer() { return producer; }
    public String getRelease_date() { return release_date; }
    public List<String> getCharacters() { return characters; }
    public List<String> getPlanets() { return planets; }
    public List<String> getStarships() { return starships; }
    public List<String> getVehicles() { return vehicles; }
    public List<String> getSpecies() { return species; }
    public String getUrl() { return url; }
}
