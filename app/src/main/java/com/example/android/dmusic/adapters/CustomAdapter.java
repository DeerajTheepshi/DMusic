package com.example.android.dmusic.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.dmusic.ModelClasses.DataList;
import com.example.android.dmusic.ModelClasses.results;
import com.example.android.dmusic.R;
import com.example.android.dmusic.TransformCircle;
import com.squareup.picasso.Picasso;

import java.util.List;
import com.example.android.dmusic.data.contractClass.faviTable;

public class CustomAdapter extends ArrayAdapter<DataList> {

    Context context ;
    List<DataList> list;
    public CustomAdapter(@NonNull Context context, @NonNull List<DataList> objects) {
        super(context, android.R.layout.simple_list_item_1 , objects);
        this.context = context;
        list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.top_tracks_list,parent,false);
        }

        final results currentResult = list.get(position).getTrack();
        TextView trackname = (TextView) view.findViewById(R.id.TrackName);
        trackname.setText(currentResult.getTrack_name());

        TextView artistname = (TextView) view.findViewById(R.id.TrackArtist);
        artistname.setText(currentResult.getArtist_name());

        ImageView albumArt = (ImageView) view.findViewById(R.id.trackArt);
        Picasso.get().load(currentResult.getAlbum_coverart_100x100()).transform(new TransformCircle()).into(albumArt);

        final ImageView fav = (ImageView) view.findViewById(R.id.fav);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav.setColorFilter(getContext().getResources().getColor(R.color.fav));
                ContentValues val = new ContentValues();
                val.put(faviTable.TRACK,currentResult.getTrack_name());
                val.put(faviTable.ALBUM,currentResult.getAlbum_name());
                val.put(faviTable.ARTIST,currentResult.getArtist_name());
                val.put(faviTable.LENGTH,currentResult.getTrack_length()/60+":"+currentResult.getTrack_length()%60);
                val.put(faviTable.YEAR,currentResult.getFirst_release_date().substring(0,4));
                Uri uri = getContext().getContentResolver().insert(faviTable.CONTENT_URI,val);
                Toast.makeText(getContext(),currentResult.getArtist_name()+"DATA INSERTED TO : "+uri,Toast.LENGTH_SHORT ).show();
            }
        });

        String selection = faviTable.TRACK + "=?";
        String[] selectionArgs = new String[]{currentResult.getTrack_name()};
        Cursor cursor = getContext().getContentResolver().query(faviTable.CONTENT_URI,new String[]{faviTable.TRACK},selection,selectionArgs,null);
        if(cursor.moveToNext()) {
            fav.setColorFilter(getContext().getResources().getColor(R.color.fav));
        }
        return view;
    }
}
