package com.example.PerfectTen.Screens;

import static com.example.PerfectTen.net_utils.Const.CHILDREN_KEY;
import static com.example.PerfectTen.net_utils.Const.CREATE_COMMENT_URL_1;
import static com.example.PerfectTen.net_utils.Const.CREATE_COMMENT_URL_2;
import static com.example.PerfectTen.net_utils.Const.ID_KEY;
import static com.example.PerfectTen.net_utils.Const.MESSAGE_KEY;
import static com.example.PerfectTen.net_utils.Const.RESULT_TAG;
import static com.example.PerfectTen.net_utils.Const.TITLE_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.PerfectTen.Adapters.CommentAdapter;
import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;
import com.example.PerfectTen.net_utils.Const;
import com.example.PerfectTen.net_utils.PerfectTenRequester;
import com.example.PerfectTen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * The code for the pop-up PostView.
 *
 * @author Jae Swanepoel
 */
public class PostView extends AppCompatActivity {

    //TextViews for the post
    TextView titleView;
    TextView bodyView;

    JSONArray commentsArr;

    RecyclerView commentsRecycler;
    RecyclerView.LayoutManager mLayoutManager;
    CommentAdapter mAdapter;

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
        submit.setOnClickListener(view -> {

            submitComment(commentText.getText().toString());
            commentText.setText("");

        });
        Button back = findViewById(R.id.back_omment);
        back.setOnClickListener(view -> finish());

        titleView = findViewById(R.id.post_view_title);
        bodyView = findViewById(R.id.post_view_body);

        commentsRecycler = findViewById(R.id.comments_Recycler);

        getPost();
    }

    /**
     * Given a text post, we need to
     * populate the TextViews with the appropriate data.
     */
    private void setUpTextPost() {

        try {
            titleView.setText(post.get(Const.TITLE_KEY).toString());
            bodyView.setText(post.get(MESSAGE_KEY).toString());
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

                try {
                    commentsArr = (JSONArray) response.get(CHILDREN_KEY);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setUpTextPost();
                populateComments();
               // getComments();
            }

            @Override
            public void onError(VolleyError error) {

                //TODO

            }
        });

        requester.request();
    }

    private void populateComments() {

        mAdapter = new CommentAdapter(this, commentsArr);
        commentsRecycler.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        commentsRecycler.setLayoutManager(mLayoutManager);
    }

    /**
     * Submits a comment using PerfectTenRequester.
     *
     * @param comment The comment content
     */
    private void submitComment(String comment) {

        JSONObject obj = new JSONObject();
        String url = CREATE_COMMENT_URL_1 + AppController.getPostID() + CREATE_COMMENT_URL_2 + AppController.getUsername();

        try {

            obj.put(TITLE_KEY, "");
            obj.put(MESSAGE_KEY, comment);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        PerfectTenRequester requester
                = new PerfectTenRequester(url, obj, new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                //unreachable
            }

            @Override
            public void onSuccess(JSONObject response) {

                Log.d("Result ", "Comment added successfully.");
                getPost();

            }

            @Override
            public void onError(VolleyError error) {
                //TODO
            }
        });

        requester.request();
    }
}