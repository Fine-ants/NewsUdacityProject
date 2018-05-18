package com.jordanhuus.www.newsudacityproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by jorda on 5/18/2018.
 */

public class Utils {

    public static ArrayList<News> fetchNewsData(String newsCategory){

    }


    /**
     * To be invoked on worker thread; not UI thread
     * @param url address used to make http connection
     * @return JSON response from server API
     * @throws IOException thrown from readFromStream(), httpUrlConnection.connect(),
     */
    public static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try{
            // Setup and establish connection
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000 /* Milliseconds */);
            httpURLConnection.setConnectTimeout(15000 /* Milliseconds */);
            httpURLConnection.connect();

            // Connection success, return string response
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            // Close HttpUrlConnection
            if(httpURLConnection!=null){httpURLConnection.disconnect();}

            // Close HttpUrlConnection
            if(inputStream!=null){inputStream.close();}
        }

        return jsonResponse;
    }


    /**
     * Converts Http request response bytes into a single string
     * @param inputStream stream of bytes returned from http request
     * @return JSON string
     * @throws IOException thrown by  bufferedReader.readLine()
     */
    public static String readFromStream(InputStream inputStream) throws IOException{

        // Init StringBuilder, InputStreamReader, and BufferedReader
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(reader);

        // Loop through each buffered reader line and append to string builder
        String line = bufferedReader.readLine();
        while(line!=null){
            // Append line to string builder
            stringBuilder.append(line);

            // Read next line
            line = bufferedReader.readLine();
        }

        return stringBuilder.toString();
    }
}
























