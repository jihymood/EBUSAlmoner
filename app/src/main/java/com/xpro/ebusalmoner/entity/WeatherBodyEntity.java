package com.xpro.ebusalmoner.entity;

/**
 * Created by houyang on 2017/1/22.
 */
public class WeatherBodyEntity {
    private String weather;

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "WeatherBodyEntity{" +
                "weather='" + weather + '\'' +
                '}';
    }
}
