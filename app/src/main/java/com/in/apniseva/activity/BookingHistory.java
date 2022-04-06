package com.in.apniseva.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.google.gson.JsonArray;
import com.in.apniseva.AppURL.AppUrl;
import com.in.apniseva.R;
import com.in.apniseva.SharedPreference;
import com.in.apniseva.adapter.OrderDetails_Adapter;
import com.in.apniseva.adapter.OrderItemAdapter;
import com.in.apniseva.modelclass.CartItem;
import com.in.apniseva.modelclass.OrderItem_ModelClass;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingHistory extends AppCompatActivity {

    ImageView image_back;
    TextView text_name,text_MobileNo,text_city,text_address_details,text_orderConfirmed, bookingid,
            subTotalPrice,payonvisite,TotalPrice,text_Techniciansid,text_designation,
            text_TechnicianPin,text_ShowWorkDetails;
    RecyclerView recyclerBookingHistory;
    ArrayList<CartItem> orderDetails = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    OrderDetails_Adapter orderDetailsAdapter;
    SharedPreference sharedPreference;
    String booking_id;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    ArrayList<OrderItem_ModelClass> orderitem;
    String booking = "BookingDetails",techmobile;

    View view_order_BookingConfirm, view_order_TechnicianAllocat, view_order_WorkInProgress, view_order_WorkCompleted,
            view_order_Payment;
    RelativeLayout BookingConfirm, TechnicianAllocat;
    LinearLayout WorkInProgress, WorkCompleted, Payment;
    ProgressBar placed_divider1, placed_divider, placed_divider2, placed_divider3;
    private int i = 0;
    private Handler hdlr = new Handler();
    private static final int REQUEST_PHONE_CALL = 1;
    SwipeRefreshLayout swipeRefreshLayout;
    OrderItemAdapter orderItemAdapter;
    CircleImageView profile_image;


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
        profile_image = findViewById(R.id.profile_image);

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
        text_Techniciansid = findViewById(R.id.text_Techniciansid);
        text_designation = findViewById(R.id.text_designation);
        text_TechnicianPin = findViewById(R.id.text_TechnicianPin);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        text_ShowWorkDetails = findViewById(R.id.text_ShowWorkDetails);

     /*   placed_divider.setProgress(100);
        placed_divider1.setProgress(100);
        placed_divider2.setProgress(100);
        placed_divider3.setProgress(100);*/


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

        text_designation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makePhoneCall();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                orderDetails(booking_id);

                //orderItemAdapter.notifyDataSetChanged();

                swipeRefreshLayout.setRefreshing(false);


            }
        });

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
                        String work_details = jsonObject.getString("work_details");
                        String book_pay_status = jsonObject.getString("book_pay_status");
                        String verification_pin = jsonObject.getString("verification_pin");
                        String pin_verification_status = jsonObject.getString("pin_verification_status");
                        String payment_status = jsonObject.getString("payment_status");
                        String pay_type = jsonObject.getString("pay_type");
                        String technician_details = jsonObject.getString("technician_details");

                        text_TechnicianPin.setText(verification_pin);
                        text_ShowWorkDetails.setText(work_details);

                        JSONArray jsonArray_technician_details = new JSONArray(technician_details);

                        if (verification_pin.equals("null")) {

                            bookingconfirm();

                            for(int i= 0;i<jsonArray_technician_details.length();i++){

                                JSONObject jsonObject_technician = jsonArray_technician_details.getJSONObject(i);

                                String id = jsonObject_technician.getString("id");
                                String techname = jsonObject_technician.getString("name");
                                String email = jsonObject_technician.getString("em  ail");
                                techmobile = jsonObject_technician.getString("mobile");
                                String profile_img = jsonObject_technician.getString("profile_img");
                                String category = jsonObject_technician.getString("category");
                                String subcategory = jsonObject_technician.getString("subcategory");

                                text_Techniciansid.setText(techname);
                                text_designation.setText(category);
                                Picasso.with(BookingHistory.this).load(profile_img).placeholder(R.drawable.profileimage).into(profile_image);

                            }

                        }else{

                            if(pin_verification_status.equals("0")){

                                tecnicianAllocate();

                                for(int i= 0;i<jsonArray_technician_details.length();i++){

                                    JSONObject jsonObject_technician = jsonArray_technician_details.getJSONObject(i);

                                    String id = jsonObject_technician.getString("id");
                                    String techname = jsonObject_technician.getString("name");
                                    String email = jsonObject_technician.getString("em  ail");
                                    techmobile = jsonObject_technician.getString("mobile");
                                    String profile_img = jsonObject_technician.getString("profile_img");
                                    String category = jsonObject_technician.getString("category");
                                    String subcategory = jsonObject_technician.getString("subcategory");

                                    text_Techniciansid.setText(techname);
                                    text_designation.setText(category);
                                    Picasso.with(BookingHistory.this).load(profile_img).placeholder(R.drawable.profileimage).into(profile_image);

                                }

                            }else{

                                if(work_status.equals("payment_done")){

                                    workingComplete();

                                    for(int i= 0 ;i<jsonArray_technician_details.length();i++){

                                        JSONObject jsonObject_technician = jsonArray_technician_details.getJSONObject(i);

                                        String id = jsonObject_technician.getString("id");
                                        String techname = jsonObject_technician.getString("name");
                                        String email = jsonObject_technician.getString("email");
                                        techmobile = jsonObject_technician.getString("mobile");
                                        String profile_img = jsonObject_technician.getString("profile_img");
                                        String category = jsonObject_technician.getString("category");
                                        String subcategory = jsonObject_technician.getString("subcategory");

                                        text_Techniciansid.setText(techname);
                                        text_designation.setText(category);
                                        Picasso.with(BookingHistory.this).load(profile_img).placeholder(R.drawable.profileimage).into(profile_image);

                                    }
                                    Log.d("pin_verification_status",pin_verification_status);



                                }else{

                                    workInProgress();

                                    for(int i= 0;i<jsonArray_technician_details.length();i++){

                                        JSONObject jsonObject_technician = jsonArray_technician_details.getJSONObject(i);

                                        String id = jsonObject_technician.getString("id");
                                        String techname = jsonObject_technician.getString("name");
                                        String email = jsonObject_technician.getString("email");
                                        techmobile = jsonObject_technician.getString("mobile");
                                        String profile_img = jsonObject_technician.getString("profile_img");
                                        String category = jsonObject_technician.getString("category");
                                        String subcategory = jsonObject_technician.getString("subcategory");

                                        text_Techniciansid.setText(techname);
                                        text_designation.setText(category);
                                        Picasso.with(BookingHistory.this).load(profile_img).placeholder(R.drawable.profileimage).into(profile_image);

                                    }

                                    Log.d("pin_verification_status",pin_verification_status);
                                }

                            }

                        }

                        orderitem = new ArrayList<>();

                        String Order_item = jsonObject.getString("Order_item");

                        JSONArray jsonArray_item = new JSONArray(Order_item);

                        for (int j = 0; j < jsonArray_item.length(); j++) {

                            JSONObject jsonObject_item = jsonArray_item.getJSONObject(j);

                            String categoryname = jsonObject_item.getString("categoryname");
                            String subcategoryname = jsonObject_item.getString("subcategoryname");
                            String Product = jsonObject_item.getString("Product");
                            String Amount = jsonObject_item.getString("Amount");

                            OrderItem_ModelClass orderItem_modelClass = new OrderItem_ModelClass(
                                    categoryname, Product, Amount,subcategoryname
                            );

                            orderitem.add(orderItem_modelClass);

                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookingHistory.this, LinearLayoutManager.VERTICAL, false);
                        orderItemAdapter = new OrderItemAdapter(BookingHistory.this, orderitem, booking);
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

    private void makePhoneCall() {
        // String number = items;
        String number = "tel:"+ techmobile;
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(BookingHistory.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(BookingHistory.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(BookingHistory.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                makePhoneCall();

            } else {

                Toast.makeText(BookingHistory.this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void bookingconfirm(){

        i = placed_divider.getProgress();
        new Thread(new Runnable() {
            public void run() {
                while (i < 100) {
                    i += 5;
                    // Update the progress bar and display the current value in text view
                    hdlr.post(new Runnable() {
                        public void run() {

                            placed_divider.setProgress(i);

                            if (i == 100) {

                                Payment.setVisibility(View.INVISIBLE);
                                WorkInProgress.setVisibility(View.INVISIBLE);
                                WorkCompleted.setVisibility(View.INVISIBLE);
                                TechnicianAllocat.setVisibility(View.INVISIBLE);
                                placed_divider1.setVisibility(View.INVISIBLE);
                                view_order_WorkInProgress.setVisibility(View.INVISIBLE);
                                placed_divider2.setVisibility(View.INVISIBLE);
                                view_order_WorkCompleted.setVisibility(View.INVISIBLE);
                                placed_divider3.setVisibility(View.INVISIBLE);
                                view_order_Payment.setVisibility(View.INVISIBLE);
                                view_order_TechnicianAllocat.setVisibility(View.INVISIBLE);
                                placed_divider.setVisibility(View.VISIBLE);

                                placed_divider.setMax(100); // 100 maximum value for the progress value
                                placed_divider1.setProgress(20); // 50 default progress value for the progress bar

                            }
                        }
                    });
                    try {
                        // Sleep for 100 milliseconds to show the progress slowly.
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void tecnicianAllocate(){

        i = placed_divider1.getProgress();
        new Thread(new Runnable() {
            public void run() {
                while (i < 100) {
                    i += 5;
                    // Update the progress bar and display the current value in text view
                    hdlr.post(new Runnable() {
                        public void run() {

                            placed_divider1.setProgress(i);

                            if (i == 100) {

                                Payment.setVisibility(View.INVISIBLE);
                                WorkInProgress.setVisibility(View.INVISIBLE);
                                WorkCompleted.setVisibility(View.INVISIBLE);
                                TechnicianAllocat.setVisibility(View.VISIBLE);
                                placed_divider1.setVisibility(View.VISIBLE);
                                view_order_WorkInProgress.setVisibility(View.INVISIBLE);
                                placed_divider2.setVisibility(View.INVISIBLE);
                                view_order_WorkCompleted.setVisibility(View.INVISIBLE);
                                placed_divider3.setVisibility(View.INVISIBLE);
                                view_order_Payment.setVisibility(View.INVISIBLE);
                                view_order_TechnicianAllocat.setVisibility(View.VISIBLE);
                                placed_divider.setVisibility(View.VISIBLE);

                                placed_divider.setMax(100); // 100 maximum value for the progress value
                                placed_divider.setProgress(100); // 50 default progress value for the progress bar
                                placed_divider2.setProgress(20); // 50 default progress value for the progress bar

                            }
                        }
                    });
                    try {
                        // Sleep for 100 milliseconds to show the progress slowly.
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void workInProgress(){

        i = placed_divider2.getProgress();
        new Thread(new Runnable() {
            public void run() {
                while (i < 100) {
                    i += 5;
                    // Update the progress bar and display the current value in text view
                    hdlr.post(new Runnable() {
                        public void run() {

                            placed_divider2.setProgress(i);

                            if (i == 100) {

                                Payment.setVisibility(View.INVISIBLE);
                                WorkInProgress.setVisibility(View.VISIBLE);
                                WorkCompleted.setVisibility(View.INVISIBLE);
                                TechnicianAllocat.setVisibility(View.VISIBLE);
                                placed_divider1.setVisibility(View.VISIBLE);
                                view_order_WorkInProgress.setVisibility(View.VISIBLE);
                                placed_divider2.setVisibility(View.VISIBLE);
                                view_order_WorkCompleted.setVisibility(View.INVISIBLE);
                                placed_divider3.setVisibility(View.INVISIBLE);
                                view_order_Payment.setVisibility(View.INVISIBLE);
                                view_order_TechnicianAllocat.setVisibility(View.VISIBLE);
                                placed_divider.setVisibility(View.VISIBLE);

                                placed_divider.setMax(100); // 100 maximum value for the progress value
                                placed_divider.setProgress(100); // 50 default progress value for the progress bar

                                placed_divider1.setProgress(100); // 50 default progress value for the progress bar
                                placed_divider3.setProgress(20); // 50 default progress value for the progress bar
                            }
                        }
                    });
                    try {
                        // Sleep for 100 milliseconds to show the progress slowly.
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void workingComplete(){

        i = placed_divider3.getProgress();
        new Thread(new Runnable() {
            public void run() {
                while (i < 100) {
                    i += 5;
                    // Update the progress bar and display the current value in text view
                    hdlr.post(new Runnable() {
                        public void run() {

                            placed_divider3.setProgress(i);

                            if (i == 100) {

                                Payment.setVisibility(View.VISIBLE);
                                WorkInProgress.setVisibility(View.VISIBLE);
                                WorkCompleted.setVisibility(View.VISIBLE);
                                TechnicianAllocat.setVisibility(View.VISIBLE);
                                placed_divider1.setVisibility(View.VISIBLE);
                                view_order_WorkInProgress.setVisibility(View.VISIBLE);
                                placed_divider2.setVisibility(View.VISIBLE);
                                view_order_WorkCompleted.setVisibility(View.VISIBLE);
                                placed_divider3.setVisibility(View.VISIBLE);
                                view_order_Payment.setVisibility(View.VISIBLE);
                                view_order_TechnicianAllocat.setVisibility(View.VISIBLE);
                                placed_divider.setVisibility(View.VISIBLE);

                                placed_divider.setMax(100); // 100 maximum value for the progress value
                                placed_divider.setProgress(100); // 50 default progress value for the progress bar

                                placed_divider1.setProgress(100); // 50 default progress value for the progress bar
                                placed_divider2.setProgress(100); // 50 default progress value for the progress bar
                            }
                        }
                    });
                    try {
                        // Sleep for 100 milliseconds to show the progress slowly.
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}