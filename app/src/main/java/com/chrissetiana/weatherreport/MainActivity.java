package com.chrissetiana.weatherreport;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String SRC = "https://andfun-weather.udacity.com/staticweather";
    private static final int LIST_ITEMS = 14;
    private WeatherAdapter adapter;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.weather_data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);

        adapter = new WeatherAdapter(LIST_ITEMS);
        recycler.setAdapter(adapter);

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

    class WeatherAsyncTask extends AsyncTask<String, Void, String> {
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
            }
        }
    }
}
