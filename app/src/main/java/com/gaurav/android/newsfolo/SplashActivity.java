package com.gaurav.android.newsfolo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity {
    //time in milliseconds
    private static final long SPLASHTIME = 3000;
    public static List<Headline> HeadlinesList;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.HomeUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    QueryUtils.writeToFile(s,getApplicationContext());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyError.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Volley Error",Toast.LENGTH_LONG).show();
                    }
            });
            SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            HeadlinesList = new ArrayList<>();
            progressBar.setVisibility(View.GONE);
            Intent i = new Intent(SplashActivity.this, DrawerActivity.class);
            startActivity(i);
        }
    }
}