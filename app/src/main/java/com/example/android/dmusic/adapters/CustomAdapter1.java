package com.example.android.dmusic.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.dmusic.ModelClasses.DataList;
import com.example.android.dmusic.ModelClasses.results;
import com.example.android.dmusic.R;

import java.util.List;

//ADAPTER FOR THE ARTISTS

public class CustomAdapter1 extends ArrayAdapter<DataList> {
    Context context ;
    List<DataList> list;
    public CustomAdapter1(@NonNull Context context, @NonNull List<DataList> objects) {
        super(context, android.R.layout.simple_list_item_1 , objects);
        this.context = context;
        list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {         //OVERRIDE AND SET THE REQUIRED VIEW WITH DATA
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.top_artist_list,parent,false);
        }

        results currentResult = list.get(position).getArtist();

        TextView artistname = (TextView) view.findViewById(R.id.ArtistName1);
        artistname.setText(currentResult.getArtist_name());

        TextView artistrating = (TextView) view.findViewById(R.id.ArtistRating);
        artistrating.setText(currentResult.getArtist_rating());
        return view;
    }
}
