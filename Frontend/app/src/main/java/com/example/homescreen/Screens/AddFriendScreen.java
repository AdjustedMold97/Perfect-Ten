package com.example.homescreen.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.homescreen.R;
import com.example.homescreen.app.AppController;
import com.example.homescreen.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

public class AddFriendScreen extends AppCompatActivity {

    Button submit;
    TextView responseView;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_screen);

        submit = findViewById(R.id.add_friend_submit_Button);
        submit.setOnClickListener(view -> addFriend());

        responseView = findViewById(R.id.add_friend_response_TextView);

        username = findViewById(R.id.editTextUsername);
    }

    /*
     * Code for the "Add Friend" Button.
     * Creates a JSON Request and adds it to the RequestQueue.
     * upon a successful Request, the text is changed from
     * "Add Friend" to "Remove Friend".
     */
    private void addFriend() {

        //Instantiating the JSONObject and populating it
        JSONObject info = new JSONObject();
        try {
            info.put("user", username.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Instantiating the JsonObjectRequest
        JsonObjectRequest addFriendRequest = new JsonObjectRequest(Request.Method.POST,
                Const.ADD_FRIEND_URL_1 + AppController.getUsername() + Const.ADD_FRIEND_URL_2, info,

                response -> {

                    try {

                        Log.d("Server response ", response.toString());

                        //Changing text on addFriendButton upon successful request
                        if (response.get("message").equals(Const.SUCCESS_MSG)) {

                            responseView.setText("Friend Added! Success!");
                            responseView.setTextColor(Color.GREEN);
                        }

                        else {

                            responseView.setTextColor(Color.RED);

                            if (response.get("message").equals(Const.GENERIC_ERROR))
                                responseView.setText("Something went wrong...");

                            else if (response.get("message").equals(Const.USER_ERROR))
                                responseView.setText("User not found.");
                        }

                        responseView.setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                error -> VolleyLog.d("Error ", error.getMessage())
        );

        //Adding the Request to the Queue
        AppController.getInstance().addToRequestQueue(addFriendRequest);
    }
}