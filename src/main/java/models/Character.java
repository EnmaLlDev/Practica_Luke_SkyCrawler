package models;

import java.util.List;

public class Character {
    private String name;
    private String height;
    private String mass;
    private String hair_color;
    private String skin_color;
    private String eye_color;
    private String birth_year;
    private String gender;
    private List<String> starships;
    private List<String> vehicles;

    // Getters
    public String getName() { return name; }
    public String getHeight() { return height; }
    public String getMass() { return mass; }
    public String getHair_color() { return hair_color; }
    public String getSkin_color() { return skin_color; }
    public String getEye_color() { return eye_color; }
    public String getBirth_year() { return birth_year; }
    public String getGender() { return gender; }
    public List<String> getStarships() { return starships; }
    public List<String> getVehicles() { return vehicles; }
}

