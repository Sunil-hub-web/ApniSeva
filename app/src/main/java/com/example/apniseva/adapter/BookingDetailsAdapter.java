package com.example.apniseva.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apniseva.R;
import com.example.apniseva.modelclass.BookingDetails_ModelClass;

import java.util.ArrayList;

public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.ViewHolder> {

       Context context;
       ArrayList<BookingDetails_ModelClass> bookingDetails;

    public BookingDetailsAdapter(Context context, ArrayList<BookingDetails_ModelClass> bookingDetails) {

         this.context = context;
         this.bookingDetails = bookingDetails;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_details,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        BookingDetails_ModelClass booking = bookingDetails.get(position);

        holder.text_BookingId.setText(booking.getBookingId());
        holder.text_Services.setText(booking.getServicesType());
        holder.text_ProductName.setText(booking.getServicesName());
        holder.text_Price.setText(booking.getPrice());

        holder.text_bookingStatues.setText(booking.getBookingStatues());

        if(booking.getBookingStatues().equals("canceled")){

            holder.text_bookingStatues.setTextColor(Color.RED);
        }
        else if(booking.getBookingStatues().equals("booked")){

            holder.text_bookingStatues.setTextColor(Color.BLUE);

        }

    }

    @Override
    public int getItemCount() {
        return bookingDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_BookingId,text_Services,text_ProductName,text_Price,text_bookingStatues;
        Button btn_Rate,btn_Reorder;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            text_BookingId = itemView.findViewById(R.id.text_BookingId);
            text_Services = itemView.findViewById(R.id.text_Services);
            text_ProductName = itemView.findViewById(R.id.text_ProductName);
            text_Price = itemView.findViewById(R.id.text_Price);
            text_bookingStatues = itemView.findViewById(R.id.text_bookingStatues);
            btn_Reorder = itemView.findViewById(R.id.btn_Reorder);
        }
    }
}
