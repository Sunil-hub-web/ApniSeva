package com.example.apniseva.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apniseva.R;
import com.example.apniseva.adapter.PaymentOptionAdapter;
import com.example.apniseva.modelclass.PaymentOption_ModelClass;

import java.util.ArrayList;

public class PaymentOption extends AppCompatActivity {

  RecyclerView recyclerPaymentOption;
  LinearLayoutManager linearLayoutManager;
  PaymentOptionAdapter paymentOptionAdapter;
  ArrayList<PaymentOption_ModelClass> paymentOPtion = new ArrayList<>();

    ImageView img_back;
    Button btn_paid;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);

        Window window = PaymentOption.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(PaymentOption.this, R.color.white));

        img_back = findViewById(R.id.img_back);
        btn_paid = findViewById(R.id.btn_paid);
        recyclerPaymentOption = findViewById(R.id.recyclerPaymentOption);

       /* real_WalletUPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                real_WalletUPI.setBackgroundResource(R.drawable.paymentselectback);
                radio_WalletUPI.setChecked(true);
                text_WalletUPI.setTextColor(Color.WHITE);
            }
        });*/

        paymentOPtion.add(new PaymentOption_ModelClass("Wallet / UPI"));
        paymentOPtion.add(new PaymentOption_ModelClass("Net Banking"));
        paymentOPtion.add(new PaymentOption_ModelClass("Credit / Debit / ATM Card"));
        paymentOPtion.add(new PaymentOption_ModelClass("Pay on Delivery"));

        linearLayoutManager = new LinearLayoutManager(PaymentOption.this,LinearLayoutManager.VERTICAL,false);
        paymentOptionAdapter = new PaymentOptionAdapter(paymentOPtion,PaymentOption.this);
        recyclerPaymentOption.setLayoutManager(linearLayoutManager);
        recyclerPaymentOption.setHasFixedSize(true);
        recyclerPaymentOption.setAdapter(paymentOptionAdapter);



        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PaymentOption.this,UserDetails.class);
                startActivity(intent);
            }
        });

        btn_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PaymentOption.this,Loding.class);
                startActivity(intent);
            }
        });
    }
}