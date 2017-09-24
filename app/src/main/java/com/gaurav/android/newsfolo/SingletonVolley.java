package com.gaurav.android.newsfolo;

/**
 * Created by gaurav on 20/8/17.
 */

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


class SingletonVolley {

    private static SingletonVolley instance;
    private RequestQueue requestQueue;
    private static Context context;

    private SingletonVolley(Context context){
        SingletonVolley.context =  context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    static synchronized SingletonVolley getInstance(Context context){

        if (instance == null){
            instance = new SingletonVolley(context);
        }
        return instance;
    }

    <T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }

}
