package com.example.apniseva.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apniseva.R;

public class PaymentSuccessFully extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentsuccessful);

        handler = new Handler();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(PaymentSuccessFully.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 5000);
    }
}
