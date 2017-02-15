package com.xpro.ebusalmoner.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "MyReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {
			Bundle bundle = intent.getExtras();
			String title = bundle.getString(JPushInterface.EXTRA_TITLE);
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			// 显示极光推送的自定义推送
			Toast.makeText(context,
					"Message title:" + title + "  content:" + message,
					Toast.LENGTH_LONG).show();
			Log.e(TAG, "title:" + title + " " + "message:" + message);
		}
	}
}
