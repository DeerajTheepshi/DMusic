package com.example.android.dmusic.ModelClasses;

//MODEL CLASS AT DEPTH 4


import java.io.Serializable;
import java.security.PublicKey;
import java.util.List;

public class results implements Serializable {
    public String track_name;
    public String artist_name;
    public String artist_rating;
    public String album_name;
    public int track_length;
    public String artist_country;
    public String artist_twitter_url;
    public String album_coverart_100x100;
    public String first_release_date;
    public String track_share_url;
    public List<Genre> music_genre_list;

    public String getArtist_rating() {
        return artist_rating;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public int getTrack_length() {
        return track_length;
    }

    public String getArtist_country() {
        return artist_country;
    }

    public String getArtist_twitter_url() {
        return artist_twitter_url;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public String getTrack_name() {
        return track_name;
    }

    public String getAlbum_coverart_100x100() {
        return album_coverart_100x100;
    }

    public String getFirst_release_date() {
        return first_release_date;
    }

    public String getTrack_share_url() {
        return track_share_url;
    }

    public List<Genre> getMusic_genre_list() {
        return music_genre_list;
    }

    public results(String track_name, String artist_name, String artist_rating, String album_name, int track_length, String artist_country, String artist_twitter_url, String album_coverart_100x100, String first_release_date, String track_share_url, List<Genre> music_genre_list) {

        this.track_name = track_name;
        this.artist_name = artist_name;
        this.artist_rating = artist_rating;
        this.album_name = album_name;
        this.track_length = track_length;
        this.artist_country = artist_country;
        this.artist_twitter_url = artist_twitter_url;
        this.album_coverart_100x100 = album_coverart_100x100;
        this.first_release_date = first_release_date;
        this.track_share_url = track_share_url;
        this.music_genre_list = music_genre_list;
    }
}
