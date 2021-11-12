package com.example.homescreen.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
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
public class AddFriendScreen extends AppCompatActivity {

    Button submit;
    TextView responseView;
    EditText username;

    PerfectTenRequester requester;
    String targetUser;

    private final String SUCCESS_TEXT = "Friend Added! Success!";
    private final String USER_ERROR_TEXT = "User not found.";

    /**
     * Initializes the views in the activity.
     *
     * @param savedInstanceState default argument
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_screen);

        //requester = new PerfectTenRequester();

        responseView = findViewById(R.id.add_friend_response_TextView);

        username = findViewById(R.id.editTextUsername);

        Button back = findViewById(R.id.add_friend_back_Button);
        back.setOnClickListener(view -> startActivity(new Intent(view.getContext(),FriendsScreen.class)));

        submit = findViewById(R.id.add_friend_submit_Button);
        submit.setOnClickListener(view -> {

            targetUser = username.getText().toString();
            onResume();

        });
    }

    /**
     * @author Jae Swanepoel
     */
    public void onResume() {
        super.onResume();

        JSONObject info = new JSONObject();
        try {
            info.put("user", targetUser);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requester = new PerfectTenRequester(Request.Method.POST, Const.ADD_FRIEND_URL_1 + AppController.getUsername() + Const.ADD_FRIEND_URL_2, info,
                new VolleyCallback() {

                    @Override
                    public void onSuccess(JSONArray response) {
                        //unreachable code
                    }

                    @Override
                    public void onSuccess(JSONObject response) {

                        try {
                            //Changing text on addFriendButton upon successful request
                            if (response.get(Const.MESSAGE_FIELD).equals(Const.SUCCESS_MSG)) {

                                responseView.setText(SUCCESS_TEXT);
                                responseView.setTextColor(Color.GREEN);

                            } else {

                                responseView.setTextColor(Color.RED);

                                if (response.get(Const.MESSAGE_FIELD).equals(Const.GENERIC_ERROR))
                                    responseView.setText(Const.GENERIC_ERROR_TEXT);

                                else if (response.get(Const.MESSAGE_FIELD).equals(Const.USER_ERROR))
                                    responseView.setText(USER_ERROR_TEXT);
                            }

                            responseView.setVisibility(View.VISIBLE);

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
}