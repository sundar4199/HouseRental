package com.example.navigationdrawer.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.util.OnGetDataListener;
import com.example.navigationdrawer.util.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewLogin extends AppCompatActivity {
    Button regbtn,loginbtn;
    EditText Username,Password;
    //progress dialog
    private ProgressDialog progressDialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        final SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        regbtn=findViewById(R.id.regbtn);
        loginbtn=findViewById(R.id.loginbtn);
        Username=findViewById(R.id.username);
        Password=findViewById(R.id.password);
        progressDialog = new ProgressDialog(this);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if
                (!validateUsername(Username) |  !validatePassword(Password))
                    return ;
                else
                    isUser(sharedPref);


            }
        });


    }

    private void isUser(final SharedPreferences sharedPref) {

        final String userEnteredName = Username.getText().toString().trim();
        final String userEnteredPassword = Password.getText().toString().trim();
        progressDialog.setMessage("Signing in Please Wait...");
        progressDialog.show();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users").child(userEnteredName);
        readData(myRef, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();
                User user = dataSnapshot.getValue(User.class);
                Log.d("OnSuccess", "Started");


                if (user != null) {
                    if (user.getPassword().equals(userEnteredPassword)) {
                        Log.d("USER", user.getName());
                        Log.d("USER", user.getPassword());

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("uname", user.getUsername());
                        editor.apply();
                        openMainActivity();
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onStart() {
                //when starting
                Log.d("ONSTART", "Started");
            }

            @Override
            public void onFailure() {
                Log.d("onFailure", "Failed");
            }
        });
//
//        firebaseAuth.signInWithEmailAndPassword(userEnteredName, userEnteredPassword)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressDialog.dismiss();
//                        //if the task is successfull
//                        if(task.isSuccessful()){
//                            //start the profile activity
//                            finish();
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        }
//                        else{
//                            Toast.makeText(NewLogin.this, "Incorrect Email or Password", Toast.LENGTH_LONG).show();                        }
//                    }
//                });


    }


    private boolean validateUsername(EditText username){
        String userInput = username.getText().toString();
        if(!userInput.isEmpty()){
//            Toast.makeText(this, "Validated", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(this, "username req", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean validatePassword(EditText Password){
        String val= Password.getText().toString();
        String passwordVal = "^" + "(?=.*[A-Za-z])" + "(?=.*[@$!%*#?&])" + ".{8,}" + "$";


        if(!val.isEmpty() && val.matches(passwordVal)){
//            Toast.makeText(this, "Strong Password", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
            return false;
        }
//        return true;
    }

    public void openActivity1(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();
            }

        });

    }
}