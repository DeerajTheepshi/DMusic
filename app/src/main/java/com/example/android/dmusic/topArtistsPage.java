package com.example.android.dmusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.dmusic.ModelClasses.DataList;
import com.example.android.dmusic.ModelClasses.EntireBody;
import com.example.android.dmusic.ModelClasses.results;
import com.example.android.dmusic.adapters.CustomAdapter1;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class topArtistsPage extends AppCompatActivity {

    private final static String API_KEY = "b4267de29314b95aa3c5e20b9a354a80";
    List<results> resultsArray = new ArrayList<results>();
    ListView popular;
    CustomAdapter1 adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_artists);
        popular = (ListView) findViewById(R.id.topArtists);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        Api service = retrofit.create(Api.class);

        Call<EntireBody> popularTracks = service.getPopularArtists(API_KEY,50);

        popular.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(topArtistsPage.this,track_info.class);
                intent.putExtra("resultObject",adapter.getItem(position).getArtist()    );
                intent.putExtra("Artist",true);
                startActivity(intent);
            }
        });

        popularTracks.enqueue(new Callback<EntireBody>() {
            @Override
            public void onResponse(Call<EntireBody> call, Response<EntireBody> response) {
                List<DataList> objects = response.body().getMessage().getBody().getArtist_list();
                adapter = new CustomAdapter1(topArtistsPage.this,objects);
                popular.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<EntireBody> call, Throwable t) {

            }
        });

    }
}