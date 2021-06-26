package com.saglissindustries.fairrepack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.saglissindustries.fairrepack.httprequests.RequestCallback;
import com.saglissindustries.fairrepack.httprequests.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class CoinActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Button validate, back;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assoc_coin);

        this.sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        Intent intent = getIntent();
        String assoc_uuid = intent.getStringExtra("uuid");
        String assoc_name = intent.getStringExtra("name");
        String assoc_descr = intent.getStringExtra("descr");
        int assoc_coin = intent.getIntExtra("coin", 0);
        String assoc_addr = intent.getStringExtra("addr");

        ((TextView) findViewById(R.id.assoc)).setText(assoc_name);
        ((TextView) findViewById(R.id.description)).setText(assoc_descr);
        ((TextView) findViewById(R.id.coins)).setText(String.valueOf(assoc_coin));

        back = (Button)findViewById(R.id.back);
        validate = (Button)findViewById(R.id.sendCoin);

        back.setOnClickListener(v -> {
            Intent backIntent = new Intent(CoinActivity.this, LandpageActivity.class);
            startActivity(backIntent);
        });

        validate.setOnClickListener(v -> {
            // récupère la quantité de coins a donner
            String coins = ((TextView) findViewById(R.id.AddCoins)).getText().toString();

            String url = "https://pa.quozul.dev/api/coin/spend.php";

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("uuid", assoc_uuid);
                jsonObject.put("coins", coins);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println(jsonObject);

            new RequestHandler(url, "POST", jsonObject, sharedPreferences.getString("token", null), new RequestCallback() {
                @Override
                public void run() {
                    System.out.println("Success!");
                }
            });
        });
    }

}
