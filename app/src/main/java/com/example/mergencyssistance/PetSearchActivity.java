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

public class PetSearchActivity extends AppCompatActivity {

    EditText ETIDPet, ETAnimalSearch, ETBreedSearch, ETPetNameSearch, ETOwnerNSearchPet, ETSearchPet, ETChooseVet;
    Button btnDeletePet, btnEditPet, btnBackSearchPet, btnSearchPet;
    Connection con;
    Statement st;
    String q="";
    int result = 0;
    ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_search);
        ETIDPet = findViewById(R.id.ETIDPet);
        ETAnimalSearch = findViewById(R.id.ETAnimalSearch);
        ETBreedSearch = findViewById(R.id.ETBreedSearch);
        ETPetNameSearch = findViewById(R.id.ETPetNameSearch);
        ETOwnerNSearchPet = findViewById(R.id.ETOwnerNSearchPet);
        ETChooseVet = findViewById(R.id.ETChooseVet);
        ETSearchPet = findViewById(R.id.ETSearchPet);
        btnDeletePet = findViewById(R.id.btnDeletePet);
        btnEditPet = findViewById(R.id.btnEditPet);
        btnBackSearchPet = findViewById(R.id.btnBackSearchPet);
        btnSearchPet = findViewById(R.id.btnSearchPet);

        btnBackSearchPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetSearchActivity.this, PetViewActivity.class));
            }
        });
        btnSearchPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pet_id;
                pet_id = Integer.parseInt(ETSearchPet.getText().toString());
                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    q = "SELECT * FROM PetTable WHERE petID=" + pet_id;
                    st = con.createStatement();
                    rs = st.executeQuery(q);
                    if (rs.next()) {
                        ETIDPet.setText(rs.getString(1));
                        ETAnimalSearch.setText(rs.getString(2));
                        ETBreedSearch.setText(rs.getString(3));
                        ETPetNameSearch.setText(rs.getString(4));
                        ETOwnerNSearchPet.setText(rs.getString(5));
                        ETChooseVet.setText(rs.getString(6));
                    }
                    rs.close();
                    con.close();

                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }

            }
        });
        btnEditPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pet_id;
                String animal, breed, petName, ownerName;
                result = 0;
                pet_id = Integer.parseInt(ETIDPet.getText().toString());
                animal = ETAnimalSearch.getText().toString();
                breed = ETBreedSearch.getText().toString();
                petName = ETPetNameSearch.getText().toString();
                ownerName = ETOwnerNSearchPet.getText().toString();

                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "update PetTable set animal='" + animal + "', breed ='" + breed + "', name ='" + petName + "', ownerName='" + ownerName + "' where petID=" + pet_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(PetSearchActivity.this, "Record Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PetSearchActivity.this, "Record NOT Updated", Toast.LENGTH_LONG).show();
                        }
                        cleanPet();
                        con.close();
                        st.close();
                    }

                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                    Toast.makeText(PetSearchActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDeletePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pet_id;
                result = 0;
                pet_id = Integer.parseInt(ETIDPet.getText().toString());
                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "delete from PetTable where petID=" +pet_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(PetSearchActivity.this, "Record Deleted", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(PetSearchActivity.this, "Record NOT Deleted", Toast.LENGTH_LONG).show();
                        }
                        cleanPet();
                        con.close();
                    }

                }
                catch (Exception e){
                    Log.e("Error : ", e.getMessage());
                }

            }
        });
    }


        public void cleanPet(){
            ETIDPet.setText("");
            ETAnimalSearch.setText("");
            ETBreedSearch.setText("");
            ETPetNameSearch.setText("");
            ETOwnerNSearchPet.setText("");
            ETChooseVet.setText("");

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