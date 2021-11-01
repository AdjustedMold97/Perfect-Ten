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

    final static String RESPONSE_TAG = "JSON Response: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        /*
         * Retrieves posts and populates the HomeScreen.
         *
         * - Jae Swanepoel
         */
        getPosts();

//========================================
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

                     Log.d(RESPONSE_TAG, response.toString());

                     /*
                      * RecyclerView that takes a JSONArray of posts from server
                      * Posts are put into ViewHolder objects to be displayed on HomeScreen
                      * - Ethan Still
                      */
                     RecyclerView recycle;
                     recycle = findViewById(R.id.recycle);
                     RecyclerView.LayoutManager mLayoutManager;

                     /*
                      * For now, the only info we need from the post objects
                      * are the title, so here, we iteratively create a JSONArray
                      * of JSONObjects that have only titles.
                      *
                      * - Jae Swanepoel
                      */
                     JSONArray jsonArray = new JSONArray();
                     JSONObject temp;

                     for (int i = 0; i < response.length(); i++) {

                         temp = new JSONObject();

                         try {
                             temp.put("title", response.getJSONObject(i).get("title"));
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }

                         jsonArray.put(temp);
                     }

                     //inserting the new JSONArray into the adapter.
                     MyAdapter mAdapter = new MyAdapter(this, jsonArray);
                     recycle.setAdapter(mAdapter);

                     mLayoutManager = new LinearLayoutManager(this);
                     recycle.setLayoutManager(mLayoutManager);
                 },

                 error ->  VolleyLog.d("Error: " + error.getMessage())
         );

        //adding request to queue - Jae Swanepoel
        AppController.getInstance().addToRequestQueue(json_arr_req);
    }
}