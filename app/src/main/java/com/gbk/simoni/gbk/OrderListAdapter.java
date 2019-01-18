package com.gbk.simoni.gbk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

// Adapter class that populates the Order list fragment.
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private ArrayList<Order> order;
    private ItemClicked activity;

    // Interface implementation to retrieve which items in the recycler view are selected.
    public interface ItemClicked{
        void onItemClicked(int which);
    }

    // Constructor of class which expects context and array list as parameters
    public OrderListAdapter (Context context, ArrayList<Order> list){
        order = list;
        activity = (ItemClicked) context;
    }

    // inner View holder class handles the content display and retrieves item clicked.
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView order_number;
        TextView order_status;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            order_number = itemView.findViewById(R.id.order_number);
            order_status = itemView.findViewById(R.id.order_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(order.indexOf((Order)v.getTag()));
                }
            });
        }
    }

    // Method that inflates from Resources file the design.
    @NonNull
    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate
                (R.layout.order_display_in_list_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    // Method that handles the content display based on the data retrieved from the database.
    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(order.get(i));
        viewHolder.order_number.setText("#" + order.get(i).getOrderID());
        viewHolder.order_status.setText(order.get(i).getStatus().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return order.size();
    }
}
