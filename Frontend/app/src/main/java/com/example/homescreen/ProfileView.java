package com.example.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.homescreen.app.AppController;
import com.example.homescreen.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        /*
         * Rigging bottom buttons.
         *
         * - Jae Swanepoel
         */
        Button home = findViewById(R.id.home_Button_profile);
        Button friends = findViewById(R.id.friends_Button_profile);
        Button settings = findViewById(R.id.settings_Button_profile);

        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(),HomeScreen.class)));
        friends.setOnClickListener(view -> startActivity(new Intent(view.getContext(),FriendsScreen.class)));
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(),SettingsScreen.class)));

        Button addFriend = findViewById(R.id.add_friend_Button);
        addFriend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Map<String, String> fields = new HashMap<>();
                fields.put("user", AppController.getUsername());
                fields.put("target", "TODO"/*TODO*/);

                JSONObject info = new JSONObject(fields);

                JsonObjectRequest json_obj_req = new JsonObjectRequest(Request.Method.GET, /*TODO FIND URL*/ "TODO", info,

                        response -> {

                            try {

                                if (response.get("message").equals(Const.SUCCESS_MSG))
                                    addFriend.setText("Remove Friend");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        },

                        error -> {
                            //TODO
                        });
            }
        });
    }
}