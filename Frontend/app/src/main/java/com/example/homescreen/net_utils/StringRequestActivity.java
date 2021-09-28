package com.example.homescreen.net_utils;

import android.app.ProgressDialog;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

public class StringRequestActivity {

    // Tag used to cancel the request

    String tag_string_req ="string_req";

    String url ="https://api.androidhive.info/volley/string_response.html";

    ProgressDialog pDialog =new ProgressDialog(this);

    pDialog.setMessage("Loading...");
    pDialog.show();

    StringRequest strReq =new StringRequest(Request.Method.GET, url,newResponse.Listener<String>() {

        @Override
                publicvoidonResponse(String response) { Log.d(TAG, response.toString()); pDialog.hide();

        }
    },newResponse.ErrorListener() {

        @Override publicvoidonErrorResponse(VolleyError error) {
            VolleyLog.d(TAG,"Error: "+ error.getMessage()); pDialog.hide();

        }
    });





AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

}
