package com.example.mergencyssistance;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;

import android.annotation.SuppressLint;

import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mergencyssistance.Adapters.VetAdapter;
import com.example.mergencyssistance.Connection.ConnectionClass;
import com.example.mergencyssistance.Entity.Vet;
import com.example.mergencyssistance.GetData.GetVetData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VetViewActivity extends AppCompatActivity {

    Vet vet;
    VetAdapter vetAdapter;
    GetVetData getVetData = new GetVetData();
    ListView vetLine;
    Connection con;
    Statement st ;
    int result = 0;
    ResultSet rs;
    String q = "";
    Dialog dialog;
    private void initDialog(Vet vet) {
        dialog = new Dialog(VetViewActivity.this);

        dialog.setContentView(R.layout.dialog_vet);

        Button saveButton = dialog.findViewById(R.id.saveVet);
        EditText ETIDVet = dialog.findViewById(R.id.ETIDVet);
        EditText ETfNameVet = dialog.findViewById(R.id.ETfNameVet);
        EditText ETlNameVet = dialog.findViewById(R.id.ETlNameVet);
        EditText ETemailVet = dialog.findViewById(R.id.ETemailVet);
        EditText ETphoneVet = dialog.findViewById(R.id.ETphoneVet);
        Button deleteButton = dialog.findViewById(R.id.deleteVet);


        if (vet != null) {
            ETIDVet.setText(String.valueOf(vet.getVetID()));
            ETfNameVet.setText(vet.getFirstNameV());
            ETlNameVet.setText(vet.getLastNameV());
            ETemailVet.setText(vet.getEmailV());
            ETphoneVet.setText(vet.getPhoneV());

        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vet_id;
                result = 0;
                vet_id = Integer.parseInt(ETIDVet.getText().toString());
                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "delete from VetTable where vetID=" + vet_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(VetViewActivity.this, "Record Deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(VetViewActivity.this, "Record NOT Deleted", Toast.LENGTH_LONG).show();
                        }
                        cleanVet();
                        con.close();

                    }

                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                }

            }


            public void cleanVet() {
                ETIDVet.setText("");
                ETfNameVet.setText("");
                ETlNameVet.setText("");
                ETemailVet.setText("");
                ETphoneVet.setText("");
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vet_id;
                String f_VetName, l_VetName, vetPhone, vetEmail;
                result = 0;
                vet_id = Integer.parseInt(ETIDVet.getText().toString());
                f_VetName = ETfNameVet.getText().toString();
                l_VetName = ETlNameVet.getText().toString();
                vetPhone = ETphoneVet.getText().toString();
                vetEmail = ETemailVet.getText().toString();


                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "update VetTable set firstName='" + f_VetName + "', lastName ='" + l_VetName + "', email='" + vetEmail + "', phone ='" + vetPhone + "' where vetID=" + vet_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(VetViewActivity.this, "Record Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(VetViewActivity.this, "Record NOT Updated", Toast.LENGTH_LONG).show();
                        }
                        cleanVet();
                        con.close();
                        st.close();

                    }

                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                    Toast.makeText(VetViewActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            public void cleanVet() {
                ETIDVet.setText("");
                ETfNameVet.setText("");
                ETlNameVet.setText("");
                ETemailVet.setText("");
                ETphoneVet.setText("");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_view);
        vetLine = findViewById(R.id.vet_list_view);
        //ImageView imgExit = findViewById(R.id.imgExit);

        Button viewButton = findViewById(R.id.viewvet_button);
        Button exitButton = findViewById(R.id.exitViewVet);
        Button searchButton = findViewById(R.id.searchViewVet);
        initDialog(vet);
        vetLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vet = (Vet) parent.getItemAtPosition(position);
                Toast.makeText(VetViewActivity.this, "You clicked: " + vet, Toast.LENGTH_SHORT).show();

                LayoutInflater inflater = LayoutInflater.from(VetViewActivity.this);
                View dialogView = inflater.inflate(R.layout.dialog_vet, null);

                initDialog(vet);
                dialog.show();
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
                                    List<Vet> vetList = new ArrayList<>();
                                    // Retrieve the data from the query result
                                    while (rs.next()) {
                                        Vet vet = new Vet();

                                        vet.setVetID(rs.getInt("vetID"));
                                        vet.setFirstNameV(rs.getString("firstName"));
                                        vet.setLastNameV(rs.getString("lastName"));
                                        vet.setEmailV(rs.getString("email"));
                                        vet.setPhoneV(rs.getString("phone"));

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
                                            vetAdapter = new VetAdapter((Context) VetViewActivity.this, (ArrayList<Vet>) vetList);
                                            vetLine.setAdapter(vetAdapter);
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