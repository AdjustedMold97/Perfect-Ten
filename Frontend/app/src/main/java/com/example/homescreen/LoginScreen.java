package com.example.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.homescreen.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        /*
         * <url> url of the server
         * <tag_json_obj> the tag for the JSON object
         *
         * - Jae Swanepoel
         */
        final String URL = "http://coms-309-060.cs.iastate.edu:8080/login";
        final String TAG_JSON_OBJ ="Login Information";
        final String SUCCESS_MSG = "success";

        /*
         * when clicked will submit the text entered in the username and password fields
         * - Ethan Still
         */
        Button login = findViewById(R.id.Submit);

        /*
         * Text inputs for username and password.
         * - Jae Swanepoel
         */
        EditText username_input = findViewById(R.id.editTextName);
        EditText password_input = findViewById(R.id.editTextPassword);

        /*
         * This checks the Name and Password fields for a login attempt and sends a request to the server
         * - Ethan Still
         */
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

                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                login_info = new JSONObject(params);

                /*
                 * Creating the Request to add to the RequestQueue
                 * - Jae Swanepoel
                 */
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL, login_info,

                        response -> {
                            //logging response
                            Log.d(TAG_JSON_OBJ, response.toString());

                            try {
                                if (response.get("message") == SUCCESS_MSG)
                                    startActivity(new Intent(view.getContext(), MainActivity.class));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },

                        error -> {
                            VolleyLog.d("Error: "+ error.getMessage());
                            startActivity(new Intent(LoginScreen.this,LoginFail.class));
                        }
                );

                //adding request to RequestQueue
                AppController.getInstance().addToRequestQueue(jsonObjReq);
            }
        });

    }

}