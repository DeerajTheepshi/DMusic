package com.example.android.dmusic.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.android.dmusic.Api;
import com.example.android.dmusic.ModelClasses.DataList;
import com.example.android.dmusic.ModelClasses.EntireBody;
import com.example.android.dmusic.ModelClasses.connectivityCheck;
import com.example.android.dmusic.ModelClasses.results;
import com.example.android.dmusic.R;
import com.example.android.dmusic.adapters.CustomAdapter1;
import com.example.android.dmusic.track_info;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArtistFragment extends Fragment {
    private final static String API_KEY = "b4267de29314b95aa3c5e20b9a354a80";
    List<results> resultsArray = new ArrayList<results>();
    ListView popular;
    CustomAdapter1 adapter;
    ProgressBar loader;

    public ArtistFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.top_artists,container,false);
        popular = (ListView) rootview.findViewById(R.id.topArtists);
        loader = (ProgressBar) rootview.findViewById(R.id.progress3);

        if(!new connectivityCheck(getContext()).connectivity()){
            new AlertDialog.Builder(getContext()).setTitle("No Network").setCancelable(false).setMessage("Internet connection is required for the app").setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = getActivity().getIntent();
                    getActivity().finish();
                    startActivity(intent);
                }
            }).create().show();
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        Api service = retrofit.create(Api.class);

        loader.setVisibility(View.VISIBLE);

        Call<EntireBody> popularTracks = service.getPopularArtists(API_KEY,50);

        popular.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplication(),track_info.class);
                intent.putExtra("resultObject",adapter.getItem(position).getArtist()    );
                intent.putExtra("Artist",true);
                startActivity(intent);
            }
        });

        popularTracks.enqueue(new Callback<EntireBody>() {
            @Override
            public void onResponse(Call<EntireBody> call, Response<EntireBody> response) {
                List<DataList> objects = response.body().getMessage().getBody().getArtist_list();
                adapter = new CustomAdapter1(getContext(),objects);
                popular.setAdapter(adapter);
                loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<EntireBody> call, Throwable t) {

            }
        });

        return rootview;

    }
}

