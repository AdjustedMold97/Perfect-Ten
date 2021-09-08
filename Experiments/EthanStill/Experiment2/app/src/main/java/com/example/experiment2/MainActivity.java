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
        Button Mbut = findViewById(R.id.mButton);
        Button Obut = findViewById(R.id.oButton);
        Button Rbut = findViewById(R.id.rButton);
        Button Fbut = findViewById(R.id.fButton);
        Button Nbut = findViewById(R.id.nButton);

        //final String[] stringVal = {""};
        ArrayList<String> stringVal = new ArrayList<>();


//        public static String outPut(ArrayList<Stirng>() inPut){
//            for(inputString.size()){
//
//            }
//        }
        TextView wordAnswer = findViewById(R.id.wordAnswer);

        Abut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringVal.add("a");
                wordAnswer.setText(stringVal.toString());
            }
        });

        Mbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                stringVal[0] = "d";
//                wordAnswer.setText(stringVal[0]);
                stringVal.add("m");
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

        Nbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringVal.add("n");
                wordAnswer.setText(stringVal.toString());
            }
        });



        //check if answer is correct
        String answer = "man";

        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                wordAnswer.setText("submitted");


            }


        });

    }
}