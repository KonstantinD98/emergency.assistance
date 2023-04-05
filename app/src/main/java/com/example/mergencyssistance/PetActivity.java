package com.example.mergencyssistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PetActivity extends AppCompatActivity {

    EditText animal, breed, ownerName, petName;
    Spinner spinnerVet;
    Button btnAddPet, btnBackPet, btnViewPet;
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

        btnBackPet.setOnClickListener(onClick);

    }
}
