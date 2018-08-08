package com.chrissetiana.weatherreport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private int count;
    private int items;

    WeatherAdapter(int numOfItems) {
        count = 0;
        items = numOfItems;
    }

    @NonNull
    @Override
    public WeatherAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutToUse = R.layout.activity_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutToUse, parent, false);
        WeatherViewHolder viewHolder = new WeatherViewHolder(view);
        count++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.WeatherViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items;
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView weatherDay;
        TextView weatherStatus;
        TextView weatherMin;
        TextView weatherMax;

        WeatherViewHolder(View itemView) {
            super(itemView);
            weatherDay = itemView.findViewById(R.id.weather_day);
            weatherStatus = itemView.findViewById(R.id.weather_status);
            weatherMin = itemView.findViewById(R.id.weather_min);
            weatherMax = itemView.findViewById(R.id.weather_max);
        }

        void bind(int position) {
            /*weatherDay.setText();
            weatherStatus.setText();
            weatherMin.setText();
            weatherMax.setText();*/
        }
    }
}
