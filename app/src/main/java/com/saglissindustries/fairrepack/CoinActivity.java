package com.saglissindustries.fairrepack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CoinActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
    Button validate, back;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.assoc_coin);

        back = (Button)findViewById(R.id.back);
        validate = (Button)findViewById(R.id.sendCoin);

        back.setOnClickListener(v ->{
            Intent backIntent = new Intent(CoinActivity.this, LandpageActivity.class);
            startActivity(backIntent);
        });

        validate.setOnClickListener(v ->{
            //TODO: APPEL API ENVOI COIN SUR ASSO SELECTIONNEE
        });
    }

}
