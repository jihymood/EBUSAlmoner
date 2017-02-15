package com.xpro.ebusalmoner.widget;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.DatePicker;

/**
 * 只显示年月日期弹出框 设置标题
 *
 * @author admin
 */
public class CommonDatePickerDialog extends DatePickerDialog {


    public CommonDatePickerDialog(Context context, OnDateSetListener callBack,
                                  int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
        // TODO Auto-generated constructor stub
    }

    @SuppressLint("NewApi")
    public CommonDatePickerDialog(Context context, int theme,
                                  OnDateSetListener callBack, int year, int monthOfYear,
                                  int dayOfMonth) {
        super(context, theme, callBack, year, monthOfYear, dayOfMonth);

        this.setTitle(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");

//		((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0))
//				.getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        this.setTitle(year + "年" + (month + 1) + "月" + day + "日");
    }


}
