package com.example.PerfectTen.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.PerfectTen.R;

public class PermaDelete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perma_delete);

        Button yes = findViewById(R.id.yes);
        Button no = findViewById(R.id.no);

        yes.setOnClickListener(view -> startActivity(new Intent(view.getContext(), HomeScreen.class)));
        no.setOnClickListener(view -> startActivity(new Intent(view.getContext(), HomeScreen.class)));
    }
}