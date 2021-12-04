package com.example.homescreen.Screens;

import static com.example.homescreen.net_utils.Const.DELETE_COMMENT_URL;
import static com.example.homescreen.net_utils.Const.DELETE_POST_URL;
import static com.example.homescreen.net_utils.Const.DELETE_USER_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.homescreen.R;
import com.example.homescreen.net_utils.PerfectTenRequester;
import com.example.homescreen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdminScreen extends AppCompatActivity {

    Button deleteUser;
    Button deletePost;
    Button deleteComment;
    TextView inputText1;
    TextView inputText2;
    EditText inputEdit1;
    EditText inputEdit2;
    Button submitButton;
    TextView errorText;

    boolean userFlag;
    boolean postFlag;
    boolean commentFlag;

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

        inputText1 = findViewById(R.id.admin_input_TextView_1);
        inputText2 = findViewById(R.id.admin_input_TextView_2);
        inputEdit1 = findViewById(R.id.admin_input_EditText_1);
        inputEdit2 = findViewById(R.id.admin_input_EditText_2);

        submitButton = findViewById(R.id.admin_submit_Button);
        submitButton.setOnClickListener(view -> submit());

        errorText = findViewById(R.id.admin_Error);

        userFlag = false;
        postFlag = false;
        commentFlag = false;

        callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                //unreachable
            }

            @Override
            public void onSuccess(JSONObject response) {

                errorText.setText("Success");
                errorText.setTextColor(Color.GREEN);
                errorText.setVisibility(View.VISIBLE);

                resetPage();

            }

            @Override
            public void onError(VolleyError error) {

                errorText.setText("Something went wrong...");
                errorText.setTextColor(Color.RED);
                errorText.setVisibility(View.VISIBLE);

            }
        };
    }

    private void deleteUser() {

        makeButtonsInvisible();

        inputText1.setText("Enter username:");
        inputEdit1.setHint("Username");

        inputText1.setVisibility(View.VISIBLE);
        inputEdit1.setVisibility(View.VISIBLE);

        submitButton.setVisibility(View.VISIBLE);

        userFlag = true;

    }

    private void deletePost() {

        makeButtonsInvisible();

        inputText1.setText("Enter Post ID:");
        inputEdit1.setHint("Post ID");

        inputText1.setVisibility(View.VISIBLE);
        inputEdit1.setVisibility(View.VISIBLE);

        submitButton.setVisibility(View.VISIBLE);

        postFlag = true;

    }

    private void deleteComment() {

        makeButtonsInvisible();

        inputText1.setText("Enter Parent Post ID:");
        inputEdit1.setHint("Post ID");
        inputText2.setText("Enter Comment Index:");
        inputEdit2.setHint("Comment Index");

        inputText1.setVisibility(View.VISIBLE);
        inputEdit1.setVisibility(View.VISIBLE);
        inputText2.setVisibility(View.VISIBLE);
        inputEdit2.setVisibility(View.VISIBLE);

        submitButton.setVisibility(View.VISIBLE);

        commentFlag = true;

    }

    private void makeButtonsInvisible() {

        deleteUser.setVisibility(View.INVISIBLE);
        deletePost.setVisibility(View.INVISIBLE);
        deleteComment.setVisibility(View.INVISIBLE);
        errorText.setVisibility(View.INVISIBLE);

    }

    private void submit() {

        PerfectTenRequester requester;
        String url = null;
        JSONObject requestObj = null;

        if (userFlag)
            url = DELETE_USER_URL;

        else if (postFlag)
            url = DELETE_POST_URL;

        else if (commentFlag)
            url = DELETE_COMMENT_URL + inputEdit1.getText().toString() + "/" + inputEdit2.getText().toString();

        requester = new PerfectTenRequester(url, requestObj, callback);
        requester.request();

    }

    private void resetPage() {

        inputText1.setVisibility(View.INVISIBLE);
        inputText2.setVisibility(View.INVISIBLE);
        inputEdit1.setVisibility(View.INVISIBLE);
        inputEdit2.setVisibility(View.INVISIBLE);
        submitButton.setVisibility(View.INVISIBLE);

        deleteUser.setVisibility(View.VISIBLE);
        deletePost.setVisibility(View.VISIBLE);
        deleteComment.setVisibility(View.VISIBLE);

        userFlag = false;
        postFlag = false;
        commentFlag = false;

    }
}