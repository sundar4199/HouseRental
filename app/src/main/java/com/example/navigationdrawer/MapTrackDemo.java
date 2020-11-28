package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MapTrackDemo extends AppCompatActivity {
    //Initialize variable
    EditText etSource,etDestination;
    Button btTrack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_track_demo);

        //Assign Variable
        etSource = findViewById(R.id.et_source);
        etDestination= findViewById(R.id.et_destination);
        btTrack = findViewById(R.id.bt_track);


        btTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Value from edit text

                String sSource = etSource.getText().toString().trim();
                String sDestination = etDestination.getText().toString().trim();

                //Check condition

                if(sSource.equals("") && sDestination.equals("")){
                    Toast.makeText(getApplicationContext(),"Enter both location",Toast.LENGTH_SHORT).show();
                }else {
                    //Display Track
                    Displaytrack(sSource,sDestination);
                }


            }
        });
    }

    private void Displaytrack(String sSource, String sDestination) {
        //If device dont have maps install redirect to playstore
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + sSource + "/" + sDestination);

            // Intialize Intent with action view
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            // Set  package
            intent.setPackage("com.google.android.apps.maps");
            //Set Flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Start Activity
            startActivity(intent);
        } catch (ActivityNotFoundException e){
            //When google map is not installed
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            // Intialize intent with action view
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            //Set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Start Activity
            startActivity(intent);
        }
    }
}