package com.example.volley_test;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.volley_test.app.AppController;

import org.json.JSONObject;

public class JsonRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_request_activiy);

        // Tag used to cancel the request
        String tag_json_obj ="json_obj_req";
        String url ="https://api.androidhive.info/volley/person_object.json";

        ProgressDialog pDialog =new ProgressDialog(this);

        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq =new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.d("this is a tag", response.toString());
                pDialog.hide();

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d("Error: "+ error.getMessage());
                // hide the progress dialog
                pDialog.hide();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }
}