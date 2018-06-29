package com.example.android.dmusic.ModelClasses;

import java.util.List;

public class Body {
    public List<DataList> track_list;
    public List<DataList> artist_list;

    public List<DataList> getArtist_list() {
        return artist_list;
    }

    public Body(List<DataList> track_list, List<DataList> artist_list) {

        this.track_list = track_list;
        this.artist_list = artist_list;
    }

    public List<DataList> getTrack_list() {
        return track_list;
    }
}
