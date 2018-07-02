package com.example.android.dmusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.dmusic.ModelClasses.Genre;
import com.example.android.dmusic.ModelClasses.results;
import com.squareup.picasso.Picasso;

import java.util.List;

//NOTE THIS CLASS IS USED BY 2 ACTITY DIFFERED BY THE INTENT SENT

public class track_info extends AppCompatActivity {
    TextView songname, artistname, tracklength,albumname,releaseDate,t1,t2,t3,t4,t5,titles;
    ImageView art;
    results resulsObject;
    Button lyricButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_info);
        songname = (TextView)findViewById(R.id.trackNameinfo);
        artistname = (TextView)findViewById(R.id.artistNameinfo);
        albumname = (TextView)findViewById(R.id.albumNameinfo);
        tracklength = (TextView)findViewById(R.id.durationinfo);
        releaseDate=(TextView)findViewById(R.id.relaseDateInfo);
        t1 = (TextView)findViewById(R.id.tn);
        titles = (TextView)findViewById(R.id.titles);
        t2 = (TextView)findViewById(R.id.an1);
        t3 = (TextView)findViewById(R.id.arn1);
        t4 = (TextView)findViewById(R.id.l1);
        t5 = (TextView)findViewById(R.id.rd1);
        art = (ImageView)findViewById(R.id.infoArt);
        lyricButton = (Button)findViewById(R.id.lyricShowButton);

        Bundle output = getIntent().getExtras();

        if(output!=null){
            resulsObject = (results) output.getSerializable("resultObject");                    //GET THE SERIALIZED INPUT
        }

        if(output.getBoolean("Artist")){                                                        //IF IT IS FROM THE ARTIST ACTIVITY
            t1.setText("Artist Name:");
            t2.setText("Artist Country:");
            t3.setText("Twitter Account:");
            t4.setText("Artist Rating: ");
            t5.setText("Genres: ");
            setTitle("About Artist");
            art.setVisibility(View.GONE);

            songname.setText(resulsObject.getArtist_name());
            artistname.setText(!resulsObject.getArtist_twitter_url().isEmpty()?resulsObject.getArtist_twitter_url():"Not Available");
            albumname.setText(!resulsObject.getArtist_country().isEmpty()?resulsObject.getArtist_country():"Not Available");
            tracklength.setText(resulsObject.getArtist_rating());
            titles.setText(resulsObject.getArtist_name());
            String Genres="";
            List<Genre> objects = resulsObject.getPrimary_genres().getMusic_genre_list();
            for(int i=0;i<objects.size();i++){
                Genres += objects.get(i).getMusic_genre().getMusic_genre_name()+"  ";
            }
            releaseDate.setText(Genres);
            lyricButton.setVisibility(View.GONE);
        }

        else{                                                                                       //IF IT IS FROM THE TRACK ACTIVITY

            songname.setText(resulsObject.getTrack_name());
            artistname.setText(resulsObject.getArtist_name());
            albumname.setText(resulsObject.getAlbum_name());
            releaseDate.setText(resulsObject.getFirst_release_date().substring(0,4));
            Picasso.get().load(resulsObject.getAlbum_coverart_100x100()).transform(new TransformCircle()).into(art);
            titles.setVisibility(View.GONE);
            int Duration = resulsObject.getTrack_length();
            String length = Duration/60+":"+Duration%60;

            tracklength.setText(length);
            lyricButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(track_info.this,LyricDisplay.class);
                    intent.putExtra("lyricURL",resulsObject.getTrack_share_url());
                    startActivity(intent);
                }
            });
        }
    }
}
