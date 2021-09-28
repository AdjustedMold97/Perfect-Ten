package com.example.homescreen.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.homescreen.R;

public class StringRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_request);
    }

    // Tag used to cancel the request

    String tag_string_req ="string_req";

    String url ="https://api.androidhive.info/volley/string_response.html";

    ProgressDialog pDialog = new ProgressDialog(this);

    pDialog.setMessage("Loading...");
    pDialog.show();

    com.android.volley.toolbox.StringRequest strReq =new com.android.volley.toolbox.StringRequest(Request.Method.GET, url,new Response.Listener<String>() {

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