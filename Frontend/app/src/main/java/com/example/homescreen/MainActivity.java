package com.example.homescreen;

import static com.example.homescreen.net_utils.Const.POST_LIST_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.homescreen.net_utils.Const;

import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String RESPONSE_TAG = "JSON Response: ";

        //need to know what user is active

        String user_Post_URL = POST_LIST_URL + MyApplication.getUsername();

        JsonObjectRequest post1 = new JsonObjectRequest(Request.Method.GET, user_Post_URL,null

                response -> {

                Log.d(RESPONSE_TAG, response.toString());



                }

                error -> {


                }

        );


        //Button to get to Friend Activity - Jae Swanepoel
        Button friend = findViewById(R.id.friends_Button);

        friend.setOnClickListener(view -> startActivity(new Intent(view.getContext(),FriendsScreen.class)));

//========================================
        /*
         * these three buttons make up the bottom tab buttons they go on each screen
         * they include Home, Settings, and Friends and reroute the user to that page form anywhere in the app
         * - Ethan Still
         */
        Button settings = findViewById(R.id.settings_Button);

        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(),SettingsScreen.class)));

        Button home = findViewById(R.id.home_Button);

        /*
         * Commenting out this code because there's really no need to
         * have a button from the home screen that goes to the home screen.
         * just having it do nothing here. -Jae
         *
         * -Ethan Still:  its just meant as a test or to reload page
         */
        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(),MainActivity.class)));
//===========================================

        /*
         * signs the user out, back to the login screen
         * - Ethan Still
         */
        Button toLogin = findViewById(R.id.sign_out_Button);

        toLogin.setOnClickListener(view -> startActivity(new Intent(view.getContext(),LoginScreen.class)));

        Button post_button = findViewById(R.id.post_create_Button);

        /*
         * - button sends to post creation screen onClick
         * - Ethan Still
         */
        post_button.setOnClickListener(view -> startActivity(new Intent(view.getContext(),PostCreation.class)));

    }

}