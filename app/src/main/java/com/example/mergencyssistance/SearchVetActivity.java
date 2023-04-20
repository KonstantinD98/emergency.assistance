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
import android.widget.Toast;

import com.example.mergencyssistance.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SearchVetActivity extends AppCompatActivity {

    EditText ETIDVet,ETFNameSearchVet, ETLNameSearchVet, ETTelephoneSearchVet, ETEmailSearchVet, ETSearchVet;
    Button btnSearch,btnDeleteSearchVet, btnEditSearchVet, btnBackSearchVet;
    Connection con ;
    Statement st ;
    int result = 0;
    ResultSet rs;
    String q = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vet);
        ETIDVet = findViewById(R.id.ETIDVet);
        ETFNameSearchVet = findViewById(R.id.ETFNameSearchVet);
        ETLNameSearchVet = findViewById(R.id.ETLNameSearchVet);
        ETTelephoneSearchVet = findViewById(R.id.ETTelephoneSearchVet);
        ETEmailSearchVet = findViewById(R.id.ETEmailSearchVet);
        ETSearchVet = findViewById(R.id.ETSearchVet);
        btnSearch = findViewById(R.id.btnSearch);
        btnDeleteSearchVet = findViewById(R.id.btnDeleteSearchVet);
        btnEditSearchVet = findViewById(R.id.btnEditSearchVet);
        btnBackSearchVet = findViewById(R.id.btnBackSearchVet);



        btnBackSearchVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchVetActivity.this, VetViewActivity.class ));
            }
        });

    }
    public void search(View view){
        int vet_id;
        vet_id = Integer.parseInt(ETSearchVet.getText().toString());
        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                ConnectionClass.ip.toString());
            q = "SELECT * FROM VetTable WHERE vetID="+vet_id;
            st = con.createStatement();
            rs = st.executeQuery(q);
            if (rs.next()){
                ETIDVet.setText(rs.getString(1));
                ETFNameSearchVet.setText(rs.getString(2));
                ETLNameSearchVet.setText(rs.getString(3));
                ETEmailSearchVet.setText(rs.getString(4));
                ETTelephoneSearchVet.setText(rs.getString(5));
            }
            rs.close();
            con.close();

        }
        catch (Exception e){
            Log.e("Error: ", e.getMessage());
        }

        }
    public void modify(View view) {
        int vet_id;
        String f_VetName, l_VetName, vetEmail, vetPhone;
        result = 0;
        vet_id = Integer.parseInt(ETIDVet.getText().toString());
        f_VetName = ETFNameSearchVet.getText().toString();
        l_VetName = ETLNameSearchVet.getText().toString();
        vetEmail = ETEmailSearchVet.getText().toString();
        vetPhone = ETTelephoneSearchVet.getText().toString();

        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            if (con != null) {
                q = "update VetTable set firstName='" + f_VetName + "', lastname ='" + l_VetName + "', email='" + vetEmail + "', phone =" + vetPhone+" where vetID=" +vet_id;
                st = con.createStatement();
                result = st.executeUpdate(q);
                if (result == 1) {
                    Toast.makeText(this, "Record Updated", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "Record NOT Updated", Toast.LENGTH_LONG).show();
                }
                clean();
                con.close();
            }

        }
        catch (Exception e){
            Log.e("Error : ", e.getMessage());
        }

    }
    public void delete(View view) {
        int vet_id;
        String f_VetName, l_VetName, vetEmail, vetPhone;
        result = 0;
        vet_id = Integer.parseInt(ETIDVet.getText().toString());

        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            if (con != null) {
                q = "delete from VetTable where vetID=" +vet_id;
                st = con.createStatement();
                result = st.executeUpdate(q);
                if (result == 1) {
                    Toast.makeText(this, "Record Deleted", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "Record NOT Deleted", Toast.LENGTH_LONG).show();
                }
                clean();
                con.close();
            }

        }
        catch (Exception e){
            Log.e("Error : ", e.getMessage());
        }

    }
        public void clean(){
            ETIDVet.setText("");
            ETFNameSearchVet.setText("");
            ETLNameSearchVet.setText("");
            ETEmailSearchVet.setText("");
            ETTelephoneSearchVet.setText("");


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
