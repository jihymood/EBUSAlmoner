package com.xpro.ebusalmoner.constants;

import android.content.Context;

import com.xpro.ebusalmoner.utils.PreferencesUtils;

/**
 * @ClassName: HttpUrls
 * @Description: url类
 * @author: houyang
 * @date: 2016年9月23日 上午10:44:54
 */
public class HttpUrls {

    // 按小时更新动态获取天气
    public static final String WEATHER = "http://apis.baidu.com/heweather/weather/free";
    public static final String WEATHER_CHINA = "http://www.weather.com.cn/data/cityinfo/101190101.html";

    //    public static final String url = "http://192.168.0.171:8080/EBUS/app?";
    public static final String url = "http://192.168.10.102:8080/EBUS/app?";
//    public static final String url = "http://101.200.63.211:8080/EBUS/app?";

    public static final String EBUSIP = "101.200.63.211";
    public static final String EBUSSERVER = "/EBUS/webSocketServer?";
    public static final String PERSONID = "personId=";
    public static final String EBUSID = "25256";


    public static String socketUrl(Context context, String ip, String server, String personId) {
        if (null == ip || "".equals(ip)) {
            ip = EBUSIP;
        }
        String port = "8080";
        if (null == server || "".equals(server)) {
            server = EBUSSERVER;
        }
        if (null == personId || "".equals(personId)) {
            personId = EBUSID;
        }
        String url = "ws://" + ip + ":" + port + server + PERSONID + personId;
        return url;
    }

    public static String baseUrl(Context context) {
        String ip = PreferencesUtils.getString(context, "changeip");
        if (null == ip || "".equals(ip)) {
            ip = "101.200.63.211";
//            ip = "192.168.10.102";
//            ip = "192.168.0.105";
//            ip = "192.168.0.197";
        }
        String port = PreferencesUtils.getString(context, "port");
        if (null == port || "".equals(port)) {
            port = "8080";
        }

        String baseUrl1 = "http://" + ip + ":" + port + "/EBUS/app?";
        return baseUrl1;
    }

    /**
     * 图片访问路径
     */
    public static String showImageUrl(Context context) {
        String ip = PreferencesUtils.getString(context, "changeip");
        if (null == ip || "".equals(ip)) {
//            ip = "101.200.63.211";
//            ip = "192.168.10.102";
            ip = "192.168.0.105";
//            ip = "192.168.0.197";
        }
        String port = PreferencesUtils.getString(context, "port");
        if (null == port || "".equals(port)) {
            port = "8080";
        }

        String baseUrl1 = "http://" + ip + ":" + port;
        return baseUrl1;
    }

    /**
     * 图片上传路径
     */
    public static String imageUrl(Context context) {
        String ip = PreferencesUtils.getString(context, "changeip");
        if (null == ip || "".equals(ip)) {
//            ip = "101.200.63.211";
//            ip = "192.168.10.102";
            ip = "192.168.0.105";
//            ip = "192.168.0.197";
        }
        String port = PreferencesUtils.getString(context, "port");
        if (null == port || "".equals(port)) {
            port = "8080";
        }

        String baseUrl1 = "http://" + ip + ":" + port + "/EBUS/fileUpload?";
        return baseUrl1;
    }






    public static String baseUrl114(Context context) {
        String ip = PreferencesUtils.getString(context, "changeip");
        if (null == ip || "".equals(ip)) {
            ip = "192.168.10.102";
        }
        String port = PreferencesUtils.getString(context, "port");
        if (null == port || "".equals(port)) {
            port = "8080";
        }

        String baseUrl1 = "http://" + ip + ":" + port + "/EBUS/app?";
        return baseUrl1;
    }


    public static String baseSocketUrl(Context context) {
        String ip = PreferencesUtils.getString(context, "changeip");
        if (null == ip || "".equals(ip)) {
//            ip = "101.200.63.211";
//            ip = "192.168.10.102";
//            ip = "192.168.0.105";
            ip = "192.168.0.197";
        }
        String port = PreferencesUtils.getString(context, "port");
        if (null == port || "".equals(port)) {
            port = "8080";
        }
        String baseUrl1 = "ws://"+ip+":8080/EBUS/webSocketServer?personId=25256";
        return baseUrl1;
    }


}
