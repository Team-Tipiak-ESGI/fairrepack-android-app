package com.saglissindustries.fairrepack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button signInBtn;
    private EditText loginText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.signInBtn = findViewById(R.id.sign_in);
        this.loginText = findViewById(R.id.login);
        this.passwordText = findViewById(R.id.login);

        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);



    }
}