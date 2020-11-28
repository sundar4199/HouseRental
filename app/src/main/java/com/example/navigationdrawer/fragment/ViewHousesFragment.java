package com.example.navigationdrawer.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.NewHouse;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.adapter.ViewHousesAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewHousesFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<NewHouse> allHouse = new ArrayList<NewHouse>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




        View rootView =  inflater.inflate(R.layout.fragment_viewhouse,container,false);
        recyclerView = rootView.findViewById(R.id.viewhouse_recycler);

        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("ads");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allHouse = new ArrayList<NewHouse>();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                   allHouse.add(ds.getValue(NewHouse.class));
                    Log.d("SNAPSHOT", ds.getValue(NewHouse.class).name.toString());
                }

                Log.d("TAG", String.valueOf(allHouse.size()));
                ViewHousesAdapter adapter = new ViewHousesAdapter(allHouse);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });





        return rootView;
    }
}
