package com.gaurav.android.newsfolo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity {
    //time in milliseconds
    private static final long SPLASHTIME = 3000;
    private String url = Const.HomeUrl;
    public static List<Headline> HeadlinesList;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        HeadlinesList = new ArrayList<>();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                progressBar.setVisibility(View.GONE);
                Intent i = new Intent(SplashActivity.this, DrawerActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASHTIME);
    }
}
/*
    protected void onCreate(Bundle savedInstanceState) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    Gson gson = new Gson();
                    JSONArray ParentArray = new JSONArray(s);
                    for (int i= 0;i<ParentArray.length();i++){
                        JSONObject ParentObject = ParentArray.getJSONObject(i);
                        JsonDataModel jsonDataModel = gson.fromJson(ParentObject.toString(), JsonDataModel.class);
                        jsonDataModel.setId(ParentObject.getInt("id"));
                        jsonDataModel.setDate(ParentObject.getString("date").substring(0,10));
                        jsonDataModel.setLink(ParentObject.getString("link"));
                        jsonDataModel.setTitleRendered(ParentObject.getJSONObject("title").getString("rendered"));
                        jsonDataModel.setFeaturedMediaUrl(ParentObject.getJSONObject("better_featured_image").getString("source_url"));
                        JsonDatas.add(jsonDataModel);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                finally {
                    if (JsonDatas!=null) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getApplicationContext(), PermissionTransferToHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                writeToFile(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.toast_network_error),Toast.LENGTH_LONG).show();
                try {
                    Gson gson = new Gson();

                    JSONArray ParentArray = new JSONArray(readFromFile());
                    for (int i= 0;i<ParentArray.length();i++){
                        JSONObject ParentObject = ParentArray.getJSONObject(i);
                        JsonDataModel jsonDataModel = gson.fromJson(ParentObject.toString(), JsonDataModel.class);
                        jsonDataModel.setId(ParentObject.getInt("id"));
                        jsonDataModel.setDate(ParentObject.getString("date").substring(0,10));
                        jsonDataModel.setLink(ParentObject.getString("link"));
                        jsonDataModel.setTitleRendered(ParentObject.getJSONObject("title").getString("rendered"));
                        jsonDataModel.setFeaturedMediaUrl(ParentObject.getJSONObject("better_featured_image").getString("source_url"));
                        JsonDatas.add(jsonDataModel);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                finally {
                    if (JsonDatas!=null) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getApplicationContext(), PermissionTransferToHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void writeToFile(String data){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("dont_delete.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFromFile(){
        String result = "";
        try {
            InputStream inputStream = openFileInput("don't_delete.txt");
            if (inputStream!=null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String tempString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((tempString = bufferedReader.readLine())!=null){
                    stringBuilder.append(tempString);
                }
                inputStream.close();
                result = stringBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
*/