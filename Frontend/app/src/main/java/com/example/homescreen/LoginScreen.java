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
        String url = "http://coms-309-060.cs.iastate.edu:8080/login";
        String tag_json_obj ="Login Information";

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

                /*
                 * adding <username> and <password> into <login_info>
                 * - Jae Swanepoel
                 */
                login_info = new JSONObject();

                try {
                    login_info.put("username", username);
                    login_info.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*
                 * Creating the Request to add to the RequestQueue
                 * - Jae Swanepoel
                 */
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, login_info,

                        response -> {
                            //logging response
                            Log.d(tag_json_obj, response.toString());
                        },

                        error -> {
                            VolleyLog.d("Error: "+ error.getMessage());
                            startActivity(new Intent(LoginScreen.this,LoginFail.class));
                        }
                );

                //adding request to RequestQueue
                AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
            }
        });

    }

}