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

public class WeatherQuery {

    public static String fetchData(String src) {
        URL url = buildUrl(src);
        String response = null;

        try {
            response = buildHttp(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    private static WeatherActivity getJSONData(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }

        try {
            JSONObject object = new JSONObject(data);
            String code = object.getString("cod");
            JSONArray list = object.getJSONArray("list");

            for (int i = 0; i < list.length(); i++) {
                JSONObject element = list.getJSONObject(i);

                JSONObject temp = element.getJSONObject("temp");
                int min = temp.getInt("min");
                int max = temp.getInt("max");

                JSONArray weather = element.getJSONArray("weather");
                JSONObject desc = weather.getJSONObject(0);
                String main = desc.getString("main");

                return new WeatherActivity(code, main, min, max);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

/*
min: list[] temp{} min# max# (roundoff)
max: list[] temp{} max# (roundoff)
desc: list[] weather[] main""
code to check if it exists: cod""
 */