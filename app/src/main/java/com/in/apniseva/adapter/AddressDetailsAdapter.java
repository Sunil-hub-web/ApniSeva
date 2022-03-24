package com.in.apniseva.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.apniseva.R;
import com.in.apniseva.activity.BillingDetails;
import com.in.apniseva.modelclass.AddressDetails_ModelClass;

import java.util.ArrayList;

public class AddressDetailsAdapter extends RecyclerView.Adapter<AddressDetailsAdapter.ViewHolder> {

    Context context;
    ArrayList<AddressDetails_ModelClass> address;

    public AddressDetailsAdapter(BillingDetails billingDetails, ArrayList<AddressDetails_ModelClass> address) {

        this.context = billingDetails;
        this.address = address;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addressdetails,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return address.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
        }
    }
}
