package com.example.mergencyssistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PatientActivity extends AppCompatActivity {

    EditText ETFNameP, ETLNameP, ETTelephoneP, ETEmailP;
    Spinner spinnerTypeP;
    Button btnAddP, btnBackP, btnViewP;
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;

            switch (view.getId()) {
                case R.id.btnBackP:
                    intent = new Intent(PatientActivity.this, HomeActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        ETFNameP = findViewById(R.id.ETFNameP);
        ETLNameP = findViewById(R.id.ETLNameP);
        ETTelephoneP = findViewById(R.id.ETTelephoneP);
        ETEmailP = findViewById(R.id.ETEmailP);
        spinnerTypeP = findViewById(R.id.spinnerTypeP);
        btnAddP = findViewById(R.id.btnAddP);
        btnBackP = findViewById(R.id.btnBackP);
        btnViewP = findViewById(R.id.btnViewP);

        btnBackP.setOnClickListener(onClick);

    }
}
