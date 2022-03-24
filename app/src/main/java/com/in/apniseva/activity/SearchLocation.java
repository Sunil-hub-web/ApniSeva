package com.in.apniseva.activity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.in.apniseva.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;

public class SearchLocation extends AppCompatActivity {

    ImageView image_back;
    EditText edit_serach;
    Button btn_Yourlocation;
    String YourAddress;

    TextView text1,text2,text3,text4,text5,text6,text7,text8,text9,text10;

    private GoogleMap mMap;
    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    Double latitude, longitude;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        image_back = findViewById(R.id.image_back);
        edit_serach = findViewById(R.id.edit_serach);
        btn_Yourlocation = findViewById(R.id.btn_Yourlocation);

       /* text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        text5 = findViewById(R.id.text5);
        text6 = findViewById(R.id.text6);
        text8 = findViewById(R.id.text8);
        text9 = findViewById(R.id.text9);*/

        Window window = SearchLocation.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(SearchLocation.this, R.color.white));

        Intent intent = getIntent();
        YourAddress = intent.getStringExtra("YourAddress");

        if (YourAddress != null){

            edit_serach.setText(YourAddress);

        }

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SearchLocation.this,LocationActivity.class);
                startActivity(intent);
            }
        });

        edit_serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SearchLocation.this,MapsActivity.class);
                intent.putExtra("Yourlocation","serach");
                startActivity(intent);

            }
        });

        btn_Yourlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SearchLocation.this,MapsActivity.class);
                intent.putExtra("Yourlocation","Yourlocation");
                startActivity(intent);

               /* if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps
                    locationPermission();

                } else {
                    //GPS is already On then
                    getLocation();
                }*/

            }
        });
    }

    @Override
    public void onBackPressed() { }

    /*private void getLocation() {

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
                        Geocoder geocoder = new Geocoder(SearchLocation.this, Locale.getDefault());

                        //initialize AddressList
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        //set Latitude On Text View
                        latitude = addresses.get(0).getLatitude();

                        //set Longitude On Text View
                        longitude = addresses.get(0).getLongitude();

                        //set address On Text View
                        edit_serach.setText(addresses.get(0).getAddressLine(0));



                        text3.setText(Html.fromHtml("<font color='#606060'><b>CountryName :</b><br></font>" + addresses.get(0).getCountryName()));

                        //set Locality On Text View
                        text4.setText(Html.fromHtml("<font color='#606060'><b>Locality :</b><br></font>" + addresses.get(0).getLocality()));

                        //set Longitude On Text View
                        text2.setText(Html.fromHtml("<font color='#606060'><b>SubLocality :</b><br></font>" + addresses.get(0).getSubLocality()));

                        //set Latitude On Text View
                        text1.setText(Html.fromHtml("<font color='#606060'><b>AdminArea :</b><br></font>" + addresses.get(0).getAdminArea()));

                        //set Locality On Text View
                        text5.setText(Html.fromHtml("<font color='#606060'><b>PostalCode :</b><br></font>" + addresses.get(0).getPostalCode()));

                        //set address On Text View
                        text6.setText(Html.fromHtml("<font color='#606060'><b>Address :</b><br></font>" + addresses.get(0).getAddressLine(0)));


                        //set address On Text View
                        text8.setText(Html.fromHtml("<font color='#606060'><b>Premises :</b><br></font>" + addresses.get(0).getPremises()));

                        //set address On Text View
                        text9.setText(Html.fromHtml("<font color='#606060'><b>SubAdminArea :</b><br></font>" + addresses.get(0).getSubAdminArea()));






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
    }*/
}