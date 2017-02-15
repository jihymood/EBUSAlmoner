package com.xpro.ebusalmoner.utils;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xpro.ebusalmoner.R;


/**
 * @ClassName: ProgressDialogUtils
 * @Description: loading框工具类
 * @author: houyang
 * @date: 2016年9月21日 上午9:34:57
 */
public class ProgressDialogUtils extends Dialog {
    public static ProgressDialogUtils dialog;

    public ProgressDialogUtils(Context context) {
        super(context);
    }

    public ProgressDialogUtils(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        // 开始动画
        spinner.start();
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    public static ProgressDialogUtils showProgressDialog(int resId, Context context) {
        return showProgressDialog(context.getString(resId), context);
    }

    public static ProgressDialogUtils showProgressDialog(int resId, Context context, Boolean cancelable) {
        return showProgressDialog(context.getString(resId), context, cancelable);
    }

    public static ProgressDialogUtils showProgressDialog(String msg, Context context) {
        return showProgressDialog(msg, context, true);
    }

    public static ProgressDialogUtils showProgressDialog(String msg, Context context, Boolean cancelable) {
        dialog = new ProgressDialogUtils(context, R.style.loading_progress);
        dialog.setTitle("");
        dialog.setContentView(R.layout.progress_custom);
        if (msg == null || msg.length() == 0) {
            dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.message);
            txt.setText(msg);
        }
        // 按返回键是否取消
        dialog.setCancelable(cancelable);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        return dialog;
    }

    public static void setDialogMsg(String msg) {
        if (dialog != null && dialog.isShowing()) {
            dialog.setMessage(msg);
        }
    }

    public static void dismissProgressBar() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
