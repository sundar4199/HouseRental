package com.example.navigationdrawer.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.activity.NewHouse;
import com.example.navigationdrawer.R;

import java.util.ArrayList;

public class ViewHousesAdapter extends RecyclerView.Adapter<ViewHousesAdapter.ViewHolder> {
    private ArrayList<NewHouse> houses;
    private Context context;

    public ViewHousesAdapter(Context context, ArrayList<NewHouse> houses) {
        this.houses = houses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHousesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.houses_cards, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHousesAdapter.ViewHolder holder, int position) {
        final NewHouse eachuser = houses.get(position);
        holder.name.setText(eachuser.getName());
        holder.address.setText(eachuser.getAddress());
        holder.amount.setText(eachuser.getAmount());
        holder.location.setText(eachuser.getLocation());
        holder.location_redirect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+eachuser.getLocation());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return houses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name,address,location,amount;
        public Button location_redirect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name =itemView.findViewById(R.id.card_name);
            this.address =itemView.findViewById(R.id.card_address);
            this.location =itemView.findViewById(R.id.card_location);
            this.amount =itemView.findViewById(R.id.card_amount);
            this.location_redirect = itemView.findViewById(R.id.location_redirect);

        }


    }
}
