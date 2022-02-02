package com.example.apniseva.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apniseva.AppURL.AppUrl;
import com.example.apniseva.R;
import com.example.apniseva.SessionManager;
import com.example.apniseva.SharedPrefManager;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class UserDetails extends AppCompatActivity {

    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1001;
    ImageView img_close;
    TextView text_UserProfile, text_BookingDetails, text_PaymentOption, text_Logout;
    ShapeableImageView profile_image;
    public static final int IMAGE_CODE = 1;
    Uri imageUri, selectedImageUri;
    String profile_photo, userId;
    File f;
    String ImageDecode;

    SessionManager sessionManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        sessionManager = new SessionManager(UserDetails.this);
        userId = SharedPrefManager.getInstance(UserDetails.this).getUser().getUserid();


        Window window = UserDetails.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(UserDetails.this, R.color.Blue));

        img_close = findViewById(R.id.img_close);
        text_UserProfile = findViewById(R.id.text_UserProfile);
        text_BookingDetails = findViewById(R.id.text_BookingDetails);
        //text_PaymentOption = findViewById(R.id.text_PaymentOption);
        profile_image = findViewById(R.id.profile_image);
        text_Logout = findViewById(R.id.text_Logout);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              /*  Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMAGE_CODE);*/

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent,"title"),IMAGE_CODE);

            }
        });


        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });

        text_UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDetails.this, EditProfile.class);
                intent.putExtra("UserProfile", "UserProfile");
                startActivity(intent);
            }
        });

        /*text_PaymentOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDetails.this, PaymentOption.class);
                startActivity(intent);
            }
        });*/

        text_BookingDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDetails.this, BookingDetails.class);
                intent.putExtra("UserProfile", "BookingDetails");
                startActivity(intent);
            }
        });

        text_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.logoutUser();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == IMAGE_CODE && resultCode == RESULT_OK &&
                    data != null && data.getData() != null) {

                imageUri = data.getData();
                //profile_image.setImageURI(imageUri);

                String[] FILE = {MediaStore.Images.Media.DATA};


                Cursor cursor = getContentResolver().query(imageUri,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                f = new File(ImageDecode);
                selectedImageUri = Uri.fromFile(f);
                profile_image.setImageURI(selectedImageUri);

                Log.d("ImageDecode",ImageDecode);
                Log.d("ImageDecode1",f.toString());
                Log.d("ImageDecode2",selectedImageUri.toString());

                cursor.close();

                if (selectedImageUri.equals("")) {

                    Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();

                } else {

                    profileImageUpload(f);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }


      /*  try {

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(UserDetails.this.getContentResolver(), imageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap = Bitmap.createScaledBitmap(bitmap, 500, 750, true);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos); //bm is the bitmap object
            byte[] img = baos.toByteArray();
            profile_photo = Base64.encodeToString(img, Base64.DEFAULT);

        } catch (
                IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onBackPressed() {

    }

    public void profileImageUpload(File image) {

        ProgressDialog progressDialog = new ProgressDialog(UserDetails.this);
        progressDialog.setMessage("Profile Pic Upload Please wait...");
        progressDialog.show();


        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("user_id", userId);
            jsonObject.put("image", image);

        } catch (Exception e) {

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.updateProfileImage, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String status = response.getString("status");

                    String message = response.getString("message");

                    if (message.equals("Profile updated successfully")) {

                        Toast.makeText(UserDetails.this, message, Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(UserDetails.this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(UserDetails.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(UserDetails.this);
        requestQueue.add(jsonObjectRequest);

    }

    public void mission(){


    }
}