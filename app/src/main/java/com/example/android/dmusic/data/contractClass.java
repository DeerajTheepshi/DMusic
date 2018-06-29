package com.example.android.dmusic.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class contractClass implements BaseColumns {

    public contractClass() {
    }

    public final static String CONTENT_AUTHORITY = "com.example.android.dmusic";                   //CONTENT URI DESCRIPTION
    public final static Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public final static String PATH = "favi";

    public final static class faviTable implements BaseColumns{                                    //ALL CONSTANTS NEEDED FOR DATABASE
        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH);
        public final static String TABLE_NAME = "favi";
        public final static String _ID = BaseColumns._ID;
        public final static String TRACK = "track";
        public final static String ARTIST = "artist";
        public final static String ALBUM = "album";
        public final static String LENGTH = "length";
        public final static String YEAR = "year";
    }
}
