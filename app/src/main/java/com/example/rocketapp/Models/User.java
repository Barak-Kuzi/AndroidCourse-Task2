package com.example.rocketapp.Models;

public class User {

    private String userName;
    private int scoreValue;
    private double latitude, longitude;

    public User(String userName, int scoreValue, double latitude, double longitude) {
        this.userName = userName;
        this.scoreValue = scoreValue;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUserName() {
        return userName;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
