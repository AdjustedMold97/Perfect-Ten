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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
//   final String RESPONSE_TAG = "JSON Response: ";

//         TextView post_title_TextView = findViewById(R.id.post_title_TextView);
//         TextView post_body_TextView = findViewById(R.id.post_body_TextView);

//         JsonArrayRequest json_arr_req = new JsonArrayRequest(POST_LIST_URL,

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


        JSONObject post1 = new JSONObject();
        JSONObject post2 = new JSONObject();
        JSONObject post3 = new JSONObject();
        JSONObject post4 = new JSONObject();
        JSONObject post5 = new JSONObject();
        JSONObject post6 = new JSONObject();



        try {

            post1.put("title", "poooost 1");
            post2.put("title", "postttt 2");
            post3.put("title", "poooost 3");
            post4.put("title", "poooost 4");
            post5.put("title", "poooost 5");
            post6.put("title", "poooost 6");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();

        jsonArray.put(post1);
        jsonArray.put(post2);
        jsonArray.put(post3);
        jsonArray.put(post4);
        jsonArray.put(post5);
        jsonArray.put(post6);

        JSONObject postobj = new JSONObject();
        try {
            postobj.put("posts", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        Button friend = findViewById(R.id.friends_Button);
        friend.setOnClickListener(view -> startActivity(new Intent(view.getContext(),FriendsScreen.class)));

        Button settings = findViewById(R.id.settings_Button);
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(),SettingsScreen.class)));

        Button home = findViewById(R.id.home_Button);
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



}
