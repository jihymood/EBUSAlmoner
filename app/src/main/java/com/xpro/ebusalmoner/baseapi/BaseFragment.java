package com.xpro.ebusalmoner.baseapi;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.constants.HttpUrls;
import com.xpro.ebusalmoner.widget.CustomDialog1;

import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.drafts.Draft_75;
import org.java_websocket.drafts.Draft_76;
import org.xutils.x;


/**
 * @ClassName: BaseFragment
 * @Description: Fragment基类
 * @author: houyang
 * @date: 2016年9月21日 上午9:25:00
 */
public class BaseFragment extends Fragment {
    private boolean injected = false;
    Context context;

    public WebSocketClient client;// 连接客户端
    public DraftInfo selectDraft;// 连接协议
    public String address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DraftInfo[] draftInfos = {new DraftInfo("WebSocket协议Draft_17", new Draft_17()), new DraftInfo("WebSocket协议Draft_10", new Draft_10()), new DraftInfo("WebSocket协议Draft_76", new Draft_76()), new DraftInfo("WebSocket协议Draft_75", new Draft_75())};// 所有连接协议
        selectDraft = draftInfos[0];// 默认选择第一个连接协议
//        ServerInfo[] serverInfos = {new ServerInfo("连接Java Web后台", "ws://192.168.10.102:8080/EBUS/webSocketServer?personId=25256"), new ServerInfo
//        ServerInfo[] serverInfos = {new ServerInfo("连接Java Web后台", "ws://192.168.10.102:8080/EBUS/webSocketServer?personId=25256"), new ServerInfo("连接Java Application后台", "ws://192.168.1" + "" +
        ServerInfo[] serverInfos = {new ServerInfo("连接Java Web后台", HttpUrls.baseSocketUrl(getActivity())), new ServerInfo("连接Java Application后台", "ws://192" +
                ".168.1" + "" +
                ".104:8887")};// 所有连接后台
        address = serverInfos[0].serverAddress;
        Log.e("wlf", "连接地址：" + address);

        WebSocketImpl.DEBUG = true;
        System.setProperty("java.net.preferIPv6Addresses", "false");
        System.setProperty("java.net.preferIPv4Stack", "true");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    /**
     * @param msg
     * @Title: showTip
     * @Description: 确定方法，无点击返回
     * @return: void
     */
    public void showTip(String msg) {
        final Dialog customDialog = new CustomDialog1(getActivity(), R.style.CustomDialogTheme, R.layout.dialog_custom);
        customDialog.show();
        TextView content = (TextView) customDialog.findViewById(R.id.custom_dialog_content);
        TextView divider = (TextView) customDialog.findViewById(R.id.custom_dialog_divider);
        TextView confirm = (TextView) customDialog.findViewById(R.id.custom_dialog_confirm);
        TextView cancel = (TextView) customDialog.findViewById(R.id.custom_dialog_cancel);
        content.setText(msg);
        divider.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }

    /**
     * @param msg
     * @param callBack
     * @Title: showTip
     * @Description: 确定方法，有点击返回
     * @return: void
     */
    public void showTip(String msg, final OnCustomDialogConfirmListener callBack) {
        final Dialog customDialog = new CustomDialog1(getActivity(), R.style.CustomDialogTheme, R.layout.dialog_custom);
        customDialog.setCancelable(false);
        customDialog.show();
        TextView content = (TextView) customDialog.findViewById(R.id.custom_dialog_content);
        TextView divider = (TextView) customDialog.findViewById(R.id.custom_dialog_divider);
        TextView confirm = (TextView) customDialog.findViewById(R.id.custom_dialog_confirm);
        TextView cancel = (TextView) customDialog.findViewById(R.id.custom_dialog_cancel);
        content.setText(msg);
        divider.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                callBack.onClick();
            }
        });
    }

    /**
     * @param msg
     * @param callBack
     * @Title: showConfirmTip
     * @Description: 取消确定方法，确定有点击返回
     * @return: void
     */
    public void showConfirmTip(String msg, final OnCustomDialogConfirmListener callBack) {
        final Dialog customDialog = new CustomDialog1(getActivity(), R.style.CustomDialogTheme, R.layout.dialog_custom);
        customDialog.show();
        TextView content = (TextView) customDialog.findViewById(R.id.custom_dialog_content);
        TextView confirm = (TextView) customDialog.findViewById(R.id.custom_dialog_confirm);
        TextView cancel = (TextView) customDialog.findViewById(R.id.custom_dialog_cancel);
        content.setText(msg);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                callBack.onClick();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }

    /**
     * @param msg
     * @param confirm
     * @param cancel
     * @Title: showConfirmTip
     * @Description: 取消确定方法，均有点击返回
     * @return: void
     */
    public void showConfirmTip(String msg, final OnCustomDialogConfirmListener confirm, final OnCustomDialogConfirmListener cancel) {
        final Dialog customDialog = new CustomDialog1(getActivity(), R.style.CustomDialogTheme, R.layout.dialog_custom);
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


    public void closeSocket() {
        if (client != null) {
            client.close();
        }
    }

    public class DraftInfo {

        public final String draftName;
        public final Draft draft;

        public DraftInfo(String draftName, Draft draft) {
            this.draftName = draftName;
            this.draft = draft;
        }

        @Override
        public String toString() {
            return draftName;
        }
    }

    public class ServerInfo {

        private final String serverName;
        private final String serverAddress;

        public ServerInfo(String serverName, String serverAddress) {
            this.serverName = serverName;
            this.serverAddress = serverAddress;
        }

        @Override
        public String toString() {
            return serverName;
        }
    }

    public interface OnCustomDialogConfirmListener {
        void onClick();
    }


}
