package com.example.experiment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize buttons
        Button Abut = findViewById(R.id.aButton);
        Button Dbut = findViewById(R.id.dButton);
        Button Obut = findViewById(R.id.oButton);
        Button Rbut = findViewById(R.id.rButton);
        Button Fbut = findViewById(R.id.fButton);
        Button Gbut = findViewById(R.id.gButton);

        final String[] stringVal = {""};

        TextView wordAnswer = findViewById(R.id.wordAnswer);

        Abut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringVal[0] = "a";
                wordAnswer.setText(stringVal[0]);
            }
        });

        Dbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringVal[0] = "d";
                wordAnswer.setText(stringVal[0]);
            }
        });

        Obut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringVal[0] = "o";
                wordAnswer.setText(stringVal[0]);
            }
        });

        Rbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringVal[0] = "r";
                wordAnswer.setText(stringVal[0]);
            }
        });

        Fbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringVal[0] = "f";
                wordAnswer.setText(stringVal[0]);
            }
        });

        Gbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringVal[0] = "g";
                wordAnswer.setText(stringVal[0]);
            }
        });
    }
}