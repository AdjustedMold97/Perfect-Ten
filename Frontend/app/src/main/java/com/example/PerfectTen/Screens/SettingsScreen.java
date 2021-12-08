package com.example.PerfectTen.Screens;

import static com.example.PerfectTen.net_utils.Const.CHANGE_USERNAME_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;
import com.example.PerfectTen.net_utils.PerfectTenRequester;
import com.example.PerfectTen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Activity where a user can edit app settings
 * Has not been implemented yet
 * @author Ethan Still
 */
public class SettingsScreen extends AppCompatActivity {

    ImageView settingsPfP;
    EditText usernameEdit;
    EditText passwordEdit;
    EditText emailEdit;
    TextView appliedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        Button friend = findViewById(R.id.friends_Button_profile);
        friend.setOnClickListener(view -> startActivity(new Intent(view.getContext(), FriendsScreen.class)));

        Button settings = findViewById(R.id.settings_Button_profile);
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(),SettingsScreen.class)));

        Button home = findViewById(R.id.home_Button_profile);
        home.setOnClickListener(view -> startActivity(new Intent(view.getContext(), HomeScreen.class)));

        settingsPfP = findViewById(R.id.settings_Profile_Pic);
        settingsPfP.setOnClickListener(view -> changePfP());

        usernameEdit = findViewById(R.id.change_username_EditText);
        passwordEdit = findViewById(R.id.change_password_EditText);
        emailEdit = findViewById(R.id.change_email_EditText);
        appliedText = findViewById(R.id.changes_applied_TextView);

        Button applyButton = findViewById(R.id.apply_Button);
        applyButton.setOnClickListener(view -> applyChanges());

        setUpSettings();
    }

    private void setUpSettings() {

        usernameEdit.setHint(AppController.getUsername());
        /*
         * TODO
         *  set profile picture
         *  set user email
         */

    }

    boolean failureFlag;

    //TODO
    private void applyChanges() {

        failureFlag = false;

        //if the username is changed, we must change it.
        if (!(usernameEdit.getText().equals(AppController.getUsername()))) {

            JSONObject newUsername = new JSONObject();

            PerfectTenRequester requester
                    = new PerfectTenRequester(CHANGE_USERNAME_URL, newUsername, new VolleyCallback() {

                @Override
                public void onSuccess(JSONArray response) {
                    //unreachable
                }

                @Override
                public void onSuccess(JSONObject response) {

                }

                @Override
                public void onError(VolleyError error) {
                    failureFlag = true;
                }
            });

            requester.request();
        }

        /*
         * TODO
         * password change
         * email change
         */

        if (failureFlag) {

            appliedText.setText("Something went wrong...");
            appliedText.setTextColor(Color.RED);

        }

        appliedText.setVisibility(View.VISIBLE);
    }

    //TODO
    private void changePfP() {

        File f = new File(Environment.DIRECTORY_PICTURES);

        Scanner scnr = null;

        try {
            scnr = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (scnr.hasNext()) {



        }

    }
}