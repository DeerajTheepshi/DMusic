package com.example.android.dmusic.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.android.dmusic.data.contractClass.faviTable;

//CONTENT PROVIDER FOR THE DATABASE, URL MATCH NOT NEEDED AS THE APP ALWAYS REQUEST FOR THE WHOLE TABLE

public class provider extends ContentProvider {

    private helperClass dbhelper;

    @Override
    public boolean onCreate() {                                                 //CREATE AN INSTANCE OF THE HELPER CLASS, THUS CREATE A DATABASE CONNECTION
        dbhelper =  new helperClass(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        cursor = db.query(faviTable.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);           //SETS UP AN LISTENER ON THE URI
        getContext().getContentResolver().notifyChange(uri,null);           //NOTIFY ALL LISTENERS
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }       //NOT USED

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {                       //INSERT DATA INTO THE DATABASE, RETURN NULL IF THERE IS ANY ERROR
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        long id = 0;
        id = db.insert(faviTable.TABLE_NAME,null,values);
        if(id==-1){
            Toast.makeText(getContext(),"Error Inserting Data into Database",Toast.LENGTH_SHORT).show();
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();                                         //DELETE FAVOURITES
        selection = faviTable._ID + "=?";
        selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
        int rows = db.delete(faviTable.TABLE_NAME,selection,selectionArgs);
        if(rows==1){
            return 1;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
