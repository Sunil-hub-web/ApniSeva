package com.in.apniseva.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.in.apniseva.AppURL.AppUrl;
import com.in.apniseva.R;
import com.in.apniseva.SessionManager;
import com.in.apniseva.activity.SubCategoryPriceDetails;
import com.in.apniseva.activity.Subcategory;
import com.in.apniseva.activity.Subcategory_Product;
import com.in.apniseva.modelclass.SubCateGory_ModelClass;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubcategoryAdapter extends RecyclerView.Adapter<SubcategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<SubCateGory_ModelClass> subcategory;
    String category_name;
    int index;
    SessionManager sessionManager;
    String item_status;

    public SubcategoryAdapter(ArrayList<SubCateGory_ModelClass> subCategory, Subcategory subcategory_,String item_status) {

        this.context = subcategory_;
        this.subcategory = subCategory;
        this.item_status = item_status;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryproduct, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SubCateGory_ModelClass sub_category = subcategory.get(position);
        sessionManager  = new SessionManager(context);

        Picasso.with(context).load(sub_category.getImage()).into(holder.category_Image);
        holder.text_subCategoryName.setText(sub_category.getCategory_name());

        holder.btn_subCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkProduct(sub_category.getId());
                notifyDataSetChanged();

                Log.d("sunilsubcategory",sub_category.getId());

            }
        });

      /*  if (index == position) {

            holder.btn_subCategoryName.setBackgroundColor(Color.BLUE);
            holder.btn_subCategoryName.setElevation(15);
        } else {

            holder.btn_subCategoryName.setBackgroundColor(Color.WHITE);
            holder.btn_subCategoryName.setElevation(5);
        }*/
    }

    @Override
    public int getItemCount() {
        return subcategory.size();
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

    public void checkProduct(String subCategoryId) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.getCategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if (status.equals("OK")) {

                        String categoryname = jsonObject.getString("category_name");
                        String item_status = jsonObject.getString("item_status");

                        if (item_status.equals("1")) {

                            Intent intent = new Intent(context, SubCategoryPriceDetails.class);
                            intent.putExtra("sub_category", subCategoryId);
                            intent.putExtra("category_name", subCategoryId);

                            sessionManager.setSubcategoryID(subCategoryId);
                            sessionManager.setCategoryName(categoryname);

                            context.startActivity(intent);

                        } else if(item_status.equals("0")) {

                            Intent intent = new Intent(context, Subcategory_Product.class);
                            intent.putExtra("sub_category", subCategoryId);
                            intent.putExtra("category_name",categoryname);

                            sessionManager.setSubcategoryID(subCategoryId);
                            sessionManager.setCategoryName(categoryname);

                            context.startActivity(intent);

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id", subCategoryId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


}

