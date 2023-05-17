package com.example.mergencyssistance.GetData;

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

public class GetDocData {
    Connection con;

    public List<Doctor> GetAllDoctors() {
        List<Doctor> doctorList = new ArrayList<>();

        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from DoctorTable");
            rs.next();
            while (rs.next()) {
                Doctor doctor = new Doctor();

                doctor.setDoctorID(rs.getInt("doctorID"));
                doctor.setFirstNameD(rs.getString("firstName"));
                doctor.setLastNameD(rs.getString("lastName"));
                doctor.setEmailD(rs.getString("email"));
                doctor.setPhone(rs.getString("phone"));
                doctor.setSpeciality(rs.getString("speciality"));

                doctorList.add(doctor);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorList;
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
