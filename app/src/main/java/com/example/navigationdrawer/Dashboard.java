package com.example.navigationdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.navigationdrawer.activity.NewLogin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {

    TextView welocmetext;
    Button btn,btnso;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btn = (Button)findViewById(R.id.btn);
        btnso = (Button)findViewById(R.id.btnSignout);
        welocmetext = (TextView)findViewById(R.id.viewtxt);

        rootNode=FirebaseDatabase.getInstance();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    if(snap.getKey().equals("name")){
                        String user_loggedin = snap.getValue().toString();
                        welocmetext.setText(" Welcome "+ user_loggedin);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                //starting login activity
                openActivity5();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();
            }
        });
    }

    public void openActivity4(){
        Intent intent = new Intent(this,MapTrackDemo.class);
        startActivity(intent);
    }

    public void openActivity5(){
        Intent intent = new Intent(this, NewLogin.class);
        startActivity(intent);
    }
}