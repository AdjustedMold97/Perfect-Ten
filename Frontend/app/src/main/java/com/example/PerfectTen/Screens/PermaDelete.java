package com.example.PerfectTen.Screens;

import static com.example.PerfectTen.net_utils.Const.DELETE_POST_URL;
import static com.example.PerfectTen.net_utils.Const.DELETE_USER_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;
import com.example.PerfectTen.net_utils.Const;
import com.example.PerfectTen.net_utils.PerfectTenRequester;
import com.example.PerfectTen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PermaDelete extends AppCompatActivity {

    TextView delText;
    VolleyCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perma_delete);

        Button yes = findViewById(R.id.yes);
        Button no = findViewById(R.id.no);

        delText = findViewById(R.id.permaDeleteText);

        /*
         * This is the easy onClick
         */
        no.setOnClickListener(view -> startActivity(new Intent(view.getContext(), ProfileView.class)));


        yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                submit();
                startActivity(new Intent(view.getContext(), HomeScreen.class));
                
            }
        });

        callback = new VolleyCallback() {

            @Override
            public void onSuccess(JSONArray response) {
                //unreachable
            }

            @Override
            public void onSuccess(JSONObject response) {
                delText.setText("success user deleted you will be returned to home");
                AppController.setTargetUser(null);
                //TODO wait 3 seconds and then start main
            }

            @Override
            public void onError(VolleyError error) {
                delText.setText("Deletion Failed");
            }
        };




    }
    private void submit(){

        PerfectTenRequester requester;
        String url;
        JSONObject obj = new JSONObject();
        String delUser = AppController.getTargetUser();


        url = DELETE_USER_URL + delUser;



        requester = new PerfectTenRequester(Request.Method.DELETE, url, obj, callback);
        requester.request();
    }

}