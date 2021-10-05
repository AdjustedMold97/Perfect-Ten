package com.example.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.homescreen.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);

        String url = "http://coms-309-060.cs.iastate.edu:8080/posts";
        String tag_json_obj = "Post Data";

        /*
         * back button to leave post creation window
         * - Ethan Still
         */
        Button back = findViewById(R.id.backButton1);

        back.setOnClickListener(view -> startActivity(new Intent(view.getContext(),MainActivity.class)));

// testing pop up screen error - Ethan Still
// TODO DON"T REMOVE THIS TEST CODE YET JAE!

//        Button test = findViewById(R.id.popUp);
//
//        test.setOnClickListener(new View.OnClickListener(){
//            @Override
//           public void onClick(View view) {
//
//                startActivity(new Intent(PostCreation.this,Error.class));
//           }
//
//        });

        /*
         * button to post the textField to home - sends a request to server
         * - Ethan
         */
        Button postSubmit = findViewById(R.id.postSubmit);

        EditText post_input = findViewById(R.id.postEditText);
        EditText post_title = findViewById(R.id.postTitleText);

        postSubmit.setOnClickListener(new View.OnClickListener()        {

            /*
             * <postText> post text data
             * <postTitle> the title of the post
             * <post> JSON Object storing post data
             * <json_obj_req> The request we will send to the server
             *
             * - Jae Swanepoel
             */

            String postText;
            String postTitle;
            JSONObject post;
            JsonObjectRequest json_obj_req;

//=================================================================

            @Override // server request onClick
            public void onClick(View view) {

                /*
                 * saving the posts text as a JSON Object to send to server
                 * - Ethan Still
                 */

                postText = String.valueOf(post_input.getText());
                postTitle = String.valueOf(post_title.getText());

                Map<String, String> params = new HashMap<>();
                params.put("title", postTitle);
                params.put("message", postText);

                post = new JSONObject(params);

                /*
                 * response posts to home screen
                 * - Ethan Still
                 */
                //TODO Ethan figure out how to send post to home screen and pop it up in a box

                json_obj_req = new JsonObjectRequest(Request.Method.POST, url, post,

                        response -> {

                            Log.d(tag_json_obj, response.toString());
                            startActivity(new Intent(view.getContext(),MainActivity.class));
                        },

                        error -> {

                            VolleyLog.d("Error: " + error.getMessage());

                            /*
                             * pop up screen for errors
                             * <Error.java>
                             * check Error.java for the code to the pop up screen
                             * - Ethan Still
                             */
                            startActivity(new Intent(PostCreation.this,Error.class));
                        });

                //Adding to RequestQueue
                AppController.getInstance().addToRequestQueue(json_obj_req);
            }
//===============================================
        });
    }
}