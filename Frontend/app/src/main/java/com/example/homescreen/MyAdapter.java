package com.example.homescreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    final int View_TYPE_LOADING = 0;
    final int VIEW_TYPE_ITEM = 1;

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_test, parent, false);

            return new ViewHolder(v);
        }
//        else{
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
//            return LoadingHolder(view);
//        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getHead());
        holder.textViewDesc.setText(listItem.getDesc());

    }

    public int getItemViewType(int position){
        return listItems.get(position) == null ? View_TYPE_LOADING : VIEW_TYPE_ITEM;

    }
    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;



        public ViewHolder(@NonNull View itemView){
            super(itemView);
//tvItem = itemView.findByID(R.id.tvitem);

//TODO textViewHead = (TextView) itemView.findViewById(R.id.); don't know what goes in R.id
//            textViewHead = (TextView) itemView.findViewById(R.id.textView4);
//            textViewDesc = (TextView) itemView.findViewById(R.id.textView5);

        }

    }

    private class LoadingHolder extends RecyclerView.ViewHolder {

        public LoadingHolder(@NonNull View itemView){
            super(itemView);
        }
    }
}
