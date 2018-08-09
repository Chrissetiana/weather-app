package com.chrissetiana.weatherreport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.TimeZone;

public class WeatherQuery {

    /* use OpenWeatherAPI when everything is working */

    private static final long TIME_IN_SECONDS = 1000;
    private static final long TIME_IN_MINUTES = TIME_IN_SECONDS * 60;
    private static final long TIME_IN_HOURS = TIME_IN_MINUTES * 60;
    private static final long TIME_IN_DAYS = TIME_IN_HOURS * 24;
    private static final String SRC = "https://andfun-weather.udacity.com/staticweather";
    private static final int DAYS = 14;

    public static String[] fetchData() {
        URL url = buildUrl(SRC);
        String response = null;

        try {
            response = buildHttp(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getJSONData(response);
    }

    private static URL buildUrl(String str) {
        URL url = null;
        try {
            url = new URL(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String buildHttp(URL url) throws IOException {
        String response = null;

        if (url == null) {
            return response;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();

            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                response = readStream(inputStream);
            } else {
                Log.d("WeatherQuery", "code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }
        return response;
    }

    private static String readStream(InputStream stream) {
        try {
            StringBuilder builder = new StringBuilder();
            if (stream != null) {
                InputStreamReader streamReader = new InputStreamReader(stream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(streamReader);
                String line = reader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String[] getJSONData(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }

        try {
            JSONObject object = new JSONObject(data);

            String code = object.getString("cod");
            if (object.has(code)) {
                int errCode = object.getInt(code);
                switch (errCode) {
                    case HttpURLConnection.HTTP_OK:
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        return null;
                    default:
                        return null;
                }
            }

            JSONArray list = object.getJSONArray("list");

            String[] count = new String[list.length()];

            long localDate = System.currentTimeMillis();
            long utcDate = dateFormatUTC(localDate);
            long startDay = dateFormatNormal(utcDate);

            for (int i = 0; i < list.length(); i++) {
                String date;
                String highAndLow;
                long dateTime;

                JSONObject element = list.getJSONObject(i);

                // dateTime = startDay + TIME_IN_DAYS * i;

                JSONObject temp = element.getJSONObject("temp");
                double min = temp.getDouble("min");
                double max = temp.getDouble("max");

                JSONArray weather = element.getJSONArray("weather");
                JSONObject desc = weather.getJSONObject(0);
                String main = desc.getString("main");

                return count;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long dateFormatLocal(long date) {
        return date - TimeZone.getDefault().getOffset(date);
    }

    private static long dateFormatUTC(long date) {
        return date + TimeZone.getDefault().getOffset(date);
    }

    private static long dateFormatNormal(long date) {
        return date / TIME_IN_DAYS * TIME_IN_DAYS;
    }

    private static String dateFormat(long dateTime, boolean b) {
        return null;
    }
}

/*
lon: city{} coord{} lon#
lat: city{} coord{} lat#
min: list[] temp{} min# (roundoff)
max: list[] temp{} max# (roundoff)
desc: list[] weather[] main""
code: cod""
 */