package com.example.PerfectTen.Screens;

import static com.example.PerfectTen.net_utils.Const.BITMAP_HEIGHT;
import static com.example.PerfectTen.net_utils.Const.BITMAP_WIDTH;
import static com.example.PerfectTen.net_utils.Const.CHANGE_PFP_URL_1;
import static com.example.PerfectTen.net_utils.Const.CHANGE_PFP_URL_2;
import static com.example.PerfectTen.net_utils.Const.CHANGE_SETTINGS_URL;
import static com.example.PerfectTen.net_utils.Const.EMAIL_KEY;
import static com.example.PerfectTen.net_utils.Const.ERROR_RESPONSE_TAG;
import static com.example.PerfectTen.net_utils.Const.GET_PFP_URL_1;
import static com.example.PerfectTen.net_utils.Const.GET_PFP_URL_2;
import static com.example.PerfectTen.net_utils.Const.GET_USER_URL;
import static com.example.PerfectTen.net_utils.Const.PASSWORD_KEY;
import static com.example.PerfectTen.net_utils.Const.PICK_IMAGE;
import static com.example.PerfectTen.net_utils.Const.QUALITY_SETTING;
import static com.example.PerfectTen.net_utils.Const.RESULT_TAG;
import static com.example.PerfectTen.net_utils.Const.USERNAME_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;
import com.example.PerfectTen.net_utils.PerfectTenRequester;
import com.example.PerfectTen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Activity where a user can edit app settings
 * Has not been implemented yet
 * @author Ethan Still
 */
public class SettingsScreen extends AppCompatActivity {

    private String userPassword;
    private String userEmail;

    ImageButton settingsPfP;
    EditText usernameEdit;
    EditText passwordEdit;
    EditText emailEdit;
    TextView appliedText;

    Uri imageUri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        /*
         * these three buttons make up the bottom tab buttons they go on each screen
         * they include Home, Settings, and Friends and reroute the user to that page form anywhere in the app
         * - Ethan Still
         */
        Button friend = findViewById(R.id.friends_Button_profile);
        friend.setOnClickListener(view -> startActivity(new Intent(view.getContext(), FriendsScreen.class)));

        Button settings = findViewById(R.id.settings_Button_profile);
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(), SettingsScreen.class)));

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

        ImageRequest imgReq = new ImageRequest(GET_PFP_URL_1 + AppController.getUsername() + GET_PFP_URL_2,

                response -> settingsPfP.setImageBitmap(response),

                BITMAP_WIDTH, BITMAP_HEIGHT, Bitmap.Config.ALPHA_8,

                error -> VolleyLog.d(ERROR_RESPONSE_TAG, error.getMessage()));

        AppController.getInstance().addToRequestQueue(imgReq);

        getUserInfo();
    }

    private void getUserInfo() {

        PerfectTenRequester requester
                = new PerfectTenRequester(GET_USER_URL + AppController.getUsername(), null, new VolleyCallback() {

            @Override
            public void onSuccess(JSONArray response) {
                //unreachable
            }

            @Override
            public void onSuccess(JSONObject response) {

                try {

                    userPassword = response.get(PASSWORD_KEY).toString();
                    userEmail = response.get(EMAIL_KEY).toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                emailEdit.setHint(userEmail);
            }

            @Override
            public void onError(VolleyError error) {

                VolleyLog.d(ERROR_RESPONSE_TAG, error.getMessage());

            }
        });

        requester.request();
    }

    private void applyChanges() {

        JSONObject user = new JSONObject();

        try {

            if (!(usernameEdit.getText().toString().equals(AppController.getUsername()))
                    && usernameEdit.getText().toString().length() > 0)
                user.put(USERNAME_KEY, usernameEdit.getText().toString());

            else
                user.put(USERNAME_KEY, AppController.getUsername());

            if (!(passwordEdit.getText().toString().equals(userPassword))
                    && passwordEdit.getText().toString().length() > 0)
                user.put(PASSWORD_KEY, passwordEdit.getText().toString());

            else
                user.put(PASSWORD_KEY, userPassword);

            if (!(emailEdit.getText().toString().equals(userEmail))
                    && emailEdit.getText().toString().length() > 0)
                user.put(EMAIL_KEY, emailEdit.getText().toString());

            else
                user.put(EMAIL_KEY, userEmail);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        PerfectTenRequester requester
                = new PerfectTenRequester(Request.Method.PUT, CHANGE_SETTINGS_URL + AppController.getUsername(), user, new VolleyCallback() {

            @Override
            public void onSuccess(JSONArray response) {
                //unreachable
            }

            @Override
            public void onSuccess(JSONObject response) {

                appliedText.setVisibility(View.VISIBLE);

                Log.d(RESULT_TAG, "Received User Info.");

            }

            @Override
            public void onError(VolleyError error) {

                VolleyLog.d(ERROR_RESPONSE_TAG, error.getMessage());

            }
        });

        requester.request();
    }

    private void changePfP() {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }

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

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {

            //converting bitmap to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY_SETTING, stream);
            byte[] byteArray = stream.toByteArray();

            @SuppressLint({"NewApi", "LocalSuppress"})
            String encodedImage = Base64.getEncoder().encodeToString(byteArray);

            StringRequest strReq = new StringRequest(Request.Method.PUT,
                    CHANGE_PFP_URL_1 + AppController.getUsername() + CHANGE_PFP_URL_2,

                    response -> {

                        //rescale image and set pfp
                        Bitmap resized = Bitmap.createScaledBitmap(bitmap, BITMAP_WIDTH, BITMAP_HEIGHT, true);
                        settingsPfP.setImageBitmap(resized);

                        appliedText.setVisibility(View.VISIBLE);
                    },

                    error -> VolleyLog.d(ERROR_RESPONSE_TAG, error.getMessage())) {

                        @Override
                        protected Map<String, String> getParams() {

                            Map<String, String> params = new HashMap<>();
                            params.put("file", encodedImage);

                            return params;
                        }
            };

            AppController.getInstance().addToRequestQueue(strReq);
        }
    }
}