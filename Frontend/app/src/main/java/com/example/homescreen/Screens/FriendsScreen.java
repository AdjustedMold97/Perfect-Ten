package com.example.homescreen.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.homescreen.Adapters.FriendsAdapter;
import com.example.homescreen.R;
import com.example.homescreen.app.AppController;
import com.example.homescreen.net_utils.Const;

import org.json.JSONArray;

/**
 * The FriendsScreen allows a user to browse the list of
 * users on their friends list.
 *
 * When a user's profile picture is
 * selected, the client will be redirected to that user's
 * profile page.
 *
 * The addFriend Button redirects the client to the AddFriendScreen activity.
 *
 * @author Jae Swanepoel
 */
public class FriendsScreen extends AppCompatActivity {

    final static String RESPONSE_TAG = "Friends List ";
    final static String ERROR_RESPONSE_TAG = "Error ";

    /**
     * Initializes the views in the activity
     * and calls getFriends() to populate the friends list.
     *
     * @param savedInstanceState default argument
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_screen);

        //populating the friends list
        getFriends();

        //Rigging the Universal Buttons
        Button friend = findViewById(R.id.friends_Button_home);
        friend.setOnClickListener(view -> startActivity(new Intent(view.getContext(), FriendsScreen.class)));

        Button settings = findViewById(R.id.settings_Button_home);
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(), SettingsScreen.class)));

        Button home = findViewById(R.id.home_Button_home);
        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(), HomeScreen.class)));

        //Rigging "Add Friend" Button
        Button addFriend = findViewById(R.id.add_friend_screen_Button);
        addFriend.setOnClickListener(view -> startActivity(new Intent(view.getContext(), AddFriendScreen.class)));
    }
        /**
         * Creates a JSON Request and adds it to the Request Queue.
         * Upon a successful response, a call is made to populate()
         * in order to populate the infinite scroll
         * with the user's friends list.
         *
         * @author Jae Swanepoel
         */
    private void getFriends() {

        JsonArrayRequest json_arr_req = new JsonArrayRequest(
                Const.FRIEND_LIST_URL_1 + AppController.getUsername() + Const.FRIEND_LIST_URL_2,

                response -> {

                    Log.d(RESPONSE_TAG, response.toString());
                    populate(response);

                },

                error -> VolleyLog.d(ERROR_RESPONSE_TAG, error.getMessage())
        );

        AppController.getInstance().addToRequestQueue(json_arr_req);
    }

    /**
     * recycle is assigned an Adapter mAdapter that is of type FriendsAdapter
     * the adapter has three types of data tracked
     * Two textViews friendName and friendDesc
     * One ImageButton friendImg
     * The adapter uses friend_object.xml
     *
     * @author Ethan Still
     */
    private void populate(JSONArray arr) {

        RecyclerView recycle;
        recycle = findViewById(R.id.friendsRecycle);
        RecyclerView.LayoutManager mLayoutManager;

        //inserting the new JSONArray into the adapter.
        FriendsAdapter mAdapter = new FriendsAdapter(this, arr);
        recycle.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        recycle.setLayoutManager(mLayoutManager);
    }
}
