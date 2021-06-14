package com.saglissindustries.fairrepack;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.saglissindustries.fairrepack.httprequests.RequestCallback;
import com.saglissindustries.fairrepack.httprequests.RequestHandler;

import org.json.JSONException;

public class LandpageActivity extends AppCompatActivity {

    TextView quant, totalSpent;
    Button disconnect, convert, assocs;
    String recupQuant, recupSpent;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landpage);

        //recuperation token
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        // url pour api
        String url = "https://pa.quozul.dev/api/user/read.php";

        //Creation des Boutons
        disconnect = (Button)findViewById(R.id.disconnect);
        convert = (Button)findViewById(R.id.requestConvert);
        assocs = (Button)findViewById(R.id.intentAssoc);

        // DATA PASSEE PAR LA REQUETE API
        try {
            new RequestHandler(url, "GET", sharedPreferences.getString("token", null), new RequestCallback() {
            @Override
                public void run(){
                   //recupQuant = response.get("");
                   //recupSpent = response.get("");
            }
            });

        } catch (Exception e){
            e.printStackTrace();
        }

        // Recuperation et Affectation de la valeur des tokens prealablement
        // recuperes par l'utilisateur
        this.quant = (TextView) findViewById(R.id.landpage_amount_coin);
        this.quant.setText(recupQuant);

        // Recuperation et Affectation de la valeur du total depense prealablement
        // recuperes par l'utilisateur
        this.totalSpent = (TextView) findViewById(R.id.landpage_amount_spent);
        this.totalSpent.setText(recupSpent);

        disconnect.setOnClickListener(v ->{
            Intent backIntent = new Intent(LandpageActivity.this, MainActivity.class);
            SharedPreferences.Editor ed = sharedPreferences.edit();
            ed.remove("token");
            ed.apply();
            Toast.makeText(LandpageActivity.this, "successfully disconnected", Toast.LENGTH_LONG).show();
            startActivity(backIntent);
        });

        convert.setOnClickListener(v ->{
            //TODO: REQUETE CONVERTION TOKEN - FORMULE
            //toConvert  = 58€
            //coins     += Math.floor(toConvert / 10) = 5 coins
            //toConvert -= toConvert % 10             = 8 €

        });

        assocs.setOnClickListener(v -> {
            Intent assocIntent = new Intent(LandpageActivity.this, AssocActivity.class);
            startActivity(assocIntent);
        });
    }

}
