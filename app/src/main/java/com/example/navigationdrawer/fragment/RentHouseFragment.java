package com.example.navigationdrawer.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.navigationdrawer.util.GpsTracker;
import com.example.navigationdrawer.activity.NewHouse;
import com.example.navigationdrawer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class RentHouseFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    TextView nameTextView,address,location,amount;
    ImageView image;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();



        View rootView =  inflater.inflate(R.layout.fragment_renthouse,container,false);
        final TextView nameTextView = rootView.findViewById(R.id.add_name);
        final TextView address = rootView.findViewById(R.id.add_address);
        final TextView location = rootView.findViewById(R.id.add_location);
        final TextView amount = rootView.findViewById(R.id.add_amount);
        final Button loc_button = (Button)rootView.findViewById(R.id.location_button);
        Button cam_button = (Button)rootView.findViewById(R.id.cam_but);
        image =  rootView.findViewById(R.id.cam_img);

        final NewHouse house = new NewHouse(nameTextView.getText().toString(),address.getText().toString(),location.getText().toString(),amount.getText().toString());

        loc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                GpsTracker gpsTracker = new GpsTracker(getActivity());
                if (gpsTracker.canGetLocation()) {
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();
                    Log.d("LOCATION", String.valueOf(latitude));
                    Log.d("LOCATION", String.valueOf(longitude));
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        Toast.makeText(getActivity(), addressList.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                        location.setText(addressList.get(0).getAddressLine(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity(),"No Permissions Provided",Toast.LENGTH_LONG).show();
                }

            }
        });


        cam_button.setOnClickListener(new View.OnClickListener() {
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
        rootView.findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("CLICk", house.getAddress());
                myRef.child("ads").child(nameTextView.getText().toString()).child("name").setValue(nameTextView.getText().toString());
                myRef.child("ads").child(nameTextView.getText().toString()).child("address").setValue(address.getText().toString());
                myRef.child("ads").child(nameTextView.getText().toString()).child("location").setValue(location.getText().toString());
                myRef.child("ads").child(nameTextView.getText().toString()).child("amount").setValue(amount.getText().toString());
                nameTextView.setText("");
                address.setText("");
                location.setText("");
                amount.setText("");
                Toast.makeText(getActivity(),"House added sucessfully",Toast.LENGTH_LONG).show();
            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            image.setImageBitmap(imageBitmap);

        }
    }
}
