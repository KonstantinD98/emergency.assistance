package com.example.mergencyssistance.EmergencyWeather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.mergencyssistance.Connection.ConnectionClass;
import com.example.mergencyssistance.R;
import android.graphics.Color;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class EmergencyWeather extends AppCompatActivity {
    EditText etCity, etCountry;
    TextView tvResult;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "86a664ae2252c10d277a287e1957d3fa";
    DecimalFormat df = new DecimalFormat("#.##");
    Connection con;
    Weather weather;
    private WeatherFromApi weatherData;
    GetWeatherDataFromDb getWeatherDataFromDb = new GetWeatherDataFromDb();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_weather);
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
        tvResult = findViewById(R.id.tvResult);

        Button btnCheckChanges = findViewById(R.id.btnCheckChanges);
        btnCheckChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForChanges();
            }
        });
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getWeatherDetails(View view) {
        String tempUrl = "";
        String city = etCity.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        if(city.equals("")){
            tvResult.setText("City field can not be empty!");
        }else{
            if(!country.equals("")){
                tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
            }else{
                tempUrl = url + "?q=" + city + "&appid=" + appid;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String output = "";
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        float pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");
                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                        String clouds = jsonObjectClouds.getString("all");
                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                        String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonResponse.getString("name");
                        tvResult.setTextColor(Color.rgb(68,134,199));
                        output += "Current weather of " + cityName + " (" + countryName + ")"
                                + "\n Temp: " + df.format(temp) + " °C"
                                + "\n Feels Like: " + df.format(feelsLike) + " °C"
                                + "\n Humidity: " + humidity + "%"
                                + "\n Description: " + description
                                + "\n Wind Speed: " + wind + "m/s (meters per second)"
                                + "\n Cloudiness: " + clouds + "%"
                                + "\n Pressure: " + pressure + " hPa";
                        tvResult.setText(output);
                        saveWeatherData(city, temp, feelsLike, humidity, description);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

    }
    private void saveWeatherData(String city, double temp, double feelsLike, int humidity, String description) {
        try {
            con = connectionClass(ConnectionClass.un.toString(),ConnectionClass.pass.toString(),ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            String insertQuery = "INSERT INTO Weather (City, Temperature, feels_like, Humidity, Description) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setString(1, city);
            preparedStatement.setDouble(2, temp);
            preparedStatement.setDouble(3, feelsLike);
            preparedStatement.setInt(4, humidity);
            preparedStatement.setString(5, description);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Weather data saved successfully.");
            } else {
                System.out.println("Failed to save weather data.");
            }

            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void checkForChanges() {
        String city = etCity.getText().toString().trim();
        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(), ConnectionClass.ip.toString());
            weather = getWeatherDataFromDb.GetWeatherForCity(city);
            weatherData = getWeatherDataFromAPI(city);

            String localDataCity = weather.getCity();
            Double localDataTemp = weather.getTemp();
            Double localDataFeelsLike = weather.getFeelsLike();
            int localDataHumidity = weather.getHumidity();
            String localDataDes = weather.getDescription();
            String remoteDataCity = weatherData.getCity();
            Double remoteDataTemp = weatherData.getTemp();
            Double remoteDataFeelsLike = weatherData.getFeelsLike();
            int remoteDataHumidity = weatherData.getHumidity();
            String remoteDataDes = weatherData.getDescription();

            boolean hasChanges = false;

            if (!localDataCity.equals(remoteDataCity) || !localDataTemp.equals(remoteDataTemp) || !localDataFeelsLike.equals(remoteDataFeelsLike) || localDataHumidity != remoteDataHumidity || !localDataDes.equals(remoteDataDes)) {
                hasChanges = true;
            }

            if (hasChanges) {
                showUpdateOptionsDialog(weather, weatherData);
            } else {
                showAlertDialog("No changes", "The local data is up to date.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private WeatherFromApi getWeatherDataFromAPI(String city) {
        String tempUrl = "";

        if (city != null) {
            tempUrl = url + "?q=" + city + "&appid=" + appid;
        }

        // Извличане на данни от API
        try {
            URL url = new URL(tempUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Обработка на получените данни
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray jsonArray = jsonResponse.getJSONArray("weather");
            JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
            String descriptionn = jsonObjectWeather.getString("description");
            JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
            double temperaturee = jsonObjectMain.getDouble("temp") - 273.15;
            double feelsLikee = jsonObjectMain.getDouble("feels_like") - 273.15;
            int humidityy = jsonObjectMain.getInt("humidity");

            // Ограничаване на броя на десетичните цифри до две след запетайката
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            temperaturee = Double.parseDouble(decimalFormat.format(temperaturee));
            feelsLikee = Double.parseDouble(decimalFormat.format(feelsLikee));

            // Създаване на обект от тип WeatherFromApi с извлечените данни
            WeatherFromApi weatherFromApi = new WeatherFromApi(city, temperaturee, feelsLikee, humidityy, descriptionn);

            // Връщане на обекта с данните от API
            return weatherFromApi;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showUpdateOptionsDialog(Weather weather, WeatherFromApi weatherData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Data has changed");
        builder.setMessage("Choose an action:");
        builder.setPositiveButton("Keep local data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Да не прави нищо и да запази старите данни
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Update with remote data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Да промени информацията за конкретния елемент с новата
                updateLocalData(weather, weatherData);
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Duplicate and update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Да дублира стария запис и след това да го обнови с новите данни
                duplicateAndUpdate(weather, weatherData);
                dialog.dismiss();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateLocalData(Weather weather, WeatherFromApi weatherData) {
        try {con = connectionClass(ConnectionClass.un.toString(),ConnectionClass.pass.toString(),ConnectionClass.db.toString(),
                ConnectionClass.ip.toString());
            String updateQuery = "UPDATE Weather SET city =?, temperature =?, feels_like =? ,humidity=? ,description = ? WHERE City = ?";
            PreparedStatement updateStatement = con.prepareStatement(updateQuery);
            updateStatement.setString(1, weatherData.getCity());
            updateStatement.setDouble(2, weatherData.getTemp());
            updateStatement.setDouble(3, weatherData.getFeelsLike());
            updateStatement.setInt(4, weatherData.getHumidity());
            updateStatement.setString(5, weatherData.getDescription());
            updateStatement.setString(6, weather.getCity());
            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show();
            }

            updateStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void duplicateAndUpdate(Weather weather, WeatherFromApi weatherData) {
        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(), ConnectionClass.ip.toString());

            // Дублиране на данните
            String duplicateQuery = "INSERT INTO Weather (City, temperature, feels_like, humidity, description, duplicate_column) SELECT TOP 1 City, temperature, feels_like, humidity, description, 'true' AS duplicate_column FROM Weather WHERE City = ?";
            PreparedStatement duplicateStatement = con.prepareStatement(duplicateQuery);
            duplicateStatement.setString(1, weather.getCity());
            int rowsAffected = duplicateStatement.executeUpdate();
            duplicateStatement.close();

            weatherData = getWeatherDataFromAPI(weatherData.getCity());
            // Актуализиране на данните
            String updateQuery = "UPDATE Weather SET temperature = ?, feels_like = ?, humidity = ?, description = ? WHERE City = ?";
            PreparedStatement updateStatement = con.prepareStatement(updateQuery);
            updateStatement.setDouble(1, weatherData.getTemp());
            updateStatement.setDouble(2, weatherData.getFeelsLike());
            updateStatement.setInt(3, weatherData.getHumidity());
            updateStatement.setString(4, weatherData.getDescription());
            updateStatement.setString(5, weatherData.getCity());
            rowsAffected += updateStatement.executeUpdate();
            updateStatement.close();

            if (rowsAffected > 0) {
                Toast.makeText(this, "Data duplicated and updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to duplicate and update data", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Затваряне на връзката с базата данни
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("NewApi")
    public Connection connectionClass(String user, String password, String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + server + "/" + database + ";user=" + user + ";password=" + password + ";";
            connection = DriverManager.getConnection(connectionURL);
        } catch (Exception e) {
            Log.e("SQL Connection Error : ", e.getMessage());
        }
        return connection;
    }
}