package com.example.PerfectTen.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PerfectTen.R;

/**
 * UniversalBar holds buttons to send a user to HomeScreen, Settings, and FriendsScreen
 * The bar is placed at the bottom of screens within the app
 * Provides access to the three screens from any page with UniversalBar
 * @author Ethan Still
 */
public class universalBar extends AppCompatActivity {

    /**
     * OnCreate sets the buttons in UniversalBar to the activities they need to send to
     *
     * @param savedInstanceState
     *
     * @author Ethan Still
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal_tab_bar);

        Button friend = findViewById(R.id.friends_Button);
        friend.setOnClickListener(view -> startActivity(new Intent(view.getContext(), FriendsScreen.class)));

        Button settings = findViewById(R.id.settings_Button);
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(), SettingsScreen.class)));

        Button home = findViewById(R.id.home_Button);
        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(), HomeScreen.class)));
    }
}
