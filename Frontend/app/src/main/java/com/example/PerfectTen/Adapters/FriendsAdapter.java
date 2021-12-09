package com.example.PerfectTen.Adapters;

import static com.example.PerfectTen.net_utils.Const.BITMAP_HEIGHT;
import static com.example.PerfectTen.net_utils.Const.BITMAP_WIDTH;
import static com.example.PerfectTen.net_utils.Const.ERROR_RESPONSE_TAG;
import static com.example.PerfectTen.net_utils.Const.GET_PFP_URL_1;
import static com.example.PerfectTen.net_utils.Const.GET_PFP_URL_2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.example.PerfectTen.R;
import com.example.PerfectTen.Screens.ProfileView;
import com.example.PerfectTen.app.AppController;
import com.example.PerfectTen.Screens.DMsScreen;

import org.json.JSONArray;
import org.json.JSONException;

/**
     * Adapter class to be used by Friends recycler view with setAdapter()
     * Adapter used to create MyViewHolder objects which hold the data for objects requested from server
     * ViewHolder uses a base friend_object.xml structure to create objects
     * @author Ethan Still
     */
    public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {

        Context mContext;
        JSONArray mtest;


        /**
         * Constructor for FriendsAdapter
         * Context sets a context to use for the inflater
         * JSONArray test is the array input used in OnBindViewHolder to fill the text for friend name. description, and pfp
         * @param context
         * @param test - JSONArray from the server
         * @ author Ethan Still
         */
        public FriendsAdapter(Context context, JSONArray test){

            mContext = context;
            mtest = test;

        }

        /**
         * MyViewHolder holds an instance of friend_object.xml
         * friend_object has two textViews friendName and friendDescription and one ImageView friendImg
         * onCreate creates a new instance of base friend_object to be filled with information
         * @param parent
         * @param viewType
         * @author Ethan Still
         */
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.friend_object,parent,false);
            return new MyViewHolder(view);
        }

        /**
         * OnBind sets the values for each ViewHolder object that will be displayed on the screen
         * JSONObject temp is a object to hold the position in mtest JSONArray
         * The "title", "message", "pfp" are pulled from the JSONObject temp
         * @param holder
         * @param position -position in JSONArray
         * @author Ethan Still
         */
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            try {

                holder.friendObjectName.setText(mtest.get(position).toString());

                String friendName = mtest.get(position).toString();

                ImageRequest imgReq = new ImageRequest(GET_PFP_URL_1 + friendName + GET_PFP_URL_2,

                        response -> holder.friendObjectImg.setImageBitmap(response),

                        BITMAP_WIDTH, BITMAP_HEIGHT, Bitmap.Config.ALPHA_8,

                        error -> VolleyLog.d(ERROR_RESPONSE_TAG, error.getMessage()));

                AppController.getInstance().addToRequestQueue(imgReq);

                //TODO holder.friendObjectImg.

                holder.friendObjectImg.setOnClickListener(view -> {
                    AppController.setTargetUser(holder.friendObjectName.getText().toString());
                    mContext.startActivity(new Intent(view.getContext(), ProfileView.class));
                });

                holder.friendObjectName.setOnClickListener(view -> {
                    AppController.setTargetUser(holder.friendObjectName.getText().toString());
                    mContext.startActivity(new Intent(view.getContext(), ProfileView.class));
                });

                 holder.friendObjectDms.setOnClickListener(view -> {
                     AppController.setTargetUser(holder.friendObjectName.getText().toString());
                     mContext.startActivity(new Intent(view.getContext(), DMsScreen.class));
                 });

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * getItemCount returns the length of the JSONArray
         * @return mtest.length()
         * @author Ethan Still
         */
        @Override
        public int getItemCount() {
            return mtest.length();
        }

        /**
         * MyViewHolder has two textViews and one ImageView
         * TODO waiting for mapping for pfp
         * friendObjectName will contain instances of "title" text from JSONObjects
         * friendObjectDesc will contain instances of "message" text from JSONObjects
         * friendObjectImg will contain instances of "pfp" text from JSONObjects
         * @author Ethan Still
         */
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView friendObjectName;
            ImageView friendObjectImg;
            Button friendObjectDms;


            /**
             * ID's for friendName, friendDescription, and friendImg
             * Can be found in friend_object.xml
             * @param itemView
             * @author Ethan Still
             */
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                friendObjectName = itemView.findViewById(R.id.friendName);
                friendObjectImg = itemView.findViewById(R.id.friendImg);
                friendObjectDms = itemView.findViewById(R.id.friendDms);
            }
        }
}
