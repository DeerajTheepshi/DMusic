package com.example.android.dmusic.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.dmusic.data.contractClass.faviTable;

public class helperClass extends SQLiteOpenHelper {

    public final static String DATABASE_NAME="DMusic";
    public final static int DATABASE_VERSION = 1;

    public helperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY = "CREATE TABLE " + faviTable.TABLE_NAME + " ("
                + faviTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + faviTable.TRACK + " TEXT NOT NULL, "
                + faviTable.ARTIST + " TEXT NOT NULL, "
                + faviTable.ALBUM + " TEXT NOT NULL, "
                + faviTable.YEAR + " TEXT NOT NULL, "
                + faviTable.LENGTH +" INT NOT NULL );";

        db.execSQL(QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
