package com.xpro.ebusalmoner.entity;

/**
 * Created by houyang on 2017/1/22.
 */
public class WeatherMainEntity {
    private WeatherBodyEntity weatherinfo;

    public WeatherBodyEntity getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(WeatherBodyEntity weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    @Override
    public String toString() {
        return "WeatherMainEntity{" +
                "weatherinfo=" + weatherinfo +
                '}';
    }
}
