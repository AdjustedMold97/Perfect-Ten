package com.example.PerfectTen.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.PerfectTen.Adapters.PostAdapter;
import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;
import com.example.PerfectTen.net_utils.Const;
import com.example.PerfectTen.net_utils.PerfectTenRequester;
import com.example.PerfectTen.net_utils.VolleyCallback;

/**
 * The main activity in Perfect Ten.
 *
 * A user is directed here immediately after logging in.
 *
 * The friend Button redirects the user to the Friends Screen
 * The settings Button redirects the user to the Settings Screen
 * The home Button refreshes the Home Page
 *
 * The toLogin Button redirects the user to the Login Screen
 * and sets the global username variable to null.
 *
 * The post_button Button redirects the user to
 * the Post Creation Activity.
 *
 * In the center is an infinite scroll populated with
 * a feed of all of the Perfect Ten posts, sorted
 * from newest to oldest.
 */
public class HomeScreen extends AppCompatActivity {

    PerfectTenRequester requester;
    String[] blockedUsers;
    JSONArray responseArr;
    int privLevel = 0;

    /**
     * Calls onResume() method onCreate to populate the homeScreen view with postObjects
     * Buttons to loginScreen and postCreation are also set up here
     * @param savedInstanceState
     * -Jae Swanepoel
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Button admin = findViewById(R.id.admin_Button);
        admin.setOnClickListener(view -> startActivity(new Intent(this, AdminScreen.class)));




        //=======================================================================================================
        //TODO ethan- interface these somehow to make more efficient
        //TODO use <include or <merge to get interface into homeScreen
        //TODO interface a back button Ethan

        /*
         * these three buttons make up the bottom tab buttons they go on each screen
         * they include Home, Settings, and Friends and reroute the user to that page form anywhere in the app
         * - Ethan Still
         */
        Button friend = findViewById(R.id.friends_Button_home);
        friend.setOnClickListener(view -> startActivity(new Intent(view.getContext(), FriendsScreen.class)));

        Button settings = findViewById(R.id.settings_Button_home);
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(), SettingsScreen.class)));

        Button home = findViewById(R.id.home_Button_home);
        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(), HomeScreen.class)));
        //======================================================================================================

        /*
         * signs the user out, back to the login screen
         * - Ethan Still
         */
        Button toLogin = findViewById(R.id.sign_out_Button);

        toLogin.setOnClickListener(view -> {
            AppController.setUsername(null);
            startActivity(new Intent(view.getContext(), LoginScreen.class));
        });

        /*
         * - button sends to post creation screen onClick
         * - Ethan Still
         */
        Button post_button = findViewById(R.id.post_create_Button);
        post_button.setOnClickListener(view -> startActivity(new Intent(view.getContext(), PostCreation.class)));

        //getting posts
        getPosts();
        getAdminStatus(); //TODO

        if(AppController.getPrivLevel()>0) {
            admin.setVisibility(View.VISIBLE);

        }
    }

    //TODO
    private void getAdminStatus() {

        requester = new PerfectTenRequester
                (Const.USER_PRIVILEGE_LEVEL_1 + AppController.getUsername() + Const.USER_PRIVILEGE_LEVEL_2, null, new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray response) {/* unreachable */}

            @Override
            public void onSuccess(JSONObject response) {

                TextView test = findViewById(R.id.testview);

                try {
                    //test.setText(response.get("pvalue").toString());
                    AppController.setPrivLevel(response.getInt("pvalue"));


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(VolleyError error) {/* TODO */}
        });

        requester.request();
    }

    /**
     * Uses the local JSONArray populated with posts,
     * creates a new JSONArray with formatted posts,
     * constructs the RecyclerView to set up infinite scroll.
     *
     * @author Jae Swanepoel
     */
    private void populate() {

        /*
         * Taking the original posts array and creating
         * a new array in reverse order. This is done iteratively.
         * This is necessary for posts to arrive from newest (top) to oldest (bottom).
         *
         * jsonArray - the array used in the RecyclerView
         * temp      - JSONObject used in for loop operations
         *
         * - Jae Swanepoel
         */
        JSONArray jsonArray = new JSONArray();
        JSONObject temp;

        reverseLoop:
        for (int i = 0; i < responseArr.length(); i++) {

            temp = new JSONObject();

            try {

                /*
                 * If a user's post is blocked,
                 * we'll take that post out of
                 * the displayed posts.
                 *
                 * - Jae Swanepoel
                 */
                for (String blockedUser : blockedUsers) {

                    if (blockedUser.equals(temp.get(Const.POST_USER_KEY)))
                        continue reverseLoop;
                }


                //getting the title from the JSONObject
                temp.put(Const.TITLE_KEY,
                        responseArr.getJSONObject(responseArr.length() - i - 1).get(Const.TITLE_KEY).toString());

                //getting the message from the JSONObject
                temp.put(Const.MESSAGE_KEY,
                        responseArr.getJSONObject(responseArr.length() - i - 1).get(Const.MESSAGE_KEY).toString());

                temp.put(Const.ID_KEY,
                        responseArr.getJSONObject(responseArr.length() - i - 1).get(Const.ID_KEY).toString());

                temp.put(Const.POST_USER_KEY,
                        responseArr.getJSONObject(responseArr.length() - i - 1).get(Const.POST_USER_KEY).toString());

                temp.put(Const.TIME_KEY,
                        responseArr.getJSONObject(responseArr.length() - i - 1).get(Const.TIME_KEY).toString());

            } catch (JSONException e) { e.printStackTrace(); }

            //inserting the formatted JSONObject into the array
            jsonArray.put(temp);
        }

        /*
         * RecyclerView that takes a JSONArray of posts from server
         * Posts are put into ViewHolder objects to be displayed on HomeScreen
         * - Ethan Still
         */
        RecyclerView recycle;
        recycle = findViewById(R.id.recycle);
        RecyclerView.LayoutManager mLayoutManager;

        /*
         * MyAdapter is an adapter class that manages the information from jsonArray
         * RecyclerView recycle is set to the adapter
         * request is then added to the queue
         * - Ethan Still
         */

        //inserting the new JSONArray into the adapter.
        PostAdapter mAdapter = new PostAdapter(this, jsonArray);
        recycle.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        recycle.setLayoutManager(mLayoutManager);
    }

    /**
     * Retrieves the list of all posts from
     * the Perfect Ten server and calls
     * the populate method.
     *
     * @author Jae Swanepoel
     */
    private void getPosts() {
        super.onResume();

        requester = new PerfectTenRequester(Const.POST_LIST_URL, new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray response) {

                responseArr = response;
                getBlockedUsers();

            }

            @Override
            public void onSuccess(JSONObject response) {/* unreachable */}

            @Override
            public void onError(VolleyError error) {/* TODO */}
        });

        requester.request();
    }

    /**
     * Retrieves the list of users
     * that have been blocked by the
     * signed-in user.
     *
     * @author Jae Swanepoel
     */
    private void getBlockedUsers() {
        super.onResume();

        requester = new PerfectTenRequester(Const.BLOCKED_LIST_URL_1 + AppController.getUsername() + Const.BLOCKED_LIST_URL_2,
                new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONArray response) {

                        blockedUsers = new String[response.length()];

                        try {
                            for (int i = 0; i < response.length(); i++)
                                blockedUsers[i] = response.get(i).toString();
                        }
                        catch (JSONException e) { e.printStackTrace(); }

                        populate();
                    }

                    @Override
                    public void onSuccess(JSONObject response) { /* do nothing */ }

                    @Override
                    public void onError(VolleyError error) {
                        //TODO
                    }
                });

        requester.request();
    }
}