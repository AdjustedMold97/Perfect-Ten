package com.example.PerfectTen.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.example.PerfectTen.Adapters.FriendsAdapter;
import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;
import com.example.PerfectTen.net_utils.Const;
import com.example.PerfectTen.net_utils.PerfectTenRequester;
import com.example.PerfectTen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

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

    private PerfectTenRequester requester;

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

        //retrieving friends list
        getFriends();

        //Rigging the Universal Buttons
        Button friend = findViewById(R.id.friends_Button_profile);
        friend.setOnClickListener(view -> startActivity(new Intent(view.getContext(), FriendsScreen.class)));

        Button settings = findViewById(R.id.settings_Button_profile);
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(), SettingsScreen.class)));

        Button home = findViewById(R.id.home_Button_profile);
        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(), HomeScreen.class)));

        //Rigging "Add Friend" Button
        Button addFriend = findViewById(R.id.add_friend_screen_Button);
        addFriend.setOnClickListener(view -> startActivity(new Intent(view.getContext(), FindUserScreen.class)));

        Button toHub = findViewById(R.id.toHub);
        toHub.setOnClickListener(view -> startActivity(new Intent(view.getContext(), DMsScreen.class)));
    }

    /**
     * Retrieving the friends list and populating
     * the infinite scroll.
     *
     * @author Jae Swanepoel
     */
    private void getFriends() {
        super.onResume();

        requester = new PerfectTenRequester(Const.FRIEND_LIST_URL_1 + AppController.getUsername() + Const.FRIEND_LIST_URL_2,
                new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONArray response) { populate(response); }

                    @Override
                    public void onSuccess(JSONObject response) {/* unreachable */ }

                    @Override
                    public void onError(VolleyError error) {
                        //TODO
                    }
                });

        requester.request();
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