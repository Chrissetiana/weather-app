package com.chrissetiana.weatherreport;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // private static final String SRC = "https://andfun-weather.udacity.com/weather";
    private static final String SRC = "https://andfun-weather.udacity.com/staticweather";
    TextView weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherData = findViewById(R.id.weather_data);
        loadData();
    }

    private void loadData() {
        new WeatherAsyncTask().execute(SRC);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_refresh:
                loadData();
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
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

            setViews();
        }

        private void setViews() {
            weatherData.setText(WeatherActivity.getCode() + WeatherActivity.getDesc() + WeatherActivity.getMin() + WeatherActivity.getMax());
        }
    }
}
