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

        Button friend = findViewById(R.id.Friends);

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(view.getContext(),FriendsScreen.class));

            }
        });

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
         * -Ethan its just meant as a test or to reload page
         */
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(view.getContext(),MainActivity.class));

            }
        });


        Button toLogin = findViewById(R.id.login);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(view.getContext(),LoginScreen.class));

            }
        });

        Button post_button = findViewById(R.id.post);

        post_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                /*
                 * TODO need Ethan
                 *
                 *  we need to come up with a post editor view
                 * this button will direct us to that view
                 *
                 * - Jae
                 */

            }
        });

        /*

         The following will be code used in the post editor screen.
         Entering it here for now so that we can apply it when necessary

         - Jae

         */



    }



}