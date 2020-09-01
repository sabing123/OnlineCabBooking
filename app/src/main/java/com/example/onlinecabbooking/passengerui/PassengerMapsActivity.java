package com.example.onlinecabbooking.passengerui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.onlinecabbooking.MainActivity;
import com.example.onlinecabbooking.R;
import com.example.onlinecabbooking.driverui.DriverMapsActivity;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PassengerMapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;

    GoogleApiClient googleApiClient;
    Location lastlocation;
    LocationRequest locationRequest;

    private Button Customer_Logout_btn, btn_call_cab;
    private String passengerID;
    private LatLng PassengerPickUpLocation;
    private int radius = 1;
    private Boolean driverFound = false;
    private String  driverFoundID;

    private FirebaseAuth mAuth;
    private FirebaseUser dCurrentUser;
    private DatabaseReference PassengerDatabaseRef;
    private DatabaseReference DriverLocationRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_maps);


        mAuth = FirebaseAuth.getInstance();
        dCurrentUser = mAuth.getCurrentUser();
        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        PassengerDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Passenger Request");
        DriverLocationRef = FirebaseDatabase.getInstance().getReference().child("Driver Available");



        Customer_Logout_btn = findViewById(R.id.btn_customer_logout);
        btn_call_cab =  findViewById(R.id.btn_call_cab);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Customer_Logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                LogoutPassenger();
            }
        });
        btn_call_cab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeoFire geoFire = new GeoFire(PassengerDatabaseRef);
                geoFire.setLocation(passengerID, new GeoLocation(lastlocation.getLatitude(),lastlocation.getLongitude()));

                PassengerPickUpLocation = new LatLng(lastlocation.getLatitude(),lastlocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(PassengerPickUpLocation).title("PickUp Passenger From Here"));


                btn_call_cab.setText("Getting Your Cab...");
                GetClosestDriverCab();
            }
        });


    }



    private void GetClosestDriverCab() {
        GeoFire geoFire = new GeoFire(DriverLocationRef);
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(PassengerPickUpLocation.latitude, PassengerPickUpLocation.longitude),radius);
        geoQuery.removeAllListeners();

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                if (!driverFound)
                {
                    driverFound = true;
                    driverFoundID  = key;
                }

            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
            if (!driverFound)
            {
                radius = radius + 1 ;
                GetClosestDriverCab();
            }
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        buildGoogleAPIClient();
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

    }


    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastlocation = location;

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    protected synchronized void buildGoogleAPIClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void LogoutPassenger() {
        Intent intent = new Intent(PassengerMapsActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

}