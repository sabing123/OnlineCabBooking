package com.example.onlinecabbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    EditText input_email,input_password;
    Button btnlogin;
    FirebaseAuth auth;
    ProgressDialog dialog;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        input_email = findViewById(R.id.edit_input_email);
        input_password = findViewById(R.id.edit_input_password);
        btnlogin = findViewById(R.id.btn_login);
        auth  = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(SigninActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        signinUser();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }
    public void signinUser(){
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(SigninActivity.this, "Congrats!! Login Successful....", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SigninActivity.this, MainPageActivity.class );
                            startActivity(i);
                            finish();
                        }
                        else {
                            Toast.makeText(SigninActivity.this, "Failed!! User Has Not Register Yet", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        btnlogin.setEnabled(true);
    }


    private boolean validate() {
        boolean valid = true;
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        if (email.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("Enter a valid email address");
            valid = false;
        } else {
            input_email.setError(null);
        }

        if (password.isEmpty() && password.length() < 4 && password.length() > 10) {
            input_password.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            input_password.setError(null);
        }
        return valid;
    }


}


