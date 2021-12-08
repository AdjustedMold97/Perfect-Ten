package com.example.PerfectTen.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;

public class DMsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dms_screen);

        TextView username = findViewById(R.id.dms_username_TextView);
        username.setText(AppController.getTargetUser());

        Button backBtn = findViewById(R.id.dms_back_Button);
        backBtn.setOnClickListener(view -> startActivity(new Intent(view.getContext(), FriendsScreen.class)));
    }
}