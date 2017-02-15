package com.xpro.ebusalmoner.entity;


import com.alibaba.fastjson.JSON;


/**
 * Created by houyang on 2016/12/20.
 * 二维码解析
 */
public class CodeAndCarParse {
    public static MainEntity mainParse(String json) {
        MainEntity mainEntity = JSON.parseObject(json, MainEntity.class);
        return mainEntity;
    }
}
