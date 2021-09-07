package com.example.experiment1_try3;

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



        Button counter = findViewById(R.id.activity_main_button_counter);

        counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // where write logic for button
                startActivity(new Intent(view.getContext(),Counter.class));
            }
        });



    }
}