package com.gbk.simoni.gbk;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderInformationFragment extends Fragment {


    public static View view;

    public OrderInformationFragment() {
        // Required empty public constructor
    }

    // Loads one part of the Screen with the Resource file order Information Fragment.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.order_information_fragment, container, false);
        return view;
    }
}
