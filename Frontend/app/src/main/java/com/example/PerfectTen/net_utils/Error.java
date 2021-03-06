package com.example.PerfectTen.net_utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;

import com.example.PerfectTen.R;

/**
 * Error class is popped up on screen when a request to server fails
 * @author Ethan Still
 */

public class Error extends Activity {

    /**
     * pop up window to display errors
     * Sets the dimensions for the pop up windows size
     * Sets up back button to previous window
     * @param savedInstancesState
     * @Author Ethan Still
     */
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);

        setContentView(R.layout.errorwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        Button back = findViewById(R.id.errorCancel);

        back.setOnClickListener(view -> finish());

    }
}
