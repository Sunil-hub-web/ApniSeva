package com.example.apniseva.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apniseva.R;
import com.example.apniseva.activity.BillingDetails;
import com.example.apniseva.activity.BookingHistory;
import com.example.apniseva.activity.PaymentSuccessFully;
import com.example.apniseva.modelclass.BookingDetails_ModelClass;
import com.example.apniseva.modelclass.OrderItem_ModelClass;

import java.util.ArrayList;

public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.ViewHolder> {

       Context context;
       ArrayList<BookingDetails_ModelClass> bookingDetails;
       OrderItemAdapter orderItemAdapter;
       ArrayList<OrderItem_ModelClass> orderitem;
       String booking_ad = "BookingAdapter";

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

        holder.text_BookingId.setText("#"+booking.getOrder_id());

        holder.text_bookingStatues.setText(booking.getWork_status());
        holder.text_Services.setText(booking.getCategoryname());

        orderitem = new ArrayList<>();
        orderitem = booking.getOrderitem();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        orderItemAdapter = new OrderItemAdapter(context, orderitem,booking_ad);
        holder.recyclerBookingHistory.setLayoutManager(linearLayoutManager);
        holder.recyclerBookingHistory.setHasFixedSize(true);
        holder.recyclerBookingHistory.setAdapter(orderItemAdapter);



        holder.btn_Reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(context, BookingHistory.class);
                intent1.putExtra("booking_id",booking.getOrder_id());
                context.startActivity(intent1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return bookingDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_BookingId,text_bookingStatues,text_Services;
        Button btn_Reorder;
        RecyclerView recyclerBookingHistory;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            text_BookingId = itemView.findViewById(R.id.text_BookingId);
            text_bookingStatues = itemView.findViewById(R.id.text_bookingStatues);
            btn_Reorder = itemView.findViewById(R.id.btn_Reorder);
            recyclerBookingHistory = itemView.findViewById(R.id.recyclerBookingHistory);
            text_Services = itemView.findViewById(R.id.text_Services);
        }
    }
}
