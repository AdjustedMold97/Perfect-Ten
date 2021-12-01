package com.example.homescreen.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.homescreen.Adapters.MyAdapter;
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

    TextView titleView;
    TextView bodyView;

    RecyclerView comments;
    RecyclerView.LayoutManager mLayoutManager;
    MyAdapter adapter;

    JSONObject post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        //configuring post for popout
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        EditText commentText = findViewById(R.id.comment_EditText);

        Button submit = findViewById(R.id.submit_comment_Button);
        submit.setOnClickListener(view -> submitComment(commentText.getText().toString()));

        titleView = findViewById(R.id.post_view_title);
        bodyView = findViewById(R.id.post_view_body);

        comments = findViewById(R.id.comments_Recycler);

        getPost();
    }

    private void setUpTextPost() {

        try {
            titleView.setText(post.get("title").toString());
            bodyView.setText(post.get("message").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getComments();
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

    private void submitComment(String comment) {

        //TODO

    }

    private void getComments() {

        //TODO

    }
}