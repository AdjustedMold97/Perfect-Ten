package com.example.volley_test;

//TODO why is this red??

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button stringRequest = findViewById(R.id.btnStringRequest);

        stringRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), StringRequestActivity.class));
            }
        });

        Button jsonReqBtn = findViewById(R.id.btnJsonRequest);

        jsonReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), JsonRequestActivity.class));
            }
        });
    }
}