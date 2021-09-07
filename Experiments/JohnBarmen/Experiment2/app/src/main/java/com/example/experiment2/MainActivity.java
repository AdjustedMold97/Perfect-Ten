package com.example.experiment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String k = "cool number is ";
    public int cnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cnt = 0;


        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(cnt%2==0){
                    Toast.makeText(getApplicationContext(), k + cnt, Toast.LENGTH_SHORT).show();
                }
                else if(cnt>= 10){
                    while(cnt <= 50){
                        Toast.makeText(getApplicationContext(), k + cnt, Toast.LENGTH_SHORT).show();
                        cnt++;
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), k + cnt, Toast.LENGTH_LONG).show();
                }
                cnt++;
            }
        });
    }
}