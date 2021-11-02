package com.example.homescreen;

import static com.example.homescreen.net_utils.Const.POST_LIST_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.homescreen.app.AppController;

public class HomeScreen extends AppCompatActivity {

    final static String RESPONSE_TAG = "JSON Response ";
    final static String TITLE_TAG = "title";
    final static String MESSAGE_TAG = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //Retrieves posts and populates the HomeScreen. - Jae Swanepoel
        getPosts();

//========================================
        //TODO ethan- interface these somehow to make more efficient
        /*
         * these three buttons make up the bottom tab buttons they go on each screen
         * they include Home, Settings, and Friends and reroute the user to that page form anywhere in the app
         * - Ethan Still
         */
        Button friend = findViewById(R.id.friends_Button_home);
        friend.setOnClickListener(view -> startActivity(new Intent(view.getContext(),FriendsScreen.class)));

        Button settings = findViewById(R.id.settings_Button_home);
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(),SettingsScreen.class)));

        Button home = findViewById(R.id.home_Button_home);
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

        /*
         * - button sends to post creation screen onClick
         * - Ethan Still
         */
        Button post_button = findViewById(R.id.post_create_Button);
        post_button.setOnClickListener(view -> startActivity(new Intent(view.getContext(),PostCreation.class)));
    }

    /*
     * Retrieves post data from the server and
     * displays it in an infinite scroll.
     *
     * - Jae Swanepoel
     */
    private void getPosts() {

        System.out.println("Getting posts");

        /*
         * The volley request that will retrieve our data.
         * Requires a URL pulled from the Const class.
         */
        JsonArrayRequest json_arr_req = new JsonArrayRequest(POST_LIST_URL,


                 response -> {

                    //logging JSON response and calling populate()
                    Log.d(RESPONSE_TAG, response.toString());
                    populate(response);

                 },

                 error ->  VolleyLog.d("Error: " + error.getMessage())
         );

        //adding request to queue - Jae Swanepoel
        AppController.getInstance().addToRequestQueue(json_arr_req);
    }

    /*
     * Accepts the JSONArray populated with posts,
     * creates a new JSONArray with formatted posts,
     * constructs the RecyclerView to set up infinite scroll.
     *
     * - Jae Swanepoel
     */
    private void populate(JSONArray arr) {

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

        for (int i = 0; i < arr.length(); i++) {

            temp = new JSONObject();

            try {
                //getting the title from the JSONObject
                temp.put(TITLE_TAG,
                        arr.getJSONObject(arr.length() - i - 1).get(TITLE_TAG).toString());

                //getting the message from the JSONObject
                temp.put(MESSAGE_TAG,
                        arr.getJSONObject(arr.length() - i - 1).get(MESSAGE_TAG).toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

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
        MyAdapter mAdapter = new MyAdapter(this, jsonArray);
        recycle.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        recycle.setLayoutManager(mLayoutManager);
    }
}