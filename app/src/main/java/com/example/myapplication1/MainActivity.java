package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the data from the saved share preferences
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        //
        Button btn1;

        Log.w("Main Activity", "On create");
         btn1 = (Button) findViewById(R.id.button);
        EditText emailEditText = (EditText) findViewById(R.id.editTextText);

        emailEditText.setText(emailAddress);
        //1- create SharedPreferences.Editor object
        SharedPreferences.Editor editor = prefs.edit();

        btn1.setOnClickListener(view -> {
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);

            nextPage.putExtra("EmailAddress", emailEditText.getText().toString());

            //2. save the data into my device memory
            editor.putString("LoginName", emailEditText.getText().toString());
            editor.apply();


            startActivity(nextPage);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("Main Activity", "on Start");
    }

    protected void onResume() {
        super.onResume();
        Log.w("Main Activity", "on Resume");
    }

    protected void onPause() {
        super.onPause();
        Log.w("Main Activity", "on Pause");
    }

    protected void onStop() {
        super.onStop();
        Log.w("Main Activity", "on Stop");

    }

    protected void onDestroy() {
        super.onDestroy();
        Log.w("Main Activity", "on Destroy");
    }
}