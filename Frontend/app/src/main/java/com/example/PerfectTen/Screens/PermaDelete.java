package com.example.PerfectTen.Screens;

import static com.example.PerfectTen.net_utils.Const.DELETE_USER_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;
import com.example.PerfectTen.net_utils.PerfectTenRequester;
import com.example.PerfectTen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

public class PermaDelete extends AppCompatActivity {

    TextView delText;
    VolleyCallback callback;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perma_delete);

        Button yes = findViewById(R.id.yes);
        Button no = findViewById(R.id.no);

        delText = findViewById(R.id.permaDeleteText);

        /*
         * This is the easy onClick
         */
        no.setOnClickListener(view -> startActivity(new Intent(view.getContext(), ProfileView.class)));

        yes.setOnClickListener(view -> {

            v = view;
            submit();

        });

        callback = new VolleyCallback() {

            @Override
            public void onSuccess(JSONArray response) {
                //unreachable
            }

            @Override
            public void onSuccess(JSONObject response) {

                delText.setText("success user deleted you will be returned to home");
                delText.setVisibility(View.VISIBLE);
                AppController.setTargetUser(null);

                Handler handler = new Handler();
                handler.postDelayed(() -> startActivity(new Intent(v.getContext(), HomeScreen.class)), 2500);
            }

            @Override
            public void onError(VolleyError error) {
                delText.setText("Deletion Failed");
            }
        };
    }
    private void submit(){

        PerfectTenRequester requester;
        JSONObject obj = new JSONObject();
        String delUser = AppController.getTargetUser();
        String url = DELETE_USER_URL + delUser;

        requester = new PerfectTenRequester(Request.Method.DELETE, url, obj, callback);
        requester.request();
    }

}