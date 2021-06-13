package com.saglissindustries.fairrepack;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

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


    }
}
