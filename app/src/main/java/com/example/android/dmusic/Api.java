package com.example.android.dmusic;

import com.example.android.dmusic.ModelClasses.EntireBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    public final String BASE_URL = "http://api.musixmatch.com/ws/1.1/";

    @GET("chart.tracks.get")
    Call<EntireBody> getPopularTracks(@Query("apikey") String apiKey, @Query("page_size") int size);

    @GET("chart.artists.get")
    Call<EntireBody> getPopularArtists(@Query("apikey") String apiKey, @Query("page_size") int size);

    @GET("track.search")
    Call<EntireBody> getSearchResults(@Query("apikey") String apiKey,@Query("q_track_artist") String searchKey);
}
