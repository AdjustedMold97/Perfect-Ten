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
import java.util.Scanner;

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
             * <final
             * <login_info> stores the username and password inputs
             *              in a JSONObject
             * <response_text> the data received in the JSON response
             * <response_reader> Scanner to parse JSON response data
             * <temp> used to assist in method functionality
             * <sub_scanner> used to assist in method functionality
             *
             * - Jae Swanepoel
             */
            String username;
            String password;
            String final_url;
            JSONObject login_info;
            String response_text;
            Scanner response_reader;
            String temp;
            Scanner sub_scanner;


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

                //creating the final url based on username - Jae Swanepoel
                final_url = url + username;

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
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, final_url, login_info,

                    new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        /*
                         * Assigning the JSON data value to the
                         * string we will parse
                         *
                         * - Jae Swanepoel
                         */

                        response_text = response.toString();
                        System.out.println(response_text);
//
//                        //logging response
//                        Log.d(tag_json_obj, response_text);
//
//                        //trimming the brackets off the string
//                        response_text = response_text.substring(1, response_text.length() - 2);
//
//                        //initializing response_reader
//                        //assigning comma as delimiter
//                        response_reader = new Scanner(response_text);
//                        response_reader.useDelimiter(",");
//
//                        /*
//                         * Going through the JSON data until
//                         * we find the password field.
//                         *
//                         * - Jae Swanepoel
//                         */
//                        while (response_reader.hasNext()) {
//
//                            temp = response_reader.next();
//
//                            //identifying the password field
//                            if (temp.substring(1,8) == "password") {
//
//                                sub_scanner = new Scanner(temp.substring(10));
//                                sub_scanner.useDelimiter("\"");
//
//                                //skipping the first substring,
//                                //which should just be " : "
//                                sub_scanner.next();
//
//                                if (sub_scanner.next() == password) {
//
//                                    response_reader.close();
//                                    sub_scanner.close();
//
//                                    startActivity(new Intent(view.getContext(),MainActivity.class));
//                                }
//                            }
//                        }
//
//                        response_reader.close();
//                        /*
//                         * response "logs a user in if information is correct"
//                         * will send user to their home page
//                         * might need a user profile
//                         * - Ethan Still
//                         */
//
//                        System.out.println(response.toString());






                        // onResponse not asking for correct kind of object



                        //"{\"message\":\"success\"}"

//                        if(response == "{\"message\":\"success\"}"){
//
//                            startActivity(new Intent(view.getContext(),MainActivity.class));
//
//                        }





                    }},

                    new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error){
                        VolleyLog.d("Error: "+ error.getMessage());

                        startActivity(new Intent(LoginScreen.this,LoginFail.class));
                    }

                });

                //adding request to RequestQueue
                AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
            }
        });

    }

}