package com.example.myapplication1;
import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ActivityResultLauncher<Intent> cameraResult;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent fromPrevious = getIntent();

        TextView txtWelcome = (TextView) findViewById(R.id.txt);

        String emailAdress = fromPrevious.getStringExtra("EmailAddress");
        txtWelcome.setText("Welcome Back: " + emailAdress);
        /////Handle Runtime Permissions
        //: If your app targets Android 6.0 (API level 23) or higher, you need to handle runtime permissions.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // You can directly request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);

            EditText editText = findViewById(R.id.phone);

            Button btnPhone = findViewById(R.id.btnCall);
            btnPhone.setOnClickListener(view -> {
                // Replace phoneNumber with the recipient's phone number
                String phoneNumber = editText.getText().toString();
                // Create the intent
                Intent call = new Intent(Intent.ACTION_DIAL);
                // Set the data URI with the recipient's phone number
                call.setData(Uri.parse("tel:" + phoneNumber));
                // Start the activity
                startActivity(call);



                });
            Button btnPic = (Button) findViewById(R.id.btnPic);
            btnPic.setOnClickListener(view -> {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraResult.launch(cameraIntent);
            });
        }
        ///

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /// show the captured image in the imageview
        ImageView img = (ImageView) findViewById(R.id.imageView);
        Bitmap thumbnail = data.getParcelableExtra("data");
        img.setImageBitmap(thumbnail);


        //// save the captured image in my device
       FileOutputStream fOut = null;
        try {
            fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);

            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            Log.w("Preeti", "file saved");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
