package com.example.homescreen;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context mContext;
    String mtest[];

    public MyAdapter(Context context, String test[]){

        mContext = context;
        mtest = test;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(mContext);
        View view = inflate.inflate(R.layout.post_object,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.postObjectTitle.setText(mtest[position]);
    }

    @Override
    public int getItemCount() {
        return mtest.length;
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
