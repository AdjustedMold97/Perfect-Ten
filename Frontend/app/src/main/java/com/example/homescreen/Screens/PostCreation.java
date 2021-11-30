package com.example.homescreen.Screens;

import static com.example.homescreen.net_utils.Const.MESSAGE_KEY;
import static com.example.homescreen.net_utils.Const.SUCCESS_MSG;
import static com.example.homescreen.net_utils.Const.TITLE_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.homescreen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.example.homescreen.app.AppController;
import com.example.homescreen.net_utils.Const;
import com.example.homescreen.net_utils.FailedPost;
import com.example.homescreen.net_utils.PerfectTenRequester;
import com.example.homescreen.net_utils.VolleyCallback;

/**
 * PostCreation allows a user to create a post object
 * post object is sent to the server
 * server adds the object to an Array of posts
 * updated array is sent back to the client
 * @author Ethan Still
 * @author Jae Swanepoel
 */
public class PostCreation extends AppCompatActivity {

    PerfectTenRequester requester;
    JSONObject post_data;

    /**
     * OnCreate sets up the buttons that change screens
     * Also sets up OnClick(View view) which creates the post object
     * @param savedInstanceState default argument
     * @author Ethan Still
     * @author Jae Swanepoel
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);

        //requester = new PerfectTenRequester();

        /*
         * back button to leave post creation window
         * - Ethan Still
         */
        Button backBtn = findViewById(R.id.post_back_Button);
        backBtn.setOnClickListener(view -> startActivity(new Intent(view.getContext(), HomeScreen.class)));

        /*
         * <postTextBox> input field for the post's message
         * <postTitleBox> input field for post's title
         *
         * - Jae Swanepoel
         */
        EditText bodyEditText = findViewById(R.id.post_body_EditText);
        EditText titleEditText = findViewById(R.id.post_title_EditText);

        /*
         * button to post the textField to home - sends a request to server
         * - Ethan
         */
        Button submitBtn = findViewById(R.id.post_submit_Button);
        submitBtn.setOnClickListener(new View.OnClickListener() {

            /**
             * onClick sends a username and password as a JSONObject to the server
             * Object is added to database
             * Posts on front end are updated with the new data
             * @param view default argument
             * @author Jae Swanepoel
             */
            @Override // server request onClick
            public void onClick(View view) {

                /*
                 * saving the posts text as a JSON Object to send to server
                 * - Ethan Still
                 */

                String postText = String.valueOf(bodyEditText.getText());
                String postTitle = String.valueOf(titleEditText.getText());

                /*
                 * <params> HashMap for instantiating <post>
                 *
                 * - Jae Swanepoel
                 */
                Map<String, String> params = new HashMap<>();
                params.put(TITLE_KEY, postTitle);
                params.put(MESSAGE_KEY, postText);

                post_data = new JSONObject(params);

                submit(view);
            }
        });
    }

    /**
     *
     *
     * @param view necessary for switching screens.
     * @author Jae Swanepoel
     */
    private void submit(View view) {
        super.onResume();

        requester = new PerfectTenRequester(Request.Method.POST, Const.POSTING_URL + AppController.getUsername(), post_data,
                new VolleyCallback() {

                    @Override
                    public void onSuccess(JSONArray response) {/* unreachable */}

                    @Override
                    public void onSuccess(JSONObject response) {

                        try {

                            if (response.get(MESSAGE_KEY).equals(SUCCESS_MSG))
                                startActivity(new Intent(view.getContext(), HomeScreen.class));

                            else
                                startActivity(new Intent(PostCreation.this, FailedPost.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        //TODO
                    }});

        requester.request();
    }
}