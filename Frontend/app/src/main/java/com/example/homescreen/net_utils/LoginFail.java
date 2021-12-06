package com.example.homescreen.net_utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homescreen.Screens.LoginScreen;
import com.example.homescreen.R;

/**
 * LoginFail pops onto screen when there is an error receiving login information from the server
 * @author Ethan Still
 */
public class LoginFail extends Activity {

    /**
     * pop up window to display login failure
     * Sets the dimensions of the pop up screen
     * @param savedInstancesState
     * @author Ethan Still
     */
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);

        setContentView(R.layout.errorwindow);
        TextView error_Box = findViewById(R.id.textView7);
        //error_Box.setText("Username or Password are incorrect");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));


        Button back = findViewById(R.id.errorCancel);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), LoginScreen.class));

            }
        });

    }
}
