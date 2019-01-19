package com.gbk.simoni.gbk;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

 /*
 The Item List Adapter class handles the population of the recycler view with the Items
 the consumer has chosen.
 */
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private ArrayList<String> items;

    // Constructor requires ArrayList parameter and passes the data to local ArrayList.
    ItemListAdapter(ArrayList<String> list){
        items = list;
    }

    // Inner class ViewHolder sets the item value.
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView item_name;
        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
        }
    }

    @NonNull
    @Override
    public ItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_items,
                viewGroup, false);
        return new ItemListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.item_name.setText(items.get(i).toUpperCase());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
