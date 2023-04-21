package com.example.mergencyssistance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.mergencyssistance.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PatientVIewActivity extends AppCompatActivity {
    private ListView patientListView;
    private ArrayList<String> patientList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    Connection con;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view);
        patientListView = findViewById(R.id.patient_list_view);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, patientList);
        patientListView.setAdapter(arrayAdapter);

        Button viewButton = findViewById(R.id.viewpatient_button);
        Button searchButton = findViewById(R.id.searchPatient_button);
        Button ExitButton = findViewById(R.id.exitViewPatient);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientVIewActivity.this,PatientSearchActivity.class));
            }
        });

        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientVIewActivity.this,PatientActivity.class));
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            con = connectionClass(ConnectionClass.un.toString(),ConnectionClass.pass.toString(),ConnectionClass.db.toString(),
                                    ConnectionClass.ip.toString());
                            String query = "SELECT * FROM PatientTable";
                            PreparedStatement stmt = con.prepareStatement(query);
                            ResultSet rs = stmt.executeQuery();

                            // Retrieve the data from the query result
                            while (rs.next()) {
                                String firstName = rs.getString("firstName");
                                String lastName = rs.getString("lastName");
                                String email = rs.getString("phone");
                                String phone = rs.getString("email");
                                String patient = "Name: " + firstName + "\nLastname: " + lastName + "\nTelephone: " + phone
                                        + "\nEmail: " + email;
                                patientList.add(patient);
                            }

                            // Close the connection and the query result
                            rs.close();
                            stmt.close();
                            con.close();

                            // Update the ListView on the main thread
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    arrayAdapter.notifyDataSetChanged();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

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
