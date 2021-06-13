package com.saglissindustries.fairrepack;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CoinActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences= getSharedPreferences("data", MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assoc_coin);
    }

}
