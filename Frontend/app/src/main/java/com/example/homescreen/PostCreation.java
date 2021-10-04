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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class PostCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);


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

            String post;
            JSONObject postText;
            @Override // server request onClick
            public void onClick(View view) {

                /*
                 * saving the posts text as a JSON Object to send to server
                 * - Ethan Still
                 */

                post = String.valueOf(post_input.getText());

                postText = new JSONObject();

                try {
                    postText.put("postText", post);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //TODO finish up send request - Ethan and Jae

                /*
                 * response posts to home screen
                 * - Ethan Still
                 */
                //TODO Ethan figure out how to send post to home screen and pop it up in a box

            }
        });

    }


}