package com.example.PerfectTen.Screens;


import static com.example.PerfectTen.net_utils.Const.DELETE_POST_URL;
import static com.example.PerfectTen.net_utils.Const.DELETE_USER_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.PerfectTen.R;
import com.example.PerfectTen.net_utils.PerfectTenRequester;
import com.example.PerfectTen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdminScreen extends AppCompatActivity {

    Button deleteUser;
    Button deletePost;
    Button deleteComment;

    TextView inputText;
    EditText inputEdit;

    Button submitButton;
    TextView resultText;

    boolean userFlag;

    VolleyCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        Button home = findViewById(R.id.admin_home_Button);
        home.setOnClickListener(view -> startActivity(new Intent(this, HomeScreen.class)));

        deleteUser = findViewById(R.id.delete_user_Button);
        deleteUser.setOnClickListener(view -> deleteUser());

        deletePost = findViewById(R.id.delete_post_Button);
        deletePost.setOnClickListener(view -> deletePost());

        deleteComment = findViewById(R.id.delete_commnet_Button);
        deleteComment.setOnClickListener(view -> deleteComment());

        inputText = findViewById(R.id.admin_input_TextView_1);
        inputEdit = findViewById(R.id.admin_input_EditText_1);

        submitButton = findViewById(R.id.admin_submit_Button);
        submitButton.setOnClickListener(view -> submit());

        resultText = findViewById(R.id.admin_Error);

        userFlag = false;

        callback = new VolleyCallback() {

            @Override
            public void onSuccess(JSONArray response) {
                //unreachable
            }

            @Override
            public void onSuccess(JSONObject response) {

                resultText.setText("Success");
                resultText.setTextColor(Color.GREEN);
                resultText.setVisibility(View.VISIBLE);

                resetPage();
            }

            @Override
            public void onError(VolleyError error) {

                resultText.setText("Something went wrong...");
                resultText.setTextColor(Color.RED);
                resultText.setVisibility(View.VISIBLE);

            }
        };
    }

    private void deleteUser() {

        makeButtonsInvisible();

        inputText.setText("Enter username:");
        inputEdit.setHint("Username");

        inputText.setVisibility(View.VISIBLE);
        inputEdit.setVisibility(View.VISIBLE);

        submitButton.setVisibility(View.VISIBLE);

        userFlag = true;

    }

    private void deletePost() {

        makeButtonsInvisible();

        inputText.setText("Enter Post ID:");
        inputEdit.setHint("Post ID");

        inputText.setVisibility(View.VISIBLE);
        inputEdit.setVisibility(View.VISIBLE);

        submitButton.setVisibility(View.VISIBLE);

    }

    private void deleteComment() {

        makeButtonsInvisible();

        inputText.setText("Enter Comment ID:");
        inputEdit.setHint("Comment ID");

        inputText.setVisibility(View.VISIBLE);
        inputEdit.setVisibility(View.VISIBLE);

        submitButton.setVisibility(View.VISIBLE);

    }

    private void makeButtonsInvisible() {

        deleteUser.setVisibility(View.INVISIBLE);
        deletePost.setVisibility(View.INVISIBLE);
        deleteComment.setVisibility(View.INVISIBLE);
        resultText.setVisibility(View.INVISIBLE);

    }

    private void submit() {

        PerfectTenRequester requester;
        String url;
        JSONObject obj = new JSONObject();

        if (userFlag)
            url = DELETE_USER_URL + inputEdit.getText().toString();

        else
            url = DELETE_POST_URL + inputEdit.getText().toString();

        requester = new PerfectTenRequester(Request.Method.DELETE, url, obj, callback);
        requester.request();
    }

    private void resetPage() {

        inputEdit.setText("");

        inputText.setVisibility(View.INVISIBLE);
        inputEdit.setVisibility(View.INVISIBLE);
        submitButton.setVisibility(View.INVISIBLE);

        deleteUser.setVisibility(View.VISIBLE);
        deletePost.setVisibility(View.VISIBLE);
        deleteComment.setVisibility(View.VISIBLE);

        userFlag = false;
    }
}