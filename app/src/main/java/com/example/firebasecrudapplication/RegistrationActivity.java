package com.example.firebasecrudapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    //creating variables for edit tesx text view
    //firebase auth,button and progres bar
    private TextInputEditText userNameEdit,pwdEdt,cnfPwdEdt;
    private Button registerBtn;
    private ProgressBar loadingPB;
    private TextView loginTV;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //initialazing all our variables
        userNameEdit = findViewById(R.id.idEtdUserName);
        pwdEdt= findViewById(R.id.idEdtPwd);
        cnfPwdEdt = findViewById(R.id.idedtCnfPwd);
        registerBtn = findViewById(R.id.idBtnRegister);
        loadingPB = findViewById(R.id.idPBLoading);
        mAuth = FirebaseAuth.getInstance();

        //adding onclick for login tv
        loginTV.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void  onClick(View v){
                //opening a login activity on clicking login text
                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        //adding click listeners for register button
        registerBtn.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View v) {
            // hiding our progress bar.
            loadingPB.setVisibility(View.VISIBLE);

            // getting data from our edit text.
            String userName = userNameEdit.getText().toString();
            String pwd = pwdEdt.getText().toString();
            String cnfPwd = cnfPwdEdt.getText().toString();

            // checking if the password and confirm password is equal or not.
            if (!pwd.equals(cnfPwd))
                Toast.makeText(RegistrationActivity.this, "Please check both having same password..", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnfPwd)) {

                // checking if the text fields are empty or not.
                Toast.makeText(RegistrationActivity.this, "Please enter your credentials..", Toast.LENGTH_SHORT).show();
            } else {

                // on below line we are creating a new user by passing email and password.
                mAuth.createUserWithEmailAndPassword(userName, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // on below line we are checking if the task is success or not.
                        if (task.isSuccessful()) {

                            // in on success method we are hiding our progress bar and opening a login activity.
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(RegistrationActivity.this, "User Registered..", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        } else {

                            // in else condition we are displaying a failure toast message.
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(RegistrationActivity.this, "Fail to register user..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    });
    }
}