package com.example.homescreen.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.homescreen.R;
import com.example.homescreen.net_utils.Const;
import com.example.homescreen.net_utils.PerfectTenRequester;
import com.example.homescreen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * SignUp allows users to create a new user
 * The user is added to the database of users
 *
 * Submit button sends request to server
 * toLogin button sets activity to homeScreen
 *
 * Errors are handled based on whether a user can be made with the specified inputs
 * Specified inputs include: name, password, and email
 * Errors will be thrown if:
 * -A field is left blank
 * -The username is taken
 * -The password is invalid
 * -The email is invalid
 * @author Jae Swanepoel
 */
public class SignUp extends AppCompatActivity {

    PerfectTenRequester requester;
    TextView errorView;

    /**
     * onCreate sets up the buttons listed above and prepares the editText fields
     * @param savedInstanceState default argument
     * @author Jae Swanepoel
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        requester = new PerfectTenRequester();

        /*
         * Button to login screen
         * -Ethan Still
         */
        Button toLogin = findViewById(R.id.to_login_button);
        toLogin.setOnClickListener(view -> startActivity(new Intent((view.getContext()), LoginScreen.class)));

        EditText name_input = findViewById(R.id.editTextName);
        EditText pass_input = findViewById(R.id.editTextPassword);
        EditText email_input = findViewById(R.id.editTextEmail);

        Button submit = findViewById(R.id.Submit);

        errorView = findViewById(R.id.error_view);
        TextView enter_user = findViewById(R.id.enter_user);
        TextView enter_pass = findViewById(R.id.enter_pass);
        TextView enter_email = findViewById(R.id.enter_email);

        enter_user.setVisibility(View.INVISIBLE);
        enter_pass.setVisibility(View.INVISIBLE);
        enter_email.setVisibility(View.INVISIBLE);

        submit.setOnClickListener(view -> {

            String username = String.valueOf(name_input.getText());
            String password = String.valueOf(pass_input.getText());
            String email = String.valueOf(email_input.getText());

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
            params.put(Const.USERNAME_FIELD, username);
            params.put(Const.PASSWORD_FIELD, password);
            params.put(Const.EMAIL_FIELD, email);

            JSONObject signup_info = new JSONObject(params);

            onResume(signup_info, view);
        });
    }

    /**
     *
     * @param signup_info JSON Object passed to request
     * @param view necessary for switching screens
     * @author Jae Swanepoel
     */
    public void onResume(JSONObject signup_info, View view) {
        super.onResume();

        requester.signUp(signup_info, new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray response) {

            }

            @Override
            public void onSuccess(JSONObject response) {

                try {

                    if (response.get(Const.MESSAGE_FIELD).equals(Const.SUCCESS_MSG))
                        startActivity(new Intent(view.getContext(), LoginScreen.class));

                    else {

                        String error_msg;

                        //TODO standardize errors across classes
                        switch(response.get((Const.MESSAGE_FIELD)).toString()) {

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

            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }
}