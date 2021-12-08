
package com.example.PerfectTen.Screens;

import static com.example.PerfectTen.net_utils.Const.DELETE_POST_URL;
import static com.example.PerfectTen.net_utils.Const.DELETE_USER_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;
import com.example.PerfectTen.net_utils.Const;
import com.example.PerfectTen.net_utils.LoginFail;
import com.example.PerfectTen.net_utils.PerfectTenRequester;
import com.example.PerfectTen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PromoteUser extends AppCompatActivity {

    boolean zeroFlag;
    boolean oneFlag;
    boolean twoFlag;
    String pvalue = "-1";

    VolleyCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote_user);


        zeroFlag = false;
        oneFlag = false;
        twoFlag = false;


        TextView promoteText = findViewById(R.id.promote_text);

        /*
         * Button set up
         * - Ethan Still
         */
        Button back = findViewById(R.id.exit_button);
        back.setOnClickListener(view -> {
            finish();

        });

        Button zero = findViewById(R.id.zeroLevel);
        Button one = findViewById(R.id.oneLevel);
        Button two = findViewById(R.id.twoLevel);

        zero.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                zeroFlag = true;

                pvalue = "0";
                Map<String, String> params = new HashMap<>();
                params.put(Const.PLEVEL_KEY, pvalue);

                JSONObject requestObj = new JSONObject(params);

                submit(requestObj);
                reset();

            }
        });

        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                oneFlag = true;
                pvalue = "1";
                Map<String, String> params = new HashMap<>();
                params.put(Const.PLEVEL_KEY, pvalue);

                JSONObject requestObj = new JSONObject(params);

                submit(requestObj);
                reset();

            }
        });

        two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                twoFlag = true;
                pvalue ="2";
                Map<String, String> params = new HashMap<>();
                params.put(Const.PLEVEL_KEY, pvalue);

                JSONObject requestObj = new JSONObject(params);

                submit(requestObj);
                reset();

            }
        });


        /**
         * callback method that changes text on screen based on success or failure of the request
         * @ author Ethan Still
         */
        callback = new VolleyCallback() {

            @Override
            public void onSuccess(JSONArray response) {
                //unreachable
            }

            @Override
            public void onSuccess(JSONObject response) {

                promoteText.setText("Success, User level changed to " + pvalue);


            }

            @Override
            public void onError(VolleyError error) {

                promoteText.setText("Something went wrong...");


            }
        };


    }


    private void submit(JSONObject promoteObj) {

        PerfectTenRequester requester;
        String url = "";
        String username = AppController.getTargetUser();

        url = Const.USER_PRIVILEGE_1 + username + Const.USER_PRIVILEGE_2;


        requester = new PerfectTenRequester(url, promoteObj, callback);
        requester.request();


    }

    private void reset(){
        pvalue = "-1";
        zeroFlag = false;
        oneFlag = false;
        twoFlag = false;

    }
}




