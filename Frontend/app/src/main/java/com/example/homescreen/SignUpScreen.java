package com.example.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import org.json.JSONObject;

public class SignUpScreen extends AppCompatActivity {

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

        /*
         * <username> stores the entered username
         * <password> stores the entered password
         * <signUp_info> stores the username and password inputs
         *              in a JSONObject
         *
         * - Jae Swanepoel
         */
        String username;
        String password;
        JSONObject login_info;

    }

}