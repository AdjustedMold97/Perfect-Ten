package com.example.homescreen.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homescreen.R;
import com.example.homescreen.Screens.ProfileView;
import com.example.homescreen.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            MyViewHolder myViewHolder = new FriendsAdapter.MyViewHolder(view);
            return  myViewHolder;
        }

        /**
         * OnBind sets the values for each ViewHolder object that will be displayed on the screen
         * JSONObject temp is a object to hold the position in mtest JSONArray
         * TODO need mappings for jsonObject of Users
         * The "title", "message", "pfp" are pulled from the JSONObject temp
         * @param holder
         * @param position -position in JSONArray
         * @author Ethan Still
         */
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            try {
                //TODO use keys here not get position for later
//                JSONObject temp;
//                temp = (JSONObject) mtest.get(position);

                //TODO get mappings go here
                holder.friendObjectName.setText(mtest.get(position).toString());
                //holder.friendObjectDesc.setText(temp.get("message").toString());

                //TODO holder.friendObjectImg.

                holder.friendObjectImg.setOnClickListener(view -> {
                    AppController.setTargetUser(holder.friendObjectName.getText().toString());
                    mContext.startActivity(new Intent(view.getContext(), ProfileView.class));
                });
                holder.friendObjectName.setOnClickListener(view -> {
                    AppController.setTargetUser(holder.friendObjectName.getText().toString());
                    mContext.startActivity(new Intent(view.getContext(), ProfileView.class));
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
        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView friendObjectName;
            TextView friendObjectDesc;
            ImageView friendObjectImg;


            /**
             * ID's for friendName, friendDescription, and friendImg
             * Can be found in friend_object.xml
             * @param itemView
             * @author Ethan Still
             */
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                friendObjectName = itemView.findViewById(R.id.friendName);
                friendObjectDesc = itemView.findViewById(R.id.friendDesc);
                friendObjectImg = itemView.findViewById(R.id.friendImg);
            }
        }
}
