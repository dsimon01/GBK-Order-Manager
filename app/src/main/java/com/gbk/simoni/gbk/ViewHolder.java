package com.gbk.simoni.gbk;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {

// click view in here:


    TextView orderNumber;
    TextView orderInfo;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        orderNumber = itemView.findViewById(R.id.orderNumberTextView);
        orderInfo = itemView.findViewById(R.id.itemInfoTextView);
    }
}
