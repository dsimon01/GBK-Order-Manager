package com.gbk.simoni.gbk;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    View view;

    public OrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.order_list_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OrderListAdapter(this.getActivity(), ParseServerConfig.orders);
        System.out.println(ParseServerConfig.orders.size() + " <-- ON ACTIVITY CREATED" );
        recyclerView.setAdapter(adapter);
    }

    public void notifyDataChange(){

        recyclerView = view.findViewById(R.id.recycler_view_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OrderListAdapter(this.getActivity(), ParseServerConfig.orders);
        System.out.println(ParseServerConfig.orders.size() + " <-- NOTIFY DATA CHANGE" );
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
