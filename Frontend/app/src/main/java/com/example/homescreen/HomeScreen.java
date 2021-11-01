package com.example.homescreen;

import static com.example.homescreen.net_utils.Const.POST_LIST_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    final static String RESPONSE_TAG = "JSON Response: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //          LEGACY CODE
//         TextView post_title_TextView = findViewById(R.id.post_title_TextView);
//         TextView post_body_TextView = findViewById(R.id.post_body_TextView);


//        /*
//         * The volley request that will retrieve our data.
//         * Requires a URL pulled from the Const class.
//         *
//         * - Jae Swanepoel
//         */
//        JsonArrayRequest json_arr_req = new JsonArrayRequest(POST_LIST_URL,


//                 response -> {

//                     Log.d(RESPONSE_TAG, response.toString());

//                     JSONObject temp;

//                     try {

//                         temp = response.getJSONObject(response.length() - 1);
//                         post_body_TextView.setText(temp.get("message").toString());
//                         post_title_TextView.setText(temp.get("title").toString());

//                     } catch (JSONException e) {
//                         e.printStackTrace();
//                     }
//                 },

//                 error -> VolleyLog.d("Error: " + error.getMessage())
//         );

//         //adding request to queue - Jae Swanepoel
//         AppController.getInstance().addToRequestQueue(json_arr_req);

        //==== use above version for homescreen

        /*
         * RecyclerView that takes a JSONArray of posts from server
         * Posts are put into ViewHolder objects to be displayed on HomeScreen
         * - Ethan Still
         * //TODO put call to server in place of jsonArray below
         */
        RecyclerView recycle;
        recycle = findViewById(R.id.recycle);
        RecyclerView.LayoutManager mLayoutManager;

        /*
        JSONObject post1 = new JSONObject();
        JSONObject post2 = new JSONObject();
        JSONObject post3 = new JSONObject();
        JSONObject post4 = new JSONObject();
        JSONObject post5 = new JSONObject();
        JSONObject post6 = new JSONObject();
         */

        /*
         * getting the array of posts and
         * initializing the target array
         *
         * - Jae Swanepoel
         */
        JSONArray postsArray = getPosts();
        JSONArray jsonArray = new JSONArray();

        /*
         * populating the target array using
         * data from the posts array
         *
         * - Jae Swanepoel
         */
        try {

            /*
            post1.put("title", "poooost 1");
            post2.put("title", "postttt 2");
            post3.put("title", "poooost 3");
            post4.put("title", "poooost 4");
            post5.put("title", "poooost 5");
            post6.put("title", "poooost 6");
            */

            JSONObject temp;

            /*
             * creating a new JSONObject to comply with
             * the standards of the adapter. inserting that
             * object into the target array. iterating through all
             * objects in the posts array.
             *
             * - Jae Swanepoel
             */
            for (int i = 0; i < postsArray.length(); i++) {

                temp = new JSONObject();
                temp.put("title", postsArray.getJSONObject(i).get("title").toString());

                jsonArray.put(temp);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*
        jsonArray.put(post1);
        jsonArray.put(post2);
        jsonArray.put(post3);
        jsonArray.put(post4);
        jsonArray.put(post5);
        jsonArray.put(post6);
        */

        //what is this used for? - Jae
        JSONObject postobj = new JSONObject();
        try {
            postobj.put("posts", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //inserting the target array into the adapter.
        MyAdapter mAdapter = new MyAdapter(this, jsonArray);
        recycle.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        recycle.setLayoutManager(mLayoutManager);



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
     * Returns an array containing (currently) all of the posts
     * on the server as JSON Objects.
     *
     * - Jae Swanepoel
     */
    private JSONArray getPosts() {

        /*
         * The JSONArray we will eventually return
         * not sure why we have to do this, but it seems
         * to be the only way to pass the value outside of
         * the method body for the JsonArrayRequest constructor.
         */
        final JSONArray[] temp = new JSONArray[1];

        /*
         * The volley request that will retrieve our data.
         * Requires a URL pulled from the Const class.
         */
        JsonArrayRequest json_arr_req = new JsonArrayRequest(POST_LIST_URL,


                 response -> {

                     Log.d(RESPONSE_TAG, response.toString());

                     temp[0] = response;
                 },

                 error -> VolleyLog.d("Error: " + error.getMessage())
         );

        //adding request to queue - Jae Swanepoel
        AppController.getInstance().addToRequestQueue(json_arr_req);

        return temp[0];
    }
}