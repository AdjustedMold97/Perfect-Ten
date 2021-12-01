package com.example.homescreen.Screens;

import static com.example.homescreen.net_utils.Const.CREATE_COMMENT_URL;
import static com.example.homescreen.net_utils.Const.ID_KEY;
import static com.example.homescreen.net_utils.Const.MESSAGE_KEY;
import static com.example.homescreen.net_utils.Const.RESULT_TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.homescreen.Adapters.PostAdapter;
import com.example.homescreen.R;
import com.example.homescreen.app.AppController;
import com.example.homescreen.net_utils.Const;
import com.example.homescreen.net_utils.PerfectTenRequester;
import com.example.homescreen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The code for the pop-up PostView.
 *
 * @author Jae Swanepoel
 */
public class PostView extends AppCompatActivity {

    //TextViews for the post
    TextView titleView;
    TextView bodyView;

    //RecyclerView stuff
    RecyclerView comments;
    RecyclerView.LayoutManager mLayoutManager;
    PostAdapter adapter;

    //JSONObject containing the post
    JSONObject post;

    /**
     * Builds the activity.
     * Determines the height and width to act like a pop up,
     * initializes the views,
     * makes a call to getPosts().
     *
     * @param savedInstanceState default argument
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        //configuring post for popout - Ethan
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width, (int) (height*.6));

        EditText commentText = findViewById(R.id.comment_EditText);

        Button submit = findViewById(R.id.submit_comment_Button);
        submit.setOnClickListener(view -> submitComment(commentText.getText().toString()));

        titleView = findViewById(R.id.post_view_title);
        bodyView = findViewById(R.id.post_view_body);

        comments = findViewById(R.id.comments_Recycler);

        getPost();
    }

    /**
     * Given a text post, we need to
     * populate the TextViews with the appropriate data.
     */
    private void setUpTextPost() {

        try {
            titleView.setText(post.get("title").toString());
            bodyView.setText(post.get("message").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a PerfectTenRequester to perform
     * a request to the server for a post (JSONObject).
     * After receiving it, we make a call to
     * setUpTextPost() and getComments().
     */
    private void getPost() {

        PerfectTenRequester requester = new PerfectTenRequester(Const.GET_POST_URL + AppController.getPostID(), null, new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                //unreachable
            }

            @Override
            public void onSuccess(JSONObject response) {

                Log.d(RESULT_TAG, "Received a post from the server.");

                post = response;

                setUpTextPost();
                getComments();
            }

            @Override
            public void onError(VolleyError error) {

                //TODO

            }
        });

        requester.request();
    }

    /**
     * Submits a comment using PerfectTenRequester.
     *
     * @param comment The comment content
     */
    private void submitComment(String comment) {

        JSONObject obj = new JSONObject();

        try {

            obj.put(ID_KEY, AppController.getPostID());
            obj.put(MESSAGE_KEY, comment);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        PerfectTenRequester requester
                = new PerfectTenRequester(CREATE_COMMENT_URL, obj, new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                //unreachable
            }

            @Override
            public void onSuccess(JSONObject response) {

                Log.d("Result ", "Comment added successfully.");
                getComments();

            }

            @Override
            public void onError(VolleyError error) {
                //TODO
            }
        });

        requester.request();
    }

    /**
     * Receives all comments and populates
     * the RecyclerView.
     */
    private void getComments() {

        //TODO

    }
}