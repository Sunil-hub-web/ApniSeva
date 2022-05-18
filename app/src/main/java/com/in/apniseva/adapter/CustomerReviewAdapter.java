package com.in.apniseva.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.apniseva.R;
import com.in.apniseva.activity.MainActivity;
import com.in.apniseva.modelclass.CustomerReview_ModelClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomerReviewAdapter extends RecyclerView.Adapter<CustomerReviewAdapter.ViewHolder> {

    Context context;
    ArrayList<CustomerReview_ModelClass> customReview;

    public CustomerReviewAdapter(MainActivity mainActivity, ArrayList<CustomerReview_ModelClass> review) {

        this.context = mainActivity;
        this.customReview = review;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customerreview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        CustomerReview_ModelClass customer_review = customReview.get(position);

        String image = "https://apniseva.com/img/profile/";

        holder.text_CustomerNmae.setText(customer_review.getUserName());
        holder.text_Description.setText(customer_review.getReview());
        holder.text_Reating.setText(customer_review.getRating());

        if(customer_review.getImage().equals(image)){

            Picasso.with(context).load(R.drawable.profile).into(holder.profile_image);

        }else{

            Picasso.with(context).load(customer_review.getImage()).into(holder.profile_image);
        }



    }

    @Override
    public int getItemCount() {
        return customReview.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profile_image;
        TextView text_CustomerNmae,text_Description,text_Reating;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            profile_image = itemView.findViewById(R.id.profile_image);
            text_CustomerNmae = itemView.findViewById(R.id.text_CustomerNmae);
            text_Description = itemView.findViewById(R.id.text_Description);
            text_Reating = itemView.findViewById(R.id.text_Reating);

        }
    }
}
