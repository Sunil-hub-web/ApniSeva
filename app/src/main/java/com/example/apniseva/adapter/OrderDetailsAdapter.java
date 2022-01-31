package com.example.apniseva.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apniseva.R;
import com.example.apniseva.activity.BillingDetails;
import com.example.apniseva.modelclass.CartItem;
import com.example.apniseva.modelclass.OrderDetails_ModelClass;

import java.util.ArrayList;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {

    Context context;
    ArrayList<CartItem> order;

    public OrderDetailsAdapter(BillingDetails billingDetails, ArrayList<CartItem> order) {

        this.context = billingDetails;
        this.order = order;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderpage,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        CartItem order_details = order.get(position);

        holder.text_productName.setText(order_details.getName());
        holder.subTotalPrice.setText(order_details.getPrice());

    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_productName,subTotalPrice;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            text_productName = itemView.findViewById(R.id.text_productName);
            subTotalPrice = itemView.findViewById(R.id.subTotalPrice);
        }
    }
}
