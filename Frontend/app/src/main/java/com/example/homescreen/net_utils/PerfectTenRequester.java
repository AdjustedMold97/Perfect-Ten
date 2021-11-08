package com.example.homescreen.net_utils;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.homescreen.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A class used to send out various HTTP Requests
 * using Volley.
 *
 * @author Jae Swanepoel
 */
public class PerfectTenRequester {

    public String addFriend(String targetUser) throws InterruptedException {

        final String[] output = new String[1];

        //Instantiating the JSONObject and populating it
        JSONObject info = new JSONObject();
        try {
            info.put("user", targetUser);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Instantiating the JsonObjectRequest
        JsonObjectRequest addFriendRequest = new JsonObjectRequest(Request.Method.POST,
                Const.ADD_FRIEND_URL_1 + AppController.getUsername() + Const.ADD_FRIEND_URL_2, info,

                response -> {

                    try {

                        Log.d("Server response ", response.toString());

                        //Changing text on addFriendButton upon successful request
                        if (response.get("message").equals(Const.SUCCESS_MSG))
                            output[0] = Const.SUCCESS_MSG;

                        else
                            output[0] = getErrorMessage(response.get("message").toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                error -> VolleyLog.d("Error ", error.getMessage())
        );

        //Adding the Request to the Queue
        AppController.getInstance().addToRequestQueue(addFriendRequest);

        return output[0];
    }

    public JSONArray getFriendsList() {

        final JSONArray[] out = new JSONArray[1];

        JsonArrayRequest friendsListReq = new JsonArrayRequest(
                Const.FRIEND_LIST_URL_1 + AppController.getUsername() + Const.FRIEND_LIST_URL_2,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(Const.RESPONSE_TAG, response.toString());
                    //    callback.onSuccess(response);
                        out[0] = response;

                    }
                },

                error -> VolleyLog.d(Const.ERROR_RESPONSE_TAG, error.getMessage())
        );

        AppController.getInstance().addToRequestQueue(friendsListReq);

        return out[0];
    }

    private String getErrorMessage(String errCode) {


        return null;
    }
}

