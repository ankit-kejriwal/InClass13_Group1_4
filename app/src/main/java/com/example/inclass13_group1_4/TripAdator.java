package com.example.inclass13_group1_4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TripAdator extends RecyclerView.Adapter<TripAdator.ViewHolder> {
    ArrayList<Trip> mData;

    public TripAdator(ArrayList<Trip> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = mData.get(position);
        holder.textViewName.setText(trip.getTripname());
        City city = trip.getCity();
        holder.textViewCity.setText(city.getDescription());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewCity;
        Button buttonplac;
        Button buttonloc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tv_trip_name);
            textViewCity = itemView.findViewById(R.id.tv_description);
            buttonloc = itemView.findViewById(R.id.buttonloc);
            buttonplac = itemView.findViewById(R.id.buttonplac);
            buttonplac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
