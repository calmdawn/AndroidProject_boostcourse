package com.calmdawn.boostcoursemobileproject.network;

import android.app.Application;
import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class NetworkManager {

    private static NetworkManager instance;
    private static Context ctx;

    private RequestQueue requestQueue;
    private String host = "boostcourse-appapi.connect.or.kr";
    private int port = 10000;
    private String baseUrl = "http://" + host + ":" + port;


    public NetworkManager(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized NetworkManager getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkManager(context);
        }
        return instance;
    }


    public void sendGetRequest(String url, String query, RequestResultListener listener) {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                baseUrl + url + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.requestSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.requestError(error);
                    }
                }
        );

        request.setShouldCache(false);
        NetworkManager.getInstance(ctx).getRequestQueue().add(request);

    }

    public void sendPostRequest(String url, String query, Map<String, String> params, RequestResultListener listener) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                baseUrl + url + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.requestSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.requestError(error);
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        request.setShouldCache(false);
        NetworkManager.getInstance(ctx).getRequestQueue().add(request);
    }


    public interface RequestResultListener {
        public void requestSuccess(String response);

        public void requestError(VolleyError error);
    }


}
