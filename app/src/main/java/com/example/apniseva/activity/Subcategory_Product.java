package com.example.apniseva.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.apniseva.AppURL.AppUrl;
import com.example.apniseva.R;
import com.example.apniseva.adapter.ServicesPackageAdapter;
import com.example.apniseva.adapter.SubCategoryProductAdapter;
import com.example.apniseva.modelclass.ServicesPackage_ModelClass;
import com.example.apniseva.modelclass.SubCateGory_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Subcategory_Product extends AppCompatActivity {


    RecyclerView recyclerSubCategoryProduct;
    SubCategoryProductAdapter subCategoryProductAdapter;
    GridLayoutManager linearLayoutManager;
    ArrayList<SubCateGory_ModelClass> subcategory = new ArrayList<>();

    Button btn_proceed;

    TextView textName, looking;
    String subcategoryid, category_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcategoryproduct);

        recyclerSubCategoryProduct = findViewById(R.id.recyclerSubCategoryProduct);
        btn_proceed = findViewById(R.id.btn_proceed);
        textName = findViewById(R.id.textName);
        looking = findViewById(R.id.looking);

        Intent intent = getIntent();
        subcategoryid = intent.getStringExtra("sub_category");
        category_name = intent.getStringExtra("category_name");
        textName.setText(category_name);

        showSubCateGory(subcategoryid);



       /* button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Subcategory_Product.this, SubCategoryPriceDetails.class);
                startActivity(intent);

                rel_splitac.setBackgroundColor(getResources().getColor(R.color.Blue));
                rel_windowac.setBackgroundColor(getResources().getColor(R.color.white));

                //lLayout.setBackgroundColor(Color.parseColor("#000000"));

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Subcategory_Product.this, SubCategoryPriceDetails.class);
                startActivity(intent);

                rel_splitac.setBackgroundColor(getResources().getColor(R.color.white));
                rel_windowac.setBackgroundColor(getResources().getColor(R.color.Blue));

                //lLayout.setBackgroundColor(Color.parseColor("#000000"));

            }
        });*/

    }

    public void showSubCateGory(String categoryId) {

        ProgressDialog progressDialog = new ProgressDialog(Subcategory_Product.this);
        progressDialog.setMessage("Retrive Data Please wait...");
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

                        looking.setText(categoryname);

                        String result = jsonObject.getString("result");

                        JSONArray jsonArray_result = new JSONArray(result);

                        for (int i = 0; i < jsonArray_result.length(); i++) {

                            JSONObject jsonObject_result = jsonArray_result.getJSONObject(i);

                            String id = jsonObject_result.getString("id");
                            String category_name = jsonObject_result.getString("category_name");
                            String image = jsonObject_result.getString("image");

                            SubCateGory_ModelClass subCateGory_modelClass = new SubCateGory_ModelClass(
                                    id, category_name, image
                            );

                            subcategory.add(subCateGory_modelClass);
                        }

                        linearLayoutManager = new GridLayoutManager(Subcategory_Product.this, 2, GridLayoutManager.VERTICAL, false);
                        subCategoryProductAdapter = new SubCategoryProductAdapter(Subcategory_Product.this, subcategory, categoryname);
                        recyclerSubCategoryProduct.setLayoutManager(linearLayoutManager);
                        recyclerSubCategoryProduct.setHasFixedSize(true);
                        recyclerSubCategoryProduct.setAdapter(subCategoryProductAdapter);
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
                Toast.makeText(Subcategory_Product.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id", categoryId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(Subcategory_Product.this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();

        Intent intent = new Intent(Subcategory_Product.this, Subcategory.class);
        startActivity(intent);
    }
}
