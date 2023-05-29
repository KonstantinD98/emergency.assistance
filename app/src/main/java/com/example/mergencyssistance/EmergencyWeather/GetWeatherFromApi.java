package com.example.mergencyssistance.EmergencyWeather;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetWeatherFromApi {
    private static final String API_URL = "https://api.weatherapi.com/v1/current.json?key=86a664ae2252c10d277a287e1957d3fa&q=";

    public WeatherFromApi getWeatherData(String city) {
        try {
            String apiUrl = API_URL + city;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                return processResponseData(responseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getWeatherData(city);
    }

    private WeatherFromApi processResponseData(String responseData) {
        try {
            JSONObject json = new JSONObject(responseData);


            String city = json.getJSONObject("location").getString("name");
            double temperature = json.getJSONObject("current").getDouble("temp_c");
            int humidity = json.getJSONObject("current").getInt("humidity");
            double feelsLike = json.getJSONObject("current").getDouble("feelslike_c");
            String description = json.getJSONObject("current").getJSONObject("condition").getString("text");


            WeatherFromApi weatherFromApi = new WeatherFromApi();
            weatherFromApi.setCity(city);
            weatherFromApi.setTemp(temperature);
            weatherFromApi.setHumidity(humidity);
            weatherFromApi.setFeelsLike(feelsLike);
            weatherFromApi.setDescription(description);

            return weatherFromApi;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}