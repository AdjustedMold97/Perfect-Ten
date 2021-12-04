package com.example.homescreen.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.homescreen.R;
import com.example.homescreen.app.AppController;
import com.example.homescreen.net_utils.Const;
import com.example.homescreen.net_utils.PerfectTenRequester;
import com.example.homescreen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An activity where a user can enter a specific username
 * to add a "target user" to their friends list.
 *
 * Currently, no search function exists, so a username
 * must be known in advance in order to add a user to
 * one's friends list.
 *
 * @author Jae Swanepoel
 */
public class FindUserScreen extends AppCompatActivity {

    Button submit;
    TextView responseView;
    EditText username;

    PerfectTenRequester requester;
    String targetUser;

    private final String USER_ERROR_TEXT = "User not found.";

    /**
     * Initializes the views in the activity.
     *
     * @param savedInstanceState default argument
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user_screen);

        responseView = findViewById(R.id.add_friend_response_TextView);

        username = findViewById(R.id.editTextUsername);

        Button back = findViewById(R.id.add_friend_back_Button);
        back.setOnClickListener(view -> startActivity(new Intent(view.getContext(),FriendsScreen.class)));

        submit = findViewById(R.id.add_friend_submit_Button);
        submit.setOnClickListener(view -> {

            targetUser = username.getText().toString();
            findUser(view);

        });
    }

    /**
     * Called when the user presses the submit button.
     * Retrieves the list of all PerfectTen users from the server.
     * If the entered user exists, we redirect to that user's
     * profile page.
     *
     * @param  view Passed in order to switch screens.
     * @author Jae Swanepoel
     */
    private void findUser(View view) {
        super.onResume();

        requester = new PerfectTenRequester(Const.USER_LIST_URL,
                new VolleyCallback() {

                    @Override
                    public void onSuccess(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                if (response.get(i).equals(targetUser)) {

                                    AppController.setTargetUser(targetUser);
                                    startActivity(new Intent(view.getContext(), ProfileView.class));
                                    return;

                                }

                            }

                            /*
                             * If we make it through the loop without escaping,
                             * the user does not exist.
                             */
                            responseView.setTextColor(Color.RED);
                            responseView.setText(USER_ERROR_TEXT);
                            responseView.setVisibility(View.VISIBLE);
                        }
                        catch (JSONException e) { e.printStackTrace(); }
                    }

                    @Override
                    public void onSuccess(JSONObject response) {/* unreachable */}

                    @Override
                    public void onError(VolleyError error) { /*TODO*/ }
                });

        requester.request();
    }
}