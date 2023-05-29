package com.example.mergencyssistance.EmergencyWeather;

public class WeatherFromApi {
    String city;
    Double temp;
    Double feelsLike;
    int humidity;
    String description;

    public WeatherFromApi(String city, Double temp, Double feelsLike, int humidity, String description) {
        this.city = city;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.description = description;
    }
    public WeatherFromApi(){

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
    @Override
    public String toString() {
        return "WeatherFromApi{" +
                "city='" + city + '\'' +
                ",description='" + description + '\'' +
                ", temperature='" + temp + '\'' +
                ", feelsLike='" + feelsLike + '\'' +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}

