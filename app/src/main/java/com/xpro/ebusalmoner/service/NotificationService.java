package com.xpro.ebusalmoner.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.xpro.ebusalmoner.MainActivity;
import com.xpro.ebusalmoner.R;

/**
 * Created by huangjh on 2017/1/22 0022 17:54
 */

public class NotificationService extends Service {
    // 获取消息线程
    private MessageThread messageThread = null;

    // 点击查看
    private Intent messageIntent = null;
    private PendingIntent messagePendingIntent = null;

    // 通知栏消息
    private int messageNotificationID = 1000;
    private Notification messageNotification = null;
    private NotificationManager messageNotificatioManager = null;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 初始化
//        messageNotification = new Notification();
//        messageNotification.icon = R.mipmap.ic_launcher;
//        messageNotification.tickerText = "新消息";
//        messageNotification.defaults = Notification.DEFAULT_SOUND;
        messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        messageIntent = new Intent(this, MainActivity.class);
        messagePendingIntent = PendingIntent.getActivity(this, 0, messageIntent, 0);

        // 开启线程
        messageThread = new MessageThread();
        messageThread.isRunning = true;
        messageThread.start();


//        Notification.Builder builder1 = new Notification.Builder(getApplicationContext());
//        builder1.setSmallIcon(R.mipmap.icon_clear); //设置图标
//        builder1.setTicker("显示第二个通知");
//        builder1.setContentTitle("通知"); //设置标题
//        builder1.setContentText("点击查看详细内容"); //消息内容
//        builder1.setWhen(System.currentTimeMillis()); //发送时间
//        builder1.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
//        builder1.setAutoCancel(true);//打开程序后图标消失
//        Intent intent =new Intent (getApplicationContext(),Center.class);
//        PendingIntent pendingIntent =PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
//        builder1.setContentIntent(pendingIntent);
//        Notification notification1 = builder1.build();
//        notificationManager.notify(124, notification1); // 通过通知管理器发送通知


        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 从服务器端获取消息
     */
    class MessageThread extends Thread {
        // 设置是否循环推送
        public boolean isRunning = true;

        public void run() {
            // while (isRunning) {
            try {
                // 间隔时间
                Thread.sleep(1000);
                // 获取服务器消息
                String serverMessage = getServerMessage();
                if (serverMessage != null && !"".equals(serverMessage)) {
                    // 更新通知栏
//                    messageNotification.setLatestEventInfo(getApplicationContext(), "新消息", "您有新消息。" + serverMessage, messagePendingIntent);
                    Notification notification = new Notification.Builder(getApplicationContext())
                            .setContentTitle("您有新消息。")
                            .setContentText("新消息")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .build();
                    messageNotificatioManager.notify(messageNotificationID, notification);
                    // 每次通知完，通知ID递增一下，避免消息覆盖掉
                    messageNotificationID++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // }
        }
    }

    @Override
    public void onDestroy() {
        // System.exit(0);
        messageThread.isRunning = false;
        super.onDestroy();
    }

    /**
     * 模拟发送消息
     *
     * @return 返回服务器要推送的消息，否则如果为空的话，不推送
     */
    public String getServerMessage() {
        return "NEWS!";
    }
}
