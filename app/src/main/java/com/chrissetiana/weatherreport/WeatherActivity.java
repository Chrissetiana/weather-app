package com.chrissetiana.weatherreport;

public class WeatherActivity {

    private static String code;
    private static String desc;
    private static int min;
    private static int max;

    public WeatherActivity(String code, String desc, int min, int max) {
        this.code = code;
        this.desc = desc;
        this.min = min;
        this.max = max;
    }

    public static String getCode() {
        return code;
    }

    public static String getDesc() {
        return desc;
    }

    public static int getMin() {
        return min;
    }

    public static int getMax() {
        return max;
    }
}
