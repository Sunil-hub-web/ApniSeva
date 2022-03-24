package com.in.apniseva.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.in.apniseva.R;
import com.in.apniseva.activity.SubCategoryPriceDetails;
import com.in.apniseva.activity.Subcategory_Product;
import com.in.apniseva.modelclass.SubCateGory_ModelClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubCategoryProductAdapter extends RecyclerView.Adapter<SubCategoryProductAdapter.ViewHolder> {

    Context context;
    ArrayList<SubCateGory_ModelClass> subcategoryproduct;
    String category_name;
    int index;

    public SubCategoryProductAdapter(Subcategory_Product subcategory_product, ArrayList<SubCateGory_ModelClass> subcategory, String categoryname) {

        this.context = subcategory_product;
        this.subcategoryproduct = subcategory;
        this.category_name = categoryname;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryproduct, parent, false);
        return new SubCategoryProductAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SubCateGory_ModelClass sub_category = subcategoryproduct.get(position);

        Picasso.with(context).load(sub_category.getImage()).into(holder.category_Image);
        holder.text_subCategoryName.setText(sub_category.getCategory_name());

        holder.btn_subCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                index = position;
                notifyDataSetChanged();

                Intent intent = new Intent(context, SubCategoryPriceDetails.class);
                intent.putExtra("sub_category", sub_category.getId());
                intent.putExtra("category_name", category_name);
                context.startActivity(intent);

            }
        });

        /*if (index == position) {

            holder.btn_subCategoryName.setBackgroundColor(Color.BLUE);
            holder.btn_subCategoryName.setElevation(15);
        } else {

            holder.btn_subCategoryName.setBackgroundColor(Color.WHITE);
            holder.btn_subCategoryName.setElevation(5);
        }*/

    }

    @Override
    public int getItemCount() {
        return subcategoryproduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_subCategoryName;
        ImageView category_Image;
        Button btn_subCategoryName;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_subCategoryName = itemView.findViewById(R.id.text_subCategoryName);
            category_Image = itemView.findViewById(R.id.category_Image);
            btn_subCategoryName = itemView.findViewById(R.id.btn_subCategoryName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
