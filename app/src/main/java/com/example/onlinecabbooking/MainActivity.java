package com.example.onlinecabbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private Button signin,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            Intent intent = new Intent(MainActivity.this,MainPageActivity.class);
            startActivity(intent);
            finish();
        }
        else {

            setContentView(R.layout.activity_main);
        }
    }


    public void open_signin(View V){
        startActivity(new Intent(MainActivity.this, SigninActivity.class));
    }

    public void open_signup(View V){
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }


}