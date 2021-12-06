package com.example.PerfectTen.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PerfectTen.R;
import com.example.PerfectTen.Screens.PostView;
import com.example.PerfectTen.app.AppController;
import com.example.PerfectTen.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Adapter class to be used by recycler view with setAdapter()
 * Adapter used to create MyViewHolder objects which hold the data for objects requested from server
 * ViewHolder uses a base post_object.xml structure to create objects
 * @author Ethan Still
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    Context mContext;
    JSONArray mtest;

    /**
     * Constructor for MyAdapter
     * Context sets a context to use for the inflater
     * JSONArray test is the array input used in OnBindViewHolder to fill the text for post title and body
     * @param context
     * @param test -JSONArray requested from server
     * @author Ethan Still
     */
    public PostAdapter(Context context, JSONArray test){

        mContext = context;
        mtest = test;

    }

    /**
     * MyViewHolder holds an instance of post_object.xml
     * post_object has two textViews postObjectTitle and postObjectBody
     * onCreate creates a new instance of base post_object to be filled with information
     * @param parent
     * @param viewType
     * @author Ethan Still
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.post_object,parent,false);
        return new MyViewHolder(view);
    }

    /**
     * OnBind sets the values for each ViewHolder object that will be displayed on the screen
     * JSONObject temp is a object to hold the position in mtest JSONArray
     * The "title" and "message" are pulled from the JSONObject temp
     * @param holder
     * @param position - position in JSONArray
     * @author Ethan Still
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        JSONObject temp = null;

        try {

            temp = (JSONObject) mtest.get(position);

            String title = temp.get(Const.TITLE_KEY).toString();
            String body = temp.get(Const.MESSAGE_KEY).toString();
            String uname = temp.get(Const.POST_USER_KEY).toString();

            int id = Integer.parseInt(temp.get(Const.ID_KEY).toString());

            String idstring = temp.get(Const.ID_KEY).toString();

            holder.postObjectTitle.setText(title);
            holder.postObjectBody.setText(body);
            holder.postObjectID.setText("ID: " + idstring);
            holder.postObjectUname.setText(uname);

            holder.postObjectTitle.setOnClickListener(view -> {

                AppController.setPostID(id);
                mContext.startActivity(new Intent(mContext, PostView.class));

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
     * MyViewHolder has two textViews
     * postObjectTitle will contain instances of "title" text from JSONObjects
     * postObjectBody will contain instances of "message" text from JSONObjects
     * @author Ethan Still
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView postObjectTitle;
        TextView postObjectBody;
        TextView postObjectID;
        TextView postObjectUname;
        TextView postObjectTime;
        /**
         * ID's for postObjectTitle and postObjectBody
         * Can be found in post_object.xml
         * @param itemView
         * @author Ethan Still
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            postObjectTitle = itemView.findViewById(R.id.postObjectTitle);
            postObjectBody = itemView.findViewById(R.id.postObjectBody);
            postObjectID = itemView.findViewById(R.id.postObjectID);
            postObjectUname = itemView.findViewById(R.id.postObjectUname);
            postObjectTime = itemView.findViewById(R.id.postObjectTime);

        }
    }
}