package com.example.ge.jsoupdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class MainActivity extends Activity {
    private static final String T = "MainAcitivity";
    private String feed;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(T, "onDestroy()");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listView = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<String>();
        arrayList.add("网站链接");
        final TextView textView_connect_status = (TextView) findViewById(R.id.textview_connect_status);
        Button button_connect = (Button) findViewById(R.id.button_connect);
        feed = getString(R.string.feed);
        final AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected void onPostExecute(String s) {
                String connectStatus = s;
                textView_connect_status.setText(connectStatus);
                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, arrayList);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            protected void onProgressUpdate(Void... values) {

            }

            @Override
            protected String doInBackground(String... params) {
                String connectStatus = "not sure";
                Log.d(T, params[0]);
                String result = "";
                Boolean flag = refreshEarthquakes(params[0]);
                if (flag) {
                    connectStatus = "WebSite bupt connected";
                                /*处理信息流，解析HTML*/
                    /*模拟信息流*/

/*                    String html = "<html><head><title>First parse</title></head>"
                            + "<body><p>Parsed HTML into a doc.</p></body></html>";
                    Document document = Jsoup.parse(html);
                    String body = document.getElementsByTag("body").toString();
                    Log.d(T, body);
                    String head = document.getElementsByTag("head").toString();
                    Log.d(T, head);
                    */

                    String feed = getString(R.string.feed);
                    try {
                        Connection connection = Jsoup.connect(feed);
                        Document document = connection.get();
                        String html = document.html();
                        Document doc = Jsoup.parse(html, feed);
                        /*采用Jsoup得到document*/
                        Elements links = doc.getElementsByTag("a");
                        for (Element link : links) {
                            String strLink = link.attr("href");
                            if (strLink.startsWith("/")) {
                                String msg = link.attr("abs:href");
                                Log.d(T, msg);
                                arrayList.add(msg);
                            } else if (strLink.startsWith("http")) {
                                String msg = link.attr("href");
                                Log.d(T, msg);
                                arrayList.add(msg);
                            }

                            //            Log.d(T, strLinkText);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    connectStatus = "webSite bupt no response";
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


    private boolean refreshEarthquakes(String feed) {
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
