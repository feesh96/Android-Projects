package com.matthewfishman.top10downloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listApps;
    private String feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
    private int feedLimit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listApps = (ListView) findViewById(R.id.xmlListView);

        if (savedInstanceState != null) {
            feedUrl = savedInstanceState.getString("feed url");
            feedLimit = savedInstanceState.getInt("feed limit");
        }

        downloadURL(String.format(feedUrl, feedLimit));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("feed limit", feedLimit);
        outState.putString("feed url", feedUrl);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feeds_menu, menu);
        if (feedLimit == 10) {
            menu.findItem(R.id.mnu10).setChecked(true);
        } else {
            menu.findItem(R.id.mnu25).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean redownload = true;
        String oldFeedUrl = feedUrl;
        int oldFeedLimit = feedLimit;

        switch (id) {
            case R.id.mnuFree:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
                break;
            case R.id.mnuPaid:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml";
                break;
            case R.id.mnuSongs:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml";
                break;
            case R.id.mnu10:
            case R.id.mnu25:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    feedLimit = 35 - feedLimit;
                    Log.d(TAG, "onOptionsItemSelected: " + item.getTitle() + " setting feed limit to " + feedLimit);
                } else {
                    Log.d(TAG, "onOptionsItemSelected: " + item.getTitle() + " feed limit unchanged");
                    break;
                }
            case R.id.mnuRefresh:
                redownload = true;
                break;
                    
            default:
                return super.onOptionsItemSelected(item);
        }
        if (item.getItemId() != R.id.mnuRefresh) {
            redownload = (!(oldFeedUrl.equals(feedUrl) && oldFeedLimit == feedLimit));
        }
        if (redownload){
            downloadURL(String.format(feedUrl, feedLimit));
        }
        return true;
    }

    private void downloadURL(String feedURL) {
        Log.d(TAG, "downloadURL: starting Asynctask");
        DownloadData downloadData = new DownloadData();
        downloadData.execute(feedURL);
        Log.d(TAG, "downloadURL: done!");
    }

    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadedData";

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "doInBackground: starts with " + params[0]);
            String rssFeed = downloadXML(params[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }
            return rssFeed;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Log.d(TAG, "onPostExecute: parameter is " + s);
            ParseApplications parseApplications = new ParseApplications();
            parseApplications.parse(s);

//            ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<>(
//                    MainActivity.this, R.layout.list_item, parseApplications.getApplications());
//            listApps.setAdapter(arrayAdapter);

            FeedAdapter<FeedEntry> feedAdapter = new FeedAdapter<>(MainActivity.this, R.layout.list_record,
                    parseApplications.getApplications());
            listApps.setAdapter(feedAdapter);
        }

        private String downloadXML (String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The response code was " + response);

//                InputStream inputStream = connection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader reader = new BufferedReader(inputStreamReader);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead;
                char[] inputBuffer = new char[500];
                do {
                    charsRead = reader.read(inputBuffer);
                    if (charsRead > 0)
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                } while (charsRead >= 0);
                reader.close();

                return xmlResult.toString();

            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML: IO exception reading data: " + e.getMessage());
            } catch (SecurityException e) {
                Log.e(TAG, "downloadXML: Security Exception. Needs permission? " + e.getMessage());
//                e.printStackTrace();
            }
            return null;
        }
    }
}
