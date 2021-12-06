package com.example.homescreen.Screens;

import static com.example.homescreen.net_utils.Const.CHANGE_PFP_URL_1;
import static com.example.homescreen.net_utils.Const.CHANGE_PFP_URL_2;
import static com.example.homescreen.net_utils.Const.ERROR_RESPONSE_TAG;
import static com.example.homescreen.net_utils.Const.RESPONSE_TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.example.homescreen.R;
import com.example.homescreen.app.AppController;
import com.example.homescreen.net_utils.PerfectTenRequester;
import com.example.homescreen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * Activity where a user can edit app settings
 * Has not been implemented yet
 * @author Ethan Still
 */
public class SettingsScreen extends AppCompatActivity {

    ImageButton settingsPfP;
    EditText usernameEdit;
    EditText passwordEdit;
    EditText emailEdit;
    TextView appliedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        Button friend = findViewById(R.id.friends_Button_settings);
        friend.setOnClickListener(view -> startActivity(new Intent(view.getContext(), FriendsScreen.class)));

        Button settings = findViewById(R.id.settings_Button_settings);
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(),SettingsScreen.class)));

        Button home = findViewById(R.id.home_Button_settings);
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

    //TODO
    private void applyChanges() {



    }

    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    //TODO
    private void changePfP() {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }

    Bitmap bitmap;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageUri = data.getData();
        bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        //trying this
        String encodedImage = Base64.getEncoder().encodeToString(byteArray);

        JSONObject obj = new JSONObject();

        try {
            obj.put("file", encodedImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PerfectTenRequester requester
                = new PerfectTenRequester(Request.Method.PUT, CHANGE_PFP_URL_1 + AppController.getUsername() + CHANGE_PFP_URL_2, obj, new VolleyCallback() {

            @Override
            public void onSuccess(JSONArray response) {
                //unreachable
            }

            @Override
            public void onSuccess(JSONObject response) {

                Log.d(RESPONSE_TAG, response.toString());

                if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {

                    Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                    settingsPfP.setImageBitmap(resized);

                    //clearing memory
                    bitmap.recycle();
                    resized.recycle();

                }

            }

            @Override
            public void onError(VolleyError error) {

                VolleyLog.d(ERROR_RESPONSE_TAG, error.getMessage());
                VolleyLog.d("Error String ", error.toString());

            }
        });

        requester.request();
    }
}