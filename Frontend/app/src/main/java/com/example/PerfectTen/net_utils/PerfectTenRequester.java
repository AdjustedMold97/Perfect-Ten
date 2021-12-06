package com.example.PerfectTen.net_utils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.PerfectTen.app.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A class used to send out various HTTP Requests
 * using Volley.
 *
 * @author Jae Swanepoel
 */
@SuppressWarnings("rawtypes")
public class PerfectTenRequester {

    private int requestMethod;
    private final String url;
    private JSONObject requestObj;
    private JSONArray requestArr;
    private final VolleyCallback callback;
    private final boolean arrayReq;
    private final boolean usesMethod;

    /**
     * Used for JsonObjectRequest.
     *
     * @param requestMethod The Request Method to be used
     * @param url The url being used
     * @param requestBody The JSONObject being sent to the server
     * @param callback The VolleyCallback used for handling the response
     */
    public PerfectTenRequester (int requestMethod, String url, JSONObject requestBody, VolleyCallback callback) {

        this.requestMethod = requestMethod;
        this.url = url;
        this.requestObj = requestBody;
        this.callback = callback;
        arrayReq = false;
        usesMethod = true;

    }

    /**
     * Used for JsonObjectRequest.
     * Uses GET if requestObj is null,
     * uses POST otherwise.
     *
     * @param url The url being used
     * @param requestObj The JSONObject being sent to the server
     * @param callback The VolleyCallback used for handling the response
     */
    public PerfectTenRequester (String url, JSONObject requestObj, VolleyCallback callback) {

        this.url = url;
        this.requestObj = requestObj;
        this.callback = callback;
        arrayReq = false;
        usesMethod = false;

    }

    /**
     * Used for JsonArrayRequest.
     *
     * @param requestMethod The Request Method to be used
     * @param url The url being used
     * @param requestArr The JSONArray being sent to the server
     * @param callback The VolleyCallback used for handling the response
     */
    public PerfectTenRequester (int requestMethod, String url, JSONArray requestArr, VolleyCallback callback) {

        this.requestMethod = requestMethod;
        this.url = url;
        this.requestArr = requestArr;
        this.callback = callback;
        arrayReq = true;
        usesMethod = true;

    }

    /**
     * Used for JsonArrayRequest.
     *
     * @param url The url being used
     * @param callback The VolleyCallback used for handling the response
     */
    public PerfectTenRequester (String url, VolleyCallback callback) {

        this.url = url;
        this.callback = callback;
        arrayReq = true;
        usesMethod = false;

    }

    public void request() {

        Request req;

        Response.Listener objResponse = response -> callback.onSuccess((JSONObject) response);

        Response.Listener arrResponse = response -> callback.onSuccess((JSONArray) response);

        Response.ErrorListener errResponse = error -> {

            VolleyLog.d(Const.ERROR_RESPONSE_TAG, error.toString());
            callback.onError(error);

        };

        if (arrayReq) {

            if (usesMethod)
                req = new JsonArrayRequest(requestMethod, url, requestArr, arrResponse, errResponse);

            else
                req = new JsonArrayRequest(url, arrResponse, errResponse);
        } else {

            if (usesMethod)
                req = new JsonObjectRequest(requestMethod, url, requestObj, objResponse, errResponse);

            else
                req = new JsonObjectRequest(url, requestObj, objResponse, errResponse);
        }

        AppController.getInstance().addToRequestQueue(req);
    }
}