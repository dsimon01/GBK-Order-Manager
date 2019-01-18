package com.gbk.simoni.gbk;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;




/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListFragment extends Fragment {


    private View view;

    public OrderListFragment() {
        // Required empty public constructor
    }

    // Populates view with designs from Resources file.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.order_list_fragment, container, false);
        return view;
    }

    // Method that populates the recycler view with a List of orders.
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateOrderList();
    }

    public void updateOrderList(){
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new OrderListAdapter(this.getActivity(), ParseServer.orders);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
