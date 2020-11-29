package com.example.navigationdrawer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.util.ImageSaver;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ProgressDialog progressDialog;
    EditText name,email,password,rpassword,username;
    Button button,cam_but;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Bitmap imageBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email=findViewById(R.id.reg_email);
        button=findViewById(R.id.sign_button);
        password=findViewById(R.id.reg_password);
        rpassword=findViewById(R.id.reg_rpassword);
        name=findViewById(R.id.reg_name);
        username=findViewById(R.id.reg_username);
        cam_but = findViewById(R.id.camera_button);
        progressDialog = new ProgressDialog(this);


        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if
                (!validateEmail(email)| !validateUsername(username) | !validateName(name) | !validatePassword(password) | !validatePasswords(rpassword))
                    return;

                else

                    registerUser(myRef);

            }




        });

        cam_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                }

            }

        });







    }

    private void registerUser(DatabaseReference myRef){
//        rootNode=FirebaseDatabase.getInstance();
//        reference=rootNode.getReference("users");
        final String nameInput = name.getText().toString();
        final String emailInput = email.getText().toString();
        final String passwordInput = password.getText().toString();
        final String usersInput = username.getText().toString();
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
        if (imageBitmap != null) {
            new ImageSaver(getApplication()).
                    setFileName(usersInput+".png").
                    setDirectoryName("images").
                    save(imageBitmap);
        }

        myRef.child("users").child(usersInput).child("name").setValue(nameInput);
        myRef.child("users").child(usersInput).child("email").setValue(emailInput);
        myRef.child("users").child(usersInput).child("password").setValue(passwordInput);
        myRef.child("users").child(usersInput).child("username").setValue(usersInput);
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "User Registered",Toast.LENGTH_SHORT).show();
        openActivity2();
    }

    private boolean validateName(EditText name){
        String nameInput = name.getText().toString();
        if(!nameInput.isEmpty()){
//            Toast.makeText(this, "Validated", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(this, "name req", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validateEmail(EditText email){
        String emailInput= email.getText().toString();


        if(!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
//            Toast.makeText(this, "Validated", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(this, "Not Validated", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validatePassword(EditText password){
        String val= password.getText().toString();
        String passwordVal = "^" + "(?=.*[A-Za-z])" + "(?=.*[@$!%*#?&])" + ".{8,}" + "$";


        if(!val.isEmpty() && val.matches(passwordVal)){
//            Toast.makeText(this, "Strong Password", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(this, "Weak Password", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validatePasswords(EditText rpassword ){
        String val= rpassword.getText().toString();
        String newPass= password.getText().toString();



        if(!val.isEmpty() && val.matches(newPass)){
//            Toast.makeText(this, "Password matches", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(this, "Not matches", Toast.LENGTH_SHORT).show();
            return false;
        }
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

    public void openActivity2(){
        Intent intent = new Intent(this,NewLogin.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");




        }
    }

}
