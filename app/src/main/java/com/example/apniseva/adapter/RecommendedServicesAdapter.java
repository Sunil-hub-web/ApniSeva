package com.example.apniseva.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apniseva.R;
import com.example.apniseva.activity.MainActivity;
import com.example.apniseva.modelclass.RecommendedServices_ModelClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendedServicesAdapter extends RecyclerView.Adapter<RecommendedServicesAdapter.ViewHolder> {

    Context context;
    ArrayList<RecommendedServices_ModelClass> services;

    public RecommendedServicesAdapter(MainActivity mainActivity, ArrayList<RecommendedServices_ModelClass> services) {

        this.context = mainActivity;
        this.services = services;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendedservices,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        RecommendedServices_ModelClass reco_serv = services.get(position);

        Picasso.with(context).load(reco_serv.getImage()).into(holder.bannerImage);
        holder.text_ServicesName.setText(reco_serv.getService_name());
        holder.text_servicesPrice.setText(reco_serv.getPrice());

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView bannerImage;
        TextView text_ServicesName,text_servicesPrice;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            bannerImage = itemView.findViewById(R.id.bannerImage);
            text_servicesPrice = itemView.findViewById(R.id.text_servicesPrice);
            text_ServicesName = itemView.findViewById(R.id.text_ServicesName);
        }
    }
}
