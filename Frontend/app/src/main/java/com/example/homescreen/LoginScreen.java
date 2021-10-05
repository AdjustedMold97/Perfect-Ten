package com.example.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homescreen.app.AppController;

import org.json.*;

import java.io.InputStreamReader;

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
        String url = "http://coms-309-060.cs.iastate.edu:8080/username/";
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
            String finalUrl;
            JSONObject login_info;


            @Override
            public void onClick(View view) {

                username = String.valueOf(username_input.getText());
                password = String.valueOf(password_input.getText());

                //creating the final url based on username - Jae Swanepoel
                finalUrl = url + username;

                login_info = new JSONObject();

                /*
                 * adding <username> and <password> into <login_info>
                 * - Jae Swanepoel
                 */
                try {
                    login_info.put("username", username);
                    login_info.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*
                 * Posts a JSON Object <login_info> to the server url
                 * - Jae Swanepoel
                 */
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, finalUrl, login_info, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(tag_json_obj, response.toString());
                        /*
                         * TODO parse response data for password - JAE
                         * authenticate baBy
                         */

                        /*
                         * response "logs a user in if information is correct"
                         * will send user to their home page
                         * might need a user profile
                         * - Ethan Still
                         */

                        System.out.println(response.toString());

                        //if (password == )
                            startActivity(new Intent(view.getContext(),MainActivity.class));

                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error){
                        VolleyLog.d("Error: "+ error.getMessage());
                    }

                });

                //adding request to RequestQueue
                AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
            }
        });

    }

}