package com.in.apniseva.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.apniseva.R;
import com.in.apniseva.SessionManager;
import com.in.apniseva.activity.MainActivity;
import com.in.apniseva.activity.Subcategory;
import com.in.apniseva.modelclass.OurServices_ModelClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OurServicesAdapter extends RecyclerView.Adapter<OurServicesAdapter.ViewHolder> {

    Context context;
    ArrayList<OurServices_ModelClass> ourservices;
    SessionManager sessionManager;

    public OurServicesAdapter(MainActivity mainActivity, ArrayList<OurServices_ModelClass> ourServices) {

        this.context = mainActivity;
        this.ourservices = ourServices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        sessionManager = new SessionManager(context);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ourservices,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        OurServices_ModelClass services = ourservices.get(position);

        String image = services.getImage();

        holder.text_ServicesName.setText(services.getName());
        Picasso.with(context).load(image).into(holder.image_ServicesImage);

        holder.service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(context, Subcategory.class);
                    intent.putExtra("categoryId",services.getId());
                    context.startActivity(intent);
                    sessionManager.setCategoryID(services.getId());

            }
        });


    }

    @Override
    public int getItemCount() {
        return ourservices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image_ServicesImage;
        TextView text_ServicesName;
        RelativeLayout service;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            image_ServicesImage = itemView.findViewById(R.id.image_ServicesImage);
            text_ServicesName = itemView.findViewById(R.id.text_ServicesName);
            service = itemView.findViewById(R.id.service);

        }
    }
}
