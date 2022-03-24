package com.in.apniseva.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.in.apniseva.SharedPrefManager;
import com.in.apniseva.adapter.BookingDetailsAdapter;
import com.in.apniseva.modelclass.BookingDetails_ModelClass;
import com.in.apniseva.modelclass.OrderItem_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingDetails extends AppCompatActivity {

    RecyclerView recyclerBookingDetails;
    ArrayList<BookingDetails_ModelClass> bookingDetails;
    LinearLayoutManager linearLayoutManager;
    BookingDetailsAdapter bookingDetailsAdapter;
    ArrayList<OrderItem_ModelClass> orderitem;
    RelativeLayout rel_product;

    ImageView img_back;
    String categoryname;

    String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookingdetails);


        recyclerBookingDetails = findViewById(R.id.recyclerBookingDetails);
        rel_product = findViewById(R.id.rel_product);
        img_back = findViewById(R.id.img_back);

        userId = SharedPrefManager.getInstance(BookingDetails.this).getUser().getUserid();

        bookingDetails(userId);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BookingDetails.this,UserDetails.class);
                startActivity(intent);
            }
        });
    }

    public void bookingDetails(String userId){

        ProgressDialog progressDialog = new ProgressDialog(BookingDetails.this);
        progressDialog.setMessage("Show Booking Details");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.bookingHistory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    bookingDetails = new ArrayList<>();

                    if(status.equals("OK")){

                        String All_Order = jsonObject.getString("All_Order");

                        JSONArray jsonArray_All_Order = new JSONArray(All_Order);

                        if(jsonArray_All_Order.length() == 0){

                            rel_product.setVisibility(View.VISIBLE);

                        }else{

                            for(int i=0;i<jsonArray_All_Order.length();i++){

                                JSONObject jsonObject_AllOrder = jsonArray_All_Order.getJSONObject(i);

                                String order_id = jsonObject_AllOrder.getString("order_id");
                                //String price = jsonObject_AllOrder.getString("price");
                                String igst = jsonObject_AllOrder.getString("igst");
                                String cgst = jsonObject_AllOrder.getString("cgst");
                                String subtotal = jsonObject_AllOrder.getString("subtotal");
                                String name = jsonObject_AllOrder.getString("name");
                                String address = jsonObject_AllOrder.getString("address");
                                String address1 = jsonObject_AllOrder.getString("address1");
                                String mobile = jsonObject_AllOrder.getString("mobile");
                                String create_user_id = jsonObject_AllOrder.getString("create_user_id");
                                String work_status = jsonObject_AllOrder.getString("work_status");
                                String book_pay_status = jsonObject_AllOrder.getString("book_pay_status");

                                orderitem = new ArrayList<>();

                                String Order_Item = jsonObject_AllOrder.getString("Order_Item");

                                JSONArray jsonArray_item = new JSONArray(Order_Item);

                                for(int j=0;j<jsonArray_item.length();j++){

                                    JSONObject jsonObject_item = jsonArray_item.getJSONObject(j);

                                    categoryname = jsonObject_item.getString("categoryname");
                                    String Product = jsonObject_item.getString("Product");
                                    String Amount = jsonObject_item.getString("Amount");

                                    OrderItem_ModelClass orderItem_modelClass = new OrderItem_ModelClass(
                                            categoryname,Product,Amount
                                    );

                                    orderitem.add(orderItem_modelClass);

                                }


                                BookingDetails_ModelClass bookingDetails_modelClass = new BookingDetails_ModelClass(
                                        order_id,"",subtotal,name,address,address1,mobile,create_user_id,work_status,book_pay_status,orderitem,categoryname
                                );

                                bookingDetails.add(bookingDetails_modelClass);
                                Log.d("bookingDetails",bookingDetails.toString());
                            }
                        }

                        if(bookingDetails.size()==0){

                            rel_product.setVisibility(View.VISIBLE);

                        }else{

                            linearLayoutManager = new LinearLayoutManager(BookingDetails.this,LinearLayoutManager.VERTICAL,false);
                            linearLayoutManager.setReverseLayout(true);
                            linearLayoutManager.setStackFromEnd(true);
                            bookingDetailsAdapter = new BookingDetailsAdapter(BookingDetails.this,bookingDetails);
                            recyclerBookingDetails.setLayoutManager(linearLayoutManager);
                            recyclerBookingDetails.setHasFixedSize(true);
                            recyclerBookingDetails.setAdapter(bookingDetailsAdapter);
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

                Toast.makeText(BookingDetails.this, "" + error, Toast.LENGTH_SHORT).show();

                Log.d("error", error.toString());

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("user_id",userId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BookingDetails.this);
        requestQueue.add(stringRequest);

    }

}
