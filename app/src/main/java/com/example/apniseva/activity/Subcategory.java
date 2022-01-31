package com.example.apniseva.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.apniseva.SessionManager;
import com.example.apniseva.adapter.SubcategoryAdapter;
import com.example.apniseva.modelclass.SubCateGory_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Subcategory extends AppCompatActivity {

    RecyclerView recyclerSubCategory;
    GridLayoutManager gridLayoutManager;
    SubcategoryAdapter subcategoryAdapter;
    ArrayList<SubCateGory_ModelClass> subCategory = new ArrayList<>();

    TextView textName;
    String categoryid;
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcategory_product);

        sessionManager = new SessionManager(this);

        textName = findViewById(R.id.textName);
        recyclerSubCategory = findViewById(R.id.recyclerSubCategory);

        Intent intent = getIntent();
        categoryid = intent.getStringExtra("categoryId");

        categoryid = sessionManager.getCategoryId();

        showSubCateGory(categoryid);

    }

    public void showSubCateGory(String categoryId){

        ProgressDialog progressDialog = new ProgressDialog(Subcategory.this);
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
                        String result = jsonObject.getString("result");

                        textName.setText(categoryname);

                        JSONArray jsonArray_result = new JSONArray(result);

                        for(int i=0;i<jsonArray_result.length();i++){

                            JSONObject jsonObject_result = jsonArray_result.getJSONObject(i);

                            String id = jsonObject_result.getString("id");
                            String category_name = jsonObject_result.getString("category_name");
                            String image = jsonObject_result.getString("image");

                            SubCateGory_ModelClass subCateGory_modelClass = new SubCateGory_ModelClass(
                                    id,category_name,image
                            );

                            subCategory.add(subCateGory_modelClass);
                        }

                        gridLayoutManager = new GridLayoutManager(Subcategory.this,2,GridLayoutManager.VERTICAL,false);
                        subcategoryAdapter = new SubcategoryAdapter(subCategory, Subcategory.this,categoryname);
                        recyclerSubCategory.setLayoutManager(gridLayoutManager);
                        recyclerSubCategory.setHasFixedSize(true);
                        recyclerSubCategory.setAdapter(subcategoryAdapter);
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
                Toast.makeText(Subcategory.this, ""+error, Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(Subcategory.this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(Subcategory.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        showSubCateGory(categoryid);
    }
}
