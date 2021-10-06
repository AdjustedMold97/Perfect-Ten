package com.example.homescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.homescreen.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.example.homescreen.net_utils.Const;

public class PostCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);

        /*
         * final constants for use throughout code
         *
         * - Jae Swanepoel
         */

        final String TAG_JSON_OBJ = "Post Data";
        final String SUCCESS_MSG = "success";
        final String TITLE_FIELD_NAME = "title";
        final String MSG_FIELD_NAME = "message";
        final String RESPONSE_TAG = "JSON Response: ";

        /*
         * back button to leave post creation window
         * - Ethan Still
         */
        Button backBtn = findViewById(R.id.post_back_Button);

        backBtn.setOnClickListener(view -> startActivity(new Intent(view.getContext(),MainActivity.class)));

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
        Button submitBtn = findViewById(R.id.post_submit_Button);

        /*
         * <postTextBox> input field for the post's message
         * <postTitleBox> input field for post's title
         *
         * - Jae Swanepoel
         */
        EditText bodyEditText = findViewById(R.id.post_body_EditText);
        EditText titleEditText = findViewById(R.id.post_title_EditText);

        //onClickListener for submit button - Jae Swanepoel
        submitBtn.setOnClickListener(new View.OnClickListener()        {

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

                postText = String.valueOf(bodyEditText.getText());
                postTitle = String.valueOf(titleEditText.getText());

                /*
                 * <params> HashMap for instantiating <post>
                 *
                 * - Jae Swanepoel
                 */
                Map<String, String> params = new HashMap<>();
                params.put(TITLE_FIELD_NAME, postTitle);
                params.put(MSG_FIELD_NAME, postText);

                post = new JSONObject(params);

                /*
                 * response posts to home screen
                 * - Ethan Still
                 */
                //TODO Ethan figure out how to send post to home screen and pop it up in a box

                /*
                 * Creating the Request to add to the RequestQueue - Jae Swanepoel
                 */
                json_obj_req = new JsonObjectRequest(Request.Method.POST, Const.POSTS_URL, post,

                        response -> {

                            Log.d(RESPONSE_TAG, response.toString());

                            /*
                             * TODO: handle responses to posts
                             *
                             * if success: redirect to MainActivity - DONE
                             *      idea: redirect to "Success!" page for a moment, then home page
                             * if failure: failure message popup
                             */

                            try {

                                if (response.get(MSG_FIELD_NAME).equals(SUCCESS_MSG))
                                    startActivity(new Intent(view.getContext(), MainActivity.class));

                                else {
                                    //TODO failed post screen?
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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

                //Adding to RequestQueue - Jae Swanepoel
                AppController.getInstance().addToRequestQueue(json_obj_req, TAG_JSON_OBJ);
            }
//===============================================
        });
    }
}