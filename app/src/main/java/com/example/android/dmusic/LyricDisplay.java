package com.example.android.dmusic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.dmusic.ModelClasses.connectivityCheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

//CLASS TO DOWNLOAD THE 100% LYRIC FROM THE NET

public class LyricDisplay extends AppCompatActivity {
    TextView display;
    ProgressBar loader;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyric_displayer);
        loader = (ProgressBar)findViewById(R.id.progress1);

        display = (TextView)findViewById(R.id.lyrics);

        String url = (String) getIntent().getExtras().get("lyricURL");

        if(!new connectivityCheck(LyricDisplay.this).connectivity()){
            new AlertDialog.Builder(this).setTitle("No Network").setCancelable(false).setMessage("Internet connection is required for the app").setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }).create().show();
        }
        else {
            //START THE BACKGORUND TASK
            lyricDownloader task = new lyricDownloader();
            task.execute(url);
        }
    }

    private class lyricDownloader extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            URL urlObject = null;                                           //THE IDEA HERE IS TO REMOVE LYRICS FROM THE SOURCE CODE OF TH WEBSITE BY PARSING THE
            URLConnection urlConnection = null;                             //SOURCE CODE
            InputStream inputStream=null;
            BufferedReader bufferedReader=null;
            StringBuilder stringBuilder=null;
            try {
                urlObject = new URL(strings[0]);
                urlConnection = urlObject.openConnection();
                urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                stringBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append("\n\n"+inputLine);                 //DISTINGUISH EACH LINE BY A \N FOR EASY PARSING
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert stringBuilder != null;
            return stringBuilder.toString();                           //RETURN THE SOURCE CODE
        }

        @Override
        protected void onPreExecute() {
            loader.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {                    //THE FOLLOWING IS DONE BY ANALYSING THE SOURCE CODE PATTERN
            String Lyrics = "";
            int start = s.indexOf("mxm-lyrics__content")+22;
            int startInstance = start, stop = s.indexOf("\n", startInstance+1);
            int finalstop = s.indexOf("</",start);

            int times = 0,st=s.indexOf("lyrics__content",0);
            while(st>=0){
                times++;
                st=s.indexOf("lyrics__content",st+1);
            }

            if(times==0)
                Lyrics += "LYRIC NOT AVAILABLE, SORRY!";

            if(times>=1) {

                while (stop < finalstop) {

                    stop = s.indexOf("\n", startInstance + 1);
                    if (stop < finalstop)
                        Lyrics += (s.substring(startInstance, stop).isEmpty() ? "" : s.substring(startInstance, stop));
                    else {
                        stop = s.indexOf("</", start);
                        Lyrics += (s.substring(startInstance, stop).isEmpty() ? "" : s.substring(startInstance, stop));
                    }

                    startInstance = stop;
                }
            }



            if(times==2) {

                int startAgain = s.indexOf("mxm-lyrics__content", finalstop + 1) + 22;
                int startInstanceAgain = startAgain, stopAgain = s.indexOf("\n", startInstanceAgain + 1);
                int finalstopAgain = s.indexOf("</", startAgain);

                while (stopAgain < finalstopAgain) {

                    stopAgain = s.indexOf("\n", startInstanceAgain + 1);
                    if (stopAgain < finalstopAgain)
                        Lyrics += (s.substring(startInstanceAgain, stopAgain).isEmpty() ? "" : s.substring(startInstanceAgain, stopAgain));
                    else {
                        stopAgain = s.indexOf("</", startAgain);
                        Lyrics += (s.substring(startInstanceAgain, stopAgain).isEmpty() ? "" : s.substring(startInstanceAgain, stopAgain));
                    }

                    startInstanceAgain = stopAgain;
                }
            }
            display.append("\n\n"+Lyrics);
            loader.setVisibility(View.GONE);
        }
    }
}
