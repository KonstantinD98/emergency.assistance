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

import com.example.mergencyssistance.Adapters.DoctorAdapter;
import com.example.mergencyssistance.Connection.ConnectionClass;
import com.example.mergencyssistance.Entity.Doctor;;
import com.example.mergencyssistance.GetData.GetDocData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DoctorViewActivity extends AppCompatActivity {
    Doctor doctor;
    DoctorAdapter doctorAdapter;
    GetDocData getDocData = new GetDocData();
    ListView doctorLine;
    Connection con;
    Statement st ;
    int result = 0;
    ResultSet rs;
    String q = "";
    Dialog dialog;
    private void initDialog(Doctor doctor) {
        dialog = new Dialog(DoctorViewActivity.this);

        dialog.setContentView(R.layout.dialog_doctor);

        Button saveButton = dialog.findViewById(R.id.saveDoc);
        EditText ETIDdoc = dialog.findViewById(R.id.ETIDdoc);
        EditText ETfNamedoc = dialog.findViewById(R.id.ETfNamedoc);
        EditText ETlNamedoc = dialog.findViewById(R.id.ETlNamedoc);
        EditText ETemaildoc = dialog.findViewById(R.id.ETemaildoc);
        EditText ETphonedoc = dialog.findViewById(R.id.ETphonedoc);
        Button deleteButton = dialog.findViewById(R.id.deleteDoc);
        Spinner spinnerDoc = dialog.findViewById(R.id.spinnerDoc);


        if (doctor != null) {
            ETIDdoc.setText(String.valueOf(doctor.getDoctorID()));
            ETfNamedoc.setText(doctor.getFirstNameD());
            ETlNamedoc.setText(doctor.getLastNameD());
            ETemaildoc.setText(doctor.getEmailD());
            ETphonedoc.setText(doctor.getPhone());
            String[] typeOfDoc = getResources().getStringArray(R.array.TypeOfDoc);
            fillSpinner(spinnerDoc, typeOfDoc, String.valueOf(doctor.getSpeciality()));

        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int doc_id;
                result = 0;
                doc_id = Integer.parseInt(ETIDdoc.getText().toString());
                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "delete from DoctorTable where doctorID=" + doc_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(DoctorViewActivity.this, "Record Deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(DoctorViewActivity.this, "Record NOT Deleted", Toast.LENGTH_LONG).show();
                        }
                        cleanDoc();
                        con.close();

                    }

                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                }

            }


            public void cleanDoc() {
                ETIDdoc.setText("");
                ETfNamedoc.setText("");
                ETlNamedoc.setText("");
                ETemaildoc.setText("");
                ETphonedoc.setText("");
                spinnerDoc.setSelection(0);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int doc_id;
                String f_DocName, l_DocName, docPhone, docEmail;
                result = 0;
                doc_id = Integer.parseInt(ETIDdoc.getText().toString());
                f_DocName = ETfNamedoc.getText().toString();
                l_DocName = ETlNamedoc.getText().toString();
                docEmail = ETemaildoc.getText().toString();
                docPhone = ETphonedoc.getText().toString();
                final String selectedSpinnerValue = spinnerDoc.getSelectedItem().toString();


                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "update DoctorTable set firstName='" + f_DocName + "', lastName ='" + l_DocName + "', email='" + docEmail + "', phone ='" + docPhone + "', speciality='" + selectedSpinnerValue +"' where doctorID=" + doc_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(DoctorViewActivity.this, "Record Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(DoctorViewActivity.this, "Record NOT Updated", Toast.LENGTH_LONG).show();
                        }
                        cleanDoc();
                        con.close();
                        st.close();

                    }

                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                    Toast.makeText(DoctorViewActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            public void cleanDoc() {
                ETIDdoc.setText("");
                ETfNamedoc.setText("");
                ETlNamedoc.setText("");
                ETemaildoc.setText("");
                ETphonedoc.setText("");
                Spinner spinner = findViewById(R.id.spinnerDoc);
                if (spinner != null) {
                    spinner.setSelection(0);
                }

            }
        });

    }
    private void fillSpinner(Spinner spin, String[] typeOfDoc, String TypeOfDoc) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(spin.getContext(), android.R.layout.simple_list_item_1, typeOfDoc);
        spin.setAdapter(arrayAdapter);

        int spinnerPos = -1;
        for (int i = 0; i < typeOfDoc.length; i++) {
            if (typeOfDoc[i].equals(TypeOfDoc)) {
                spinnerPos = i;
                break;
            }
        }

        if (spinnerPos >= 0) {
            spin.setSelection(spinnerPos);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view);
        doctorLine = findViewById(R.id.doctor_list_view);
        //ImageView imgExit = findViewById(R.id.imgExit);

        Button viewButton = findViewById(R.id.view_button);
        Button exitButton = findViewById(R.id.exitView);
        Button searchButton = findViewById(R.id.search_button);
        initDialog(doctor);
        doctorLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doctor = (Doctor) parent.getItemAtPosition(position);
                Toast.makeText(DoctorViewActivity.this, "You clicked: " + doctor, Toast.LENGTH_SHORT).show();

                LayoutInflater inflater = LayoutInflater.from(DoctorViewActivity.this);
                View dialogView = inflater.inflate(R.layout.dialog_doctor, null);

                initDialog(doctor);
                dialog.show();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorViewActivity.this, SearchDoctorActivity.class));

            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorViewActivity.this, DoctorActivity.class));
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
                            String query = "SELECT * FROM DoctorTable";
                            PreparedStatement stmt = con.prepareStatement(query);
                            ResultSet rs = stmt.executeQuery();
                            List<Doctor> doctorList = new ArrayList<>();
                            // Retrieve the data from the query result
                            while (rs.next()) {
                                Doctor doctor = new Doctor();

                                doctor.setDoctorID(rs.getInt("doctorID"));
                                doctor.setFirstNameD(rs.getString("firstName"));
                                doctor.setLastNameD(rs.getString("lastName"));
                                doctor.setEmailD(rs.getString("email"));
                                doctor.setPhone(rs.getString("phone"));
                                doctor.setSpeciality(rs.getString("speciality"));

                                doctorList.add(doctor);
                            }

                            // Close the connection and the query result
                            rs.close();
                            stmt.close();
                            con.close();

                            // Update the ListView on the main thread
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    doctorAdapter = new DoctorAdapter((Context) DoctorViewActivity.this, (ArrayList<Doctor>) doctorList);
                                    doctorLine.setAdapter(doctorAdapter);
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