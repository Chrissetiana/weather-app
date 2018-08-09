package com.chrissetiana.weatherreport;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private WeatherAdapter adapter;
    private RecyclerView recycler;
    private ProgressBar progress;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.weather_data);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);

        adapter = new WeatherAdapter();
        recycler.setAdapter(adapter);

        emptyText = findViewById(R.id.list_empty);
        progress = findViewById(R.id.list_progress);

        loadData();
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
                progress.setVisibility(View.VISIBLE);
                adapter.setWeatherData(null);
                loadData();
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        showData();
        new WeatherAsyncTask().execute();
    }

    private void showData() {
        recycler.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.INVISIBLE);
    }

    private void showError() {
        recycler.setVisibility(View.INVISIBLE);
        emptyText.setVisibility(View.VISIBLE);
    }

    class WeatherAsyncTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {
            if (params.length == 0 || params[0] == null) {
                return null;
            }

            try {
                return WeatherQuery.fetchData();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] data) {
            progress.setVisibility(View.INVISIBLE);

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                if (data != null) {
                    showData();
                    adapter.setWeatherData(data);
                } else {
                    showError();
                    emptyText.setText(getString(R.string.no_result));
                }
            } else {
                showError();
                emptyText.setText(getString(R.string.no_conn));
            }
        }
    }
}
