package com.example.onlinecabbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private EditText txtname,txtaddress,txtemail,txtpnumber,txtpassword,txtrepassword;
    private Button btnsignup;
    private TextView linklogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtname = findViewById(R.id.input_name);
        txtaddress = findViewById(R.id.input_address);
        txtemail = findViewById(R.id.input_email);
        txtpnumber = findViewById(R.id.input_mobile);
        txtpassword = findViewById(R.id.input_password);
        txtrepassword =findViewById(R.id.input_reEnterPassword);
        btnsignup = findViewById(R.id.btn_signup);
        linklogin = findViewById(R.id.link_login);

        linklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }

    private void signup() {

        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnsignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = txtname.getText().toString();
        String address = txtaddress.getText().toString();
        String email = txtemail.getText().toString();
        String mobile = txtpnumber.getText().toString();
        String password = txtpassword.getText().toString();
        String reEnterPassword = txtrepassword.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    private void onSignupSuccess() {
        btnsignup.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        btnsignup.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String name = txtname.getText().toString();
        String address = txtaddress.getText().toString();
        String email = txtemail.getText().toString();
        String mobile = txtpnumber.getText().toString();
        String password = txtpassword.getText().toString();
        String reEnterPassword = txtrepassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            txtname.setError("at least 3 characters");
            valid = false;
        } else {
            txtname.setError(null);
        }

        if (address.isEmpty()) {
            txtaddress.setError("Enter Valid Address");
            valid = false;
        } else {
            txtaddress.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtemail.setError("enter a valid email address");
            valid = false;
        } else {
            txtemail.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            txtpnumber.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            txtpnumber.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            txtpassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            txtpassword.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            txtrepassword.setError("Password Do not match");
            valid = false;
        } else {
            txtrepassword.setError(null);
        }

        return valid;
    }

}