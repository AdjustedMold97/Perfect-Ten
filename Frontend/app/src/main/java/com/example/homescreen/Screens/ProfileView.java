package com.example.homescreen.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.homescreen.R;
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

    /*
     * Constants used mostly in JSON Requests.
     *
     * - Jae Swanepoel
     */
    private static final String RESPONSE_TAG = "HTTP Response ";
    private static final String ERROR_RESPONSE_TAG = "Error ";

    /*
     * Maintains whether or not our user and the
     * Target User are friends. This allows us to set
     * the appropriate text to the add/remove button.
     *
     * - Jae Swanepoel
     */
    boolean areFriends;
    boolean isBlocked;

    /*
     * We reference these objects in multiple
     * functions, so we have to declare them here.
     */
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

        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(), HomeScreen.class)));
        friends.setOnClickListener(view -> startActivity(new Intent(view.getContext(), FriendsScreen.class)));
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(), SettingsScreen.class)));

        //Rigging the "Add Friend" Button
        addFriendButton = findViewById(R.id.add_friend_Button);
        addFriendButton.setOnClickListener(view -> {

            //if we are already friends, remove friend.
            if (areFriends)
                removeFriend();

            //if we aren't friends, add the friend.
            else
                addFriend();
        });

        //Rigging the "Block" Button
        blockButton = findViewById(R.id.block_Button);
        blockButton.setOnClickListener(view -> {

            if (isBlocked)
                unblock();

            else
                block();
        });

        //Setting the username TextView
        TextView username = findViewById(R.id.username_TextView_profile);
        username.setText(AppController.getTargetUser());

        //Setting up post TextViews
        //TODO remove this and replace with infinite scroll
        postTitleTextView = findViewById(R.id.title_placeholder);
        postBodyTextView = findViewById(R.id.body_placeholder);

        //populating the post TextViews
        fetchPostData();

        //by default, we assume we are not friends
        //until we can prove it otherwise.
        areFriends = false;
        isBlocked = false;

        checkFriendStatus();
        checkBlockedStatus();
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
            fields.put("user", AppController.getTargetUser());

            //Instantiating the JSON Object using the complied data
            JSONObject info = new JSONObject(fields);

            //Instantiating the JsonObjectRequest
            JsonObjectRequest addFriendRequest = new JsonObjectRequest(Request.Method.POST,
                    Const.ADD_FRIEND_URL_1 + AppController.getUsername() + Const.ADD_FRIEND_URL_2, info,

                    response -> {

                        try {

                            Log.d("Server response ", response.toString());

                            //Changing text on addFriendButton and
                            // settings areFriends to true
                            // upon successful request
                            if (response.get("message").equals(Const.SUCCESS_MSG)) {

                                areFriends = true;
                                addFriendButton.setText("Remove Friend");
                            }

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

        //Instantiating the Request
        JsonArrayRequest userPostRequest = new JsonArrayRequest

                (Const.USER_POST_URL_1 + AppController.getTargetUser() + Const.USER_POST_URL_2,

                response -> {

                    try {

                        //logging the response
                        Log.d("Response ", response.toString());

                        //populating TextViews with post data
                        JSONObject temp = response.getJSONObject(response.length() - 1);

                        postTitleTextView.setText(temp.get("title").toString());
                        postBodyTextView.setText(temp.get("message").toString());

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
        fields.put("user", AppController.getTargetUser());

        //Instantiating the JSON Object using the complied data
        JSONObject info = new JSONObject(fields);

        //Instantiating the JsonObjectRequest
        JsonObjectRequest blockRequest = new JsonObjectRequest(Request.Method.POST,
                Const.BLOCK_USER_URL_1 + AppController.getUsername() + Const.BLOCK_USER_URL_2, info,

                response -> {

                    Log.d("Server Response ", response.toString());

                    try {

                        //Changing text on addFriendButton upon successful request
                        if (response.get("message").equals(Const.SUCCESS_MSG)) {

                            isBlocked = true;
                            blockButton.setText("Unblock");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                error -> VolleyLog.d("Error ", error.getMessage())
        );

        //Adding the Request to the Queue
        AppController.getInstance().addToRequestQueue(blockRequest);
    }

    /*
     * Code for unblocking a user
     *
     * - Jae Swanepoel
     */
    private void unblock() {

        //Compiling data for the JSON Request
        Map<String, String> fields = new HashMap<>();
        fields.put("user", AppController.getTargetUser());

        //Instantiating the JSON Object using the complied data
        JSONObject info = new JSONObject(fields);

        //Instantiating the JsonObjectRequest
        JsonObjectRequest blockRequest = new JsonObjectRequest(Request.Method.PUT,
                Const.UNBLOCK_USER_URL_1 + AppController.getUsername() + Const.UNBLOCK_USER_URL_2, info,

                response -> {

                    Log.d("Server Response ", response.toString());

                    try {

                        //Changing text on blockButton upon successful request
                        if (response.get("message").equals(Const.SUCCESS_MSG)) {

                            isBlocked = false;
                            blockButton.setText("Block");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                error -> VolleyLog.d("Error ", error.getMessage())
        );

        //Adding the Request to the Queue
        AppController.getInstance().addToRequestQueue(blockRequest);

    }

    /*
     * Method for removing someone from your Friends list.
     *
     * - Jae Swanepoel
     */
    private void removeFriend() {

        JSONObject user = new JSONObject();
        try {
            user.put("user", AppController.getTargetUser());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest json_arr_req = new JsonObjectRequest(Request.Method.PUT,
                Const.REMOVE_FRIEND_URL_1 + AppController.getUsername() + Const.REMOVE_FRIEND_URL_2, user,

                response -> {

                    Log.d("Server Response ", response.toString());

                    try {

                        /*
                         * if successful, we
                         *
                         * set areFriends to false
                         * set the Button text to "Add Friend".
                         */
                        if (response.get("message").equals(Const.SUCCESS_MSG)) {

                            areFriends = false;
                            addFriendButton.setText("Add Friend");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                error -> VolleyLog.d("Error ", error.getMessage()));

        AppController.getInstance().addToRequestQueue(json_arr_req);
    }

    /*
     * Finding out if we are friends with this user
     * (finding out if this user is in our friends list)
     *
     * if yes, the Button should say "Remove Friend"
     * if no, then the Button will say "Add Friend"
     *
     * - Jae Swanepoel
     */
    private void checkFriendStatus() {

        JsonArrayRequest json_arr_req = new JsonArrayRequest(
                Const.FRIEND_LIST_URL_1 + AppController.getUsername() + Const.FRIEND_LIST_URL_2,

                response -> {

                    Log.d(RESPONSE_TAG, response.toString());

                    /*
                     * iterating through the friends list
                     * to see if we can find the target user
                     */
                    for (int i = 0; i < response.length(); i++) {

                        try {

                            /*
                             * if we find the target user, we
                             *
                             * set areFriends to true, showing we are friends
                             * change the text on the Button to "Remove Friend"
                             */
                            if (response.get(i).toString().equals(AppController.getTargetUser())) {

                                areFriends = true;
                                addFriendButton.setText("Remove Friend");
                                break;

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                error -> VolleyLog.d(ERROR_RESPONSE_TAG, error.getMessage())
        );

        AppController.getInstance().addToRequestQueue(json_arr_req);
    }

    /*
     * Checks if the user is blocked
     * called during onCreate
     *
     * - Jae Swanepoel
     */
    private void checkBlockedStatus() {

        JsonArrayRequest blocked_list_req = new JsonArrayRequest(
                Const.BLOCKED_LIST_URL_1 + AppController.getUsername() + Const.BLOCKED_LIST_URL_2,

                response -> {

                    Log.d(RESPONSE_TAG, response.toString());

                    /*
                     * iterating through the blocked list
                     * to see if we can find the target user
                     */
                    for (int i = 0; i < response.length(); i++) {

                        try {

                            /*
                             * if we find the target user, we
                             *
                             * set isBlocked to true, showing the user is blocked
                             * change the text on the Button to "Unblock"
                             */
                            if (response.get(i).toString().equals(AppController.getTargetUser())) {

                                isBlocked = true;
                                blockButton.setText("Unblock");
                                break;

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                error -> VolleyLog.d(ERROR_RESPONSE_TAG, error.getMessage())
        );

        AppController.getInstance().addToRequestQueue(blocked_list_req);
    }
}