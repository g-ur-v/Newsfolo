package com.gaurav.android.newsfolo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils extends AppCompatActivity {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public QueryUtils(){
    }

    public static List<Headline> fetchHeadlines(String requestUrl, Context context){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            if (!fileDataChecker("download.txt", context)) {
                jsonResponse = makeHttpRequest(url);
                writeToFile(jsonResponse, context);
            } else{
                jsonResponse = readFromFile(context);
            }
        } catch(IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return extractFeatureFromJson(jsonResponse);
    }

    public static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the URL", e);
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException{
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
        } catch(IOException e){
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
    private static List<Headline> extractFeatureFromJson(String headlinesJSON){
        if (TextUtils.isEmpty(headlinesJSON)){
            return null;
        }
        List<Headline> headlines = new ArrayList<>();
        try {
            JSONArray headlinesArray = new JSONArray(headlinesJSON);

            for (int i = 0; i < headlinesArray.length(); i++) {
                JSONObject headlineObj = headlinesArray.getJSONObject(i);

                int id = headlineObj.getInt("id");

                String time = headlineObj.getString("modified_gmt");

                JSONObject titleObj = headlineObj.getJSONObject("title");
                String title = titleObj.getString("rendered");

                String link = headlineObj.getString("link");

                JSONObject contentObj = headlineObj.getJSONObject("content");
                String content = contentObj.getString("rendered");

                String authorName= headlineObj.getString("author");

                JSONObject imageObj = headlineObj.getJSONObject("better_featured_image");
                String imageSrc = imageObj.getString("source_url");

                Headline headline = new Headline(id, title, link, authorName,imageSrc, content, time);
                headlines.add(headline);
            }
        }catch(Exception e){
            Log.e("QueryUtils", "Problem parsing the headlines JSON results", e);
            e.printStackTrace();
        }
        return headlines;
    }

    private static void writeToFile(String data, Context context){
        try{
            OutputStreamWriter outputStreamWriter =  new OutputStreamWriter(context.openFileOutput("download.txt", Context.MODE_APPEND));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }catch(IOException e){
            Log.e("QueryUtils", "Problem writing JSON to the file", e);
            e.printStackTrace();
        }
    }

    private static String readFromFile(Context context){
        String result = "";
        try {
            InputStream inputStream = context.openFileInput("download.txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String tempString;
                StringBuilder stringBuilder = new StringBuilder();
                while((tempString =bufferedReader.readLine())!=null){
                    stringBuilder.append(tempString);
                }
                inputStream.close();
                result = stringBuilder.toString();
            }
        } catch(IOException e){
            Log.e("QueryUtils", "Problem reading JSON from the file", e);
            e.printStackTrace();
        }
        catch (NullPointerException e){
            Log.e("QueryUtils", "Problem reading JSON from the file (open File Input causing error)", e);
            e.printStackTrace();
        }
        return result;
    }

    private static boolean fileDataChecker(String fileName, Context context){
        boolean result = false;
        try{
            File file = new File(context.getCacheDir(),fileName);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                result = br.readLine() != null;
            }
        }  catch(FileNotFoundException file){
            Log.e("QueryUtils", "Problem reading JSON from the file while file checking(file not present)", file);
            file.printStackTrace();
        } catch (IOException e){
            Log.e("QueryUtils", "Problem reading JSON form the file while checking", e);
            e.printStackTrace();
        }
        return result;
    }
}