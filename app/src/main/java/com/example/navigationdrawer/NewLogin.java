package com.example.navigationdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class NewLogin extends AppCompatActivity {
    Button regbtn,loginbtn;
    EditText Username,Password;
    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
        }
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
                    isUser();


            }
        });


    }

    private void isUser() {

        final String userEnteredName = Username.getText().toString().trim();
        final String userEnteredPassword = Password.getText().toString().trim();
        progressDialog.setMessage("Signing in Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userEnteredName, userEnteredPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(NewLogin.this, "Incorrect Email or Password", Toast.LENGTH_LONG).show();                        }
                    }
                });








//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
//                Log.d("tag1", "Value is: " + map);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Failed to read value
//                Log.w("2", "Failed to read value.", error.toException());
//            }
//        });
//        Query checkUser = reference.orderByChild("username").equalTo(userEnteredName);
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    for (DataSnapshot messageSnapshot: snapshot.getChildren()) {
//                        String passwordFromDB = (String) messageSnapshot.child("password").getValue();
//                        //Log.d("pass", "onDataChange: "+passwordFromDB);
//                    }
//                    Username.setError(null);
//
//                    String passwordFromDB = snapshot.child(userEnteredName).child("password").getValue(String.class);
//                    if(passwordFromDB.equals(userEnteredPassword)){
//                        Username.setError(null);
//                        openActivity();
//                    }
//                    else {
//                        Password.setError("Wrong Password");
//                        Password.requestFocus();
//                    }
//                }
//                else{
//                    Username.setError("User doesnot exist");
//                    Username.requestFocus();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
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
    }

    public void openActivity1(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
    public void openActivity(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}