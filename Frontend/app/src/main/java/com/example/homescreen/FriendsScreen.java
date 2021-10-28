package com.example.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.homescreen.app.AppController;

public class FriendsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_screen);

        //Rigging the Universal Buttons
        Button friend = findViewById(R.id.friends_Button_home);
        friend.setOnClickListener(view -> startActivity(new Intent(view.getContext(),FriendsScreen.class)));

        Button settings = findViewById(R.id.settings_Button_home);
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(),SettingsScreen.class)));

        Button home = findViewById(R.id.home_Button_home);
        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(), HomeScreen.class)));

        /*
         * For now, clicking on the ImageButton will
         * set the target user to "Bob" and direct you to
         * the profile page.
         *
         * - Jae Swanepoel
         */
        ImageButton bob = findViewById(R.id.Bob_profile_Button);
        bob.setOnClickListener(view -> {
            AppController.setTargetUser("Bob");
            startActivity(new Intent(view.getContext(), ProfileView.class));
        });
    }
}