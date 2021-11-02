package com.example.homescreen.Screens;

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
import com.example.homescreen.R;
import com.example.homescreen.Screens.LoginScreen;
import com.example.homescreen.app.AppController;
import com.example.homescreen.net_utils.Const;
import com.example.homescreen.net_utils.Error;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /*
         * Button to login screen
         * -Ethan Still
         */
        Button toLogin = findViewById(R.id.to_login_button);
        toLogin.setOnClickListener(view -> startActivity(new Intent((view.getContext()), LoginScreen.class)));

        /*
         * Still not sure what an AtomicReference is.
         * It seems to make things work, though, so we use them.
         * - Jae Swanepoel
         */
        AtomicReference<TextView> taken = new AtomicReference<>(findViewById(R.id.taken));

        /*
         * constants for use throughout code
         *
         * - Jae Swanepoel
         */
        final String TAG_JSON_OBJ ="Sign-Up Information";
        final String SUCCESS_MSG = "success";
        final String MSG_FIELD_NAME = "message";
        final String USER_FIELD_JSON = "username";
        final String PASS_FIELD_JSON = "password";
        final String EMAIL_FIELD_JSON = "email";

        EditText name_input = findViewById(R.id.editTextName);
        EditText pass_input = findViewById(R.id.editTextPassword);
        EditText email_input = findViewById(R.id.editTextEmail);

        Button submit = findViewById(R.id.Submit);

        TextView errorView = findViewById(R.id.error_view);
        TextView enter_user = findViewById(R.id.enter_user);
        TextView enter_pass = findViewById(R.id.enter_pass);
        TextView enter_email = findViewById(R.id.enter_email);

        enter_user.setVisibility(View.INVISIBLE);
        enter_pass.setVisibility(View.INVISIBLE);
        enter_email.setVisibility(View.INVISIBLE);

        submit.setOnClickListener(view -> {
            /*
             * <username> stores the entered username
             * <password> stores the entered password
             * <email> stores entered email
             * <signup_info> stores the username and password inputs
             *              in a JSONObject
             *
             * - Jae Swanepoel
             */
            String username;
            String password;
            String email;
            JSONObject signup_info;

            username = String.valueOf(name_input.getText());
            password = String.valueOf(pass_input.getText());
            email = String.valueOf(email_input.getText());

            /*
             * if any of the entered fields are empty or null,
             * we won't create a request.
             *
             * - Jae Swanepoel
             */

            boolean flag = false;

            if (username.equals("")) {

                flag = true;
                enter_user.setVisibility(View.VISIBLE);

            }

            else
                enter_user.setVisibility(View.INVISIBLE);

            if (password.equals("")) {

                flag = true;
                enter_pass.setVisibility(View.VISIBLE);

            }

            else
                enter_pass.setVisibility(View.INVISIBLE);

            if (email.equals("")) {

                flag = true;
                enter_email.setVisibility(View.VISIBLE);

            }

            else
                enter_email.setVisibility(View.INVISIBLE);

            if (flag)
                return;

            Map<String, String> params = new HashMap<>();
            params.put(USER_FIELD_JSON, username);
            params.put(PASS_FIELD_JSON, password);
            params.put(EMAIL_FIELD_JSON, email);

            signup_info = new JSONObject(params);

            JsonObjectRequest json_obj_req = new JsonObjectRequest(Request.Method.POST, Const.SIGN_UP_URL, signup_info,

                    response -> {

                        //log the response
                        Log.d(TAG_JSON_OBJ, response.toString());

                        try {

                            /*
                             * Upon a successful response message, redirect to the login
                             * screen and set global username
                             */

                            if (response.get(MSG_FIELD_NAME).equals(SUCCESS_MSG)) {

                                AppController.setUsername(username);
                                startActivity(new Intent(view.getContext(), LoginScreen.class));

                            }

                            else {

                                //hoping this cast will cut it
                                String response_msg = (String) response.get((MSG_FIELD_NAME));
                                String error_msg;

                                //TODO
                                //standardize errors across classes
                                switch(response_msg) {

                                    default:
                                        //generic error
                                        error_msg = "Something went wrong...";
                                        break;

                                    case Const.USER_ERROR:
                                        //username taken
                                        error_msg = "This username is taken. Try another one!";
                                        break;

                                    case Const.EMAIL_ERROR:
                                        //email invalid
                                        error_msg = "This email is invalid.";
                                        break;

                                    case Const.PASSWORD_ERROR:
                                        //password invalid
                                        error_msg = "This password is invalid.";
                                        break;
                                }

                                errorView.setText(error_msg);
                                errorView.setVisibility(View.VISIBLE);
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