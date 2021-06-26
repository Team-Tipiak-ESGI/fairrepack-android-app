package com.saglissindustries.fairrepack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.saglissindustries.fairrepack.httprequests.RequestCallback;
import com.saglissindustries.fairrepack.httprequests.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AssocActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ListView listView;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assoc_activity);

        this.sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        back = findViewById(R.id.back);
        listView = findViewById(R.id.assoc_view);

        getAssoc();

        back.setOnClickListener(v ->{
            Intent backIntent = new Intent(AssocActivity.this, LandpageActivity.class);
            startActivity(backIntent);
        });
    }

    public void getAssoc() {
        String url = "https://pa.quozul.dev/api/association/read.php";
        List<Assoc> assocs = new ArrayList<>();
        try {
            new RequestHandler(url, "GET", sharedPreferences.getString("token", null), new RequestCallback() {
                @Override
                public void run() {
                    try {
                        // On va avoir un tableau d'associations il faut donc parse le tableau
                        JSONArray jsonArray = response.getJSONArray("items");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject associationObject = jsonArray.getJSONObject(i);

                            //  Recupration de chaque membres des associations
                            String name = associationObject.getString("name");
                            String descr = associationObject.getString("description");
                            int coin = Integer.parseInt(associationObject.getString("coins"));
                            String address = associationObject.getString("address");
                            String uuid = associationObject.getString("uuid_association");
                            // Mise en place dans un tableau
                            assocs.add(new Assoc(name, descr, address, coin, uuid));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    AssocAdapter assocAdapter = new AssocAdapter(AssocActivity.this, assocs);
                    listView.setAdapter(assocAdapter);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
