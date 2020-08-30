package com.example.onlinecabbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.onlinecabbooking.driverui.DriverLoginRegisterActivity;
import com.example.onlinecabbooking.passengerui.CustomerLoginRegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null)
//        {
//            Intent intent = new Intent(MainActivity.this,MainPageActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        else {
//
//
//        }
    }


    public void open_driver(View V){
        startActivity(new Intent(MainActivity.this, DriverLoginRegisterActivity.class));
    }

    public void open_passenger(View V){
        startActivity(new Intent(MainActivity.this, CustomerLoginRegisterActivity.class));
    }


}