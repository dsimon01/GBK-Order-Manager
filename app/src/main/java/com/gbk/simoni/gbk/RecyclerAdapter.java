package com.gbk.simoni.gbk;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;



public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Order> order;

    // Constructor
    public RecyclerAdapter(List<Order> order) {
        this.order = order;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_recycler, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        final Order MockOrder = order.get(i);
        viewHolder.orderNumber.setText(MockOrder.tableNumber);
        viewHolder.orderInfo.setText(MockOrder.items);

        //System.out.println(menuItem);   = >  com.gbk.simoni.gbk.Items@ee5429c
        // System.out.println(menuItem.itemName);  = > Gourmet Very Spicy
        // System.out.println(menuItem.itemDescriptionList); Description1
        // System.out.println(menuItem.itemImageList); => 2131165283

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ItemClicked", "Item: " + i);
                //Intent intent = new Intent(v.getContext(), ItemSelectionActivity.class);
                //v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return order.size();
    }
}
