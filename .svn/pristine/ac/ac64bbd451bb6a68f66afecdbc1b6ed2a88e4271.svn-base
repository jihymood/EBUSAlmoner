package com.xpro.ebusalmoner.baseapi;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: AtyContainer
 * @Description: Activtiy的创建，销毁
 * @author: houyang
 * @date: 2016年9月20日 下午7:15:42
 */
public class AtyContainer {
    private AtyContainer() {
    }

    private static AtyContainer instance = new AtyContainer();
    private static List<Activity> activityStack = new ArrayList<Activity>();

    public static AtyContainer getInstance() {
        return instance;
    }

    public void addActivity(Activity aty) {
        activityStack.add(aty);
    }

    public void removeActivity(Activity aty) {
        activityStack.remove(aty);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivityButLast() {
        int size = activityStack.size();
        Activity a = activityStack.get(size - 1);
        for (int i = 0; i < size - 1; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
        activityStack.add(a);
    }

    /**
     * 结束中间Activity
     */
    public void finishCenterActivity() {
        int size = activityStack.size();
        if (size > 2) {
            for (int i = size - 2; i > 0; i--) {
                activityStack.get(i).finish();
                activityStack.remove(i);
            }
        }
    }

    /**
     * 结束最新n个Activity
     */
    public void finishLastestActivityByCount(int count) {
        int size = activityStack.size();
        if (count >= size) {
            finishAllActivity();
            return;
        }
        for (int i = size - 1; i >= size - count; i--) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
                activityStack.remove(i);
            }
        }

    }
}
