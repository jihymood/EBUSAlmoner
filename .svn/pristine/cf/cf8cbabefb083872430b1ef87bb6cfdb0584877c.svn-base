package com.xpro.ebusalmoner.widget;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.xpro.ebusalmoner.utils.NetUtil;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

public class MainApplication extends Application {
    private static Application mApplication;
    public static int mNetWorkState;

    public static synchronized Application getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false);

        JPushInterface.setDebugMode(true);// true为调试模式，会显示多余的信息
        JPushInterface.init(this);

        mApplication = this;
        initData();
        SDKInitializer.initialize(this);

    }

    public void initData() {
        mNetWorkState = NetUtil.getNetworkState(this);
    }
}
