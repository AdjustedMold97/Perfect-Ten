package com.example.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ProfileView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        /*
         * Rigging bottom buttons.
         *
         * - Jae Swanepoel
         */
        Button home = findViewById(R.id.home_Button_profile);
        Button friends = findViewById(R.id.friends_Button_profile);
        Button settings = findViewById(R.id.settings_Button_profile);

        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(),HomeScreen.class)));
        friends.setOnClickListener(view -> startActivity(new Intent(view.getContext(),FriendsScreen.class)));
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(),SettingsScreen.class)));
    }
}