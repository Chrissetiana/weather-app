package com.chrissetiana.weatherreport;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

    private static String readStream(InputStream inputStream) {
        return null;
    }
}
