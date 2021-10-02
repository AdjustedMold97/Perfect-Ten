package com.example.volley_test;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.volley_test.app.AppController;
import com.example.volley_test.net_utlls.Const;

public class StringRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_request);

        Button requestString = findViewById(R.id.button);

        // Tag used to cancel the request
        String tag_string_req ="string_req";
        ProgressDialog pDialog = new ProgressDialog(this);

        pDialog.setMessage("Loading...");
        pDialog.show();

        TextView tv = findViewById(R.id.textView);

        StringRequest strReq = new StringRequest(Request.Method.GET, Const.URL_STRING_REQ, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(tag_string_req, response.toString());
                pDialog.hide();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d("Error: "+ error.getMessage());
                pDialog.hide();
            }
        });


        requestString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

            }
        });




    }



}