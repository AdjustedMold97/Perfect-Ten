package com.example.volley_test;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.volley_test.app.AppController;

public class StringRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_request);

        Button requestString = findViewById(R.id.button);

        // Tag used to cancel the request
        String tag_string_req ="string_req";
        String url ="https://api.androidhive.info/volley/string_response.html";
        ProgressDialog pDialog = new ProgressDialog(this);

        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq =new StringRequest(Request.Method.GET, url,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(tag_string_req, response.toString());
                pDialog.hide();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d("","Error: "+ error.getMessage());
                pDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        requestString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


    }



}