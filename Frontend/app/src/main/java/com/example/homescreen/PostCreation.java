package com.example.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.homescreen.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

public class PostCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);

        String url = "coms-309-060.cs.iastate.edu";
        String tag_json_obj = "Post Data";

        /*
         * back button to leave post creation window
         * - Ethan Still
         */
        Button back = findViewById(R.id.backButton1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(view.getContext(),MainActivity.class));

            }
        });

        /*
         * button to post the textField to home - sends a request to server
         * - Ethan
         */
        Button postSubmit = findViewById(R.id.postSubmit);

        EditText post_input = findViewById(R.id.postEditText);

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

            @Override // server request onClick
            public void onClick(View view) {

                /*
                 * saving the posts text as a JSON Object to send to server
                 * - Ethan Still
                 */

                postText = String.valueOf(post_input.getText());
                postTitle = "filler for now"; //TODO
                post = new JSONObject();

                try {
                    post.put("title", postTitle);
                    post.put("message", postText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*
                 * response posts to home screen
                 * - Ethan Still
                 */
                //TODO Ethan figure out how to send post to home screen and pop it up in a box

                json_obj_req = new JsonObjectRequest(Request.Method.POST, url, post,

                        new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(tag_json_obj, response.toString());
                    }


                }, new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Error: " + error.getMessage());
                    }

                });

                //Adding to RequestQueue
                AppController.getInstance().addToRequestQueue(json_obj_req);

            }

        });

    }


}