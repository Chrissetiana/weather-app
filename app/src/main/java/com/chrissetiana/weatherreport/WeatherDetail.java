package com.chrissetiana.weatherreport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class WeatherDetail extends AppCompatActivity {

    private String forecast;
    private TextView weatherDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        weatherDisplay = findViewById(R.id.weather_display);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                forecast = intent.getStringExtra(Intent.EXTRA_TEXT);
                weatherDisplay.setText(forecast);
            }
        }
    }

    private Intent WeatherIntent() {
        Intent intent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(forecast)
                .getIntent();

        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        MenuItem item = menu.findItem(R.id.menu_share);
        item.setIntent(WeatherIntent());
        return true;
    }
}
