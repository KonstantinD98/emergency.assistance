package com.example.mergencyssistance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

public class DoctorActivity extends AppCompatActivity {
    EditText ETFNameD, ETLNameD, ETTelephoneD, ETEmailD;
    Spinner spinnerTypeD;
    Button btnAddDoc, btnBackDoc, btnViewDoc;
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;

            switch (view.getId()) {
                case R.id.btnBackDoc:
                    intent = new Intent(DoctorActivity.this, HomeActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        ETFNameD = findViewById(R.id.ETFNameD);
        ETLNameD = findViewById(R.id.ETLNameD);
        ETTelephoneD = findViewById(R.id.ETTelephoneD);
        ETEmailD = findViewById(R.id.ETEmailD);
        spinnerTypeD = findViewById(R.id.spinnerTypeD);
        btnAddDoc = findViewById(R.id.btnAddDoc);
        btnBackDoc = findViewById(R.id.btnBackDoc);
        btnViewDoc = findViewById(R.id.btnViewDoc);

        btnBackDoc.setOnClickListener(onClick);

    }
}

