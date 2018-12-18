package com.gbk.simoni.gbk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    ArrayList<String> items;

    public ItemListAdapter (Context context, ArrayList<String> list){

        items = list;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView item_name;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
        }
    }

    @NonNull
    @Override
    public ItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_items, viewGroup, false);
        return new ItemListAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.ViewHolder viewHolder, int i) {

        viewHolder.item_name.setText(items.get(i).toUpperCase());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
