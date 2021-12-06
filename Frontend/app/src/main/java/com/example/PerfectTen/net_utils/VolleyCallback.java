package com.example.PerfectTen.net_utils;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Allows activities to execute some code
 * inside of a Volley Request body.
 *
 * @author Jae Swanepoel
 */
public interface VolleyCallback{
    void onSuccess(JSONArray response);
    void onSuccess(JSONObject response);
    void onError(VolleyError error);
}
