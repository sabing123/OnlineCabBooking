package com.example.onlinecabbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentLocationActivity extends AppCompatActivity {

    private TextView getcurrentlocation;
    private Button btnnextpage;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        getcurrentlocation = findViewById(R.id.currentLocation);
        btnnextpage = findViewById(R.id.nextpage);
        btnnextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentLocationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        //initialize fusedLocationProviderClient

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getcurrentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                check permission

                if (ActivityCompat.checkSelfPermission(CurrentLocationActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//when location is granted
                    getClocation();
                } else {
                    ActivityCompat.requestPermissions(CurrentLocationActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44
                    );
                }
            }
        });

    }

    private void getClocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();
                if(location != null){
                    Geocoder geocoder = new Geocoder(CurrentLocationActivity.this, Locale.getDefault());
                    //address
                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1

                        );

//                        setLocation on text View
                        getcurrentlocation.setText(Html.fromHtml("<font color='#fff'><b>Address : </b> <br><br></font>"
                        +addresses.get(0).getAddressLine(0)

                        ));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }
}