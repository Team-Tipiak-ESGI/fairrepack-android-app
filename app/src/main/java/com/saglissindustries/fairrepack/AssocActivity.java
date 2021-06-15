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

public class AssocActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SharedPreferences sharedPreferences;
    ListView listView;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assoc_activity);

        this.sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        back = (Button) findViewById(R.id.back);
        listView = (ListView) findViewById(R.id.assoc_view);

        AssocAdapter assocAdapter = new AssocAdapter(AssocActivity.this, getAssoc());
        listView.setAdapter(assocAdapter);

        back.setOnClickListener(v ->{
            Intent backIntent = new Intent(AssocActivity.this, LandpageActivity.class);
            startActivity(backIntent);
        });
    }

    public List<Assoc> getAssoc() {
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
                            int coin = Integer.parseInt(associationObject.getString("coin"));
                            String address = associationObject.getString("address");
                            // Mise en place dans un tableau
                            assocs.add(new Assoc(name, descr, address, coin));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assocs;
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
