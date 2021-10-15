package com.example.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.homescreen.app.AppController;
import com.example.homescreen.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /*
         * Button to login screen
         * -Ethan Still
         */
        Button login = findViewById(R.id.Login);
        login.setOnClickListener(view -> startActivity(new Intent((view.getContext()),LoginScreen.class)));

        //==========================================================
        //test

        Button test = findViewById(R.id.button);
        TextView taken = findViewById(R.id.taken);
        test.setOnClickListener(view -> {
            taken.setVisibility(View.VISIBLE);


        });
        //====================================================

        /*
         * final constants for use throughout code
         *
         * - Jae Swanepoel
         */
        final String TAG_JSON_OBJ ="SignUp Information";
        final String SUCCESS_MSG = "success";
        final String MSG_FIELD_NAME = "message";
        final String USER_FIELD_NAME = "username";
        final String PASS_FIELD_NAME = "password";

        EditText name_input = findViewById(R.id.editTextName);
        EditText pass_input = findViewById(R.id.editTextPassword);

        Button submit = findViewById(R.id.Submit);

        submit.setOnClickListener(view -> {
            /*
             * <username> stores the entered username
             * <password> stores the entered password
             * <signup_info> stores the username and password inputs
             *              in a JSONObject
             *
             * - Jae Swanepoel
             */
            String username;
            String password;
            JSONObject signup_info;

            username = String.valueOf(name_input.getText());
            password = String.valueOf(pass_input.getText());

            Map<String, String> params = new HashMap<>();
            params.put(USER_FIELD_NAME, username);
            params.put(PASS_FIELD_NAME, password);

            signup_info = new JSONObject(params);

            JsonObjectRequest json_obj_req = new JsonObjectRequest(Request.Method.POST, Const.SIGN_UP_URL, signup_info,

                    response -> {

                        //log the response
                        Log.d(TAG_JSON_OBJ, response.toString());

                        try {

                            /*
                             * Upon a successful response message, redirect to the login
                             * screen.
                             */
                            if (response.get(MSG_FIELD_NAME).equals(SUCCESS_MSG))
                                startActivity(new Intent(view.getContext(), LoginScreen.class));

                            /*
                             * if a failed response is received
                             * should only ever happen if the username is already taken
                             * -Ethan Still
                             */
                            else {
                                //TODO
//                                TextView taken = findViewById(R.id.taken);
//                                taken.setVisibility(View.VISIBLE);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> startActivity(new Intent(view.getContext(), Error.class)));

            AppController.getInstance().addToRequestQueue(json_obj_req);
        });

    }

}