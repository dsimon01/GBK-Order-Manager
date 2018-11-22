package com.gbk.simoni.gbk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;



public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Order> order;
    private ListView orderItemsListView;
    ArrayList<Order> ordersForAdapter = new ArrayList<>();
    OnOrderClickListener listener;

    public void setListener(OnOrderClickListener listener) {
        this.listener = listener;
    }




    // Constructor
    public RecyclerAdapter(List<Order> order) {
        this.order = order;
        System.out.println("THE ORDERS LIST SIZE IS : " + order.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_recycler, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        final Order mockOrder = order.get(i);
        viewHolder.orderNumber.setText(Integer.toString(mockOrder.orderID));
        viewHolder.orderInfo.setText(mockOrder.items);
        ordersForAdapter.add(mockOrder);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onOrderClicked(mockOrder);
                }

                System.out.println("Clicked on item: " + i);
                System.out.println(mockOrder.items);
                System.out.println(mockOrder.orderID);
                System.out.println(mockOrder.tableNumber);
                System.out.println(mockOrder.price);
                System.out.println(mockOrder.status);

                System.out.println("THE ORDERS LIST SIZE IS : " + order.size());
                v = v.getRootView();
                orderItemsListView = v.findViewById(R.id.itemsDisplayListView);

                ArrayAdapter<Order> arrayAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, ordersForAdapter);
                System.out.println("THE ARRAY ADAPTER LIST SIZE IS : " + arrayAdapter.getCount());
                System.out.println(orderItemsListView); // null
                orderItemsListView.setAdapter(arrayAdapter);




                //Intent intent = new Intent(v.getContext(), ItemSelectionActivity.class);
                //v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return order.size();
    }



    public interface OnOrderClickListener{

        void onOrderClicked(Order order);

    }
}
