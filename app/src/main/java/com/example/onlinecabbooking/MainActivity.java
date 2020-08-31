package com.example.onlinecabbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.onlinecabbooking.driverui.DriverLoginRegisterActivity;
import com.example.onlinecabbooking.passengerui.CustomerLoginRegisterActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void open_driver(View V){

        startActivity(new Intent(MainActivity.this, DriverLoginRegisterActivity.class));
    finish();
    }

    public void open_passenger(View V){
        startActivity(new Intent(MainActivity.this, CustomerLoginRegisterActivity.class));
        finish();
    }


}