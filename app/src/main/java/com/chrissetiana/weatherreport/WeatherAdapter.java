package com.chrissetiana.weatherreport;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private static String[] weatherData;
    private final WeatherOnClickHandler clickHandler;
    private WeatherActivity weather;

    WeatherAdapter(WeatherOnClickHandler handler) {
        clickHandler = handler;
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

    public void setWeatherData(String[] data) {
        weatherData = data;
        notifyDataSetChanged();
    }

    public interface WeatherOnClickHandler {
        void onClick(String today);
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        TextView weatherData;

        WeatherViewHolder(View view) {
            super(view);
            weatherData = view.findViewById(R.id.weather_data);
        }

        void bind(String str) {
            weatherData.setText(str);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String weatherToday = WeatherAdapter.weatherData[position];
            clickHandler.onClick(weatherToday);
        }
    }
}
