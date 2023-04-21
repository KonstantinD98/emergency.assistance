package com.example.mergencyssistance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mergencyssistance.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PatientSearchActivity extends AppCompatActivity {

    EditText ETIDPatient, ETFNameSearchPatient, ETLNameSearchPatient, ETEmailSearchPatient,ETTelephoneSearchPatient, ETSearchPatient;
    Spinner spinnerTypePatient;
    Button btnDeletePatient, btnEditPatient, btnBackSearchPatient, btnSearchPatient;
    Connection con;
    Statement st;
    String q="";
    int result = 0;
    ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_search);

        ETIDPatient = findViewById(R.id.ETIDPatient);
        ETFNameSearchPatient = findViewById(R.id.ETFNameSearchPatient);
        ETLNameSearchPatient = findViewById(R.id.ETLNameSearchPatient);
        ETEmailSearchPatient = findViewById(R.id.ETEmailSearchPatient);
        ETTelephoneSearchPatient = findViewById(R.id.ETTelephoneSearchPatient);
        ETSearchPatient = findViewById(R.id.ETSearchPatient);
        spinnerTypePatient = findViewById(R.id.spinnerTypePatient);
        btnDeletePatient = findViewById(R.id.btnDeletePatient);
        btnEditPatient = findViewById(R.id.btnEditPatient);
        btnBackSearchPatient = findViewById(R.id.btnBackSearchPatient);
        btnSearchPatient = findViewById(R.id.btnSearchPatient);

        btnBackSearchPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientSearchActivity.this, PatientVIewActivity.class));
            }
        });

        btnSearchPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p_id;
                p_id = Integer.parseInt(ETSearchPatient.getText().toString());
                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    q = "SELECT * FROM PatientTable WHERE patientID=" + p_id;
                    st = con.createStatement();
                    rs = st.executeQuery(q);
                    if (rs.next()) {
                        ETIDPatient.setText(rs.getString(1));
                        ETFNameSearchPatient.setText(rs.getString(2));
                        ETLNameSearchPatient.setText(rs.getString(3));
                        ETTelephoneSearchPatient.setText(rs.getString(4));
                        ETEmailSearchPatient.setText(rs.getString(5));
                        //spinnerTypePatient.setSelection(rs.findColumn("speciality")); ????
                    }
                    rs.close();
                    con.close();

                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }

            }
        });

        btnEditPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p_id;
                String f_PatientName, l_PatientName, PatientPhone, PatientEmail;
                result = 0;
                p_id = Integer.parseInt(ETIDPatient.getText().toString());
                f_PatientName = ETFNameSearchPatient.getText().toString();
                l_PatientName = ETLNameSearchPatient.getText().toString();
                PatientPhone = ETTelephoneSearchPatient.getText().toString();
                PatientEmail = ETEmailSearchPatient.getText().toString();

                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "update PatientTable set firstName='" + f_PatientName + "', lastName ='" + l_PatientName + "', phone ='" + PatientPhone+ "', email='" + PatientEmail +"' where patientID=" +p_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(PatientSearchActivity.this, "Record Updated", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(PatientSearchActivity.this, "Record NOT Updated", Toast.LENGTH_LONG).show();
                        }
                        cleanPatient();
                        con.close();
                        st.close();
                    }

                }
                catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                    Toast.makeText(PatientSearchActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDeletePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p_id;
                result = 0;
                p_id = Integer.parseInt(ETIDPatient.getText().toString());
                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "delete from PatientTable where patientID=" +p_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(PatientSearchActivity.this, "Record Deleted", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(PatientSearchActivity.this, "Record NOT Deleted", Toast.LENGTH_LONG).show();
                        }
                        cleanPatient();
                        con.close();
                    }

                }
                catch (Exception e){
                    Log.e("Error : ", e.getMessage());
                }

            }
        });
    }


    public void cleanPatient(){
        ETIDPatient.setText("");
        ETFNameSearchPatient.setText("");
        ETLNameSearchPatient.setText("");
        ETTelephoneSearchPatient.setText("");
        ETEmailSearchPatient.setText("");
        //spinnerTypePatient.setSelection(0);


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