package com.gbk.simoni.gbk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    ArrayList<Order> order;

    public OrderListAdapter (Context context, ArrayList<Order> list){

        order = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView order_number;
        TextView item_count;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            order_number = itemView.findViewById(R.id.order_number);
            item_count = itemView.findViewById(R.id.item_count);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.i("click", "on :");

                }
            });
        }
    }

    @NonNull
    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_display_in_list_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.ViewHolder viewHolder, int i) {

        viewHolder.itemView.setTag(order.get(i));
        viewHolder.order_number.setText("#" + order.get(i).getOrderID());
        //viewHolder.item_count.setText(order.get(i).getItems().length());

    }

    @Override
    public int getItemCount() {
        return order.size();
    }
}
