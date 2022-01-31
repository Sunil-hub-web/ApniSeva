package com.example.apniseva.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apniseva.R;
import com.example.apniseva.adapter.BookingDetailsAdapter;
import com.example.apniseva.modelclass.BookingDetails_ModelClass;

import java.util.ArrayList;

public class BookingDetails extends AppCompatActivity {

    RecyclerView recyclerBookingDetails;
    ArrayList<BookingDetails_ModelClass> bookingDetails = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    BookingDetailsAdapter bookingDetailsAdapter;

    ImageView img_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookingdetails);


        recyclerBookingDetails = findViewById(R.id.recyclerBookingDetails);
        img_back = findViewById(R.id.img_back);

        bookingDetails.add(new BookingDetails_ModelClass("ghdythddfgrt","paid","AC services","Split AC services","1599.00"));
        bookingDetails.add(new BookingDetails_ModelClass("ghdythdrtrer","canceled","AC services","Split AC services","1599.00"));
        bookingDetails.add(new BookingDetails_ModelClass("ghdythdghjiu","booked","AC services","Split AC services","1599.00"));
        bookingDetails.add(new BookingDetails_ModelClass("ghdythdqwsac","paid","AC services","Split AC services","1599.00"));

        linearLayoutManager = new LinearLayoutManager(BookingDetails.this,LinearLayoutManager.VERTICAL,false);
        bookingDetailsAdapter = new BookingDetailsAdapter(BookingDetails.this,bookingDetails);
        recyclerBookingDetails.setLayoutManager(linearLayoutManager);
        recyclerBookingDetails.setHasFixedSize(true);

        recyclerBookingDetails.setAdapter(bookingDetailsAdapter);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BookingDetails.this,UserDetails.class);
                startActivity(intent);
            }
        });
    }
}
