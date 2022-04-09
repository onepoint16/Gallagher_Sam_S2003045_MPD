package com.example.gallagher_sam_s2003045;

public class TrafficScotlandInfo {

    private String roadName;
    private String description;
    private String published;
    private String coordinates;

    public TrafficScotlandInfo() { }

    public TrafficScotlandInfo(String roadName, String description, String published, String coordinates) {
        this.roadName = roadName;
        this.description = description;
        this.published = published;
        this.coordinates = coordinates;
    }

    public String getRoad() {
        return roadName;
    }

    public void setRoad(String roadName) {
        this.roadName = roadName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String toString()
    {
        String result;
        result = "Road Name: " + roadName + " Description: " + description + " Coordinates: " + coordinates + " Date: " + published;
        return  result;
    }
}
