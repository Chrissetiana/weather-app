package com.chrissetiana.weatherreport;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static String SRC = "https://";
    TextView weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherData = findViewById(R.id.weather_data);

        WeatherAsyncTask task = new WeatherAsyncTask();
        task.execute(SRC);
    }

    private class WeatherAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            if (strings.length < 1 || strings[0] == null) {
                return null;
            }

            return WeatherQuery.fetchData(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                weatherData.setText(s);
            }
        }
    }
}
