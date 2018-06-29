package com.example.android.dmusic.ModelClasses;

public class DataList {
    public results track;
    public results artist;

    public results getArtist() {
        return artist;
    }

    public DataList(results track, results artist) {

        this.track = track;
        this.artist = artist;
    }

    public results getTrack() {
        return track;
    }
}
