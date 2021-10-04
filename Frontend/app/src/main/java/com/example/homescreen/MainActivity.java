package com.example.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button to get to Friend Activity - Jae Swanepoel
        Button friend = findViewById(R.id.Friends);

        friend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),FriendsScreen.class));
            }

        });
//========================================
        /*
         * these three buttons make up the bottom tab buttons they go on each screen
         * they include Home, Settings, and Friends and reroute the user to that page form anywhere in the app
         * - Ethan Still
         */
        Button settings = findViewById(R.id.Settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(view.getContext(),SettingsScreen.class));

            }
        });

        Button home = findViewById(R.id.Home);

        /*
         * Commenting out this code because there's really no need to
         * have a button from the home screen that goes to the home screen.
         * just having it do nothing here. -Jae
         *
         * -Ethan Still:  its just meant as a test or to reload page
         */
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(view.getContext(),MainActivity.class));

            }
        });
//===========================================

        /*
         * signs the user out, back to the login screen
         * - Ethan Still
         */
        Button toLogin = findViewById(R.id.login);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(view.getContext(),LoginScreen.class));

            }
        });

        Button post_button = findViewById(R.id.post);

        post_button.setOnClickListener(new View.OnClickListener() {
            /*
             * - button sends to post creation screen onClick
             * - Ethan Still
             */
            @Override
            public void onClick(View view) {

                startActivity(new Intent(view.getContext(),PostCreation.class));

            }
        });

    }

}