package com.example.mergencyssistance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mergencyssistance.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PatientActivity extends AppCompatActivity {

    EditText ETFNameP, ETLNameP, ETTelephoneP, ETEmailP;
    Spinner spinnerTypeP;
    Button btnAddP, btnBackP, btnViewP;
    TextView statuspatient;
    Connection con;
    Statement stmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        ETFNameP = findViewById(R.id.ETFNameP);
        ETLNameP = findViewById(R.id.ETLNameP);
        ETTelephoneP = findViewById(R.id.ETTelephoneP);
        ETEmailP = findViewById(R.id.ETEmailP);
        spinnerTypeP = findViewById(R.id.spinnerTypeP);
        btnAddP = findViewById(R.id.btnAddP);
        btnBackP = findViewById(R.id.btnBackP);
        btnViewP = findViewById(R.id.btnViewP);
        statuspatient = findViewById(R.id.statuspatient);

        btnBackP.setOnClickListener(onClick);
        btnViewP.setOnClickListener(onClick);
        fillSpinner();
        btnAddP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               new PatientActivity.addpatient().execute("");

            }
        });

    }
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;

            switch (view.getId()) {
                case R.id.btnBackP:
                    intent = new Intent(PatientActivity.this, HomeActivity.class);
                    break;
                case R.id.btnViewP:
                    intent = new Intent(PatientActivity.this, PatientVIewActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };

    public class addpatient extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            statuspatient.setText("Sending Data to Database");

        }

        @Override
        protected void onPostExecute(String s) {
            statuspatient.setText("Patient Added");
            ETFNameP.setText("");
            ETLNameP.setText("");
            ETTelephoneP.setText("");
            ETEmailP.setText("");
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
                    String sql = "INSERT INTO PatientTable (firstName,lastName, phone, email, doctorID) VALUES ('"+ETFNameP.getText()+"','"+ETLNameP.getText()
                            +"','"+ETTelephoneP.getText()+"','"+ETEmailP.getText()+"','"+spinnerTypeP.getAdapter().getItemId(1)+"')";
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
    public void fillSpinner() {
        try {
            con = connectionClass(ConnectionClass.un.toString(),ConnectionClass.pass.toString(),ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            String query = "SELECT * FROM DoctorTable";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> data = new ArrayList<String>();
            while(rs.next()){
                String name = rs.getString("firstName");
                data.add(name);
            }
            ArrayAdapter array = new ArrayAdapter(this, android.R.layout.simple_list_item_1,data);
            spinnerTypeP.setAdapter(array);
        } catch (Exception ex) {
            ex.printStackTrace();

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

