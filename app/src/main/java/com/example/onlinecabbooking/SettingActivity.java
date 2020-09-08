package com.example.onlinecabbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinecabbooking.driverui.DriverMapsActivity;
import com.example.onlinecabbooking.passengerui.PassengerMapsActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {


    private String getType;

    private CircleImageView profileImageView;
    private EditText NameEditText,phoneEditText,DriverCarName;
    private ImageView btn_close,btn_save;
    private TextView btnprofile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getType = getIntent().getStringExtra("type");
        Toast.makeText(this, getType, Toast.LENGTH_SHORT).show();

        profileImageView = findViewById(R.id.profile_image);

        NameEditText = findViewById(R.id.user_name);
        phoneEditText = findViewById(R.id.contact_num);
        DriverCarName = findViewById(R.id.driver_car_name);

        if (getType.equals("Drivers"))
        {
            DriverCarName.setVisibility(View.VISIBLE);
        }

        btn_close = findViewById(R.id.btn_close);
        btn_save = findViewById(R.id.btn_save);
        btnprofile = findViewById(R.id.btn_change_pp);


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getType.equals("Drivers"))
                {
                    startActivity(new Intent(SettingActivity.this, DriverMapsActivity.class));
                }
                else
                {
                    startActivity(new Intent(SettingActivity.this, PassengerMapsActivity.class));
                }
            }
        });
    }
}