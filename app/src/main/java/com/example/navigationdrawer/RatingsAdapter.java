package com.example.navigationdrawer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;

public class RatingsAdapter extends RecyclerView.Adapter<RatingsAdapter.ViewHolder> {
    private ArrayList<Rating> allratings;

    @NonNull
    @Override
    public RatingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.ratings_card, parent, false);
        return new RatingsAdapter.ViewHolder(listItem);
    }

    public RatingsAdapter(ArrayList<Rating> allratings) {
        this.allratings = allratings;
    }

    @Override
    public void onBindViewHolder(@NonNull RatingsAdapter.ViewHolder holder, int position) {
        holder.comment.setText(allratings.get(position).comment);
        holder.ratings.setRating(allratings.get(position).val.floatValue());

    }

    @Override
    public int getItemCount() {
        return allratings.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView comment;
        public ScaleRatingBar ratings;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.comment =itemView.findViewById(R.id.card_comment);
            this.ratings =itemView.findViewById(R.id.simpleRatingBar);
        }
    }
}
