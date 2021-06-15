package com.saglissindustries.fairrepack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AssocAdapter extends BaseAdapter {
    private Context context;
    List<Assoc> assocs;

    public AssocAdapter(Context context, List<Assoc> assocs){
        this.context = context;
        this.assocs = assocs;
    }

    @Override
    public int getCount(){return assocs.size();}

    @Override
    public Object getItem(int position){return assocs.get(position);}

    @Override
    public long getItemId(int position){return 0;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null ){
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            convertView = layoutInflater.inflate(R.layout.assoc, null);
        }

        TextView name = convertView.findViewById(R.id.name);
        TextView descr = convertView.findViewById(R.id.description);
        TextView coin = convertView.findViewById(R.id.coin);
        TextView addr =convertView.findViewById(R.id.address);

        Assoc assoc = (Assoc)getItem(position);

        name.setText(assoc.getName());
        descr.setText(assoc.getDescr());
        coin.setText(String.valueOf(assoc.getCoin()));
        addr.setText(assoc.getAddress());

        convertView.setOnClickListener(v -> {
            Intent coinIntent = new Intent(context, CoinActivity.class);
            coinIntent.putExtra("uuid", assoc.getUUID());
            coinIntent.putExtra("name", assoc.getName());
            coinIntent.putExtra("descr", assoc.getDescr());
            coinIntent.putExtra("coin", assoc.getCoin());
            coinIntent.putExtra("addr", assoc.getAddress());
            context.startActivity(coinIntent);
        });

        return convertView;
    }
}
