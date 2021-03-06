package com.example.experiment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GorillaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gorilla);

        //initializing objects
        ImageView gorilla_image = findViewById(R.id.gorilla_image);
        TextView gorilla_text = findViewById(R.id.gorilla_text);
        Button appear = findViewById(R.id.view_gorilla_button);
        Button disappear = findViewById(R.id.delete_gorilla_button);
        Button homeButton = findViewById(R.id.gorilla_home_button);

        //setting default state
        gorilla_text.setVisibility(View.INVISIBLE);
        gorilla_image.setVisibility(View.INVISIBLE);
        appear.setVisibility(View.VISIBLE);
        disappear.setVisibility(View.INVISIBLE);

        //fixing buttons
        appear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gorilla_text.setVisibility(View.VISIBLE);
                gorilla_image.setVisibility(View.VISIBLE);
                appear.setVisibility(View.INVISIBLE);
                disappear.setVisibility(View.VISIBLE);

            }
        });

        disappear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gorilla_text.setVisibility(View.INVISIBLE);
                gorilla_image.setVisibility(View.INVISIBLE);
                appear.setVisibility(View.VISIBLE);
                disappear.setVisibility(View.INVISIBLE);

            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(view.getContext(), MainActivity.class));

            }
        });
    }
}