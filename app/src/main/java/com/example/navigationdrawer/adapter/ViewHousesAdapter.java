package com.example.navigationdrawer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.NewHouse;
import com.example.navigationdrawer.R;

import java.util.ArrayList;

public class ViewHousesAdapter extends RecyclerView.Adapter<ViewHousesAdapter.ViewHolder> {
    private ArrayList<NewHouse> houses;

    public ViewHousesAdapter(ArrayList<NewHouse> houses) {
        this.houses = houses;
    }

    @NonNull
    @Override
    public ViewHousesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.houses_cards, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHousesAdapter.ViewHolder holder, int position) {
        NewHouse eachuser = houses.get(position);
        holder.name.setText(eachuser.getName());
        holder.address.setText(eachuser.getAddress());
        holder.amount.setText(eachuser.getAmount());
        holder.location.setText(eachuser.getLocation());
    }

    @Override
    public int getItemCount() {
        return houses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,address,location,amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name =itemView.findViewById(R.id.card_name);
            this.address =itemView.findViewById(R.id.card_address);
            this.location =itemView.findViewById(R.id.card_location);
            this.amount =itemView.findViewById(R.id.card_amount);
        }
    }
}
