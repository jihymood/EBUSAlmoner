package com.xpro.ebusalmoner.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 *
 * @ClassName: CustomDialog
 * @Description: Activtiy的创建于销毁
 * @author: houyang
 * @date: 2016年9月20日 下午7:14:22
 */
public class CustomDialog1 extends Dialog {
	private int layoutResourceId;

	public CustomDialog1(Context context) {
		super(context);
	}

	public CustomDialog1(Context context, int themeResId, int layoutResourceId) {
		super(context, themeResId);

		this.layoutResourceId = layoutResourceId;
	}

	protected CustomDialog1(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(layoutResourceId);
	}
}
