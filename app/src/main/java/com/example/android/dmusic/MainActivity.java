package com.example.android.dmusic;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.android.dmusic.ModelClasses.DataList;
import com.example.android.dmusic.ModelClasses.EntireBody;
import com.example.android.dmusic.adapters.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.android.dmusic.ModelClasses.connectivityCheck;

//THE MAIN ACTIVITY CLASS

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = "b4267de29314b95aa3c5e20b9a354a80";
    List<DataList> resultsArray = new ArrayList<DataList>();
    ListView popular;
    CustomAdapter adapter;
    ProgressBar loader;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {                 //INFLATE THE OPTIONS MENU
        getMenuInflater().inflate(R.menu.home_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {               //SET ONCLICK LISTER FOR MENU
        int id = item.getItemId();
        switch (id){
            case R.id.popArtistMenu:
                Intent intent = new Intent(this, topArtistsPage.class);
                startActivity(intent);
                break;
            case R.id.favi:
                Intent intent1 = new Intent(this, SearchableActivity.class);
                intent1.putExtra("forFav",true);
                startActivity(intent1);
                break;
            case R.id.search:
                Log.v("123",onSearchRequested()+"");
                onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        popular = (ListView) findViewById(R.id.topTracks);
        loader = (ProgressBar)findViewById(R.id.progress);

        if(!new connectivityCheck(MainActivity.this).connectivity()){
            new AlertDialog.Builder(this).setTitle("No Network").setMessage("Internet connection is required for the app").setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = getIntent();
                    finish();
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
                Intent intent = new Intent(MainActivity.this,track_info.class);
                intent.putExtra("resultObject",adapter.getItem(position).getTrack());
                startActivity(intent);
            }
        });

        popularTracks.enqueue(new Callback<EntireBody>() {                                          //PARSE THE RESPONSE USING RETROFIT
            @Override
            public void onResponse(Call<EntireBody> call, Response<EntireBody> response) {
                List<DataList> objects = response.body().getMessage().getBody().getTrack_list();
                adapter = new CustomAdapter(MainActivity.this,objects);
                popular.setAdapter(adapter);
                loader.setVisibility(View.GONE);
            }


            @Override
            public void onFailure(Call<EntireBody> call, Throwable t) {

            }
        });
    }


}
