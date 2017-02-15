package com.xpro.ebusalmoner.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.baseapi.BaseActivity.OnCustomDialogConfirmListener;
import com.xpro.ebusalmoner.entity.BreakdownData_M;
import com.xpro.ebusalmoner.logic.TaskLogic;
import com.xpro.ebusalmoner.widget.CustomDialog1;

import java.util.List;


public class RouteAdapter2 extends BaseAdapter {

    private Context context;
    private List<BreakdownData_M> list;
    private LayoutInflater inflater;
    private TaskLogic logic;

    public RouteAdapter2(Context context, List<BreakdownData_M> list, TaskLogic logic) {
        super();
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.logic = logic;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        BreakdownData_M breakDown = list.get(position);

        final String driverId=breakDown.getId();
        final String trailerCode=breakDown.getTrailerCode();
        final String tel = breakDown.getDriverTel();
        Log.e("RouteAdapter2", driverId + " /" + trailerCode+" /"+tel);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.break_dowm_item2, null);
            holder.line = (TextView) convertView.findViewById(R.id.textView);
            holder.time = (TextView) convertView.findViewById(R.id.timeText);
            holder.number = (TextView) convertView.findViewById(R.id.numberText);
            holder.telImg = (TextView) convertView.findViewById(R.id.telImg);
            holder.driverTel = (TextView) convertView.findViewById(R.id.driverTel);
            holder.trailerTel = (TextView) convertView.findViewById(R.id.trailerTel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.line.setText(breakDown.getLineName() + "故障");
        holder.time.setText(breakDown.getHitchTime());
        holder.number.setText(breakDown.getCode());
        holder.telImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showConfirmTip("确定取消", new OnCustomDialogConfirmListener() {
                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub
                        // 取消接口
                        logic.cancelFaultVehicle(driverId,trailerCode);

                    }
                }, new OnCustomDialogConfirmListener() {

                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub
                    }
                });
            }
        });
        // 司机电话
        holder.driverTel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showConfirmTip("确定拨打电话", new OnCustomDialogConfirmListener() {

                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + tel));
                        context.startActivity(intent);

                    }
                }, new OnCustomDialogConfirmListener() {

                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub
                    }
                });
            }
        });

        holder.trailerTel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showConfirmTip("确定拨打电话", new OnCustomDialogConfirmListener() {

                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + tel));
                        context.startActivity(intent);
                    }
                }, new OnCustomDialogConfirmListener() {
                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub
                    }
                });
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView line;
        TextView time;
        TextView number;
        TextView telImg;
        TextView driverTel;
        TextView trailerTel;

    }

    /**
     * @param msg
     * @param confirm
     * @param cancel
     * @Title: showConfirmTip
     * @Description: 取消确定方法，均有点击返回
     * @return: void
     */
    public void showConfirmTip(String msg, final OnCustomDialogConfirmListener confirm, final
    OnCustomDialogConfirmListener cancel) {
        final Dialog customDialog = new CustomDialog1(context, R.style.CustomDialogTheme, R.layout.dialog_custom);
        customDialog.show();
        TextView content = (TextView) customDialog.findViewById(R.id.custom_dialog_content);
        TextView confirmBtn = (TextView) customDialog.findViewById(R.id.custom_dialog_confirm);
        TextView cancelBtn = (TextView) customDialog.findViewById(R.id.custom_dialog_cancel);
        content.setText(msg);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                confirm.onClick();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                cancel.onClick();
            }
        });
    }

}
