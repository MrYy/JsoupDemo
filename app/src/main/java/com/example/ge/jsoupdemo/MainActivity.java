package com.example.ge.jsoupdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends Activity {
    private static final String T = "MainAcitivity";
    private String feed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView_connect_status = (TextView) findViewById(R.id.textview_connect_status);
        Button button_connect = (Button) findViewById(R.id.button_connect);
        feed=getString(R.string.feed);
        final AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected void onPostExecute(String s) {
                String connectStatus = s;
                textView_connect_status.setText(connectStatus);

            }

            @Override
            protected void onProgressUpdate(Void... values) {

            }

            @Override
            protected String doInBackground(String... params) {
                String connectStatus = "not sure";
                Log.d(T, params[0]);
                Boolean flag = refreshEarthquakes(params[0]);
                if (flag) {
                    connectStatus = "WebSite baidu connected";
                } else {
                    connectStatus = "webSite baidu no response";
                }
                return connectStatus;

            }
        };
        button_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncTask.execute(feed);
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.d(T, "onDrestroy excute");


    }

    public boolean refreshEarthquakes(String feed) {
        URL url;
        String quakeFeed = feed;
        boolean b = false;
        try {
            url = new URL(quakeFeed);
            URLConnection connection;
            connection = url.openConnection();

            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            int responseCode = httpURLConnection.getResponseCode();
            b = responseCode == HttpURLConnection.HTTP_OK;
            Log.d(T, Boolean.toString(b));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;


    }


}
