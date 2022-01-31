package com.example.apniseva.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.apniseva.R;
import com.example.apniseva.activity.MainActivity;
import com.example.apniseva.modelclass.Banner_ModelClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewModel> {

    Context context;

    ArrayList<Banner_ModelClass> show_Image;

    ViewPager2 viewPager2;

    public BannerAdapter(MainActivity mainActivity, ArrayList<Banner_ModelClass> banner, ViewPager2 showImageViewPager) {

        this.context = mainActivity;
        this.show_Image = banner;
        this.viewPager2 = showImageViewPager;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showbanner,parent,false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {

        Banner_ModelClass banner_image = show_Image.get(position);

        String image = banner_image.getImage();

        Picasso.with(context).load(image).into(holder.img_showImage);

        if(position == show_Image.size() - 2){

            viewPager2.post(runnable);
        }

    }

    @Override
    public int getItemCount() {
        return show_Image.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder {

        ImageView img_showImage;

        public ViewModel(@NonNull  View itemView) {
            super(itemView);

            img_showImage = itemView.findViewById(R.id.img_showImage);
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            show_Image.addAll(show_Image);
            notifyDataSetChanged();
        }
    };
}
