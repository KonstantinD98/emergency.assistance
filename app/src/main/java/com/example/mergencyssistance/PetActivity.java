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

public class PetActivity extends AppCompatActivity {

    EditText animal, breed, ownerName, petName;
    Spinner spinnerVet;
    Button btnAddPet, btnBackPet, btnViewPet;
    TextView statuspet;
    Connection con;
    Statement stmt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        animal = findViewById(R.id.animal);
        breed = findViewById(R.id.breed);
        ownerName = findViewById(R.id.ownerName);
        petName = findViewById(R.id.petName);
        spinnerVet = findViewById(R.id.spinnerVet);
        btnAddPet = findViewById(R.id.btnAddPet);
        btnBackPet = findViewById(R.id.btnBackPet);
        btnViewPet = findViewById(R.id.btnViewPet);
        statuspet = findViewById(R.id.statuspet);

        btnBackPet.setOnClickListener(onClick);
        fillSpinner();
        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new PetActivity.addpet().execute("");

            }
        });

    }



    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;

            switch (view.getId()) {
                case R.id.btnBackPet:
                    intent = new Intent(PetActivity.this, HomeActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };

    public class addpet extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            statuspet.setText("Sending Data to Database");

        }

        @Override
        protected void onPostExecute(String s) {
            statuspet.setText("Pet Added");
            animal.setText("");
            breed.setText("");
            ownerName.setText("");
            petName.setText("");
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
                    String sql = "INSERT INTO PetTable (animal,breed, name, ownerName, vetID) VALUES ('"+animal.getText()+"','"+breed.getText()
                            +"','"+petName.getText()+"','"+ownerName.getText()+"','"+spinnerVet.getAdapter().getItemId(1)+"')";
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
            String query = "SELECT * FROM VetTable";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> data = new ArrayList<String>();
            while(rs.next()){
                String name = rs.getString("firstName");
                data.add(name);
            }
            ArrayAdapter array = new ArrayAdapter(this, android.R.layout.simple_list_item_1,data);
            spinnerVet.setAdapter(array);
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


