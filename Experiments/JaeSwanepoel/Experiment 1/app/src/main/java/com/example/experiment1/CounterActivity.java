package com.example.experiment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        Button increment = findViewById(R.id.increment_button);
        Button home = findViewById(R.id.counter_home_button);
        TextView counterText = findViewById(R.id.counter_text);
        TextView woo = findViewById(R.id.woo_text);

        woo.setVisibility(View.INVISIBLE);

        Integer[] count = {0};

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count[0]++;
                counterText.setText(count[0].toString());

                if (count[0] == 10)
                    woo.setVisibility(View.VISIBLE);

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(view.getContext(), MainActivity.class));

            }
        });

    }
}