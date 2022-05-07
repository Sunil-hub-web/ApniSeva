package com.in.apniseva.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

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
import com.in.apniseva.adapter.BannerAdapter;
import com.in.apniseva.adapter.CustomerReviewAdapter;
import com.in.apniseva.adapter.OurServicesAdapter;
import com.in.apniseva.adapter.RecommendedServicesAdapter;
import com.in.apniseva.modelclass.Banner_ModelClass;
import com.in.apniseva.modelclass.CustomerReview_ModelClass;
import com.in.apniseva.modelclass.OurServices_ModelClass;
import com.in.apniseva.modelclass.RecommendedServices_ModelClass;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ViewPager2 showImageViewPager;
    RecyclerView showrecommendedServices, customerReviewRecycler, recyclerOurServices;
    LinearLayoutManager linearLayoutManager, linearLayoutManager1;
    GridLayoutManager gridLayoutManager;
    ArrayList<Banner_ModelClass> homeBanner;
    ArrayList<RecommendedServices_ModelClass> services;
    ArrayList<CustomerReview_ModelClass> user_review;
    ArrayList<OurServices_ModelClass> ourServices;
    BannerAdapter bannerAdapter;
    RecommendedServicesAdapter recommendedServicesAdapter;
    CustomerReviewAdapter customerReviewAdapter;
    OurServicesAdapter ourServicesAdapter;
    TextView[] dots;
    Handler sliderhandler = new Handler();
    int currentPossition = 0;
    LinearLayout dots_container;
    BottomNavigationView bottomNavigation;
    LinearLayout line_AcServices;
    ImageView img_editLocation;
    RelativeLayout rel_loc;
    EditText serach;
    TextView text_location;

    private Boolean exit = false;

    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    Double latitude, longitude;
    String YourAddress,username,password;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.Blue));

        showImageViewPager = findViewById(R.id.showImageViewPager);
        dots_container = findViewById(R.id.dots_container);
        //showrecommendedServices = findViewById(R.id.showrecommendedServices);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        recyclerOurServices = findViewById(R.id.recyclerOurServices);
        customerReviewRecycler = findViewById(R.id.customerReviewRecycler);
        img_editLocation = findViewById(R.id.img_editLocation);
        rel_loc = findViewById(R.id.rel_loc);
        //serach = findViewById(R.id.serach);
        text_location = findViewById(R.id.location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Intent intent = getIntent();
        YourAddress = intent.getStringExtra("YourAddress");

        username = SharedPrefManager.getInstance(MainActivity.this).getUser().getMobileNo();
        password = SharedPrefManager.getInstance(MainActivity.this).getUser().getPassword();
        String userid = SharedPrefManager.getInstance(MainActivity.this).getUser().getUserid();

        Log.d("password_sunil",password + username + userid);

        if (YourAddress != null) {

            text_location.setText(YourAddress);

        } else {

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //Write Function To enable gps
                locationPermission();
                enableUserLocation();

            } else {
                //GPS is already On then
                getLocation();
            }

        }


        showImageViewPager.setClipToPadding(false);
        showImageViewPager.setClipChildren(false);
        showImageViewPager.setOffscreenPageLimit(3);
        showImageViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();

        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {

                float r = 1 - Math.abs(position);
                page.setScaleY(1.2f + r * 0.1f);

            }
        });

        showImageViewPager.setPageTransformer(compositePageTransformer);

        Runnable sliderRunable = new Runnable() {
            @Override
            public void run() {

                showImageViewPager.setCurrentItem(showImageViewPager.getCurrentItem() + 1);
            }
        };

        showImageViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                //super.onPageSelected(position);

                if (currentPossition > 4)
                    currentPossition = 0;
                selectedIndicatorPosition(currentPossition++);

                sliderhandler.removeCallbacks(sliderRunable);
                sliderhandler.postDelayed(sliderRunable, 2000);

            }
        });


            bottomNavigation.setSelectedItemId(R.id.home);

            bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {

                        case R.id.home:
                            return true;

                        case R.id.profile:

                            startActivity(new Intent(getApplicationContext(), UserDetails.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.orders:

                            startActivity(new Intent(getApplicationContext(), BookingDetails.class));
                            overridePendingTransition(0, 0);
                            return true;

                    }

                    return false;
                }
            });

        img_editLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });

        rel_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });

        getHomePageDetails();


    }

    private void dotsIndicator() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);

        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(MainActivity.this);
            dots[i].setText(Html.fromHtml("&#9679;"));
            dots[i].setTextSize(20);
            dots[i].setPadding(5, 0, 5, 0);
            dots[i].setLayoutParams(params);
            dots_container.addView(dots[i]);
            dots_container.bringToFront();
        }
    }

    private void selectedIndicatorPosition(int position) {


        for (int i = 0; i < dots.length; i++) {


            if (i == position) {

                dots[i].setTextColor(Color.WHITE);

            } else {

                dots[i].setTextColor(Color.GRAY);
            }
        }

    }


    public void getHomePageDetails() {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Get Home Page Details");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUrl.userHomePage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("status");

                    if (message.equals("OK")) {

                        String category = jsonObject.getString("category");
                        String recomended_services = jsonObject.getString("recomended_services");
                        String reviews = jsonObject.getString("reviews");
                        String banner = jsonObject.getString("banner");

                        ourServices = new ArrayList<>();

                        JSONArray jsonArray_category = new JSONArray(category);

                        for (int i = 0; i < jsonArray_category.length(); i++) {

                            JSONObject jsonObject_category = jsonArray_category.getJSONObject(i);

                            String id = jsonObject_category.getString("id");
                            String category_name = jsonObject_category.getString("category_name");
                            String image = jsonObject_category.getString("image");

                            OurServices_ModelClass ourServices_modelClass = new OurServices_ModelClass(
                                    category_name, id, image
                            );

                            ourServices.add(ourServices_modelClass);
                        }

                        gridLayoutManager = new GridLayoutManager(MainActivity.this, 4, GridLayoutManager.VERTICAL, false);
                        ourServicesAdapter = new OurServicesAdapter(MainActivity.this, ourServices);
                        recyclerOurServices.setLayoutManager(gridLayoutManager);
                        recyclerOurServices.setHasFixedSize(true);
                        recyclerOurServices.setAdapter(ourServicesAdapter);

                        services = new ArrayList<>();

                       /* JSONArray jsonArray_offers = new JSONArray(recomended_services);

                        for (int i = 0; i < jsonArray_offers.length(); i++) {

                            JSONObject jsonObject_offers = jsonArray_offers.getJSONObject(i);

                            String image = jsonObject_offers.getString("image");
                            String service_name = jsonObject_offers.getString("service_name");
                            String price = jsonObject_offers.getString("price");

                            RecommendedServices_ModelClass recommendedServices_modelClass = new RecommendedServices_ModelClass(
                                    image, service_name, price
                            );

                            services.add(recommendedServices_modelClass);
                        }

                        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        recommendedServicesAdapter = new RecommendedServicesAdapter(MainActivity.this, services);
                        showrecommendedServices.setLayoutManager(linearLayoutManager);
                        showrecommendedServices.setHasFixedSize(true);
                        showrecommendedServices.setAdapter(recommendedServicesAdapter);*/


                        user_review = new ArrayList<>();

                        JSONArray jsonArray_reviews = new JSONArray(reviews);

                        for (int i = 0; i < jsonArray_reviews.length(); i++) {

                            JSONObject jsonObject_reviews = jsonArray_reviews.getJSONObject(i);

                            String user_name = jsonObject_reviews.getString("user_name");
                            String image = jsonObject_reviews.getString("image");
                            String review = jsonObject_reviews.getString("review");
                            String rating = jsonObject_reviews.getString("rating");

                            CustomerReview_ModelClass customerReview_modelClass = new CustomerReview_ModelClass(
                                    user_name, image, review, rating
                            );

                            user_review.add(customerReview_modelClass);
                        }

                        linearLayoutManager1 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        customerReviewAdapter = new CustomerReviewAdapter(MainActivity.this, user_review);
                        customerReviewRecycler.setLayoutManager(linearLayoutManager1);
                        customerReviewRecycler.setHasFixedSize(true);
                        customerReviewRecycler.setAdapter(customerReviewAdapter);


                        homeBanner = new ArrayList<>();
                        JSONArray jsonArray_banner = new JSONArray(banner);

                        for (int k = 0; k < jsonArray_banner.length(); k++) {

                            JSONObject jsonObject_banner = jsonArray_banner.getJSONObject(k);

                            String image = jsonObject_banner.getString("image");

                            Banner_ModelClass banner_modelClass = new Banner_ModelClass(
                                    image
                            );

                            homeBanner.add(banner_modelClass);
                        }

                        bannerAdapter = new BannerAdapter(MainActivity.this, homeBanner, showImageViewPager);
                        showImageViewPager.setAdapter(bannerAdapter);

                        int arraysize = homeBanner.size();

                        dots = new TextView[arraysize];

                        dotsIndicator();

                        selectedIndicatorPosition(currentPossition);

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
                Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }


    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                //initialize location
                Location location = task.getResult();

                if (location != null) {

                    try {
                        //initialize geocoder
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                        //initialize AddressList
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        //set Latitude On Text View
                        latitude = addresses.get(0).getLatitude();

                        //set Longitude On Text View
                        longitude = addresses.get(0).getLongitude();

                        //set address On Text View
                        text_location.setText(addresses.get(0).getSubLocality() + "," + addresses.get(0).getLocality());


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    public void locationPermission() {

        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);

        builder.setMessage("Enable Your GPS Location").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            //Ask for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We need to show user a dialog for displaying why the permission is needed and then ask for the permission...
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            } else {
                //We do not have the permission..
            }
        }
    }

    @Override
    public void onBackPressed() {


        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                }
            }, 4 * 1000);
        }
    }
}