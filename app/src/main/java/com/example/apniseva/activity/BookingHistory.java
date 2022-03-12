package com.example.apniseva.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.apniseva.SharedPreference;
import com.example.apniseva.adapter.OrderDetails_Adapter;
import com.example.apniseva.adapter.OrderItemAdapter;
import com.example.apniseva.modelclass.CartItem;
import com.example.apniseva.modelclass.OrderItem_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingHistory extends AppCompatActivity {

    ImageView image_back;
    TextView text_name,text_MobileNo,text_city,text_address_details,text_orderConfirmed, bookingid,
            subTotalPrice,payonvisite,TotalPrice;
    RecyclerView recyclerBookingHistory;
    ArrayList<CartItem> orderDetails = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    OrderDetails_Adapter orderDetailsAdapter;
    SharedPreference sharedPreference;
    String booking_id;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    ArrayList<OrderItem_ModelClass> orderitem;
    String booking = "BookingDetails";

    View view_order_BookingConfirm, view_order_TechnicianAllocat, view_order_WorkInProgress, view_order_WorkCompleted,
            view_order_Payment;
    RelativeLayout BookingConfirm, TechnicianAllocat;
    LinearLayout WorkInProgress, WorkCompleted, Payment;
    ProgressBar placed_divider1, placed_divider, placed_divider2, placed_divider3;
    private int i = 0;
    private Handler hdlr = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        image_back = findViewById(R.id.image_back);
        text_name = findViewById(R.id.text_name);
        text_MobileNo = findViewById(R.id.text_MobileNo);
        text_city = findViewById(R.id.text_city);
        text_address_details = findViewById(R.id.text_address_details);
        text_orderConfirmed = findViewById(R.id.text_orderConfirmed);
        bookingid = findViewById(R.id.bookingid);
        subTotalPrice = findViewById(R.id.subTotalPrice);
        payonvisite = findViewById(R.id.payonvisite);
        TotalPrice = findViewById(R.id.TotalPrice);
        recyclerBookingHistory = findViewById(R.id.recyclerBookingHistory);

        Payment = findViewById(R.id.Payment);
        BookingConfirm = findViewById(R.id.BookingConfirm);
        WorkInProgress = findViewById(R.id.WorkInProgress);
        WorkCompleted = findViewById(R.id.WorkCompleted);
        TechnicianAllocat = findViewById(R.id.TechnicianAllocat);
        placed_divider1 = findViewById(R.id.placed_divider1);
        view_order_WorkInProgress = findViewById(R.id.view_order_WorkInProgress);
        placed_divider2 = findViewById(R.id.placed_divider2);
        view_order_WorkCompleted = findViewById(R.id.view_order_WorkCompleted);
        placed_divider3 = findViewById(R.id.placed_divider3);
        view_order_Payment = findViewById(R.id.view_order_Payment);
        view_order_TechnicianAllocat = findViewById(R.id.view_order_TechnicianAllocat);
        placed_divider = findViewById(R.id.placed_divider);
        view_order_BookingConfirm = findViewById(R.id.view_order_BookingConfirm);

        placed_divider.setProgress(100);
        placed_divider1.setProgress(100);
        placed_divider2.setProgress(100);
        placed_divider3.setProgress(100);


        Intent intent = getIntent();
        booking_id = intent.getStringExtra("booking_id");

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BookingHistory.this,BookingDetails.class);
                startActivity(intent);
            }
        });

        orderDetails(booking_id);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(BookingHistory.this,BookingDetails.class);
        startActivity(intent);

        //finish();
    }

    public void orderDetails(String orderId) {

        ProgressDialog dialog = new ProgressDialog(BookingHistory.this);
        dialog.setMessage("Show Booking History");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.bookingDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("OK")) {

                        String order_id = jsonObject.getString("order_id");
                        String price = jsonObject.getString("price");
                        String igst = jsonObject.getString("igst");
                        String cgst = jsonObject.getString("cgst");
                        String subtotal = jsonObject.getString("subtotal");
                        String name = jsonObject.getString("name");
                        String address = jsonObject.getString("address");
                        String address1 = jsonObject.getString("address1");
                        String mobile = jsonObject.getString("mobile");
                        String create_user_id = jsonObject.getString("create_user_id");
                        String work_status = jsonObject.getString("work_status");
                        String book_pay_status = jsonObject.getString("book_pay_status");

                        orderitem = new ArrayList<>();

                        String Order_item = jsonObject.getString("Order_item");

                        JSONArray jsonArray_item = new JSONArray(Order_item);

                        for (int j = 0; j < jsonArray_item.length(); j++) {

                            JSONObject jsonObject_item = jsonArray_item.getJSONObject(j);

                            String categoryname = jsonObject_item.getString("categoryname");
                            String Product = jsonObject_item.getString("Product");
                            String Amount = jsonObject_item.getString("Amount");

                            OrderItem_ModelClass orderItem_modelClass = new OrderItem_ModelClass(
                                    categoryname, Product, Amount
                            );

                            orderitem.add(orderItem_modelClass);

                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookingHistory.this, LinearLayoutManager.VERTICAL, false);
                        OrderItemAdapter orderItemAdapter = new OrderItemAdapter(BookingHistory.this, orderitem, booking);
                        recyclerBookingHistory.setLayoutManager(linearLayoutManager);
                        recyclerBookingHistory.setHasFixedSize(true);
                        recyclerBookingHistory.setAdapter(orderItemAdapter);

                        text_name.setText(name);
                        text_MobileNo.setText(mobile);
                        text_city.setText(address);
                        text_address_details.setText(address1);
                        bookingid.setText("#" + order_id);
                        text_orderConfirmed.setText("Order" + " " + work_status);
                        TotalPrice.setText(price);
                        subTotalPrice.setText(subtotal);

                        double int_price = Double.valueOf(price);
                        double int_total = Double.valueOf(subtotal);
                        double int_amount = int_price - int_total;
                        String payOnVisit = df.format(int_amount);

                        payonvisite.setText(payOnVisit);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
                error.printStackTrace();

                Toast.makeText(BookingHistory.this, "" + error, Toast.LENGTH_SHORT).show();

                Log.d("error", error.toString());

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("order_id", orderId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BookingHistory.this);
        requestQueue.add(stringRequest);
    }
}