package com.example.homescreen.net_utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;

import com.example.homescreen.Screens.PostCreation;
import com.example.homescreen.R;

public class FailedPost extends Activity {


    /*
     * pop up window class to display postCreation failure
     * theoretically this screen should never show up because data should always be formatted correctly
     * - Ethan Still
     */
    protected void onCreate(Bundle savedInstancesState) {

        String error_message = "Post failed to send";

        super.onCreate(savedInstancesState);

        setContentView(R.layout.errorwindow);
        TextView error_Box = findViewById(R.id.textView7);
        error_Box.setText(error_message);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));


        Button back = findViewById(R.id.errorCancel);

        back.setOnClickListener(view -> startActivity(new Intent(view.getContext(), PostCreation.class)));

    }


}
