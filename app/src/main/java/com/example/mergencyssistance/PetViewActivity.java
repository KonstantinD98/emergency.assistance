package com.example.mergencyssistance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mergencyssistance.Adapters.PatientAdapter;
import com.example.mergencyssistance.Adapters.PetAdapter;
import com.example.mergencyssistance.Connection.ConnectionClass;
import com.example.mergencyssistance.Entity.Patient;
import com.example.mergencyssistance.Entity.Pet;
import com.example.mergencyssistance.GetData.GetPatientData;
import com.example.mergencyssistance.GetData.GetPetData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PetViewActivity extends AppCompatActivity {
    Pet pet;
    PetAdapter petAdapter;
    GetPetData getPetData = new GetPetData();
    ListView petLine;
    Connection con;
    Statement st ;
    int result = 0;
    ResultSet rs;
    String q = "";
    Dialog dialog;
    private void initDialog(Pet pet) {
        dialog = new Dialog(PetViewActivity.this);

        dialog.setContentView(R.layout.dialog_pet);

        Button saveButton = dialog.findViewById(R.id.savePet);
        EditText ETIDpet = dialog.findViewById(R.id.ETIDpet);
        EditText ETanimal = dialog.findViewById(R.id.ETanimal);
        EditText ETbreed = dialog.findViewById(R.id.ETbreed);
        EditText ETnamePet = dialog.findViewById(R.id.ETnamePet);
        EditText ETownerName = dialog.findViewById(R.id.ETownerName);
        Spinner spinnerPet = dialog.findViewById(R.id.spinnerPet);
        Button deleteButton = dialog.findViewById(R.id.deletePet);


        if (pet != null) {
            ETIDpet.setText(String.valueOf(pet.getPetID()));
            ETanimal.setText(pet.getAnimal());
            ETbreed.setText(pet.getBreed());
            ETnamePet.setText(pet.getName());
            ETownerName.setText(pet.getOwnerName());
            fillSpinner(spinnerPet, pet.getVetID());

        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pet_id;
                result = 0;
                pet_id = Integer.parseInt(ETIDpet.getText().toString());
                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "delete from PetTable where petID=" + pet_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(PetViewActivity.this, "Record Deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PetViewActivity.this, "Record NOT Deleted", Toast.LENGTH_LONG).show();
                        }
                        cleanPet();
                        con.close();

                    }

                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                }

            }


            public void cleanPet() {
                ETIDpet.setText("");
                ETanimal.setText("");
                ETbreed.setText("");
                ETnamePet.setText("");
                ETownerName.setText("");
                spinnerPet.setSelection(0);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pet_id;
                String animal, breed, namePet, ownerName, vetId;
                result = 0;
                pet_id = Integer.parseInt(ETIDpet.getText().toString());
                animal = ETanimal.getText().toString();
                breed = ETbreed.getText().toString();
                namePet = ETnamePet.getText().toString();
                ownerName = ETownerName.getText().toString();
                String[] parts = spinnerPet.getSelectedItem().toString().split(" - ");
                vetId = parts[1];


                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "update PetTable set animal='" + animal + "', breed ='" + breed + "', name ='" + namePet + "', ownerName ='" +  ownerName + "', vetID='" + vetId +"' where petID=" + pet_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(PetViewActivity.this, "Record Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PetViewActivity.this, "Record NOT Updated", Toast.LENGTH_LONG).show();
                        }
                        cleanPet();
                        con.close();
                        st.close();

                    }

                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                    Toast.makeText(PetViewActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            public void cleanPet() {
                ETIDpet.setText("");
                ETanimal.setText("");
                ETbreed.setText("");
                ETnamePet.setText("");
                ETownerName.setText("");
                spinnerPet.setSelection(0);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_view);
        petLine = findViewById(R.id.pet_list_view);
        //ImageView imgExit = findViewById(R.id.imgExit);

        Button viewButton = findViewById(R.id.viewpet_button);
        Button exitButton = findViewById(R.id.exitViewPet);
        Button searchButton = findViewById(R.id.searchPet_button);
        initDialog(pet);
        petLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pet = (Pet) parent.getItemAtPosition(position);
                Toast.makeText(PetViewActivity.this, "You clicked: " +  pet, Toast.LENGTH_SHORT).show();

                LayoutInflater inflater = LayoutInflater.from(PetViewActivity.this);
                View dialogView = inflater.inflate(R.layout.dialog_pet, null);

                initDialog( pet);
                dialog.show();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetViewActivity.this, PetSearchActivity.class));

            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetViewActivity.this, PetActivity.class));
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
                            String query = "SELECT * FROM PetTable";
                            PreparedStatement stmt = con.prepareStatement(query);
                            ResultSet rs = stmt.executeQuery();
                            List<Pet> petList = new ArrayList<>();
                            // Retrieve the data from the query result
                            while (rs.next()) {
                                Pet pet = new Pet();

                                pet.setPetID(rs.getInt("petID"));
                                pet.setAnimal(rs.getString("animal"));
                                pet.setBreed(rs.getString("breed"));
                                pet.setName(rs.getString("name"));
                                pet.setOwnerName(rs.getString("ownerName"));
                                pet.setVetID(rs.getInt("vetID"));

                                petList.add(pet);
                            }

                            // Close the connection and the query result
                            rs.close();
                            stmt.close();
                            con.close();

                            // Update the ListView on the main thread
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    petAdapter = new PetAdapter((Context) PetViewActivity.this, (ArrayList<Pet>) petList);
                                    petLine.setAdapter(petAdapter);
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
    private void fillSpinner(Spinner spin, int id) {
        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            String query = "SELECT * FROM VetTable";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> data = new ArrayList<String>();

            String selectionRow = "";

            while (rs.next()) {
                int vetID = rs.getInt("vetID");
                String firstName = rs.getString("firstName");
                String entry = firstName + " - " + vetID;
                data.add(entry);

                if(vetID == id){
                    selectionRow = entry;
                }
            }
            ArrayAdapter array = new ArrayAdapter(PetViewActivity.this, android.R.layout.simple_list_item_1, data);
            spin.setAdapter(array);

            int spinnerPos = array.getPosition(selectionRow);
            spin.setSelection(spinnerPos);

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }


    @SuppressLint("NewApi")
    public Connection connectionClass (String user, String password, String
            database, String
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