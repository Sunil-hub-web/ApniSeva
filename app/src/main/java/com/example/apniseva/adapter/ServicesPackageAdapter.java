package com.example.apniseva.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apniseva.R;
import com.example.apniseva.SharedPreference;
import com.example.apniseva.activity.SubCategoryPriceDetails;
import com.example.apniseva.modelclass.CartItem;
import com.example.apniseva.modelclass.ServicesPackage_ModelClass;

import java.util.ArrayList;

public class ServicesPackageAdapter extends RecyclerView.Adapter<ServicesPackageAdapter.ViewHolder> {

    Context context;
    ArrayList<ServicesPackage_ModelClass> acservices;
    int index;
    double price = 0;
    String str_name, str_price;

    ArrayList<CartItem> servicesItem = new ArrayList<>();
    public static ArrayList<String> servicesid = new ArrayList<>();

    SharedPreference sharedPreference = new SharedPreference();

    public ServicesPackageAdapter(SubCategoryPriceDetails subCategoryPriceDetails, ArrayList<ServicesPackage_ModelClass> acPackage) {

        this.context = subCategoryPriceDetails;
        this.acservices = acPackage;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acservices, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ServicesPackage_ModelClass ac_services = acservices.get(position);

        //holder.text_Description.setText(ac_services.getServicesDescription());
        holder.text_Price.setText(ac_services.getServicesPrice());
        holder.text_ServicesName.setText(ac_services.getServicesName());

        holder.rel_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*index = position;
                notifyDataSetChanged();*/

                if (holder.rel_right.getVisibility() == View.VISIBLE) {

                    holder.rel_right.setVisibility(View.GONE);
                    holder.rel_right1.setVisibility(View.VISIBLE);

                    str_price = ac_services.getServicesPrice();

                    double d_price = Double.valueOf(str_price);

                    price = price + d_price;

                    String tot_price = String.valueOf(price);

                    SubCategoryPriceDetails.price.setText(tot_price);

                    str_name = ac_services.getServicesName();
                    str_price = ac_services.getServicesPrice();

                    CartItem cartItem = new CartItem(str_name, str_price);

                    sharedPreference.addFavorite(context, cartItem);

                    servicesItem.add(cartItem);

                    servicesid.add(ac_services.getServicesId());

                    Log.d("servicesid", servicesid.toString());

                }

            }
        });

        holder.rel_right1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*index = position;
                notifyDataSetChanged();*/

                if((holder.rel_right1.getVisibility() == View.VISIBLE)){


                    holder.rel_right.setVisibility(View.VISIBLE);
                    holder.rel_right1.setVisibility(View.GONE);

                    String str_price = ac_services.getServicesPrice();

                    double d_price = Double.valueOf(str_price);

                    String tot_price = SubCategoryPriceDetails.price.getText().toString().trim();

                    double d_totprice = Double.valueOf(tot_price);

                    double d_amount = d_totprice - d_price;

                    String str_amount = String.valueOf(d_amount);

                    SubCategoryPriceDetails.price.setText(str_amount);

                    sharedPreference.removeFavorite(context, position);

                    servicesItem.remove(position);

                    servicesid.remove(position);
                    notifyDataSetChanged();

                    Log.d("servicesid", servicesid.toString());

                }

            }
        });



       /* if(index == position){

            holder.rel_right.setBackgroundResource(R.drawable.layoutback);
            holder.rel_right.setElevation(5);
        }
        else {

            holder.rel_right.setBackgroundResource(R.drawable.layouback1);
            holder.rel_right.setElevation(5);
        }*/
    }

    @Override
    public int getItemCount() {
        return acservices.size();
    }

      public ArrayList<String> getVAs(){
        return servicesid;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_ServicesName, text_Price, text_Description;
        Button button_Services;
        RelativeLayout rel_right, rel_right1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_ServicesName = itemView.findViewById(R.id.text_ServicesName);
            text_Price = itemView.findViewById(R.id.text_Price);
            //text_Description = itemView.findViewById(R.id.text_Description);
            button_Services = itemView.findViewById(R.id.button_Services);
            rel_right = itemView.findViewById(R.id.rel_right);
            rel_right1 = itemView.findViewById(R.id.rel_right1);


        }
    }
}
