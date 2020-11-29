package com.example.navigationdrawer.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.util.ImageSaver;
import com.example.navigationdrawer.util.OnGetDataListener;
import com.example.navigationdrawer.util.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View rootView = inflater.inflate(R.layout.fragment_profile,container, false);


        final SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String uname = sharedPref.getString("uname","");
        Log.d("UNAME", uname);
        if (uname != ""){
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users").child(uname);
            readData(myRef, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {


                    User user = dataSnapshot.getValue(User.class);
                    Log.d("OnSuccess", "Started");

                    TextView email = (TextView)rootView.findViewById(R.id.profile_email);
                    email .setText(user.getEmail());
                    TextView name = (TextView)rootView.findViewById(R.id.profile_name);
                    name .setText(user.getName());
                    TextView uname = (TextView)rootView.findViewById(R.id.profile_username);
                    uname .setText(user.getUsername());
                    TextView password = (TextView)rootView.findViewById(R.id.profile_password);
                    password .setText(user.getPassword());

                    Bitmap bitmap = new ImageSaver(getActivity()).
                            setFileName(user.getUsername()+".png").
                            setDirectoryName("images").
                            load();
                    ImageView profile = (ImageView)rootView.findViewById(R.id.profile_pic);
                    profile.setImageBitmap(bitmap);

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
        }

        return rootView;
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
