package com.bku.cse.karaoke.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by quangthanh on 5/7/2017.
 */

public class NetworkConnection {
    private static NetworkConnection networkConn;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private NetworkConnection (Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized NetworkConnection getInstance(Context context) {
        if (networkConn == null) {
            networkConn = new NetworkConnection(context);
        }
        return networkConn;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


}
