package com.saglissindustries.fairrepack;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AssocActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assoc_activity);

        ListView listView = findViewById(R.id.assoc_view);
        AssocAdapter assocAdapter = new AssocAdapter(AssocActivity.this, getAssoc());
        listView.setAdapter(assocAdapter);
    }

    public List<Assoc> getAssoc(){
        List<Assoc> assocs = new ArrayList<>();
        //TODO: Faire requete API pour recuperer la liste des associations avec le callback
        return assocs;
    }
}
