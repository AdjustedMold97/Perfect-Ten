package com.example.experiment1_try3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Counter extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter);


        Button Home = findViewById(R.id.Home);
        Button GoUp = findViewById(R.id.GoUp);
        TextView counterText = findViewById(R.id.CounterText);

        Integer[] value = {0};

        Home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // where write logic for button
                startActivity(new Intent(view.getContext(),MainActivity.class));
            }
        });

        GoUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                value[0]++;
                counterText.setText(value[0].toString());
            }
        });


    }
}