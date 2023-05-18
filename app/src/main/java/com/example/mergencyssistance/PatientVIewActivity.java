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
import com.example.mergencyssistance.Adapters.VetAdapter;
import com.example.mergencyssistance.Connection.ConnectionClass;
import com.example.mergencyssistance.Entity.Patient;
import com.example.mergencyssistance.Entity.Vet;
import com.example.mergencyssistance.GetData.GetPatientData;
import com.example.mergencyssistance.GetData.GetVetData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientVIewActivity extends AppCompatActivity {
    Patient patient;
    PatientAdapter patientAdapter;
    GetPatientData getPatientData = new GetPatientData();
    ListView patientLine;
    Connection con;
    Statement st ;
    int result = 0;
    ResultSet rs;
    String q = "";
    Dialog dialog;
    private void initDialog(Patient patient) {
        dialog = new Dialog(PatientVIewActivity.this);

        dialog.setContentView(R.layout.dialog_patient);

        Button saveButton = dialog.findViewById(R.id.saveP);
        EditText ETIDp = dialog.findViewById(R.id.ETIDp);
        EditText ETfNameP = dialog.findViewById(R.id.ETfNameP);
        EditText ETlNameP = dialog.findViewById(R.id.ETlNameP);
        EditText ETemailP = dialog.findViewById(R.id.ETemailP);
        EditText ETphoneP = dialog.findViewById(R.id.ETphoneP);
        Spinner spinnerP = dialog.findViewById(R.id.spinnerP);
        Button deleteButton = dialog.findViewById(R.id.deleteP);


        if (patient != null) {
            ETIDp.setText(String.valueOf(patient.getPatientID()));
            ETfNameP.setText(patient.getFirstNameP());
            ETlNameP.setText(patient.getLastNameP());
            ETemailP.setText(patient.getEmailP());
            ETphoneP.setText(patient.getPhoneP());
            fillSpinner(spinnerP, patient.getDoctorID());

        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p_id;
                result = 0;
                p_id = Integer.parseInt(ETIDp.getText().toString());
                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "delete from PatientTable where patientID=" + p_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(PatientVIewActivity.this, "Record Deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PatientVIewActivity.this, "Record NOT Deleted", Toast.LENGTH_LONG).show();
                        }
                        cleanP();
                        con.close();

                    }

                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                }

            }


            public void cleanP() {
                ETIDp.setText("");
                ETfNameP.setText("");
                ETlNameP.setText("");
                ETemailP.setText("");
                ETphoneP.setText("");
                spinnerP.setSelection(0);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p_id;
                String f_pName, l_pName, pPhone, pEmail, doctorId;
                result = 0;
                p_id = Integer.parseInt(ETIDp.getText().toString());
                f_pName = ETfNameP.getText().toString();
                l_pName = ETlNameP.getText().toString();
                pPhone = ETphoneP.getText().toString();
                pEmail = ETemailP.getText().toString();
                String[] parts = spinnerP.getSelectedItem().toString().split(" - ");
                doctorId = parts[1];


                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "update PatientTable set firstName='" + f_pName + "', lastName ='" + l_pName + "', phone ='" + pPhone + "', email ='" +  pEmail + "', doctorID='" + doctorId +"' where patientID=" + p_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(PatientVIewActivity.this, "Record Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PatientVIewActivity.this, "Record NOT Updated", Toast.LENGTH_LONG).show();
                        }
                        cleanP();
                        con.close();
                        st.close();

                    }

                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                    Toast.makeText(PatientVIewActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            public void cleanP() {
                ETIDp.setText("");
                ETfNameP.setText("");
                ETlNameP.setText("");
                ETemailP.setText("");
                ETphoneP.setText("");
                spinnerP.setSelection(0);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view);
        patientLine = findViewById(R.id.patient_list_view);
        //ImageView imgExit = findViewById(R.id.imgExit);

        Button viewButton = findViewById(R.id.viewpatient_button);
        Button exitButton = findViewById(R.id.exitViewPatient);
        Button searchButton = findViewById(R.id.searchPatient_button);
        initDialog(patient);
        patientLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                patient = (Patient) parent.getItemAtPosition(position);
                Toast.makeText(PatientVIewActivity.this, "You clicked: " + patient, Toast.LENGTH_SHORT).show();

                LayoutInflater inflater = LayoutInflater.from(PatientVIewActivity.this);
                View dialogView = inflater.inflate(R.layout.dialog_patient, null);

                initDialog(patient);
                dialog.show();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientVIewActivity.this, PatientSearchActivity.class));

            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientVIewActivity.this, PatientActivity.class));
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
                            String query = "SELECT * FROM PatientTable";
                            PreparedStatement stmt = con.prepareStatement(query);
                            ResultSet rs = stmt.executeQuery();
                            List<Patient> patientList = new ArrayList<>();
                            // Retrieve the data from the query result
                            while (rs.next()) {
                                Patient patient = new Patient();

                                patient.setPatientID(rs.getInt("patientID"));
                                patient.setFirstNameP(rs.getString("firstName"));
                                patient.setLastNameP(rs.getString("lastName"));
                                patient.setPhoneP(rs.getString("phone"));
                                patient.setEmailP(rs.getString("email"));
                                patient.setDoctorID(rs.getInt("doctorID"));

                                patientList.add(patient);
                            }

                            // Close the connection and the query result
                            rs.close();
                            stmt.close();
                            con.close();

                            // Update the ListView on the main thread
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    patientAdapter = new PatientAdapter((Context) PatientVIewActivity.this, (ArrayList<Patient>) patientList);
                                    patientLine.setAdapter(patientAdapter);
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
            String query = "SELECT * FROM DoctorTable";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> data = new ArrayList<String>();

            String selectionRow = "";

            while (rs.next()) {
                int doctorID = rs.getInt("DoctorID");
                String firstName = rs.getString("firstName");
                String entry = firstName + " - " + doctorID;
                data.add(entry);

                if(doctorID == id){
                    selectionRow = entry;
                }
            }
            ArrayAdapter array = new ArrayAdapter(PatientVIewActivity.this, android.R.layout.simple_list_item_1, data);
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