package com.example.homescreen.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.homescreen.R;

public class SettingsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        Button friend = findViewById(R.id.friends_Button_settings);
        friend.setOnClickListener(view -> startActivity(new Intent(view.getContext(), FriendsScreen.class)));

        Button settings = findViewById(R.id.settings_Button_settings);
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(),SettingsScreen.class)));

        Button home = findViewById(R.id.home_Button_settings);
        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(), HomeScreen.class)));

    }
}