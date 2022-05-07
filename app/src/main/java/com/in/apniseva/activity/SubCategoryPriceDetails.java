package com.in.apniseva.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.in.apniseva.SharedPreference;
import com.in.apniseva.adapter.ServicesPackageAdapter;
import com.in.apniseva.modelclass.ServicesPackage_ModelClass;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubCategoryPriceDetails extends AppCompatActivity {

    ServicesPackageAdapter servicesPackageAdapter;
    RecyclerView acServiceRecycler;
    LinearLayoutManager linearLayoutManager;
    ArrayList<ServicesPackage_ModelClass> acPackage = new ArrayList<>();
    ArrayList<String> servicesId = new ArrayList<>();

    Button btn_proceed;

    public static TextView textName,looking,price;
    String subcategoryid,category_name;
    ImageView image_back;

    SharedPreference sharedPreference = new SharedPreference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcategorypricedetails);

        acServiceRecycler = findViewById(R.id.acServiceRecycler);
        btn_proceed = findViewById(R.id.btn_proceed);
        textName = findViewById(R.id.textName);
        looking = findViewById(R.id.looking);
        price = findViewById(R.id.price);
        image_back = findViewById(R.id.image_back);

        Intent intent = getIntent();
        subcategoryid = intent.getStringExtra("sub_category");
        category_name = intent.getStringExtra("category_name");
        textName.setText(category_name);



        showSubCateGory(subcategoryid);

        SharedPreference sharedPreference = new SharedPreference();
        sharedPreference.clearDate(SubCategoryPriceDetails.this);

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(price.getText().toString().trim().equals("0.0")){

                    Toast.makeText(SubCategoryPriceDetails.this, "You must select one product", Toast.LENGTH_SHORT).show();

                }else{

                    Intent intent = new Intent(SubCategoryPriceDetails.this, Booking.class);
                    intent.putExtra("total_price",price.getText().toString().trim());
                    startActivity(intent);

                    servicesId = servicesPackageAdapter.getVAs();

                    Log.d("servicesId",servicesId.toString());

                    SharedPreferences sharedPreferences_services = getSharedPreferences("shared preferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences_services.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(servicesId);
                    editor.putString("task list", json);
                    editor.apply();

                }
            }
        });

    }

    public void showSubCateGory(String categoryId){

        acPackage.clear();

        ProgressDialog progressDialog = new ProgressDialog(SubCategoryPriceDetails.this);
        progressDialog.setMessage("Retrive Data Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.getCategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if(status.equals("OK")){

                        String categoryname = jsonObject.getString("category_name");
                        String item_status = jsonObject.getString("item_status");

                        looking.setText(categoryname);

                        if(item_status.equals("0")){ }else{

                            String result = jsonObject.getString("result");

                            JSONArray jsonArray_result = new JSONArray(result);

                            for(int i=0;i<jsonArray_result.length();i++){

                                JSONObject jsonObject_result = jsonArray_result.getJSONObject(i);

                                String id = jsonObject_result.getString("id");
                                String category_id = jsonObject_result.getString("category_id");
                                String title = jsonObject_result.getString("title");
                                String price = jsonObject_result.getString("price");
                                String description = jsonObject_result.getString("description");

                                ServicesPackage_ModelClass subCateGory_modelClass = new ServicesPackage_ModelClass(
                                        id,category_id,title,price,description
                                );

                                acPackage.add(subCateGory_modelClass);
                            }

                            linearLayoutManager = new LinearLayoutManager(SubCategoryPriceDetails.this,LinearLayoutManager.VERTICAL,false);
                            servicesPackageAdapter = new ServicesPackageAdapter(SubCategoryPriceDetails.this,acPackage);
                            acServiceRecycler.setLayoutManager(linearLayoutManager);
                            acServiceRecycler.setHasFixedSize(true);
                            acServiceRecycler.setAdapter(servicesPackageAdapter);

                            sharedPreference.clearDate(SubCategoryPriceDetails.this);
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
                Toast.makeText(SubCategoryPriceDetails.this, " Facing Technical issues, Try again! ", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("id",categoryId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(SubCategoryPriceDetails.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(SubCategoryPriceDetails.this,Subcategory.class);
        startActivity(intent);
    }
}
