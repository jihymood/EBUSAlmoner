package com.xpro.ebusalmoner.entity;

import android.util.Log;

import com.alibaba.fastjson.JSON;


/**
 * Created by houyang on 2016/12/20.
 */
public class LoginParse {
    public static LoginMainEntity loginMainParse(String json) {
        LoginMainEntity loginMainEntity = JSON.parseObject(json, LoginMainEntity.class);
        Log.e("LoginParse", "json:"+json);
        return loginMainEntity;
    }
}
