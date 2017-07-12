package com.gaurav.android.newsfolo;

import android.text.TextUtils;
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
import java.util.List;

public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private QueryUtils(){
    }
    public static List<Headlines> fetchHeadlines(String requesrUrl){
        URL url = createUrl(requesrUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        } catch(IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Headlines> headlines = extractFratureFromJson(jsonResponse);
        return headlines;
    }
    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the URL", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG, "Error Response code: "+ urlConnection.getResponseCode());
            }
        }catch(IOException e){
            Log.e(LOG_TAG, "Problem retrieving the headlines JSON results.", e);
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}private static List<Headlines> extractFratureFromJson(String headlinesJSON){
    if (TextUtils.isEmpty(headlinesJSON)){
        return null;
    }
    List<Headlines> headlines = new ArrayList<>();
    try{
        JSONObject baseJsonResponse = new JSONObject(headlinesJSON);
        JSONArray headlinesArray = baseJsonResponse.getJSONArray("");
        for(int i=0; i<headlinesArray.length();i++){
            JSONObject
        }
    }catch(Exception e){

    }
}
