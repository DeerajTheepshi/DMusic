package com.example.android.dmusic.ModelClasses;

import java.io.Serializable;
import java.util.List;

public class PGenre implements Serializable{
    public List<Genre> music_genre_list;


    public List<Genre> getMusic_genre_list() {
        return music_genre_list;
    }

    public PGenre(List<Genre> music_genre_list) {

        this.music_genre_list = music_genre_list;
    }
}
