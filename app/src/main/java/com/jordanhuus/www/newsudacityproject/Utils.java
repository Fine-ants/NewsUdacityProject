package com.jordanhuus.www.newsudacityproject;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by jorda on 5/18/2018.
 */

public class Utils {

    private static final String GUARDIAN_API_KEY = "2f433fae-3b8c-4a71-866a-0d326fde047a";

    public static ArrayList<News> fetchNewsData(String searchItem, boolean isCategory){

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http");
        builder.authority("content.guardianapis.com");
        builder.appendPath("search");
        builder.appendQueryParameter("from-date", "2018-05-01");
        if(isCategory){
            builder.appendQueryParameter("tag", searchItem);
        }else{
            builder.appendQueryParameter("q", searchItem);
        }
        builder.appendQueryParameter("api-key", GUARDIAN_API_KEY);

        // Get url string from
        String urlString = builder.build().toString();

        // Build URL
        URL url = null;
        try{
            url = new URL(urlString);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        // API request and
        ArrayList<News> articles = null;
        try{
            String jsonResponse = makeHttpRequest(url);
            articles = parseJson(jsonResponse);
        }catch (IOException e){
            e.printStackTrace();
        }

        return articles;
    }

    /**
     *  Converts JSON response into News Objects
     * @param jsonString String returned from Http API request
     * @return ArrayList of News Objects ready to be passed to ListView inside ArticlesFragment
     *
     *   API traversal path:
     *   Url                -     response->results[webUrl]
     *   Title              -     response->results[webUrl]
     *   Publication Date   -     response->results[webPublicationDate]
     */
    public static ArrayList<News> parseJson(String jsonString){
        // Init empty news ArrayList
        ArrayList<News> articles = new ArrayList<>();

        try{
            // Root JSON
            JSONObject root = new JSONObject(jsonString);

            // Response JSON
            JSONObject response = root.getJSONObject("response");

            // Results JSON Array
            JSONArray results = response.getJSONArray("results");


            // Loop through array of articles
            for(int i=0; i<results.length(); i++) {

                // Article JSON
                JSONObject article = results.getJSONObject(i);

                // Article Url
                String articleUrlString = article.getString("webUrl");

                // Article Title
                String title = article.getString("webTitle");

                // Article Publication Date
                String publicationDate = article.getString("webPublicationDate");

                // Store new News object
                articles.add(new News(title, articleUrlString, publicationDate));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return articles;
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

    public static String getPlaceholderJson(){
        // http://content.guardianapis.com/search?q=debates&api-key=2f433fae-3b8c-4a71-866a-0d326fde047a
        return "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":24798,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":2480,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"politics/2018/may/10/tories-accused-of-subverting-democracy-by-not-tabling-brexit-debates\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2018-05-10T16:09:05Z\",\"webTitle\":\"Tories accused of 'subverting democracy' by not tabling Brexit debates\",\"webUrl\":\"https://www.theguardian.com/politics/2018/may/10/tories-accused-of-subverting-democracy-by-not-tabling-brexit-debates\",\"apiUrl\":\"https://content.guardianapis.com/politics/2018/may/10/tories-accused-of-subverting-democracy-by-not-tabling-brexit-debates\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"world/2018/may/04/project-fantasy-german-exam-question-debates-brexit-reality\",\"type\":\"article\",\"sectionId\":\"world\",\"sectionName\":\"World news\",\"webPublicationDate\":\"2018-05-04T15:19:29Z\",\"webTitle\":\"Project Fantasy? German exam question debates Brexit reality\",\"webUrl\":\"https://www.theguardian.com/world/2018/may/04/project-fantasy-german-exam-question-debates-brexit-reality\",\"apiUrl\":\"https://content.guardianapis.com/world/2018/may/04/project-fantasy-german-exam-question-debates-brexit-reality\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"science/occams-corner/2017/nov/06/universities-are-part-of-the-solution-to-dysfunctional-brexit-debates\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2017-11-06T11:14:55Z\",\"webTitle\":\"Universities are part of the solution to dysfunctional Brexit debates\",\"webUrl\":\"https://www.theguardian.com/science/occams-corner/2017/nov/06/universities-are-part-of-the-solution-to-dysfunctional-brexit-debates\",\"apiUrl\":\"https://content.guardianapis.com/science/occams-corner/2017/nov/06/universities-are-part-of-the-solution-to-dysfunctional-brexit-debates\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"society/2017/oct/19/paul-keating-says-assisted-dying-unacceptable-as-victoria-debates-law\",\"type\":\"article\",\"sectionId\":\"society\",\"sectionName\":\"Society\",\"webPublicationDate\":\"2017-10-19T05:30:45Z\",\"webTitle\":\"Paul Keating says assisted dying 'unacceptable' as Victoria debates law\",\"webUrl\":\"https://www.theguardian.com/society/2017/oct/19/paul-keating-says-assisted-dying-unacceptable-as-victoria-debates-law\",\"apiUrl\":\"https://content.guardianapis.com/society/2017/oct/19/paul-keating-says-assisted-dying-unacceptable-as-victoria-debates-law\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"news/2018/apr/26/putting-the-antisemitism-debate-in-perspective\",\"type\":\"article\",\"sectionId\":\"news\",\"sectionName\":\"News\",\"webPublicationDate\":\"2018-04-26T16:31:31Z\",\"webTitle\":\"Putting the antisemitism debate in perspective | Letters\",\"webUrl\":\"https://www.theguardian.com/news/2018/apr/26/putting-the-antisemitism-debate-in-perspective\",\"apiUrl\":\"https://content.guardianapis.com/news/2018/apr/26/putting-the-antisemitism-debate-in-perspective\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/blog/live/2018/jan/09/reshuffle-government-tory-cabinet-theresa-may-not-quite-says-new-tory-chair-when-asked-about-party-being-in-a-mess-politics-live\",\"type\":\"liveblog\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2018-01-09T17:58:13Z\",\"webTitle\":\"Brexit department announces concessions over EU withdrawal bill ahead of key debates next week - Politics live\",\"webUrl\":\"https://www.theguardian.com/politics/blog/live/2018/jan/09/reshuffle-government-tory-cabinet-theresa-may-not-quite-says-new-tory-chair-when-asked-about-party-being-in-a-mess-politics-live\",\"apiUrl\":\"https://content.guardianapis.com/politics/blog/live/2018/jan/09/reshuffle-government-tory-cabinet-theresa-may-not-quite-says-new-tory-chair-when-asked-about-party-being-in-a-mess-politics-live\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"music/2018/mar/09/debate-over-nmes-heyday\",\"type\":\"article\",\"sectionId\":\"music\",\"sectionName\":\"Music\",\"webPublicationDate\":\"2018-03-09T17:05:42Z\",\"webTitle\":\"Debate over NME’s heyday | Letters\",\"webUrl\":\"https://www.theguardian.com/music/2018/mar/09/debate-over-nmes-heyday\",\"apiUrl\":\"https://content.guardianapis.com/music/2018/mar/09/debate-over-nmes-heyday\",\"isHosted\":false,\"pillarId\":\"pillar/arts\",\"pillarName\":\"Arts\"},{\"id\":\"technology/2018/apr/09/killer-robots-pressure-builds-for-ban-as-governments-meet\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2018-04-09T10:35:50Z\",\"webTitle\":\"Killer robots: pressure builds for ban as governments meet\",\"webUrl\":\"https://www.theguardian.com/technology/2018/apr/09/killer-robots-pressure-builds-for-ban-as-governments-meet\",\"apiUrl\":\"https://content.guardianapis.com/technology/2018/apr/09/killer-robots-pressure-builds-for-ban-as-governments-meet\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"teacher-network/2017/jul/04/is-technology-delivering-in-schools-our-panel-debates\",\"type\":\"article\",\"sectionId\":\"teacher-network\",\"sectionName\":\"Teacher Network\",\"webPublicationDate\":\"2017-07-04T11:05:54Z\",\"webTitle\":\"Is technology delivering in schools? Our panel debates\",\"webUrl\":\"https://www.theguardian.com/teacher-network/2017/jul/04/is-technology-delivering-in-schools-our-panel-debates\",\"apiUrl\":\"https://content.guardianapis.com/teacher-network/2017/jul/04/is-technology-delivering-in-schools-our-panel-debates\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"science/2018/apr/25/growing-brains-in-labs-why-its-time-for-an-ethical-debate\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2018-04-25T17:07:27Z\",\"webTitle\":\"Growing brains in labs: why it's time for an ethical debate\",\"webUrl\":\"https://www.theguardian.com/science/2018/apr/25/growing-brains-in-labs-why-its-time-for-an-ethical-debate\",\"apiUrl\":\"https://content.guardianapis.com/science/2018/apr/25/growing-brains-in-labs-why-its-time-for-an-ethical-debate\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"}]}}";
    }
}
























