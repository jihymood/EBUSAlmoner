package com.xpro.ebusalmoner.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xpro.ebusalmoner.R;


/**
 * Created by zhangcz on 2016/1/27.
 */
public class DialogUtils {
    /**
     * 得到自定义的 waitingDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createWaitingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view

        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局

        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);// main.xml中的ImageView
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字

        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);// 加载动画

        spaceshipImage.startAnimation(hyperspaceJumpAnimation);// 使用ImageView显示动画

        if (msg != null) {
            tipTextView.setText(msg);// 设置加载信息
        }

        Dialog waitingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        waitingDialog.setCancelable(false);// 不可以用“返回键”取消
        waitingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局

        return waitingDialog;

    }
}
