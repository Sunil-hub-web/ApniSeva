package com.in.apniseva.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.in.apniseva.R;
import com.in.apniseva.activity.PaymentOption;
import com.in.apniseva.modelclass.PaymentOption_ModelClass;

import java.util.ArrayList;

public class PaymentOptionAdapter extends RecyclerView.Adapter<PaymentOptionAdapter.ViewHolder> {

    Context context;
    ArrayList<PaymentOption_ModelClass> paymentOption;
    int index;

    public PaymentOptionAdapter(ArrayList<PaymentOption_ModelClass> paymentOPtion, PaymentOption paymentOption) {

        this.context = paymentOption;
        this.paymentOption = paymentOPtion;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paymentoption,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        PaymentOption_ModelClass payment = paymentOption.get(position);

        holder.text_PaymentName.setText(payment.getName());

        holder.real_WalletUPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                index = position;
                notifyDataSetChanged();
                holder.radio_CheckOut.setChecked(true);
            }
        });

        if(index == position){

            holder.real_WalletUPI.setBackgroundResource(R.drawable.paymentselectback);
            holder.real_WalletUPI.setElevation(15);
            holder.radio_CheckOut.setChecked(true);
        }
        else {

            holder.real_WalletUPI.setBackgroundResource(R.color.white);
            holder.real_WalletUPI.setElevation(5);
            holder.radio_CheckOut.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return paymentOption.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_PaymentName;
        RelativeLayout real_WalletUPI;
        RadioButton radio_CheckOut;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            real_WalletUPI = itemView.findViewById(R.id.real_WalletUPI);
            text_PaymentName = itemView.findViewById(R.id.text_PaymentName);
            radio_CheckOut = itemView.findViewById(R.id.radio_CheckOut);
        }
    }
}
