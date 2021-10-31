package com.example.homescreen;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;


/*
 * Adapter class to be used by recycler view with setAdapter()
 * Adapter used to create MyViewHolder objects which hold the data for objects requested from server
 * ViewHolder uses a base post_object.xml structure to create objects
 * - Ethan Still
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context mContext;
    JSONArray mtest;



    /*
     * Constructor for MyAdapter
     * Context sets a context to use for the inflater
     * JSONArray test[] is the array input used in OnBindViewHolder to fill the text for post title and body
     * test[] is converted to a string array
     * - Ethan Still
     */
    public MyAdapter(Context context, JSONArray test){

        mContext = context;
        mtest = test;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.post_object,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return  myViewHolder;
    }

    /*
     * OnBind sets the values for each ViewHolder object that will be displayed on the screen
     * Values set are based on the position of the JSONArray[]
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.postObjectTitle.setText(mtest.get(position).toString());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mtest.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView postObjectTitle;
        //TextView postObjectBody;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            postObjectTitle = itemView.findViewById(R.id.postObjectTitle);
            ///postObjectBody = itemView.findViewById(R.id.postObjectBody);
        }
    }

}
