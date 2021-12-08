package com.example.PerfectTen.Screens;

import static com.example.PerfectTen.net_utils.Const.DELETE_POST_URL;
import static com.example.PerfectTen.net_utils.Const.DELETE_USER_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;
import com.example.PerfectTen.net_utils.Const;
import com.example.PerfectTen.net_utils.LoginFail;
import com.example.PerfectTen.net_utils.PerfectTenRequester;
import com.example.PerfectTen.net_utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PromoteUser extends AppCompatActivity {
//
//    boolean zeroFlag;
//    boolean oneFlag;
//    boolean twoFlag;
//
//    VolleyCallback callback;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_promote_user);
//
//        zeroFlag = false;
//        oneFlag = false;
//        twoFlag = false;
//
//        callback = new VolleyCallback() {
//
//            @Override
//            public void onSuccess(JSONArray response) {
//                //unreachable
//            }
//
//            @Override
//            public void onSuccess(JSONObject response) {
//
////                errorText.setText("Success");
////                errorText.setTextColor(Color.GREEN);
////                errorText.setVisibility(View.VISIBLE);
//
//
//
//            }
//
//            @Override
//            public void onError(VolleyError error) {
//
////                errorText.setText("Something went wrong...");
////                errorText.setTextColor(Color.RED);
////                errorText.setVisibility(View.VISIBLE);
//
//            }
//        };
//
//        private void submit() {
//
//            PerfectTenRequester requester;
//            String url = null;
//            JSONObject requestObj = null;
//            String userID;
//            String ID;
//
//
//            if (zeroFlag) {
//
//                //userID = String.valueOf(inputEdit1.getText());
////
////
////                //TODO need user specifci ID for url
////                url = DELETE_USER_URL + userID;
////
////
////
////                Map<String, String> params = new HashMap<>();
////                params.put(Const.ID_KEY, userID);
////
////                requestObj = new JSONObject(params);
//            }
//
//            else if (oneFlag) {
//
//
//                //ID = String.valueOf(inputEdit1.getText());
////
////                url = DELETE_POST_URL + ID;
////
////
////                Map<String, String> params = new HashMap<>();
////                params.put(Const.ID_KEY, ID);
////
////                requestObj = new JSONObject(params);
//
//
//            }
//
//            else if (twoFlag) {
//
//
////                ID = String.valueOf(inputEdit1.getText());
////
////                url = DELETE_POST_URL + ID;
////
////                Map<String, String> params = new HashMap<>();
////                params.put(Const.ID_KEY, ID);
////
////                requestObj = new JSONObject(params);
//
//            }
//
//            requester = new PerfectTenRequester(url, requestObj, callback);
//            requester.request();
//
//
//        }
//        }
//        int privLevel = 0;
//        PerfectTenRequester requester;
//
//        Button zero = findViewById(R.id.zeroLevel);
//        Button one = findViewById(R.id.oneLevel);
//        Button two = findViewById(R.id.twoLevel);
//
//        zero.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
//
//
//
//
//
////        String username = AppController.getTargetUser();
////
////        requester = new PerfectTenRequester(Const.USER_PRIVILEGE_1 + username + Const.USER_PRIVILEGE_2,
////                privLevel, new VolleyCallback() {
////
////            @Override
////            public void onSuccess(JSONArray response) {/* unreachable */}
////
////            @Override
////            public void onSuccess(JSONObject response) {
////
////                try {
////
////
////                    //TODO
////
////
////                    }
////
////
////                catch (JSONException e) {
////                    e.printStackTrace();
////                }
////
////            }
////
////
////            @Override
////            public void onError(VolleyError error) {
////
////            }
////        });
////
////        requester.request();
//    }
//
}