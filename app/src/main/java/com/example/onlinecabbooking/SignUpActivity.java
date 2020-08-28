package com.example.onlinecabbooking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText Uname, Uaddress, Uemail, UMblnumber, Upassword;
    Button btn_signup;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Uname = findViewById(R.id.input_name);
        Uaddress = findViewById(R.id.input_address);
        Uemail = findViewById(R.id.input_email);
        UMblnumber = findViewById(R.id.input_mobile);
        Upassword = findViewById(R.id.input_password);
        btn_signup = findViewById(R.id.btn_signup);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }


    public void signup() {
        dialog.setMessage("Authenticating...");
        dialog.show();
        String Usrname = Uname.getText().toString();
        String Usraddress = Uaddress.getText().toString();
        String Usremail = Uemail.getText().toString();
        String UsrMblnumber = UMblnumber.getText().toString();
        String Usrpassword = Upassword.getText().toString();

        if (Usrname.equals("") && Usraddress.equals("") && Usremail.equals("") && UsrMblnumber.equals("") && Usrpassword.equals("")) {
            Toast.makeText(this, "Field Cannot be Empty", Toast.LENGTH_SHORT).show();
        } else {
            auth.createUserWithEmailAndPassword(Usremail, Usrpassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Successfully register", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SignUpActivity.this, MainPageActivity.class);
                                startActivity(i);

                            } else {
                                Toast.makeText(SignUpActivity.this, "Not Register", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

}