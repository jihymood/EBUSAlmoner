package com.xpro.ebusalmoner.baseapi;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.utils.ProgressDialogUtils;
import com.xpro.ebusalmoner.utils.ToastUtils;


/**
 *
 * @ClassName: BaseHandler
 * @Description: handler基类
 * @author: houyang
 * @date: 2016年9月21日 上午9:26:41
 */
public class BaseHandler extends Handler {

	public Context context;
	public BaseHandler(Context context) {
		this.context = context;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		doOther();
		switch (msg.what) {
			case Constants.HANDLE_WHAT_NET_ERROR:
				ProgressDialogUtils.dismissProgressBar();
				ToastUtils.showToast(context, msg.obj + "");
				break;

			default:
				doHandle(msg);
				break;
		}
	}

	public void doOther() {

	}

	public void doHandle(Message msg) {

	}
}
