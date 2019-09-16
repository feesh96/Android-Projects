package org.example.matthewfishman.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus { IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK }

/**
 * Created by Matthew on 1/6/2017.
 */

class GetRawData extends AsyncTask<String, Void, String>{
    private static final String TAG = "GetRawData";

    private DownloadStatus mDownloadStatus;
    private final OnDownloadComplete mCallback;

    public interface OnDownloadComplete {
        void onDownloadComplete(String data, DownloadStatus status);
    }

    public GetRawData(OnDownloadComplete callback) {
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mCallback = callback;
    }

    void runInSameThread(String s) {
        Log.d(TAG, "runInSameThread: Starts");

        onPostExecute(doInBackground(s));

        Log.d(TAG, "runInSameThread: Finished");
    }

    @Override
    protected void onPostExecute(String s) {
//        Log.d(TAG, "onPostExecute: parameter = " + s);

        if (mCallback != null) {
            mCallback.onDownloadComplete(s, mDownloadStatus);
        }

        Log.d(TAG, "onPostExecute: Finished");
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if (params == null) mDownloadStatus = DownloadStatus.NOT_INITIALIZED;

        try {
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(params[0]);   //Malformed URL exception?

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); //Also the default request method
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: the response code was " + response);

            StringBuilder result = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

//            String line;
//            while (null != (line = reader.readLine())) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.append(line).append("\n");
            }

            mDownloadStatus = DownloadStatus.OK;
            return result.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid URL " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IO exception reading data " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "doInBackground: Security exception. Needs permission? " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                }
                catch(IOException e) {
                    Log.e(TAG, "doInBackground: Error closing stream " + e.getMessage());
                }
            }
        }

        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }
}
