package com.example.mergencyssistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class VetActivity extends AppCompatActivity {
    EditText ETFNameVet, ETLNameVet, ETTelephoneVet, ETEmailVet;
    Button btnAddVet, btnBackVet, btnViewVet;
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;

            switch (view.getId()) {
                case R.id.btnBackVet:
                    intent = new Intent(VetActivity.this, HomeActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet);
        ETFNameVet = findViewById(R.id.ETFNameVet);
        ETLNameVet = findViewById(R.id.ETLNameVet);
        ETTelephoneVet = findViewById(R.id.ETTelephoneVet);
        ETEmailVet = findViewById(R.id.ETEmailVet);
        btnAddVet = findViewById(R.id.btnAddVet);
        btnBackVet = findViewById(R.id.btnBackVet);
        btnViewVet = findViewById(R.id.btnViewVet);

        btnBackVet.setOnClickListener(onClick);

    }
}
