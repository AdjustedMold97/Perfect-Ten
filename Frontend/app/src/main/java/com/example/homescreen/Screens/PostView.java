package com.example.homescreen.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.homescreen.R;
import com.example.homescreen.app.AppController;
import com.example.homescreen.net_utils.Const;
import com.example.homescreen.net_utils.PerfectTenRequester;
import com.example.homescreen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jae Swanepoel
 */
public class PostView extends AppCompatActivity {

    JSONObject post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        getPost();
    }

    private void setUpTextPost() {

        TextView titleView = findViewById(R.id.post_view_title);
        TextView bodyView = findViewById(R.id.post_view_body);

        try {
            titleView.setText(post.get("title").toString());
            bodyView.setText(post.get("message").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //TODO comments
    }

    private void getPost() {

        PerfectTenRequester requester = new PerfectTenRequester(Const.GET_POST_URL + AppController.getPostID(), null, new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                //unreachable
                System.out.println("Received an array");
            }

            @Override
            public void onSuccess(JSONObject response) {
                post = response;
                setUpTextPost();
            }

            @Override
            public void onError(VolleyError error) {
                //TODO
                System.out.println("ruh roh");
            }
        });

        requester.request();
    }
}