package com.example.navigationdrawer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.ArrayList;
import java.util.Arrays;

public class RatingsFragment extends Fragment implements RatingDialogListener {
    final static String TAG = "RATINGS";
    ArrayList<Rating> allratings ;
    private FloatingActionButton addrating;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_ratings,container,false);
        addrating = rootView.findViewById(R.id.add_ratings);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_ratings);
        allratings = new ArrayList<Rating>();

        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("ratings");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allratings = new ArrayList<Rating>();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    allratings.add(ds.getValue(Rating.class));
                    Log.d("SNAPSHOT", ds.getValue(Rating.class).comment);
                }

                RatingsAdapter adapter = new RatingsAdapter(allratings);
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













        addrating.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return rootView;
    }
    private void showDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")

                .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                .setDefaultRating(2)
                .setTitle("Rate this application")
                .setDescription("Please select some stars and give your feedback")
                .setCommentInputEnabled(true)
                .setDefaultComment("This app is pretty cool !")
                .setStarColor(R.color.starColor)
                .setHint("Please write your comment here ...")
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setCommentTextColor(R.color.commentTextColor)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(getActivity())
                .setTargetFragment(this,0)
                 // only if listener is implemented by fragment
                .show();
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int i, String s) {
        Log.d(TAG, s);
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("ratings");
        myRef.push().setValue(new Rating(s,i));



    }
}
