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
import com.example.homescreen.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.example.homescreen.net_utils.Const;
import com.example.homescreen.net_utils.FailedPost;
import com.example.homescreen.net_utils.LoginFail;
import com.example.homescreen.net_utils.PerfectTenRequester;
import com.example.homescreen.net_utils.VolleyCallback;

/**
 * Screen showed on start up of the app when logged out
 * Allows users to login to their profile with username and password
 * @author Ethan Still
 */
public class LoginScreen extends AppCompatActivity {

    //necessary to declare these here for scope
    PerfectTenRequester requester;
    TextView login_fail;

    /**
     * Sets up the buttons for loginScreen
     * @param savedInstanceState default argument
     * @author Ethan Still
     * @author Jae Swanepoel
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        /*
         * Button to sign up screen
         * -Ethan Still
         */
        Button signUp = findViewById(R.id.SignUp);
        signUp.setOnClickListener(view -> startActivity(new Intent((view.getContext()), SignUp.class)));

        /*
         * Text inputs for username and password.
         * - Jae Swanepoel
         */
        EditText username_input = findViewById(R.id.editTextName);
        EditText password_input = findViewById(R.id.editTextPassword);
        login_fail = findViewById(R.id.login_fail_textView);


        /*
         * This checks the Name and Password fields for a login attempt and sends a request to the server
         * - Ethan Still
         */
        Button login = findViewById(R.id.Submit);
        login.setOnClickListener(new View.OnClickListener() {

            /*
             * <username> stores the entered username
             * <password> stores the entered password
             * <login_info> stores the username and password inputs
             *              in a JSONObject
             *
             * - Jae Swanepoel
             */
            String username;
            String password;
            JSONObject login_info;


            /**
             * onClick sends a request to the server with a username and password
             * The server checks whether a user exists with the given username and password
             * Server then sends back a response based on if the user exists
             * Request is added to queue
             * @param view default argument
             * @author Jae Swanepoel
             */
            @Override
            public void onClick(View view) {

                /*
                 * Assigning username and password values
                 * from user input
                 *
                 * - Jae Swanepoel
                 */
                username = String.valueOf(username_input.getText());
                password = String.valueOf(password_input.getText());

                Map<String, String> params = new HashMap<>();
                params.put(Const.USERNAME_KEY, username);
                params.put(Const.PASSWORD_KEY, password);

                login_info = new JSONObject(params);

                login(login_info, view);
            }
        });
    }

    /**
     * Calls login() from the requester.
     *
     * @param login_info The object being passed for the request.
     * @param view It's necessary to pass this in order to change screens.
     * @author Jae Swanepoel
     */
    private void login(JSONObject login_info, View view) {
        super.onResume();

        requester = new PerfectTenRequester(Const.LOGIN_URL, login_info, new VolleyCallback() {

            @Override
            public void onSuccess(JSONArray response) {/* unreachable */}

            @Override
            public void onSuccess(JSONObject response) {

                try {

                    if (response.get(Const.MESSAGE_KEY).equals(Const.SUCCESS_MSG)) {

                        AppController.setUsername(login_info.get(Const.USERNAME_KEY).toString());
                        startActivity(new Intent(view.getContext(), HomeScreen.class));

                    }

                    else {

                        /*
                         * - if a correct object is returned but "success" is fail then a pop up screen, LoginFail.java pops up
                         * - Ethan Still
                         */
                        login_fail.setVisibility(View.VISIBLE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onError(VolleyError error) {
                startActivity(new Intent(view.getContext(), LoginFail.class));
            }
        });

        requester.request();
    }
}