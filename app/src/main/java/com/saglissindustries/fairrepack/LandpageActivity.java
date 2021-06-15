package com.saglissindustries.fairrepack;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.saglissindustries.fairrepack.httprequests.RequestCallback;
import com.saglissindustries.fairrepack.httprequests.RequestHandler;

import org.json.JSONException;

public class LandpageActivity extends AppCompatActivity {

    TextView quant, totalSpent;
    Button disconnect, convert, assocs, quit;
    String recupQuant, recupSpent;

    private void updateCoinCount(String url, SharedPreferences sharedPreferences) {
        new RequestHandler(url, "GET", sharedPreferences.getString("token", null), new RequestCallback() {
            @Override
            public void run() {
                try {
                    recupQuant = response.getJSONObject("information").getString("coins");
                    recupSpent = response.getJSONObject("information").getString("waiting_coins");

                    // Recuperation et Affectation de la valeur des tokens prealablement
                    // recuperes par l'utilisateur
                    quant = (TextView) findViewById(R.id.landpage_amount_coin);

                    // Recuperation et Affectation de la valeur du total depense prealablement
                    // recuperes par l'utilisateur
                    totalSpent = (TextView) findViewById(R.id.landpage_amount_spent);

                    runOnUiThread(() -> {
                        quant.setText(recupQuant);
                        totalSpent.setText(recupSpent);
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

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
        quit = (Button)findViewById(R.id.quit);

        recupQuant = "";
        recupSpent = "";

        // DATA PASSEE PAR LA REQUETE API
        updateCoinCount(url, sharedPreferences);

        disconnect.setOnClickListener(v ->{
            Intent backIntent = new Intent(LandpageActivity.this, MainActivity.class);
            SharedPreferences.Editor ed = sharedPreferences.edit();
            ed.remove("token");
            ed.apply();
            Toast.makeText(LandpageActivity.this, "successfully disconnected", Toast.LENGTH_LONG).show();
            startActivity(backIntent);
        });

        convert.setOnClickListener(v -> {
            new RequestHandler("https://pa.quozul.dev/api/coin/convert.php", "POST", sharedPreferences.getString("token", null), new RequestCallback() {
                @Override
                public void run() {
                    updateCoinCount(url, sharedPreferences);
                }
            });
        });

        assocs.setOnClickListener(v -> {
            Intent assocIntent = new Intent(LandpageActivity.this, AssocActivity.class);
            startActivity(assocIntent);
        });

        quit.setOnClickListener(v ->{
            new AlertDialog.Builder(LandpageActivity.this)
                    .setTitle(getString(R.string.pop_title))
                    .setMessage(getString(R.string.pop_content))
                    .setPositiveButton(getString(R.string.yes), (dialog, which) -> finish())
                    .setNegativeButton(getString(R.string.no), (dialog, which) -> {})
                    .setCancelable(false)
                    .show();
        });
    }

}
