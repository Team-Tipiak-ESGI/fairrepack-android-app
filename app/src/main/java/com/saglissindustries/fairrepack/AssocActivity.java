package com.saglissindustries.fairrepack;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.saglissindustries.fairrepack.httprequests.RequestCallback;
import com.saglissindustries.fairrepack.httprequests.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AssocActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.assoc_activity);


        ListView listView = findViewById(R.id.assoc_view);
        AssocAdapter assocAdapter = new AssocAdapter(AssocActivity.this, getAssoc());
        listView.setAdapter(assocAdapter);
    }

    public List<Assoc> getAssoc(){
        String url = "https://pa.quozul.dev/api/association/read.php";
        List<Assoc> assocs = new ArrayList<>();
        try {
           new RequestHandler(url, "GET", sharedPreferences.getString("token", null), new RequestCallback() {
           @Override
           public void run(){
               try {
                   // On va avoir un tableau d'associations il faut donc parse le tableau
                   JSONArray jsonArray = (JSONArray)response.get("items");
                   for (int i = 0; i < jsonArray.length(); i++) {
                       //  Recupration de chaque membres des associations
                       String name = response.get("name").toString();
                       String descr = response.get("description").toString();
                       int coin = Integer.parseInt(response.get("coin").toString());
                       String address = response.get("address").toString();
                       // Mise en place dans un tableau
                       assocs.add(new Assoc(name, descr, address, coin));
                   }
               } catch (JSONException e){
                   e.printStackTrace();
               }
           }
           });
        } catch (Exception e){
            e.printStackTrace();
        }
        return assocs;
    }
}
