package com.in.apniseva.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.apniseva.R;
import com.in.apniseva.modelclass.OrderItem_ModelClass;

import java.util.ArrayList;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    Context context;
    ArrayList<OrderItem_ModelClass> order;
    String booking;

    public OrderItemAdapter(Context context, ArrayList<OrderItem_ModelClass> orderitem, String booking) {

        this.context = context;
        this.order = orderitem;
        this.booking = booking;
    }

    @NonNull
    @Override
    public OrderItemAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderpage,parent,false);
        return new OrderItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  OrderItemAdapter.ViewHolder holder, int position) {

        OrderItem_ModelClass itemorder = order.get(position);

        holder.text_productName.setText(itemorder.getProduct());
        holder.subTotalPrice.setText(itemorder.getAmount());

    }

    @Override
    public int getItemCount() {

        if(booking.equals("BookingDetails")){

            return order.size();

        }else{

            return  order.size()>2?2:order.size();
        }

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
