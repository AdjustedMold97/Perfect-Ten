
package com.example.PerfectTen.Screens;

import static com.example.PerfectTen.net_utils.Const.USER_PRIVILEGE_NEW_1;
import static com.example.PerfectTen.net_utils.Const.USER_PRIVILEGE_NEW_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;
import com.example.PerfectTen.net_utils.PerfectTenRequester;
import com.example.PerfectTen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PromoteUser extends AppCompatActivity {

    String pvalue = "-1";

    VolleyCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote_user);

        TextView promoteText = findViewById(R.id.promote_text);

        /*
         * Button set up
         * - Ethan Still
         */
        Button back = findViewById(R.id.exit_button);
        back.setOnClickListener(view -> finish());

        Button zero = findViewById(R.id.zeroLevel);
        Button one = findViewById(R.id.oneLevel);
        Button two = findViewById(R.id.twoLevel);

        zero.setOnClickListener(view -> {

            pvalue = "0";
            submit();

        });

        one.setOnClickListener(view -> {

            pvalue = "1";
            submit();

        });

        two.setOnClickListener(view -> {

            pvalue ="2";
            submit();

        });

        /*
          callback method that changes text on screen based on success or failure of the request
          @author Ethan Still
         */
        callback = new VolleyCallback() {

            @Override
            public void onSuccess(JSONArray response) {
                //unreachable
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(JSONObject response) {

                promoteText.setText("Success, User level changed to " + pvalue);
                pvalue = "-1";

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onError(VolleyError error) {

                promoteText.setText("Something went wrong...");

            }
        };
    }

    private void submit() {

        JSONObject promoteObj = new JSONObject();

        try {
            promoteObj.put("pLevel", pvalue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = USER_PRIVILEGE_NEW_1 + AppController.getTargetUser() + USER_PRIVILEGE_NEW_2;

        PerfectTenRequester requester = new PerfectTenRequester(Request.Method.PUT, url, promoteObj, callback);
        requester.request();
    }
}




