package com.example.appexperiment;

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

        final TextView text = findViewById(R.id.text_id);
        Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            String text1 = "This is an experiment by Adam Sweiger";
            String text2 = "This text has been changed by a button press";
            public void onClick(View v) {
                if (text.getText().equals(text1)) {
                    text.setText(text2);
                }
                else {
                    text.setText(text1);
                }
            }
        });
    }
}
