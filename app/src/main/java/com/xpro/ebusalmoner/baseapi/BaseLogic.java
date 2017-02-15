package com.xpro.ebusalmoner.baseapi;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.xpro.ebusalmoner.constants.Constants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

/**
 * @ClassName: BaseLogic
 * @Description: 发送请求基类
 * @author: houyang
 * @date: 2016年9月23日 上午10:36:16
 */
public class BaseLogic {
    private static final String TAG = "BaseLogic";
    public Handler handler;
    private Callback.Cancelable cancelable;

    public BaseLogic(Handler handler) {
        this.handler = handler;
    }

    public void doCancel() {
        if (cancelable != null) {
            cancelable.cancel();
        }
    }

    public void doRequest(RequestParams params, String type, final int flag) {
        Log.e("requestAddress**", params.toString());
        Log.e(TAG, "doRequest: " + params.getQueryStringParams().toString());

        type = type.toLowerCase();
        if (type.equals("get")) {
            cancelable = x.http().get(params,
                    new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            doData(result, flag);
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            // 打印请求错误的详细信息
                            Log.e(TAG, "ex = " + ex.getMessage());
                            ex.printStackTrace();
                            doResponseError(flag);
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                            Log.e("onCancelled",
                                    "*********** onCancelled ***********");
                        }

                        @Override
                        public void onFinished() {
                            Log.e("onFinished",
                                    "*********** onFinished ***********");
                        }
                    });
        } else if (type.equals("post")) {
            cancelable = x.http().post(params,
                    new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            doData(result, flag);
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            // 打印请求错误的详细信息
                            Log.e(TAG, "ex = " + ex.getMessage());
                            ex.printStackTrace();
                            doResponseError(flag);
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                            Log.e("onCancelled",
                                    "*********** onCancelled ***********");
                        }

                        @Override
                        public void onFinished() {
                            Log.e("onFinished",
                                    "*********** onFinished ***********");
                        }
                    });
        }
    }

    public void doData(String result, int flag) {
        Log.e("onSuccess", "*********** onSuccess ***********" + result);
        doResponse(result, flag);
    }

    public void doResponse(String result, int flag) {
    }

    public void doResponse(Map<String, Object> map, int flag) {
    }

    public void doResponseError(int flag) {
        Log.e("onError", "*********** onError ***********");
        Message msg = new Message();
        msg.what = Constants.HANDLE_WHAT_NET_ERROR;
        msg.obj = "网络异常，请检查网络后重试";
        msg.arg1 = flag; // 请求标识
        handler.sendMessage(msg);
    }
}
