package com.in.apniseva.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.in.apniseva.R;
import com.in.apniseva.SharedPreference;
import com.in.apniseva.activity.SubCategoryPriceDetails;
import com.in.apniseva.modelclass.CartItem;
import com.in.apniseva.modelclass.ServicesPackage_ModelClass;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ServicesPackageAdapter extends RecyclerView.Adapter<ServicesPackageAdapter.ViewHolder> {

    Context context;
    ArrayList<ServicesPackage_ModelClass> acservices;
    int index;
    double price = 0.00;
    String str_name, str_price;

    ArrayList<CartItem> servicesItem = new ArrayList<>();
    public static ArrayList<String> servicesid = new ArrayList<>() ;

    SharedPreference sharedPreference = new SharedPreference();

    boolean value = false;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ServicesPackage_ModelClass ac_services = acservices.get(position);

        sharedPreference.clearDate(context);
        servicesid.clear();

        //holder.text_Description.setText(ac_services.getServicesDescription());
        holder.text_Price.setText(ac_services.getServicesPrice());
        holder.text_ServicesName.setText(ac_services.getServicesName());


       /* holder.button_Services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //index = position;

                try {

                    if (holder.rel_right1.getVisibility() == View.VISIBLE) {

                        holder.rel_right.setVisibility(View.VISIBLE);
                        holder.rel_right1.setVisibility(View.GONE);



                    } else if(holder.rel_right.getVisibility() == View.VISIBLE) {

                            holder.rel_right.setVisibility(View.GONE);
                            holder.rel_right1.setVisibility(View.VISIBLE);


                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });*/

        holder.button_Services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                index = holder.getAdapterPosition();

                CartItem cartItem;

                String buttonClick = "1";

                try{

                    if(holder.rel_rightGray.getVisibility() == View.VISIBLE){

                        holder.rel_rightGray.setVisibility(View.GONE);
                        holder.rel_rightGreen.setVisibility(View.VISIBLE);


                        Log.d("servicesid", servicesid.toString());

                        //holder.rel_rightGreen.setBackgroundColor(ac_services.isSelected() ? ContextCompat.getColor(context, R.color.button2) : ContextCompat.getColor(context, R.color.button1));

                        str_price = ac_services.getServicesPrice();

                        str_name = ac_services.getServicesName();
                        str_price = ac_services.getServicesPrice();

                        double d_price = Double.valueOf(str_price);

                        price = price + d_price;

                        String tot_price = String.valueOf(price);

                        SubCategoryPriceDetails.price.setText(tot_price+"0");

                        cartItem = new CartItem(str_name, str_price);

                      /*  sharedPreference.addFavorite(context, cartItem);*/

                        servicesItem.add(cartItem);
                        servicesid.add(ac_services.getServicesId());

                        Log.d("showaraylist",servicesid.toString());

                    }else{

                        //ac_services.setSelected(!ac_services.isSelected());
                        //holder.rel_rightGreen.setBackgroundColor(ac_services.isSelected() ?
                        // ContextCompat.getColor(context, R.color.button2) : ContextCompat.getColor(context, R.color.button1));

                        holder.rel_rightGreen.setVisibility(View.GONE);
                        holder.rel_rightGray.setVisibility(View.VISIBLE);

                        str_price = ac_services.getServicesPrice();

                        double d_price = Double.valueOf(str_price);

                        String tot_price = SubCategoryPriceDetails.price.getText().toString().trim();

                        double d_totprice = Double.valueOf(tot_price);

                        price = d_totprice - d_price;

                        String str_totprice = String.valueOf(price);

                        SubCategoryPriceDetails.price.setText(str_totprice+"0");

                        servicesid.remove(index);
                        servicesItem.remove(index);
                        //sharedPreference.removeFavorite(context,index);

                        Log.d("removearaylist",servicesid.toString());

                        //notifyItemRemoved(index);


                    }

                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return acservices.size();
    }

    public ArrayList<String> getVAs() {
        return servicesid;
    }
    public ArrayList<CartItem> getData() {
        return servicesItem;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_ServicesName, text_Price, text_Description;
        Button button_Services;
        RelativeLayout rel_rightGreen, rel_rightGray;
        //CardView cardView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_ServicesName = itemView.findViewById(R.id.text_ServicesName);
            text_Price = itemView.findViewById(R.id.text_Price);
            //text_Description = itemView.findViewById(R.id.text_Description);
            button_Services = itemView.findViewById(R.id.button_Services);
            //checkBox = itemView.findViewById(R.id.radio);
            rel_rightGreen = itemView.findViewById(R.id.rel_rightGreen);
            rel_rightGray = itemView.findViewById(R.id.rel_rightGray);


        }
    }


}
