package com.xpro.ebusalmoner.entity;

import com.alibaba.fastjson.JSON;


/**
 * Created by houyang on 2017/1/22.
 */
public class WeatherMainParse {
    public static WeatherMainEntity weatherMainParse(String json) {
        WeatherMainEntity weatherMainEntity = JSON.parseObject(json, WeatherMainEntity.class);
        return weatherMainEntity;
    }
}
