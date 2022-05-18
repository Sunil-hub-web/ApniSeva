
package com.in.apniseva.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.in.apniseva.AppURL.AppUrl;
import com.in.apniseva.AppURL.VolleyMultipartRequest;
import com.in.apniseva.R;
import com.in.apniseva.SessionManager;
import com.in.apniseva.SharedPrefManager;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDetails extends AppCompatActivity {

    ImageView img_close;
    TextView text_UserProfile, text_BookingDetails, text_PaymentOption, text_Logout,text_UserName,text_UserMobileNo;
    ShapeableImageView profile_image;
    public static final int IMAGE_CODE = 1;
    Uri imageUri, selectedImageUri;
    String profile_photo, userId;
    File f;
    String ImageDecode;
    GoogleSignInClient mGoogleSignInClient;

    SessionManager sessionManager;

    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private String filePath;


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
        text_UserName = findViewById(R.id.text_UserName);
        text_UserMobileNo = findViewById(R.id.text_UserMobileNo);

        viewProfileDetails();

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(UserDetails.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(UserDetails.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(UserDetails.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    Log.e("Else", "Else");
                    showFileChooser();
                }

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

                SharedPrefManager.getInstance(UserDetails.this).logout();

                //signOut();

            }
        });
    }

    private void signOut() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(UserDetails.this, "Your Account Logout", Toast.LENGTH_SHORT).show();
                        // ...
                    }
                });
    }

    public void showFileChooser() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "title"), IMAGE_CODE);

    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
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
                Log.d("selectedImageUri", selectedImageUri.toString());
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                profile_image.setImageBitmap(bitmap);
                //profile_image.setImageURI(selectedImageUri);

                Log.d("ImageDecode", ImageDecode);
                Log.d("ImageDecode1", f.toString());
                Log.d("ImageDecode2", selectedImageUri.toString());

                cursor.close();

                if (selectedImageUri.equals("")) {

                    Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();

                } else {

                    profileImageUpload(bitmap);
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
        super.onBackPressed();

        finish();
    }

    public void profileImageUpload(final Bitmap bitmap) {

        ProgressDialog progressDialog = new ProgressDialog(UserDetails.this);
        progressDialog.setMessage("Profile Pic Upload Please wait...");
        progressDialog.show();


        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppUrl.updateProfileImage, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response.data));

                    String status = jsonObject.getString("status");

                    String message = jsonObject.getString("message");

                    if (message.equals("Profile updated successfully")) {

                        Toast.makeText(UserDetails.this, message, Toast.LENGTH_SHORT).show();

                        viewProfileDetails();

                    } else {

                        Toast.makeText(UserDetails.this, message, Toast.LENGTH_SHORT).show();

                        viewProfileDetails();
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
                Toast.makeText(UserDetails.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", userId);

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(UserDetails.this);
        requestQueue.add(volleyMultipartRequest);

      /*  JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("user_id", userId);
            jsonObject.put("image", bitmap);

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
        requestQueue.add(jsonObjectRequest);*/

    }

    public void viewProfileDetails(){

        ProgressDialog progressDialog = new ProgressDialog(UserDetails.this);
        progressDialog.setMessage("Loading Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.viewUserProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("status");

                    if (message.equals("ok")) {

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        String mobile = jsonObject.getString("mobile");
                        String image = jsonObject.getString("image");

                        Picasso.with(UserDetails.this).load(image).
                                 placeholder(R.drawable.profileimage)
                                .into(profile_image);

                        text_UserName.setText(name);
                        text_UserMobileNo.setText("+91 " + mobile);

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

                Toast.makeText(UserDetails.this, "Your user id not found", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(UserDetails.this);
        requestQueue.add(stringRequest);
    }


}