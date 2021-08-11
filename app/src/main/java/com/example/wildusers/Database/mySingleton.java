package com.example.wildusers.Database;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;


public class mySingleton {
    private static mySingleton singleton;
    private RequestQueue requestQueue;
    private static Context mContext;

    private mySingleton(Context context){

        mContext = context;
        requestQueue = getRequestQueue();
    }
    private RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized mySingleton getInstance(Context context){
        if(singleton == null){
            singleton = new mySingleton(context);
        }
        return singleton;
    }
    public<T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }
}
