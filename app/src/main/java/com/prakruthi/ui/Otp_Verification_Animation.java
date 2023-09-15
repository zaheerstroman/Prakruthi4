package com.prakruthi.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prakruthi.R;

public class Otp_Verification_Animation extends AppCompatActivity {

    String Status;
    SharedPreferences sharedPreferences;
    String fullname,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification_animation);
        sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);

        // In your Otp_Verification_Animation activity's onCreate() method
        Intent intent = getIntent();
        int departmentId = intent.getIntExtra("department_id", 0);
        password=intent.getStringExtra("password");
        fullname=intent.getStringExtra("fullname");
        Status=intent.getStringExtra("Status");// Here, 0 is the default value if the extra is not found
        if (departmentId == 2)
        {
            TextView txt_24_hour = findViewById(R.id.txt_24_hour);
            txt_24_hour.setVisibility(View.GONE);
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(this::nextActivtyScreen,1000);
        Toast.makeText(this, "Account Created Successful", Toast.LENGTH_SHORT).show();
        // You can now use the departmentId value as needed
    }

    private void nextActivtyScreen() {
        rememberMe();
        startActivity(new Intent(Otp_Verification_Animation.this,Login.class));
    }
    public void rememberMe()
    {
        // Get SharedPreferences.Editor object
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Set String value
        editor.putString("username", fullname);
        editor.putString("password", password);
        editor.putBoolean("rememberMe",true);
        // Apply changes
        editor.apply();
    }
}