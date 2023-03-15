package com.example.mergencyssistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText ETRegUsername, ETEmail, ETRegPassword, ETRegPasswordConfirm;
    Button btnRegister;
    TextView TVHaveAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ETRegUsername = findViewById(R.id.ETRegUsername);
        ETRegPassword = findViewById(R.id.ETRegPassword);
        ETEmail = findViewById(R.id.ETEmail);
        ETRegPasswordConfirm = findViewById(R.id.ETRegPasswordConfirm);
        btnRegister = findViewById(R.id.btnRegister);
        TVHaveAcc = findViewById(R.id.TVHaveAcc);

        TVHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ETRegUsername.getText().toString();
                String email = ETEmail.getText().toString();
                String password = ETRegPassword.getText().toString();
                String confirm = ETRegPasswordConfirm.getText().toString();
                Database db = new Database(getApplicationContext(),"emergency", null, 1);
                if (username.length() == 0 || email.length() == 0 || password.length() == 0 || confirm.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill All details", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.compareTo(confirm) == 0) {
                        if (isValid(password)){
                            db.register(username,email,password);
                            Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters, having letter, digit and special symbol", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Password and Confirm Password didn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public static boolean isValid(String passwordhere) {
        int f1 = 0, f2 = 0, f3 = 0;
        if (passwordhere.length()<8){
            return false;
        }else{
            for (int p = 0; p < passwordhere.length(); p++){
                if (Character.isLetter(passwordhere.charAt(p))){
                    f1 = 1;
                }
            }
            for (int r = 0; r < passwordhere.length(); r++) {
                if (Character.isDigit(passwordhere.charAt(r))) {
                    f2 = 1;
                }
            }
            for (int s = 0; s < passwordhere.length(); s++) {
                char c = passwordhere.charAt(s);
                if (c >= 33 && c <= 46 || c == 64) {
                    f3 = 1;
                }
            }
            if (f1==1 && f2 == 1 && f3 ==1)
                return true;
            return false;
        }
    }
}