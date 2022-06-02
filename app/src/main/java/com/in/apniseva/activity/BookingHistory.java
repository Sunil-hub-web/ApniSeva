package com.in.apniseva.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.JsonArray;
import com.in.apniseva.AppURL.AppUrl;
import com.in.apniseva.R;
import com.in.apniseva.SharedPrefManager;
import com.in.apniseva.SharedPreference;
import com.in.apniseva.adapter.OrderDetails_Adapter;
import com.in.apniseva.adapter.OrderItemAdapter;
import com.in.apniseva.modelclass.CartItem;
import com.in.apniseva.modelclass.OrderItem_ModelClass;
import com.razorpay.Checkout;
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
   /* TextView text_name,text_MobileNo,text_city,text_address_details,text_orderConfirmed, bookingid,
            subTotalPrice,payonvisite,TotalPrice;*/
    //RecyclerView recyclerBookingHistory;

    //ArrayList<CartItem> orderDetails = new ArrayList<>();
   /* LinearLayoutManager linearLayoutManager;
    OrderDetails_Adapter orderDetailsAdapter;
    SharedPreference sharedPreference;*/
    String booking_id;
    TextView bookingid, totalAmount, text_Techniciansid, text_designation, text_TechnicianPin, text_allocatetechnician,
            text_ShowWorkDetails, text_DeliveryAddress,text3,show_Payment,pay_Payment,text_orderConfirmed,
            text_Submit,text_ShowMessage;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    //ArrayList<OrderItem_ModelClass> orderitem;
    String booking = "BookingDetails", techmobile, techname, category, profile_img,str_Email,price,
            str_mobileno,technicianid;

    View view_order_BookingConfirm, view_order_TechnicianAllocat, view_order_WorkInProgress, view_order_WorkCompleted,
            view_order_Payment;
    RelativeLayout BookingConfirm, TechnicianAllocat,text_technicianDetails;
    LinearLayout WorkInProgress, WorkCompleted, Payment;
    View placed_divider1, placed_divider, placed_divider2, placed_divider3;
    private int i = 0;
    private Handler hdlr = new Handler();
    private static final int REQUEST_PHONE_CALL = 1;
    SwipeRefreshLayout swipeRefreshLayout;
    OrderItemAdapter orderItemAdapter;
    ShapeableImageView profile_image;
    CardView payment_card,card_reating;
    RelativeLayout rel_card2;
    int amount,noofstars;
    RatingBar ratingbar;
    float rating;
    EditText edit_UserReview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);


       /* text_name = findViewById(R.id.text_name);
        text_MobileNo = findViewById(R.id.text_MobileNo);
        text_city = findViewById(R.id.text_city);
        text_address_details = findViewById(R.id.text_address_details);


        subTotalPrice = findViewById(R.id.subTotalPrice);
        payonvisite = findViewById(R.id.payonvisite);
        TotalPrice = findViewById(R.id.TotalPrice);
        recyclerBookingHistory = findViewById(R.id.recyclerBookingHistory);*/

        image_back = findViewById(R.id.image_back);
        bookingid = findViewById(R.id.bookingid);
        profile_image = findViewById(R.id.profile_image);
        totalAmount = findViewById(R.id.totalAmount);
        text_orderConfirmed = findViewById(R.id.text_orderConfirmed);

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
        //text_ShowWorkDetails = findViewById(R.id.text_ShowWorkDetails);
        payment_card = findViewById(R.id.payment_card);
        text_DeliveryAddress = findViewById(R.id.text_DeliveryAddress);
        text_allocatetechnician = findViewById(R.id.text_allocatetechnician);
        text3 = findViewById(R.id.text3);
        text_technicianDetails = findViewById(R.id.text_technicianDetails);
        show_Payment = findViewById(R.id.show_Payment);
        pay_Payment = findViewById(R.id.pay_Payment);
        card_reating = findViewById(R.id.card_reating);
        ratingbar = findViewById(R.id.ratingbar);
        text_Submit = findViewById(R.id.text_Submit);
        edit_UserReview = findViewById(R.id.edit_UserReview);
        text_ShowMessage = findViewById(R.id.text_ShowMessage);

     /*   placed_divider.setProgress(100);
        placed_divider1.setProgress(100);
        placed_divider2.setProgress(100);
        placed_divider3.setProgress(100);*/

        edit_UserReview.setFocusable(true);
        edit_UserReview.setFocusable(true);
        card_reating.setVisibility(View.GONE);

        Intent intent = getIntent();
        booking_id = intent.getStringExtra("booking_id");

        str_Email = SharedPrefManager.getInstance(BookingHistory.this).getUser().getEmailId();
        str_mobileno = SharedPrefManager.getInstance(BookingHistory.this).getUser().getMobileNo();

        text_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_UserReview.getText().toString().trim().equals("")){

                    Toast.makeText(BookingHistory.this, "Fill the Review", Toast.LENGTH_SHORT).show();
                }else{

                    noofstars = ratingbar.getNumStars();
                    rating = ratingbar.getRating();
                    String str_rating = String.valueOf(rating);
                    String review = edit_UserReview.getText().toString().trim();

                    submitUserReview(str_rating,review,booking_id,technicianid);

                }
            }
        });


        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BookingHistory.this, BookingDetails.class);
                startActivity(intent);
            }
        });

        orderDetails(booking_id);

        text_designation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //makePhoneCall();
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

        pay_Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                amount = Math.round(Float.parseFloat(price) * 100);
                startPayment(amount);

            }
        });

     /*   card_reating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.in.apniseva"));
                marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(marketIntent);


            }
        });*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(BookingHistory.this, BookingDetails.class);
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

                    Log.d("sunilresponse", response);

                    String status = jsonObject.getString("status");

                    if (status.equals("OK")) {

                        String order_id = jsonObject.getString("order_id");
                        price = jsonObject.getString("price");
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
                        String review = jsonObject.getString("review");
                        String deliaddress = address + "," + address1;
/*

                        if(review.equals("0")){

                            card_reating.setVisibility(View.VISIBLE);

                        }else{

                            card_reating.setVisibility(View.GONE);
                        }
*/

                        //text_ShowWorkDetails.setText(work_details);

                       /* StringBuilder sb = new StringBuilder(work_status);
                        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                        String str_workatatus = sb.toString();*/

                        //String upperString = work_status.substring(0, 1).toUpperCase() + work_status.substring(1).toLowerCase();
                        //tv.setText(StringUtils.capitalize(myString.toLowerCase().trim()));
                       // String captilizedString = name.substring(0,1).toUpperCase() + name.substring(1);
                        //myString.toLowerCase(Locale.getDefault()).capitalize()

                        //CharSequence text = String.valueOf(text.charAt(0)).toUpperCase() + text.subSequence(1, text.length());

                        text_orderConfirmed.setText(work_status);

                        if(pin_verification_status.equals("1")){

                            text_TechnicianPin.setText("Pin Verify Success");
                            text_TechnicianPin.setTextColor(ContextCompat.getColor(BookingHistory.this,R.color.button1));

                        }else{

                            text_TechnicianPin.setText(verification_pin);
                        }

                        bookingid.setText("#" + order_id);
                        totalAmount.setText("Rs " + price + "0");
                        text_DeliveryAddress.setText(deliaddress);

                        double int_price = Double.valueOf(price);
                        double int_total = Double.valueOf(subtotal);
                        double int_amount = int_price - int_total;
                        String payOnVisit = df.format(int_amount);

                        JSONArray jsonArray_technician_details = new JSONArray(technician_details);

                        if(jsonArray_technician_details != null){

                            for (int i = 0; i < jsonArray_technician_details.length(); i++) {

                                JSONObject jsonObject_technician = jsonArray_technician_details.getJSONObject(i);

                                technicianid = jsonObject_technician.getString("id");
                                techname = jsonObject_technician.getString("name");
                                String email = jsonObject_technician.getString("email");
                                techmobile = jsonObject_technician.getString("mobile");
                                profile_img = jsonObject_technician.getString("profile_img");
                                category = jsonObject_technician.getString("category");
                                String subcategory = jsonObject_technician.getString("subcategory");

                                text_Techniciansid.setText(techname);
                                text_designation.setText(category);
                                Picasso.with(BookingHistory.this).load(profile_img).placeholder(R.drawable.no_avatar).into(profile_image);

                            }
                        }

                        if (work_status.equals("Confirmed")) {

                            view_order_BookingConfirm.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            text_allocatetechnician.setVisibility(View.VISIBLE);
                            placed_divider.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                        } else if (work_status.equals("Allocated")) {



                            view_order_BookingConfirm.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            text_allocatetechnician.setVisibility(View.VISIBLE);
                            placed_divider1.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));
                            //placed_divider1.setLayoutParams(new LinearLayout.LayoutParams(2,120));

                            view_order_TechnicianAllocat.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            text_technicianDetails.setVisibility(View.VISIBLE);
                            text3.setVisibility(View.VISIBLE);
                            placed_divider.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));


                        }else if(work_status.equals("Progress")){

                            view_order_BookingConfirm.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            text_allocatetechnician.setVisibility(View.VISIBLE);
                            placed_divider1.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                            view_order_TechnicianAllocat.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            text_technicianDetails.setVisibility(View.VISIBLE);
                            text3.setVisibility(View.VISIBLE);
                            placed_divider.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                            view_order_WorkInProgress.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            WorkInProgress.setVisibility(View.VISIBLE);
                            placed_divider2.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));


                        }else if(work_status.equals("Completed")){

                            view_order_BookingConfirm.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            text_allocatetechnician.setVisibility(View.VISIBLE);
                            placed_divider1.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                            view_order_TechnicianAllocat.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            text_technicianDetails.setVisibility(View.VISIBLE);
                            text3.setVisibility(View.VISIBLE);
                            placed_divider.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                            view_order_WorkInProgress.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            WorkInProgress.setVisibility(View.VISIBLE);
                            placed_divider2.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                            view_order_WorkCompleted.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            WorkCompleted.setVisibility(View.VISIBLE);
                            payment_card.setVisibility(View.VISIBLE);
                            placed_divider3.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                            show_Payment.setText(price);

                        }else if(work_status.equals("Generated")){

                            view_order_BookingConfirm.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            text_allocatetechnician.setVisibility(View.VISIBLE);
                            placed_divider1.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                            view_order_TechnicianAllocat.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            text_technicianDetails.setVisibility(View.VISIBLE);
                            text3.setVisibility(View.VISIBLE);
                            placed_divider.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                            view_order_WorkInProgress.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            WorkInProgress.setVisibility(View.VISIBLE);
                            placed_divider2.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                            view_order_WorkCompleted.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            WorkCompleted.setVisibility(View.VISIBLE);
                            payment_card.setVisibility(View.VISIBLE);
                            placed_divider3.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                            show_Payment.setText(price);

                        }else if(work_status.equals("Payment_done")){

                            view_order_BookingConfirm.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            text_allocatetechnician.setVisibility(View.VISIBLE);
                            placed_divider1.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                            view_order_TechnicianAllocat.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            text_technicianDetails.setVisibility(View.VISIBLE);
                            text3.setVisibility(View.VISIBLE);
                            placed_divider.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                            view_order_WorkInProgress.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            WorkInProgress.setVisibility(View.VISIBLE);
                            placed_divider2.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                            view_order_WorkCompleted.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));
                            WorkCompleted.setVisibility(View.VISIBLE);
                            payment_card.setVisibility(View.GONE);
                            placed_divider3.setBackgroundColor(ContextCompat.getColor(BookingHistory.this, R.color.button1));

                            show_Payment.setText(price);

                            view_order_Payment.setBackgroundDrawable(ContextCompat.getDrawable(BookingHistory.this, R.drawable.check1));

                            if(review.equals("0")){

                                card_reating.setVisibility(View.VISIBLE);

                            }else{

                                card_reating.setVisibility(View.GONE);
                            }

                        }

                       /* orderitem = new ArrayList<>();

                        String Order_item = jsonObject.getString("Order_item");

                        JSONArray jsonArray_item = new JSONArray(Order_item);

                        for (int j = 0; j < jsonArray_item.length(); j++) {

                            JSONObject jsonObject_item = jsonArray_item.getJSONObject(j);

                            String categoryname = jsonObject_item.getString("categoryname");
                            String subcategoryname = jsonObject_item.getString("subcategoryname");
                            String Product = jsonObject_item.getString("Product");
                            String Amount = jsonObject_item.getString("Amount");

                            OrderItem_ModelClass orderItem_modelClass = new OrderItem_ModelClass(
                                    categoryname, Product, Amount, subcategoryname
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
                        text_orderConfirmed.setText("Order" + " " + work_status);
                        TotalPrice.setText(price);
                        subTotalPrice.setText(subtotal);
                        payonvisite.setText(payOnVisit);*/

                        //v.setBackgroundResource(R.color.myGreenWithAlpha);
                        //v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.myGreen));

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

                Toast.makeText(BookingHistory.this, "Facing Technical issues, Try again!", Toast.LENGTH_SHORT).show();

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

   /* private void makePhoneCall() {
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
    }*/

    public void startPayment(int amount) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setKeyID("rzp_live_pEiadfp4ZIDBJT");

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Apni Seva");
            options.put("description", "Aap Se Aap Tak");
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            //options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", amount);

            JSONObject preFill = new JSONObject();
            preFill.put("email", str_Email);
            preFill.put("contact", str_mobileno);

            options.put("prefill", preFill);

            co.open(activity, options);

        } catch (Exception e) {

            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void submitUserReview(String rate,String review,String order_id,String technician_id){

        ProgressDialog dialog = new ProgressDialog(BookingHistory.this);
        dialog.setMessage("Update Review Wait..");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.givereview, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    Toast.makeText(BookingHistory.this, message, Toast.LENGTH_SHORT).show();

                    edit_UserReview.setFocusable(false);
                    edit_UserReview.setFocusable(false);

                    ratingbar.setVisibility(View.GONE);
                    edit_UserReview.setVisibility(View.GONE);
                    text_Submit.setVisibility(View.GONE);
                    card_reating.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();

                error.printStackTrace();

                Toast.makeText(BookingHistory.this, "Facing Technical issues, Try again!", Toast.LENGTH_SHORT).show();

                Log.d("error", error.toString());
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("rate",rate);
                params.put("review",review);
                params.put("order_id",order_id);
                params.put("technician_id",technician_id);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BookingHistory.this);
        requestQueue.add(stringRequest);

    }

}