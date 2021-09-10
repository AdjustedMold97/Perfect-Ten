package com.example.experiment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String k = "cool number is ";
    public int cnt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = (TextView) findViewById(R.id.text);
        cnt1 = 0;


        Button btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(cnt1%2==0){
                    text.setText(k+cnt1);
                    Toast.makeText(getApplicationContext(), k + cnt1, Toast.LENGTH_SHORT).show();
                }
                else if(cnt1>= 10) {
                    while (cnt1 <= 50) {
                        text.setText(k + cnt1);
                        Toast.makeText(getApplicationContext(), k + cnt1, Toast.LENGTH_SHORT).show();
                        cnt1++;
                    }
                }
                else{
                    text.setText(k+cnt1);
                    Toast.makeText(getApplicationContext(), k + cnt1, Toast.LENGTH_LONG).show();
                }
                cnt1++;
            }
        });
    }
}