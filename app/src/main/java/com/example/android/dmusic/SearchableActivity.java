package com.example.android.dmusic;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.android.dmusic.ModelClasses.DataList;
import com.example.android.dmusic.ModelClasses.EntireBody;
import com.example.android.dmusic.adapters.CustomAdapter;
import com.example.android.dmusic.data.contractClass.faviTable;
import com.example.android.dmusic.adapters.customCusrorAdap;
import com.example.android.dmusic.data.searchHistory;

//NOT THIS CLASS IS USED  BY TO ACTIVITES, DIFFERED BY THE INTENT THEY SEND TO THIS CLASS

public class SearchableActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static String API_KEY = "b4267de29314b95aa3c5e20b9a354a80";
    ListView searchList;
    CustomAdapter adapter;
    customCusrorAdap adapter1;
    List data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        searchList = (ListView)findViewById(R.id.searchResultList);
        if(getIntent().getExtras().getBoolean("forFav")){                                       //WHEN REQUESTED TO VIEW FAVOURITES
            adapter1 = new customCusrorAdap(this,null);
            searchList.setAdapter(adapter1);
            getLoaderManager().initLoader(0,null,this);                           //BEGIN THE CURSOR LOADER
        }
        else{                                                                                       //WHEN REQUESTED TO VIEW SEARCH RESULTS
            getSearchResults(getIntent());
            searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(SearchableActivity.this,track_info.class);
                    intent.putExtra("resultObject",adapter.getItem(position).getTrack());
                    startActivity(intent);

                }
            });
        }
    }
    @Override
    public void onNewIntent(Intent intent) {                                                        //SNGLE TOP CALLS ONNEWINTENT METHOD
        setIntent(intent);
        getSearchResults(intent);
    }

    private void getSearchResults(Intent intent) {                                                  //PARSE THE SEARCH RESULT USING RETROFIT
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions src = new SearchRecentSuggestions(this,searchHistory.CONTENT_URI,searchHistory.MODE);
            src.saveRecentQuery(query,null);

            Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            Api service = retrofit.create(Api.class);
            Call<EntireBody> searchBody = service.getSearchResults(API_KEY,query,"DESC","DESC");

            searchBody.enqueue(new Callback<EntireBody>() {
                @Override
                public void onResponse(Call<EntireBody> call, Response<EntireBody> response) {
                    List<DataList> objects = response.body().getMessage().getBody().getTrack_list();
                    adapter = new CustomAdapter(SearchableActivity.this,objects);
                    searchList.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<EntireBody> call, Throwable t) {

                }
            });
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {faviTable._ID,faviTable.TRACK,faviTable.ALBUM,faviTable.ARTIST,faviTable.LENGTH,faviTable.YEAR};
        return new CursorLoader(this,faviTable.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter1.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter1.swapCursor(null);
    }
}
