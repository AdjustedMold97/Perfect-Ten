package com.example.homescreen.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.homescreen.R;

public class AdminScreen extends AppCompatActivity {

    Button deleteUser;
    Button deletePost;
    Button deleteComment;
    TextView inputText1;
    TextView inputText2;
    EditText inputEdit1;
    EditText inputEdit2;
    Button submitButton;

    boolean userFlag;
    boolean postFlag;
    boolean commentFlag;

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

        userFlag = false;
        postFlag = false;
        commentFlag = false;
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

    }

    private void submit() {

        inputText1.setVisibility(View.INVISIBLE);
        inputText2.setVisibility(View.INVISIBLE);
        inputEdit1.setVisibility(View.INVISIBLE);
        inputEdit2.setVisibility(View.INVISIBLE);
        submitButton.setVisibility(View.INVISIBLE);

        deleteUser.setVisibility(View.VISIBLE);
        deletePost.setVisibility(View.VISIBLE);
        deleteComment.setVisibility(View.VISIBLE);

        if (userFlag) {

            //TODO delete flag

            userFlag = false;
        }

        else if (postFlag) {

            //TODO delete post

            postFlag = false;
        }

        else if (commentFlag) {

            //TODO delete comment

            commentFlag = false;
        }

    }
}