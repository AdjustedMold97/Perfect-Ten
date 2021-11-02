package com.example.homescreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

    /*
     * Adapter class to be used by Friends recycler view with setAdapter()
     * Adapter used to create MyViewHolder objects which hold the data for objects requested from server
     * ViewHolder uses a base post_object.xml structure to create objects
     * - Ethan Still
     */
    public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {

        Context mContext;
        JSONArray mtest;


        /*
         * Constructor for FriendsAdapter
         * Context sets a context to use for the inflater
         * JSONArray test is the array input used in OnBindViewHolder to fill the text for post title and body
         * - Ethan Still
         */
        public FriendsAdapter(Context context, JSONArray test){

            mContext = context;
            mtest = test;

        }

        /*
         * MyViewHolder holds an instance of friend_object.xml
         * friend_object has two textViews friendName and friendDescription and one ImageView friendImg
         * onCreate creates a new instance of base friend_object to be filled with information
         * - Ethan Still
         */
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.friend_object,parent,false);
            MyViewHolder myViewHolder = new FriendsAdapter.MyViewHolder(view);
            return  myViewHolder;
        }

        /*
         * OnBind sets the values for each ViewHolder object that will be displayed on the screen
         * JSONObject temp is a object to hold the position in mtest JSONArray
         * TODO need mappings for jsonObject of Users
         * The "title", "message", "pfp" are pulled from the JSONObject temp
         * - Ethan Still
         */
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            try {

                JSONObject temp;
                temp = (JSONObject) mtest.get(position);

                //TODO get mappings go here
                holder.friendObjectName.setText(temp.get("title").toString());
                holder.friendObjectDesc.setText(temp.get("message").toString());
                //TODO holder.friendObjectImg.


            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        /*
         * getItemCount returns the length of mtest JSONArray
         * - Ethan Still
         */
        @Override
        public int getItemCount() {
            return mtest.length();
        }

        /*
         * MyViewHolder has two textViews and one ImageView
         * TODO waiting for mappings
         * friendObjectName will contain instances of "title" text from JSONObjects
         * friendObjectDesc will contain instances of "message" text from JSONObjects
         * friendObjectImg will contain instances of "pfp" text from JSONObjects
         * - Ethan Still
         */
        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView friendObjectName;
            TextView friendObjectDesc;
            ImageView friendObjectImg;


            /*
             * ID's for friendName, friendDescription, and friendImg
             * Can be found in friend_object.xml
             * - Ethan Still
             */
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                friendObjectName = itemView.findViewById(R.id.friendName);
                friendObjectDesc = itemView.findViewById(R.id.friendDescription);
                friendObjectImg = itemView.findViewById(R.id.friendImg);
            }
        }



}
