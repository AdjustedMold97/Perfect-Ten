package com.example.PerfectTen.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;
import com.example.PerfectTen.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * Adapter class to be used by recycler view with setAdapter()
 * Adapter used to create MyViewHolder objects which hold the data for objects requested from server
 * ViewHolder uses a base comment_object.xml structure to create objects
 * @author Ethan Still
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    Context mContext;
    JSONArray mtest;


    /**
     * Constructor for MyAdapter
     * Context sets a context to use for the inflater
     * JSONArray test is the array input used in OnBindViewHolder to fill the text for comments
     * @param context
     * @param test -JSONArray requested from server
     * @author Ethan Still
     */
    public CommentAdapter(Context context, JSONArray test){
        mContext =context;
        mtest = test;
    }

    /**
     * MyViewHolder holds an instance of comment_object.xml
     * comment_object has one commentObjectText
     * onCreate creates a new instance of base comment_object to be filled with information
     * @param parent
     * @param viewType
     * @author Ethan Still
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.comment_object, parent,false);

        return new MyViewHolder(view);

    }

    /**
     * OnBind sets the values for each ViewHolder object that will be displayed on the screen
     * JSONObject temp is a object to hold the position in mtest JSONArray
     * The "text" is pulled from the JSONObject temp
     * @param holder
     * @param position - position in JSONArray
     * @author Ethan Still
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try{

            JSONObject temp;
            temp = (JSONObject) mtest.get(position);

            String idstring = temp.get(Const.ID_KEY).toString();

            holder.commentObjectUser.setText(temp.get(Const.POST_USER_KEY).toString());
            holder.commentObjectText.setText(temp.get(Const.MESSAGE_KEY).toString());
            holder.commentObjectID.setText("ID: " + idstring);

            if(AppController.getPrivLevel()>0)
                holder.commentObjectID.setVisibility(View.VISIBLE);

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
    public int getItemCount(){
        return mtest.length();
    }

    /**
     * MyViewHolder has one textView
     * commentObjectText will contain instances of "text" text from JSONObjects
     * @author Ethan Still
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView commentObjectText;
        TextView commentObjectUser;
        TextView commentObjectID;

        /**
         * ID's for postObjectTitle and postObjectBody
         * Can be found in post_object.xml
         * @param itemView
         * @author Ethan Still
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            commentObjectText = itemView.findViewById(R.id.commentObjectText);
            commentObjectUser = itemView.findViewById(R.id.commentObjectUsername);
            commentObjectID = itemView.findViewById(R.id.commentObjectID);

        }
    }

}











