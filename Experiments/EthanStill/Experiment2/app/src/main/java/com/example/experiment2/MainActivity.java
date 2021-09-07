package com.example.experiment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // buttons code below
        //initialize buttons
        Button Abut = findViewById(R.id.aButton);
        Button Dbut = findViewById(R.id.dButton);
        Button Obut = findViewById(R.id.oButton);
        Button Rbut = findViewById(R.id.rButton);
        Button Fbut = findViewById(R.id.fButton);
        Button Gbut = findViewById(R.id.gButton);

        //final String[] stringVal = {""};
        ArrayList<String> stringVal = new ArrayList<>();

        TextView wordAnswer = findViewById(R.id.wordAnswer);

        Abut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringVal.add("a");
                wordAnswer.setText(stringVal.toString());
            }
        });

        Dbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                stringVal[0] = "d";
//                wordAnswer.setText(stringVal[0]);
                stringVal.add("d");
                wordAnswer.setText(stringVal.toString());
            }
        });

        Obut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringVal.add("o");
                wordAnswer.setText(stringVal.toString());
            }
        });

        Rbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringVal.add("r");
                wordAnswer.setText(stringVal.toString());
            }
        });

        Fbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringVal.add("r");
                wordAnswer.setText(stringVal.toString());
            }
        });

        Gbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringVal.add("g");
                wordAnswer.setText(stringVal.toString());
            }
        });



        //check if answer is correct
        String answer = "[d,o,g]";

        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(stringVal.toString().equals(answer)) {

                    wordAnswer.setText("correct!");
                }
                else{
                    wordAnswer.setText("Wrong!");

//                    while(stringVal.size() > 0){
//
//                        stringVal.remove(stringVal.size());
//
//                    }
                    }
                }





        });




    }
}