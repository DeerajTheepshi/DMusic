package com.example.android.dmusic.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.dmusic.R;
import com.example.android.dmusic.data.contractClass.faviTable;

public class customCusrorAdap extends CursorAdapter {

    public customCusrorAdap(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.top_tracks_list,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView trackname = (TextView)view.findViewById(R.id.TrackName);
        TextView artistname = (TextView)view.findViewById(R.id.TrackArtist);
        view.findViewById(R.id.fav).setVisibility(View.GONE);

        trackname.setText(cursor.getString(cursor.getColumnIndex(faviTable.TRACK)));
        artistname.setText(cursor.getString(cursor.getColumnIndex(faviTable.ARTIST)));
    }
}
