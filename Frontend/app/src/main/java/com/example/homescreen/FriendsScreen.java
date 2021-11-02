package com.example.homescreen;

import static com.example.homescreen.net_utils.Const.POST_LIST_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.homescreen.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendsScreen extends AppCompatActivity {


    final static String TITLE_TAG = "title";
    final static String MESSAGE_TAG = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_screen);

        //Rigging the Universal Buttons
        Button friend = findViewById(R.id.friends_Button_home);
        friend.setOnClickListener(view -> startActivity(new Intent(view.getContext(),FriendsScreen.class)));

        Button settings = findViewById(R.id.settings_Button_home);
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(),SettingsScreen.class)));

        Button home = findViewById(R.id.home_Button_home);
        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(), HomeScreen.class)));
//==============================



        /*
         * For now, clicking on the ImageButton will
         * set the target user to "Bob" and direct you to
         * the profile page.
         *
         * - Jae Swanepoel
         */
        ImageButton bob = findViewById(R.id.Bob_profile_Button);
        bob.setOnClickListener(view -> {
            AppController.setTargetUser("Bob");
            startActivity(new Intent(view.getContext(), ProfileView.class));
        });

    }


    /*
     * Retrieves post data from the server and
     * displays it in an infinite scroll.
     *
     * - Jae Swanepoel
     */
    //TODO need url and format of json request for users data
//    private void getPosts() {
//
//        System.out.println("Getting friend users");
//
//        /*
//         * The volley request that will retrieve our data.
//         * Requires a URL pulled from the Const class.
//         */
//
//        JsonArrayRequest json_arr_req = new JsonArrayRequest(POST_LIST_URL,
//
//
//                response -> {
//
//                    //logging JSON response and calling populate()
//                    Log.d(RESPONSE_TAG, response.toString());
//                    populate(response);
//
//                },
//
//                error ->  VolleyLog.d("Error: " + error.getMessage())
//        );
//
//        //adding request to queue - Jae Swanepoel
//        AppController.getInstance().addToRequestQueue(json_arr_req);
//    }


    //TODO
    /*
     * Accepts the JSONArray populated with posts,
     * creates a new JSONArray with formatted posts,
     * constructs the RecyclerView to set up infinite scroll.
     *
     * - Jae Swanepoel
     */
//    private void populate(JSONArray arr) {
//
//        /*
//         * Setting a jsonArray equal to the JSONArray response from the server
//         * MyAdapter is an adapter class that manages the information from jsonArray
//         * RecyclerView recycle is set to the adapter
//         * request is then added to the queue
//         * - Ethan Still
//         */
//
//        JSONArray jsonArray = new JSONArray();
//        JSONObject temp;
//
//        for (int i = 0; i < arr.length(); i++) {
//
//            temp = new JSONObject();
//
//            try {
//                //basically getting the title from the JSON Object
//                temp.put(TITLE_TAG,
//                        arr.getJSONObject(arr.length() - i - 1).get(TITLE_TAG).toString());
//
//                temp.put(MESSAGE_TAG,
//                        arr.getJSONObject(arr.length() - i - 1).get(MESSAGE_TAG).toString());
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            jsonArray.put(temp);
//        }
//
//        /*
//         * RecyclerView that takes a JSONArray of users from server
//         * Users are put into ViewHolder objects to be displayed on HomeScreen
//         * - Ethan Still
//         */
//        RecyclerView recycle;
//        recycle = findViewById(R.id.friendsRecycle);
//        RecyclerView.LayoutManager mLayoutManager;
//
//        //inserting the new JSONArray into the adapter.
//        FriendsAdapter mAdapter = new FriendsAdapter(this, jsonArray);
//        recycle.setAdapter(mAdapter);
//
//        mLayoutManager = new LinearLayoutManager(this);
//        recycle.setLayoutManager(mLayoutManager);
//    }
}
