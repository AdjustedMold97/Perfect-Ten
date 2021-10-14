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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.homescreen.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import com.example.homescreen.net_utils.Const;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        /*
         * final constants for use throughout code
         *
         * - Jae Swanepoel
         */
        final String TAG_JSON_OBJ ="Login Information";
        final String SUCCESS_MSG = "success";
        final String MSG_FIELD_NAME = "message";
        final String USER_FIELD_NAME = "username";
        final String PASS_FIELD_NAME = "password";

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
        TextView login_fail = findViewById(R.id.login_fail_textView);


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

                /*
                 * Backdoor for testing when server is down.
                 *
                 * -Jae Swanepoel
                 */
                if (username.equals("Chuck") && password.equals("1")) {

                    AppController.setUsername("Chuck");
                    startActivity(new Intent(view.getContext(), HomeScreen.class));

                }

                Map<String, String> params = new HashMap<>();
                params.put(USER_FIELD_NAME, username);
                params.put(PASS_FIELD_NAME, password);

                login_info = new JSONObject(params);

                /*
                 * Creating the Request to add to the RequestQueue
                 * - Jae Swanepoel
                 */
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Const.LOGIN_URL, login_info,

                        //Code for successful responses - Jae Swanepoel
                        response -> {

                            //logging response
                            Log.d(TAG_JSON_OBJ, response.toString());

                            try {
                                if (response.get(MSG_FIELD_NAME).equals(SUCCESS_MSG)) {

                                    AppController.setUsername(username);
                                    startActivity(new Intent(view.getContext(), HomeScreen.class));
                                }

                                else {
                                    /*
                                     * - if a correct object is returned but "success" is fail then a pop up screen, LoginFail.java pops up
                                     * - Ethan Still
                                     */
                                    login_fail.setVisibility(0);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },


                        //Code for errors - Jae Swanepoel
                        error -> {
                            VolleyLog.d("Error: "+ error.getMessage());
                            startActivity(new Intent(LoginScreen.this,Error.class));
                        }
                );

                //adding request to RequestQueue
                AppController.getInstance().addToRequestQueue(jsonObjReq);
            }
        });

    }

}