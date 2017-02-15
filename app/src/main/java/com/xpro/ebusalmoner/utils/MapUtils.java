package com.xpro.ebusalmoner.utils;

import com.baidu.mapapi.model.LatLng;
import com.xpro.ebusalmoner.constants.Constants;

import java.math.BigDecimal;

/**
 * Created by houyang on 2016/12/6.
 * 用于获得地图缩放等级
 */
public class MapUtils {

    //用于地图缩放等级集合
    static int[] distance = new int[]{10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 25000, 50000, 100000, 200000, 500000, 1000000, 2000000, 1000000000};
    static Float[] distanceZoom = new Float[]{20f, 20F, 19F, 18F, 17f, 16F, 15F, 14F, 13F, 12F, 11F, 10F, 9F, 8F, 7F, 6F, 5F, 4F, 3F, 2F};


    /**
     * 获取地图缩放等级
     *
     * @param distances
     * @return
     */
    public static Float getZoom(double distances, int flag) {
        switch (flag) {
            //homepage中获得缩放等级
            case Constants.HOMEPAGEDISTANCE:
                for (int i = 0; i < distance.length; i++) {
                    if (distances > distance[i] && distances < distance[i + 1]) {
                        return distanceZoom[i];
                    }
                }
                break;

            case Constants.SHIFTDETAILDISTANCE:
                //shiftdetail（班次详情）中获得缩放等级
                for (int i = 0; i < distance.length; i++) {
                    if (distances > distance[i] && distances < distance[i + 1]) {
                        //如果i<5，则强制将i变成5（缩放等级为18F）
                        if (i <= 5) {
                            i = 5;
                        }
                        return distanceZoom[i - 1];
                    }
                }
                break;
        }

        return 10F;
    }

    /**
     * 两点间的距离
     * @param latLng
     * @param latLng2
     * @return
     */
    public static double distanceStr(LatLng latLng, LatLng latLng2) {
        double R = 6371;// 地球半径
        double juli = 0.0;
        double dLat = Double.valueOf(new BigDecimal(String
                .valueOf((latLng.latitude - latLng2.latitude)))
                .multiply(new BigDecimal(String.valueOf(Math.PI)))
                .divide(new BigDecimal(String.valueOf(180)), 10,
                        BigDecimal.ROUND_HALF_EVEN).toString());
        double dLon = Double.valueOf(new BigDecimal(String
                .valueOf((latLng.longitude - latLng2.longitude)))
                .multiply(new BigDecimal(String.valueOf(Math.PI)))
                .divide(new BigDecimal(String.valueOf(180)), 10,
                        BigDecimal.ROUND_HALF_EVEN).toString());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(latLng.latitude * Math.PI / 180)
                * Math.cos(latLng2.latitude * Math.PI / 180)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        juli = (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))) * R * 1000;
        System.out.println(juli);// juli两个坐标点之间的直线距离
        return juli;
    }

    /**
     * 计算两点间距离是否大于r半径
     * @param latLng
     * @param latLng2
     * @param r
     * @return
     */
    public static boolean isLocation(LatLng latLng, LatLng latLng2, Integer r) {
        double R = 6371;// 地球半径
        double distance = 0.0;
        double dLat = Double.valueOf(new BigDecimal(String
                .valueOf((latLng.latitude - latLng2.latitude)))
                .multiply(new BigDecimal(String.valueOf(Math.PI)))
                .divide(new BigDecimal(String.valueOf(180)), 10,
                        BigDecimal.ROUND_HALF_EVEN).toString());
        double dLon = Double.valueOf(new BigDecimal(String
                .valueOf((latLng.longitude - latLng2.longitude)))
                .multiply(new BigDecimal(String.valueOf(Math.PI)))
                .divide(new BigDecimal(String.valueOf(180)), 10,
                        BigDecimal.ROUND_HALF_EVEN).toString());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(latLng.latitude * Math.PI / 180)
                * Math.cos(latLng2.latitude * Math.PI / 180)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        distance = (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))) * R * 1000;
        System.out.println(distance);// distance两个坐标点之间的直线距离
        if (distance > Double.valueOf(String.valueOf(r))) {// r为设置的半径
            return true;
        }
        return false;
    }
}
