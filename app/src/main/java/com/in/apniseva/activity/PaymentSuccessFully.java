package com.in.apniseva.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.in.apniseva.R;

public class PaymentSuccessFully extends AppCompatActivity {

    Handler handler;
    String paymentmethod,booking_id;
    TextView textName,bookingStatus;

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentsuccessful);

        textName = findViewById(R.id.textName);
        bookingStatus = findViewById(R.id.bookingStatus);

        Intent intent = getIntent();
        paymentmethod = intent.getStringExtra("paymentmethod");
        booking_id = intent.getStringExtra("booking_id");

        bookingStatus.setText("YOUR BOOKING" + "("+"#"+booking_id+")" + "STATUS");

        if(paymentmethod.equals("PayNow")){

            textName.setText("Payment is successful");

        }else{

            textName.setText("Booking is successful");
        }

        bookingStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(PaymentSuccessFully.this,BookingHistory.class);
                intent1.putExtra("booking_id",booking_id);
                startActivity(intent1);
            }
        });

       /* handler = new Handler();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(PaymentSuccessFully.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 5000);*/
    }
}
