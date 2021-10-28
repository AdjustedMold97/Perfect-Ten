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

/*
 * written by Jae Swanepoel
 */
public class ProfileView extends AppCompatActivity {

    Button addFriendButton;
    Button blockButton;
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

        //Rigging the "Add Friend" Button
        addFriendButton = findViewById(R.id.add_friend_Button);
        addFriendButton.setOnClickListener(view -> addFriend());

        //Rigging the "Block" Button
        blockButton = findViewById(R.id.block_Button);
        blockButton.setOnClickListener(view -> block());

        //Setting the username TextView
        TextView username = findViewById(R.id.username_TextView_profile);
        username.setText(AppController.getTargetUser());

        //Setting up post TextViews
        postTitleTextView = findViewById(R.id.post_title_TextView);
        postBodyTextView = findViewById(R.id.post_body_TextView);

        //populating the post TextViews
        fetchPostData();
    }

    /*
     * Code for the "Add Friend" Button.
     * Creates a JSON Request and adds it to the RequestQueue.
     * upon a successful Request, the text is changed from
     * "Add Friend" to "Remove Friend".
     */
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

    /*
     * Code for populating the post TextViews.
     * Creates a JSON Request and adds it to the RequestQueue.
     * upon a successful Request, the TextViews are populated.
     */
    private void fetchPostData() {

        //Setting up JSONObject for the Request
        Map<String, String> postRequestInfo = new HashMap<>();
        postRequestInfo.put("username", AppController.getTargetUser());
        JSONObject postRequestObject = new JSONObject(postRequestInfo);

        //Instantiating the Request
        JsonObjectRequest userPostRequest = new JsonObjectRequest(Request.Method.GET, "TODO"/*TODO GET URL*/, postRequestObject,

                response -> {

                    try {

                        //upon a successful response, populate the TextViews
                        if (response.get("message").equals(Const.SUCCESS_MSG)){
                            //TODO format fields correctly
                            postTitleTextView.setText(String.valueOf(response.get("title")));
                            postBodyTextView.setText(String.valueOf(response.get("body")));
                        }

                        else {
                            //TODO set up failure response
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                error -> {
                    //TODO set up error handling
                });

        //adding the Request to the Queue
        AppController.getInstance().addToRequestQueue(userPostRequest);
    }

    /*
     * Code for the "Block" Button.
     * Creates a JSON Request and adds it to the RequestQueue.
     * Upon a successful Request, the Button's text is changed
     * from "Block" to "Unblock".
     */
    private void block() {

        //Compiling data for the JSON Request
        Map<String, String> fields = new HashMap<>();
        fields.put("user", AppController.getUsername());
        fields.put("target", AppController.getTargetUser());

        //Instantiating the JSON Object using the complied data
        JSONObject info = new JSONObject(fields);

        //Instantiating the JsonObjectRequest
        JsonObjectRequest blockRequest = new JsonObjectRequest(Request.Method.GET, /*TODO FIND URL*/ "TODO", info,

                response -> {

                    try {

                        //Changing text on addFriendButton upon successful request
                        if (response.get("message").equals(Const.SUCCESS_MSG))
                            blockButton.setText("Unblock");

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
        AppController.getInstance().addToRequestQueue(blockRequest);
    }
}