package com.example.PerfectTen.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.PerfectTen.Adapters.PostAdapter;
import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;
import com.example.PerfectTen.net_utils.Const;
import com.example.PerfectTen.net_utils.PerfectTenRequester;
import com.example.PerfectTen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * The ProfileView allows the client to view a user's profile.
 * There are three main functions in this class.
 *
 * The first is viewing a user's post history. This is done
 * through an implementation of an infinite scroll.
 *
 * Second, a user can change their friendship status to
 * the user being viewed.
 *
 * Third, a user can change the blocked status of the
 * user being viewed.
 *
 * @author  Jae Swanepoel
 */
public class ProfileView extends AppCompatActivity {

    private final String ADD_FRIEND_TEXT = "Add Friend";
    private final String REMOVE_FRIEND_TEXT = "Remove Friend";
    private final String BLOCK_TEXT = "Block";
    private final String UNBLOCK_TEXT = "Unblock";

    PerfectTenRequester requester;

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
    RecyclerView recycler;

    /**
     *
     * Initializes the views found in the activity,
     * populates the page with the user's post history,
     * and checks the viewed user's friend and blocked status
     * to correctly label the addFriendButton and blockButton.
     *
     * @param savedInstanceState default argument
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        //Rigging Universal Buttons
        Button home = findViewById(R.id.home_Button_profile);
        Button friends = findViewById(R.id.friends_Button_profile);
        Button settings = findViewById(R.id.settings_Button_profile);
        Button dms = findViewById(R.id.dms_Button);

        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(), HomeScreen.class)));
        friends.setOnClickListener(view -> startActivity(new Intent(view.getContext(), FriendsScreen.class)));
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(), SettingsScreen.class)));
        dms.setOnClickListener(view -> startActivity(new Intent(view.getContext(), DMsScreen.class)));

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

        //Initializing RecyclerView
        recycler = findViewById(R.id.PV_Posts_Recycler);

        //populating the post TextViews
        fetchPostData();

        //by default, we assume we are not friends
        //until we can prove it otherwise.
        areFriends = false;
        isBlocked = false;

        checkFriendStatus();
        checkBlockedStatus();
    }

    /**
     * Code for the "Add Friend" Button.
     * Creates a JSON Request and adds it to the RequestQueue.
     * upon a successful Request, the text is changed from
     * "Add Friend" to "Remove Friend".
     */
    private void addFriend() {
        super.onResume();

        //Compiling data for the JSON Request
        Map<String, String> fields = new HashMap<>();
        fields.put("user", AppController.getTargetUser());

        //Instantiating the JSON Object using the complied data
        JSONObject info = new JSONObject(fields);

        requester = new PerfectTenRequester(Request.Method.POST, Const.ADD_FRIEND_URL_1 + AppController.getUsername() + Const.ADD_FRIEND_URL_2, info,
                new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONArray response) {
                        //unreachable
                    }

                    @Override
                    public void onSuccess(JSONObject response) {
                        try {

                            //Changing text on addFriendButton and
                            // settings areFriends to true
                            // upon successful request
                            if (response.get("message").equals(Const.SUCCESS_MSG)) {

                                areFriends = true;
                                addFriendButton.setText(REMOVE_FRIEND_TEXT);
                                
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        //TODO
                    }
                });

        requester.request();
    }

    /**
     * Code for populating the post TextViews.
     * Creates a JSON Request and adds it to the RequestQueue.
     * upon a successful Request, the TextViews are populated.
     */
    private void fetchPostData() {
        super.onResume();

        Context temp = this;

        requester = new PerfectTenRequester(Const.USER_POST_URL_1 + AppController.getTargetUser() + Const.USER_POST_URL_2,
                new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONArray response) {

                        JSONArray postsArr = new JSONArray();

                        try {

                            if(response.length() == 0){

                                TextView noPost = findViewById(R.id.noPosts);
                                noPost.setVisibility(View.VISIBLE);
                            }
                            else{
                            for (int i = 0; i < response.length(); i++) {

                                postsArr.put(response.getJSONObject(response.length() - i - 1));

                            }
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecyclerView.LayoutManager mLayoutManager;

                        PostAdapter postsAdapter = new PostAdapter(temp, postsArr);
                        recycler.setAdapter(postsAdapter);

                        mLayoutManager = new LinearLayoutManager(temp);
                        recycler.setLayoutManager(mLayoutManager);
                    }

                    @Override
                    public void onSuccess(JSONObject response) {
                        //unreachable
                    }

                    @Override
                    public void onError(VolleyError error) {
                        //TODO
                    }
                });

        requester.request();
    }

    /**
     * Code for the "Block" Button.
     * Creates a JSON Request and adds it to the RequestQueue.
     * Upon a successful Request, the Button's text is changed
     * from "Block" to "Unblock".
     */
    private void block() {
        super.onResume();

        //Compiling data for the JSON Request
        Map<String, String> fields = new HashMap<>();
        fields.put("user", AppController.getTargetUser());

        //Instantiating the JSON Object using the complied data
        JSONObject info = new JSONObject(fields);

        requester = new PerfectTenRequester(Request.Method.POST, Const.BLOCK_USER_URL_1 + AppController.getUsername() + Const.BLOCK_USER_URL_2, info,
                new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONArray response) {
                        //unreachable
                    }

                    @Override
                    public void onSuccess(JSONObject response) {
                        try {

                            //Changing text on addFriendButton upon successful request
                            if (response.get(Const.MESSAGE_KEY).equals(Const.SUCCESS_MSG)) {

                                isBlocked = true;
                                blockButton.setText(UNBLOCK_TEXT);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        //TODO
                    }
                });

        requester.request();
    }

    /**
     * Code for unblocking a user.
     * Upon a successful response message,
     * the text on blockButton is changed from
     * "Unblock" to "Block".
     */
    private void unblock() {
        super.onResume();

        //Compiling data for the JSON Request
        Map<String, String> fields = new HashMap<>();
        fields.put("user", AppController.getTargetUser());

        //Instantiating the JSON Object using the complied data
        JSONObject info = new JSONObject(fields);

        requester = new PerfectTenRequester(Request.Method.PUT, Const.UNBLOCK_USER_URL_1 + AppController.getUsername() + Const.UNBLOCK_USER_URL_2, info,
                new VolleyCallback() {

                    @Override
                    public void onSuccess(JSONArray response) {
                        //unreachable
                    }

                    @Override
                    public void onSuccess(JSONObject response) {
                        try {

                            //Changing text on blockButton upon successful request
                            if (response.get(Const.MESSAGE_KEY).equals(Const.SUCCESS_MSG)) {

                                isBlocked = false;
                                blockButton.setText(BLOCK_TEXT);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        //TODO
                    }
                });

        requester.request();
    }

    /**
     * Code for removing someone from your Friends list.
     * Creates a JSON Request and adds it to the Request Queue.
     * Upon a success response, changes the text on addFriendButton
     * from "Remove Friend" to "Add Friend".
     */
    private void removeFriend() {
        super.onResume();

        JSONObject user = new JSONObject();
        try {
            user.put("user", AppController.getTargetUser());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requester = new PerfectTenRequester(Request.Method.PUT, Const.REMOVE_FRIEND_URL_1 + AppController.getUsername() + Const.REMOVE_FRIEND_URL_2, user,
                new VolleyCallback() {
                    @Override

                    public void onSuccess(JSONArray response) {
                        //unreachable code
                    }

                    @Override
                    public void onSuccess(JSONObject response) {

                        try {
                            if (response.get(Const.MESSAGE_KEY).equals(Const.SUCCESS_MSG)) {

                                areFriends = false;
                                addFriendButton.setText(ADD_FRIEND_TEXT);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(VolleyError error) {
                        //TODO
                    }
                });

        requester.request();
    }

    /**
     * Finding out if we are friends with this user
     * (finding out if this user is in our friends list)
     *
     * if yes, then addFriendButton will say "Remove Friend"
     * if no, then addFriendButton will say "Add Friend"
     */
    private void checkFriendStatus() {
        super.onResume();

        requester = new PerfectTenRequester(Const.FRIEND_LIST_URL_1 + AppController.getUsername() + Const.FRIEND_LIST_URL_2,
                new VolleyCallback() {

                    @Override
                    public void onSuccess(JSONArray response) {
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
                                    addFriendButton.setText(REMOVE_FRIEND_TEXT);
                                    break;

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onSuccess(JSONObject response) {
                        //unreachable
                    }

                    @Override
                    public void onError(VolleyError error) {
                        //TODO
                    }
                });

        requester.request();
    }

    /**
     * Checks if the user is blocked.
     * If yes, the text on blockButton will read "Unblock".
     * If no, the text on blockButton will read "Block".
     */
    private void checkBlockedStatus() {
        super.onResume();

        requester = new PerfectTenRequester(Const.BLOCKED_LIST_URL_1 + AppController.getUsername() + Const.BLOCKED_LIST_URL_2,
                new VolleyCallback() {

                    @Override
                    public void onSuccess(JSONArray response) {

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
                                    blockButton.setText(UNBLOCK_TEXT);
                                    break;

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onSuccess(JSONObject response) {
                        //unreachable
                    }

                    @Override
                    public void onError(VolleyError error) {
                        //TODO
                    }
                });

        requester.request();
    }
}