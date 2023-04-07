package com.example.mergencyssistance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mergencyssistance.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DoctorActivity extends AppCompatActivity {
    EditText ETFNameD, ETLNameD, ETTelephoneD, ETEmailD;
    Spinner spinnerTypeD;
    Button btnAddDoc, btnBackDoc, btnViewDoc;
    TextView statusdoc;
    Connection con;
    Statement stmt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        ETFNameD = findViewById(R.id.ETFNameD);
        ETLNameD = findViewById(R.id.ETLNameD);
        ETTelephoneD = findViewById(R.id.ETTelephoneD);
        ETEmailD = findViewById(R.id.ETEmailD);
        spinnerTypeD = findViewById(R.id.spinnerTypeD);
        btnAddDoc = findViewById(R.id.btnAddDoc);
        btnBackDoc = findViewById(R.id.btnBackDoc);
        btnViewDoc = findViewById(R.id.btnViewDoc);
        statusdoc = findViewById(R.id.statusdoc);

        btnBackDoc.setOnClickListener(onClick);
        btnViewDoc.setOnClickListener(onClick);


        btnAddDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DoctorActivity.adddoctor().execute("");

            }
        });

    }


    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;

            switch (view.getId()) {
                case R.id.btnBackDoc:
                    intent = new Intent(DoctorActivity.this, HomeActivity.class);
                    break;
                case R.id.btnViewDoc:
                    intent = new Intent(DoctorActivity.this, DoctorViewActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };

    public class adddoctor extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            statusdoc.setText("Sending Data to Database");

        }

        @Override
        protected void onPostExecute(String s) {
            statusdoc.setText("Doctor Added");
            ETFNameD.setText("");
            ETLNameD.setText("");
            ETTelephoneD.setText("");
            ETEmailD.setText("");
        }

        @Override
            protected String doInBackground(String... strings) {
                try {
                    con = connectionClass(ConnectionClass.un.toString(),ConnectionClass.pass.toString(),ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con == null){
                        z = "Check Your Internet Connection";
                    }
                    else{
                        String sql = "INSERT INTO DoctorTable (firstName,lastName, email, phone, speciality) VALUES ('"+ETFNameD.getText()+"','"+ETLNameD.getText()
                                +"','"+ETEmailD.getText()+"','"+ETTelephoneD.getText()+"','"+spinnerTypeD.getSelectedItem()+"')";
                        stmt = con.createStatement();
                        stmt.executeUpdate(sql);
                    }

                }catch (Exception e){
                    isSuccess = false;
                    z = e.getMessage();

                }


                return z;
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



