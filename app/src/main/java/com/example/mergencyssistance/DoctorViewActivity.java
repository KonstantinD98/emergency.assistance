package com.example.mergencyssistance;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mergencyssistance.Connection.ConnectionClass;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DoctorViewActivity extends AppCompatActivity {
    private ListView doctorListView;
    private ArrayList<String> doctorList = new ArrayList<>();

    private ArrayAdapter<String> arrayAdapter;
    Connection con;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view);
        doctorListView = findViewById(R.id.doctor_list_view);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, doctorList);
        doctorListView.setAdapter(arrayAdapter);

        Button viewButton = findViewById(R.id.view_button);
        Button searchButton = findViewById(R.id.search_button);
        Button ExitButton = findViewById(R.id.exitView);
        doctorListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorViewActivity.this,SearchDoctorActivity.class ));
            }
        });


        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorViewActivity.this, DoctorActivity.class));
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                                    ConnectionClass.ip.toString());
                            String query = "SELECT * FROM DoctorTable";
                            PreparedStatement stmt = con.prepareStatement(query);
                            ResultSet rs = stmt.executeQuery();

                            // Retrieve the data from the query result
                            while (rs.next()) {
                                String firstName = rs.getString("firstName");
                                String lastName = rs.getString("lastName");
                                String email = rs.getString("email");
                                String phone = rs.getString("phone");
                                String doctor = "Name: " + firstName + "\nLastname: " + lastName + "\nEmail: " + email
                                        + "\nTelephone: " + phone;
                                doctorList.add(doctor);
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

        doctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(DoctorViewActivity.this, "You clicked: " + selectedItem, Toast.LENGTH_SHORT).show();

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
