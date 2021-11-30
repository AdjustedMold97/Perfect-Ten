package com.example.homescreen.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.homescreen.R;

/**
 * @author Jae Swanepoel
 */
public class PostView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
    }

    /**
     *
     *
     * @param title
     * @param body
     */
    private void setUpTextPost(String title, String body) {

        TextView titleView = findViewById(R.id.post_view_title);
        TextView bodyView = findViewById(R.id.post_view_body);

        titleView.setText(title);
        bodyView.setText(body);

        //TODO comments
    }
}