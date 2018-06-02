package com.example.mohanteja.top10downloader;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.XMLFormatter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listApps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: in the on create method ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //  listApps = (ListView) findViewById(R.id.xmllistview);



        Log.d(TAG, "onCreate: starting Async process");
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");
        Log.d(TAG, "onC0reate: done");


    }

    private class DownloadData extends AsyncTask<String,Void,String> {
        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: *******************************************************************\n"+s);
//            ParseApplications parseApplications =new ParseApplications();
//            parseApplications.parseXml(s);
//
//            parseApplications.getApplications();
//            ArrayAdapter<FeedEntry> arrayAdapter =new ArrayAdapter<FeedEntry>(MainActivity.this,R.layout.list_item,parseApplications.getApplications());
//            listApps.setAdapter(arrayAdapter);


            Log.d(TAG, "onPostExecute: executed parameter is" + s);


        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: passed darameter is :" + strings[0]);
            String rssFeed = downloadXml(strings[0]);

            if(rssFeed==null)
            {
                Log.e(TAG, "doInBackground: Error downloading" );
            }

            return rssFeed;
        }
    }

    private String downloadXml(String urlPath) {
        StringBuffer xmlResult = new StringBuffer(71000);

        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int response = connection.getResponseCode();
            Log.d(TAG, "downloadXml: the response code was " + response);

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            Log.d(TAG, "downloadXml:------------------------------------------------------------------------------------------------\n-----------------------------------------------------------------------------");
            int count=0;
            String stringLine=null;

            while ((stringLine = reader.readLine()) != null) {
                Log.d(TAG, stringLine);
              xmlResult.append(stringLine);

            }
            reader.close();

            Log.d(TAG, "downloadXml:------------------------------------------\n-----------------------\n-------------------------------");
          //
            Log.d(TAG, "______________________--------------------------------------------------------------_________________________________________________--------------------------__________________________");
            Log.d(TAG, "" + xmlResult);
            String xmlString = xmlResult.toString();
            Log.d(TAG, "---------------------------------------------xml--------------------------------------------\n" + xmlString);
            return xmlString;

        } catch (MalformedURLException e) {
            Log.e(TAG, "downloadXml: Invalid URL" + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "downloadXml: IO exception reading data" + e.getMessage());

        }


        return null;

    }






}
