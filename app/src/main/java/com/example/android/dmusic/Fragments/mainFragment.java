package com.example.android.dmusic.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.android.dmusic.Api;
import com.example.android.dmusic.ModelClasses.DataList;
import com.example.android.dmusic.ModelClasses.EntireBody;
import com.example.android.dmusic.ModelClasses.connectivityCheck;
import com.example.android.dmusic.R;
import com.example.android.dmusic.adapters.CustomAdapter;
import com.example.android.dmusic.track_info;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class mainFragment extends Fragment {

    private final static String API_KEY = "b4267de29314b95aa3c5e20b9a354a80";
    List<DataList> resultsArray = new ArrayList<DataList>();
    ListView popular;
    CustomAdapter adapter;
    ProgressBar loader;

    public mainFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu,menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        setHasOptionsMenu(true);
        popular = (ListView) rootView.findViewById(R.id.topTracks);
        loader = (ProgressBar)rootView.findViewById(R.id.progress);

        Log.v("1234","HOLA");

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

        Api service = retrofit.create(Api.class);                                                   //CREATE A CONNECTION TO RETROFIT API AND USE ITS SERVICE GET METHODS

        loader.setVisibility(View.VISIBLE);

        Call<EntireBody> popularTracks = service.getPopularTracks(API_KEY,50);

        popular.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(),track_info.class);
                intent.putExtra("resultObject",adapter.getItem(position).getTrack());
                startActivity(intent);
            }
        });

        popularTracks.enqueue(new Callback<EntireBody>() {                                          //PARSE THE RESPONSE USING RETROFIT
            @Override
            public void onResponse(Call<EntireBody> call, Response<EntireBody> response) {
                List<DataList> objects = response.body().getMessage().getBody().getTrack_list();
                adapter = new CustomAdapter(getActivity().getApplicationContext(),objects);
                popular.setAdapter(adapter);
                loader.setVisibility(View.GONE);
            }


            @Override
            public void onFailure(Call<EntireBody> call, Throwable t) {

            }
        });

        return rootView;
    }
}

