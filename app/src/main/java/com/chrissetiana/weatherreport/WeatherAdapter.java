package com.chrissetiana.weatherreport;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private WeatherActivity weather;
    private static String[] weatherData;

    public WeatherAdapter() {
    }

    public void setWeatherData(String[] data) {
        weatherData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_layout, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        String current = weatherData[position];
        holder.bind(current);
    }

    @Override
    public int getItemCount() {
        if (weatherData == null) {
            return 0;
        }
        return weatherData.length;
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {
        static TextView weatherDay;
        TextView weatherStatus;
        TextView weatherMin;
        TextView weatherMax;

        WeatherViewHolder(View view) {
            super(view);
            weatherDay = view.findViewById(R.id.weather_day);
            weatherStatus = view.findViewById(R.id.weather_status);
            weatherMin = view.findViewById(R.id.weather_min);
            weatherMax = view.findViewById(R.id.weather_max);
        }

        void bind(String str) {
            weatherDay.setText(str);
            /*weatherStatus.setText();
            weatherMin.setText();
            weatherMax.setText();*/
        }
    }
}
