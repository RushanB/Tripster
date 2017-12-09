package com.example.trip.tripster;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

/**
 * Created by rush on 2017-12-08.
 */

public class RequestManager {

    private static final String TAG = RequestManager.class.getSimpleName();

    public RequestManager() {

    }

    public String readResponse(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        }catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return stringBuilder.toString();
    }

    public String makeRequest(String request) {
        String response = null;

        try {
            //make the request
            URL url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //read the request
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            response = readResponse(inputStream);
        }catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }catch (ProtocolException e) {
            Log.e(TAG, e.getMessage());
        }catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return response;
    }




}
