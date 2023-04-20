package com.example.mergencyssistance;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mergencyssistance.Connection.ConnectionClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class VetViewActivity extends AppCompatActivity {
    private ListView vetListView;
    private ArrayList<String> vetList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    Connection con;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_view);

        vetListView = findViewById(R.id.vet_list_view);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vetList);
        vetListView.setAdapter(arrayAdapter);

        Button viewButton = findViewById(R.id.viewvet_button);
        Button exitButton = findViewById(R.id.exitViewVet);
        Button searchButton = findViewById(R.id.searchViewVet);

        vetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(VetViewActivity.this, "You clicked: " + selectedItem, Toast.LENGTH_SHORT).show();

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VetViewActivity.this, SearchVetActivity.class));

            }
        });


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VetViewActivity.this, VetActivity.class));
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
                            String query = "SELECT * FROM VetTable";
                            PreparedStatement stmt = con.prepareStatement(query);
                            ResultSet rs = stmt.executeQuery();

                            // Retrieve the data from the query result
                            while (rs.next()) {
                                String firstName = rs.getString("firstName");
                                String lastName = rs.getString("lastName");
                                String email = rs.getString("email");
                                String phone = rs.getString("phone");
                                String vet = "Name: " + firstName + "\nLastname: " + lastName + "\nEmail: " + email
                                        + "\nTelephone: " + phone;
                                vetList.add(vet);
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
        public Connection connectionClass (String user, String password, String database, String
        server){
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