package com.example.android.dmusic.ModelClasses;

import java.io.Serializable;

public class musicType implements Serializable{
    public String music_genre_name;

    public String getMusic_genre_name() {
        return music_genre_name;
    }

    public musicType(String music_genre_name) {

        this.music_genre_name = music_genre_name;
    }
}
