package com.example.homescreen.net_utils;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.homescreen.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A class used to send out various HTTP Requests
 * using Volley.
 *
 * @author Jae Swanepoel
 */
public class PerfectTenRequester {

    public void addFriend(String targetUser, final VolleyCallback callback) {

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

                    Log.d("Server response ", response.toString());
                    callback.onSuccess(response);

                },

                error -> {

                    VolleyLog.d(Const.ERROR_RESPONSE_TAG, error.toString());
                    callback.onError(error);

                }
        );

        //Adding the Request to the Queue
        AppController.getInstance().addToRequestQueue(addFriendRequest);
    }

    /**
     * Retrieves the friends list of the user
     * as determined by the global "username" variable.
     *
     * @param callback The VolleyCallback being used for activities.
     * @author Jae Swanepoel
     */
    public void getFriendsList(final VolleyCallback callback) {

        JsonArrayRequest friendsListReq = new JsonArrayRequest(
                Const.FRIEND_LIST_URL_1 + AppController.getUsername() + Const.FRIEND_LIST_URL_2,

                response -> {

                    Log.d(Const.RESPONSE_TAG, response.toString());
                    callback.onSuccess(response);

                },

                error -> VolleyLog.d(Const.ERROR_RESPONSE_TAG, error.getMessage())
        );

        AppController.getInstance().addToRequestQueue(friendsListReq);
    }

    /**
     *
     * @param login_info
     * @param callback
     * @author Jae Swanepoel
     */
    public void login(JSONObject login_info, final VolleyCallback callback) {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Const.LOGIN_URL, login_info,

                response -> {

                    Log.d(Const.RESPONSE_TAG, response.toString());
                    callback.onSuccess(response);

                },

                error -> {

                    VolleyLog.d(Const.ERROR_RESPONSE_TAG, error.toString());
                    callback.onError(error);

                });

        AppController.getInstance().addToRequestQueue(req);
    }

    /**
     *
     * @param signup_info
     * @param callback
     * @author Jae Swanepoel
     */
    public void signUp(JSONObject signup_info, final VolleyCallback callback) {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Const.SIGN_UP_URL, signup_info,

                response -> {

                    Log.d(Const.RESPONSE_TAG, response.toString());
                    callback.onSuccess(response);

                },

                error -> {

                    VolleyLog.d(Const.ERROR_RESPONSE_TAG, error.toString());
                    callback.onError(error);

                });

        AppController.getInstance().addToRequestQueue(req);
    }

    /**
     *
     * @param callback
     * @author Jae Swanepoel
     */
    public void getAllPosts(final VolleyCallback callback) {

        JsonArrayRequest req = new JsonArrayRequest(Const.POST_LIST_URL,

                response -> {

                    Log.d(Const.RESPONSE_TAG, response.toString());
                    callback.onSuccess(response);

                },

                error -> {

                    VolleyLog.d(Const.ERROR_RESPONSE_TAG, error.toString());
                    callback.onError(error);

                });

        AppController.getInstance().addToRequestQueue(req);
    }

    public void createPost(JSONObject post_data, final VolleyCallback callback) {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Const.POSTING_URL + AppController.getUsername(), post_data,

                response -> {

                    Log.d(Const.RESPONSE_TAG, response.toString());
                    callback.onSuccess(response);

                },

                error -> {

                    VolleyLog.d(Const.ERROR_RESPONSE_TAG, error.toString());
                    callback.onError(error);

                });

        AppController.getInstance().addToRequestQueue(req);
    }
}