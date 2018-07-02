package com.example.android.dmusic.ModelClasses;

public class Genre {
    public musicType music_genre;

    public musicType getMusic_genre() {
        return music_genre;
    }

    public Genre(musicType music_genre) {

        this.music_genre = music_genre;
    }
}
