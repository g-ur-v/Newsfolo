package com.gaurav.android.newsfolo;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
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

public final class QueryUtils extends AppCompatActivity {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){
    }

    public static List<Headline> fetchHeadlines(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);
        } catch(IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return extractFeatureFromJson(jsonResponse);
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
    private static List<Headline> extractFeatureFromJson(String headlinesJSON){
        if (TextUtils.isEmpty(headlinesJSON)){
            return null;
        }
        List<Headline> headlines = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(headlinesJSON);
            JSONArray headlinesArray = baseJsonResponse.getJSONArray("");

            for (int i = 0; i < baseJsonResponse.length(); i++) {
                JSONObject currentHeadline = headlinesArray.getJSONObject(i);

                JSONObject titleObj = currentHeadline.getJSONObject("title");
                String title = titleObj.getString("rendered");

                JSONObject idObj = currentHeadline.getJSONObject("id");
                int id = idObj.getInt("id");

                JSONObject linkObj = currentHeadline.getJSONObject("link");
                String link = linkObj.getString("link");

                JSONObject authorNameObj = currentHeadline.getJSONObject("link");
                String authorName= authorNameObj.getString("author");

                Headline headline = new Headline(id, title, link, authorName);
                headlines.add(headline);
            }
        }catch(Exception e){
            Log.e("QueryUtils", "Problem parsing the headlines JSON results", e);
            e.printStackTrace();
        }
        return headlines;
    }
}
/*String url = "https://www.newsfolo.com/wp-json/wp/v2/posts";
    ListView postList;
    Map<String,Object> mapPost;
    Map<String,Object> mapTitle;
    int postID;
    String postTitle[];
    List<Object> list;
    Gson gson;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        postList = (ListView)findViewById(R.id.postList);
        progressDialog = new ProgressDialog(QueryUtils.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                gson = new Gson();
                list = (List) gson.fromJson(s, List.class);
                postTitle = new String[list.size()];

                for(int i=0;i<list.size();++i){
                    mapPost = (Map<String,Object>)list.get(i);
                    mapTitle = (Map<String, Object>) mapPost.get("title");
                    postTitle[i] = (String) mapTitle.get("rendered");
                }

                postList.setAdapter(new ArrayAdapter(QueryUtils.this,R.layout.home_list_item,postTitle));
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(QueryUtils.this, "Some error occurred", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(QueryUtils.this);
        rQueue.add(request);

        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mapPost = (Map<String,Object>)list.get(position);
                postID = ((Double)mapPost.get("id")).intValue();

                Intent intent = new Intent(getApplicationContext(),HomeFragment.class);
                intent.putExtra("id", ""+postID);
                startActivity(intent);
            }
        });
    }*/