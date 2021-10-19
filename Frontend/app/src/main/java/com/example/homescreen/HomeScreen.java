package com.example.homescreen;

import static com.example.homescreen.net_utils.Const.POST_LIST_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.homescreen.app.AppController;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String RESPONSE_TAG = "JSON Response: ";

        TextView post_title_TextView = findViewById(R.id.post_title_TextView);
        TextView post_body_TextView = findViewById(R.id.post_body_TextView);

        final JSONArray[] posts_arr = new JSONArray[1];
        
        JsonArrayRequest json_arr_req = new JsonArrayRequest(POST_LIST_URL,

                response -> {

                    Log.d(RESPONSE_TAG, response.toString());

                    JSONObject temp;

                    try {

                        /*
                         * As of now, when we request a post, we really take in all posts
                         * ever created. We will change this, but for now we get around this
                         * by selecting the most recent post.
                         */
                        temp = response.getJSONObject(response.length() - 1);
                        post_body_TextView.setText(temp.get("message").toString());
                        post_title_TextView.setText(temp.get("title").toString());

                        posts_arr[0] = response;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                error -> VolleyLog.d("Error: " + error.getMessage())
        );

        //adding request to queue - Jae Swanepoel
        AppController.getInstance().addToRequestQueue(json_arr_req);



//========================================
        /*
         * these three buttons make up the bottom tab buttons they go on each screen
         * they include Home, Settings, and Friends and reroute the user to that page form anywhere in the app
         * - Ethan Still
         */
        Button friend = findViewById(R.id.friends_Button);
        friend.setOnClickListener(view -> startActivity(new Intent(view.getContext(),FriendsScreen.class)));

        Button settings = findViewById(R.id.settings_Button);
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(),SettingsScreen.class)));

        Button home = findViewById(R.id.home_Button);
        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(), HomeScreen.class)));
//===========================================

        /*
         * signs the user out, back to the login screen
         * - Ethan Still
         */
        Button toLogin = findViewById(R.id.sign_out_Button);

        toLogin.setOnClickListener(view -> {

            AppController.setUsername(null);
            startActivity(new Intent(view.getContext(),LoginScreen.class));
        });

        Button post_button = findViewById(R.id.post_create_Button);

        /*
         * - button sends to post creation screen onClick
         * - Ethan Still
         */
        post_button.setOnClickListener(view -> startActivity(new Intent(view.getContext(),PostCreation.class)));
    }
}