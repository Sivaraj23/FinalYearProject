package com.example.user.finalf;


import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HTTPDataHandler {

    static String stream = null;
    Context context;
    public HTTPDataHandler(Context c){
    	context=c;
    }

    public String GetHTTPData(String urlString){
        try{
            URL url = new URL(urlString);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Check the connection status
            if(urlConnection.getResponseCode() == 200)
            {
                // if response code = 200 ok
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                // Read the BufferedInputStream
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }
                stream = sb.toString();
                // End reading...............

                // Disconnect the HttpURLConnection
                urlConnection.disconnect();
            }
            else
            {
                // Do something
            	Toast.makeText(context, "Else  Url connection !=200", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
        	Toast.makeText(context, "Error1 "+e.toString(), Toast.LENGTH_LONG).show();
        }finally {

        }
        // Return the data from specified url
        return stream;
    }
    
}
