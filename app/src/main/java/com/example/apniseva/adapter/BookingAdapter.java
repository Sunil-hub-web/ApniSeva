package com.example.apniseva.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apniseva.R;
import com.example.apniseva.activity.Booking;
import com.example.apniseva.modelclass.CartItem;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    Context context;
    ArrayList<CartItem> services;

    public BookingAdapter(Booking booking, ArrayList<CartItem> servicesItem) {

        this.context = booking;
        this.services = servicesItem;
    }

    @NonNull
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookingservices,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  BookingAdapter.ViewHolder holder, int position) {

        CartItem services_item = services.get(position);

        holder.services_Name.setText(services_item.getName());
        holder.services_Price.setText(services_item.getPrice());

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView services_Name,services_Price;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            services_Price = itemView.findViewById(R.id.services_Price);
            services_Name = itemView.findViewById(R.id.services_Name);
        }
    }
}
