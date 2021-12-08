//package com.example.PerfectTen.Screens;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.android.volley.VolleyError;
//import com.example.PerfectTen.R;
//import com.example.PerfectTen.app.AppController;
//import com.example.PerfectTen.net_utils.Const;
//import com.example.PerfectTen.net_utils.LoginFail;
//import com.example.PerfectTen.net_utils.PerfectTenRequester;
//import com.example.PerfectTen.net_utils.VolleyCallback;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class PromoteUser extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_promote_user);
//
//
//        PerfectTenRequester requester;
//        Button zero = findViewById(R.id.zeroLevel);
//        Button one = findViewById(R.id.oneLevel);
//        Button two = findViewById(R.id.twoLevel);
//
//        int privLevel = 0;
//
//        String username = AppController.getTargetUser();
//
////        requester = new PerfectTenRequester(Const.LOGIN_URL, privLevel, new VolleyCallback() {
//
//            @Override
//            public void onSuccess(JSONArray response) {/* unreachable */}
//
//            @Override
//            public void onSuccess(JSONObject response) {
//
//                try {
//
//                    if (response.get(Const.MESSAGE_KEY).equals(Const.SUCCESS_MSG)) {
//
//                      //  AppController.setUsername(login_info.get(Const.USERNAME_KEY).toString());
//                      //  startActivity(new Intent(view.getContext(), HomeScreen.class));
//
//                    }
//
//                    else {
//
//                        /*
//                         * - if a correct object is returned but "success" is fail then a pop up screen, LoginFail.java pops up
//                         * - Ethan Still
//                         */
//                    //    login_fail.setVisibility(View.VISIBLE);
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//
//            @Override
//            public void onError(VolleyError error) {
//          //      startActivity(new Intent(view.getContext(), LoginFail.class));
//            }
//        });
//
//        requester.request();
//    }
//    }
//}*/