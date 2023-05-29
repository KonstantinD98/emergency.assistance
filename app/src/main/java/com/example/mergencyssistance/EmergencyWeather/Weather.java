package com.example.mergencyssistance.EmergencyWeather;

public class Weather {
    String city;
    Double temp;
    Double feelsLike;
    int humidity;
    String description;
    Boolean duplicate_column;

    public Weather(String city, Double temp, Double feelsLike, int humidity, String description) {
        this.city = city;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.description = description;
    }
    public Weather(){

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDuplicate_column() {
        return duplicate_column;
    }

    public void setDuplicate_column(Boolean duplicate_column) {
        this.duplicate_column = duplicate_column;
    }
}
