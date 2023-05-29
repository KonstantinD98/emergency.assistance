package com.example.mergencyssistance.EmergencyWeather;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import com.example.mergencyssistance.Connection.ConnectionClass;
import com.example.mergencyssistance.Entity.Doctor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetWeatherDataFromDb {
    Connection con;

    public Weather GetWeatherForCity(String city) {
        Weather weather = new Weather();

        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from weather where city = '" +  city + "'");//+ " and duplicate_column = 0");
            if (rs.next()) {

                weather.setCity(rs.getString("city"));
                weather.setTemp(rs.getDouble("temperature"));
                weather.setFeelsLike(rs.getDouble("feels_like"));
                weather.setHumidity(rs.getInt("humidity"));
                weather.setDescription(rs.getString("description"));
                //weather.setDuplicate_column(rs.getBoolean("duplicate_column"));


            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return weather;
    }

    public List<Weather> GetAllWeathers() {
        List<Weather> weatherList = new ArrayList<>();

        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from weather");
            rs.next();
            while (rs.next()) {
                Weather weather = new Weather();

                weather.setCity(rs.getString("city"));
                weather.setTemp(rs.getDouble("temperature"));
                weather.setFeelsLike(rs.getDouble("feels_like"));
                weather.setHumidity(rs.getInt("humidity"));
                weather.setDescription(rs.getString("description"));


                weatherList.add(weather);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return weatherList;
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

