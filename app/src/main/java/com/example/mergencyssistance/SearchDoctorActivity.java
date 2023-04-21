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

public class SearchDoctorActivity extends AppCompatActivity {
    EditText ETIDDoc,ETFNameSearchDoc, ETLNameSearchDoc, ETEmailSearchDoc, ETTelephoneSearchDoc, ETSearchDoc;
    Spinner spinnerTypeDoc;
    Button btnSearchDoc,btnDeleteDoctor, btnEditDoctor,btnBackSearchDoctor;
    Connection con ;
    Statement st ;
    int result = 0;
    ResultSet rs;
    String q = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor);
        ETIDDoc = findViewById(R.id.ETIDDoc);
        ETFNameSearchDoc = findViewById(R.id.ETFNameSearchDoc);
        ETLNameSearchDoc = findViewById(R.id.ETLNameSearchDoc);
        ETEmailSearchDoc = findViewById(R.id.ETEmailSearchDoc);
        ETTelephoneSearchDoc = findViewById(R.id.ETTelephoneSearchDoc);
        ETSearchDoc = findViewById(R.id.ETSearchDoc);
        spinnerTypeDoc = findViewById(R.id.spinnerTypeDoc);
        btnSearchDoc = findViewById(R.id.btnSearchDoc);
        btnDeleteDoctor = findViewById(R.id.btnDeleteDoctor);
        btnEditDoctor = findViewById(R.id.btnEditDoctor);
        btnBackSearchDoctor = findViewById(R.id.btnBackSearchDoctor);

     btnBackSearchDoctor.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(SearchDoctorActivity.this, DoctorViewActivity.class ));
        }
    });
     btnSearchDoc.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             int doc_id;
             doc_id = Integer.parseInt(ETSearchDoc.getText().toString());
             try {
                 con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                         ConnectionClass.ip.toString());
                 q = "SELECT * FROM DoctorTable WHERE doctorID="+doc_id;
                 st = con.createStatement();
                 rs = st.executeQuery(q);
                 if (rs.next()){
                     ETIDDoc.setText(rs.getString(1));
                     ETFNameSearchDoc.setText(rs.getString(2));
                     ETLNameSearchDoc.setText(rs.getString(3));
                     ETEmailSearchDoc.setText(rs.getString(4));
                     ETTelephoneSearchDoc.setText(rs.getString(5));
                     //spinnerTypeDoc.setSelection(rs.findColumn("speciality")); ????
                 }
                 rs.close();
                 con.close();

             }
             catch (Exception e){
                 Log.e("Error: ", e.getMessage());
             }

         }
     });
     btnEditDoctor.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             int doc_id;
             String f_DocName, l_DocName, DocEmail, DocPhone;
             result = 0;
             doc_id = Integer.parseInt(ETIDDoc.getText().toString());
             f_DocName = ETFNameSearchDoc.getText().toString();
             l_DocName = ETLNameSearchDoc.getText().toString();
             DocEmail = ETEmailSearchDoc.getText().toString();
             DocPhone = ETTelephoneSearchDoc.getText().toString();

             try {
                 con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                         ConnectionClass.ip.toString());
                 if (con != null) {
                     q = "update DoctorTable set firstName='" + f_DocName + "', lastName ='" + l_DocName + "', email='" + DocEmail + "', phone =" + DocPhone+" where doctorID=" +doc_id;
                     st = con.createStatement();
                     result = st.executeUpdate(q);
                     if (result == 1) {
                         Toast.makeText(SearchDoctorActivity.this, "Record Updated", Toast.LENGTH_LONG).show();
                     }
                     else {
                         Toast.makeText(SearchDoctorActivity.this, "Record NOT Updated", Toast.LENGTH_LONG).show();
                     }
                     cleanDoc();
                     con.close();
                     st.close();
                 }

             }
             catch (Exception e) {
                 Log.e("Error : ", e.getMessage());
                 Toast.makeText(SearchDoctorActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
             }
         }
     });
     btnDeleteDoctor.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             int doc_id;
             result = 0;
             doc_id = Integer.parseInt(ETIDDoc.getText().toString());
             try {
                 con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                         ConnectionClass.ip.toString());
                 if (con != null) {
                     q = "delete from DoctorTable where doctorID=" +doc_id;
                     st = con.createStatement();
                     result = st.executeUpdate(q);
                     if (result == 1) {
                         Toast.makeText(SearchDoctorActivity.this, "Record Deleted", Toast.LENGTH_LONG).show();
                     }
                     else {
                         Toast.makeText(SearchDoctorActivity.this, "Record NOT Deleted", Toast.LENGTH_LONG).show();
                     }
                     cleanDoc();
                     con.close();
                 }

             }
             catch (Exception e){
                 Log.e("Error : ", e.getMessage());
             }

         }
     });
}


    public void cleanDoc(){
        ETIDDoc.setText("");
        ETFNameSearchDoc.setText("");
        ETLNameSearchDoc.setText("");
        ETEmailSearchDoc.setText("");
        ETTelephoneSearchDoc.setText("");
        //spinnerTypeDoc.setSelection(0);


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
