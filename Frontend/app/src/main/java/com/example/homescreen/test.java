package com.example.homescreen;

import static com.example.homescreen.net_utils.Const.POST_LIST_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.homescreen.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //============================================================================
        RecyclerView recycle;
        recycle = findViewById(R.id.recycle);
        RecyclerView.LayoutManager mLayoutManager;

        //String dogs[] = {"pug","lab","black lab","golden lab","chawawa","flat coat", "bernise moutain dog"};

        //======================================================
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
        //=======================================================


        MyAdapter mAdapter = new MyAdapter(this, jsonArray);
        recycle.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        recycle.setLayoutManager(mLayoutManager);


        //===================================================================
//        final String RESPONSE_TAG = "JSON Response: ";
//
//        TextView post_title_TextView = findViewById(R.id.post_title_TextView);
//        TextView post_body_TextView = findViewById(R.id.post_body_TextView);
//
//        final JSONArray[] posts_arr = new JSONArray[1];
//
//        JsonArrayRequest json_arr_req = new JsonArrayRequest(POST_LIST_URL,
//
//                response -> {
//
//                    Log.d(RESPONSE_TAG, response.toString());
//
//                    JSONObject temp;
//
//                    try {
//
//                        /*
//                         * As of now, when we request a post, we really take in all posts
//                         * ever created. We will change this, but for now we get around this
//                         * by selecting the most recent post.
//                         */
//                        temp = response.getJSONObject(response.length() - 1);
//                        post_body_TextView.setText(temp.get("message").toString());
//                        post_title_TextView.setText(temp.get("title").toString());
//
//                        posts_arr[0] = response;
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                },
//
//                error -> VolleyLog.d("Error: " + error.getMessage())
//        );
//
//        //adding request to queue - Jae Swanepoel
//        AppController.getInstance().addToRequestQueue(json_arr_req);

        //====================================================================================






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
    }
}