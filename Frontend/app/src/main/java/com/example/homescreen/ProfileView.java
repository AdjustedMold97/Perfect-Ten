package com.example.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.homescreen.app.AppController;
import com.example.homescreen.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileView extends AppCompatActivity {

    Button addFriendButton;
    TextView postTitleTextView;
    TextView postBodyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        //Rigging Universal Buttons
        Button home = findViewById(R.id.home_Button_profile);
        Button friends = findViewById(R.id.friends_Button_profile);
        Button settings = findViewById(R.id.settings_Button_profile);

        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(),HomeScreen.class)));
        friends.setOnClickListener(view -> startActivity(new Intent(view.getContext(),FriendsScreen.class)));
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(),SettingsScreen.class)));

        //Rigging the "Add Friend" Button.
        addFriendButton = findViewById(R.id.add_friend_Button);
        addFriendButton.setOnClickListener(view -> addFriend());

        //Setting the username TextView
        TextView username = findViewById(R.id.username_TextView_profile);
        username.setText(AppController.getTargetUser());

        //Setting up post TextViews
        postTitleTextView = findViewById(R.id.post_title_TextView);
        postBodyTextView = findViewById(R.id.post_body_TextView);

        fetchPostData();
    }

    private void addFriend() {

            //Compiling data for the JSON Request
            Map<String, String> fields = new HashMap<>();
            fields.put("user", AppController.getUsername());
            fields.put("target", AppController.getTargetUser());

            //Instantiating the JSON Object using the complied data
            JSONObject info = new JSONObject(fields);

            //Instantiating the JsonObjectRequest
            JsonObjectRequest addFriendRequest = new JsonObjectRequest(Request.Method.GET, /*TODO FIND URL*/ "TODO", info,

                    response -> {

                        try {

                            //Changing text on addFriendButton upon successful request
                            if (response.get("message").equals(Const.SUCCESS_MSG))
                                addFriendButton.setText("Remove Friend");

                            else {
                                //TODO set up failure response
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },

                    error -> {
                        //TODO set up error response
                    });

            //Adding the Request to the Queue
            AppController.getInstance().addToRequestQueue(addFriendRequest);
    }

    private void fetchPostData() {

        //Declaring Strings to store post data
        final String[] postTitle = new String[1];
        final String[] postBody = new String[1];

        //Setting up JSONObject for the Request
        Map<String, String> postRequestInfo = new HashMap<>();
        postRequestInfo.put("username", AppController.getTargetUser());
        JSONObject postRequestObject = new JSONObject(postRequestInfo);

        //Instantiating the Request to get a post from the target user
        JsonObjectRequest userPostRequest = new JsonObjectRequest(Request.Method.GET, "TODO"/*TODO GET URL*/, postRequestObject,

                response -> {

                    try {

                        if (response.get("message").equals(Const.SUCCESS_MSG)){
                            //TODO format fields correctly
                            postTitle[0] = String.valueOf(response.get("title"));
                            postBody[0] = String.valueOf(response.get("body"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                error -> {
                    //TODO set up error handling
                });

        AppController.getInstance().addToRequestQueue(userPostRequest);

        postTitleTextView.setText(postTitle[0]);
        postBodyTextView.setText(postBody[0]);
    }
}