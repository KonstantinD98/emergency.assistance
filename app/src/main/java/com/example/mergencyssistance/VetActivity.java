package com.example.mergencyssistance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mergencyssistance.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class VetActivity extends AppCompatActivity {
    EditText ETFNameVet, ETLNameVet, ETTelephoneVet, ETEmailVet;
    Button btnAddVet, btnBackVet, btnViewVet;
    TextView statusvet;
    Connection con;
    Statement stmt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet);
        ETFNameVet = findViewById(R.id.ETFNameVet);
        ETLNameVet = findViewById(R.id.ETLNameVet);
        ETTelephoneVet = findViewById(R.id.ETTelephoneVet);
        ETEmailVet = findViewById(R.id.ETEmailVet);
        btnAddVet = findViewById(R.id.btnAddVet);
        btnBackVet = findViewById(R.id.btnBackVet);
        btnViewVet = findViewById(R.id.btnViewVet);
        statusvet = findViewById(R.id.statusvet);

        btnBackVet.setOnClickListener(onClick);
        btnAddVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new VetActivity.addvet().execute("");

            }
        });

    }


    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;

            switch (view.getId()) {
                case R.id.btnBackVet:
                    intent = new Intent(VetActivity.this, HomeActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };
    public class addvet extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            statusvet.setText("Sending Data to Database");

        }

        @Override
        protected void onPostExecute(String s) {
            statusvet.setText("Vet Added");
            ETFNameVet.setText("");
            ETLNameVet.setText("");
            ETTelephoneVet.setText("");
            ETEmailVet.setText("");
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
                    String sql = "INSERT INTO VetTable (firstName,lastName, email, phone) VALUES ('"+ETFNameVet.getText()+"','"+ETLNameVet.getText()
                            +"','"+ETEmailVet.getText()+"','"+ETTelephoneVet.getText()+"')";
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


