package com.xpro.ebusalmoner.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.activity.AnimationActivity;
import com.xpro.ebusalmoner.activity.SigninActivity;
import com.xpro.ebusalmoner.adapter.RouteAdapter;
import com.xpro.ebusalmoner.baseapi.BaseActivity;
import com.xpro.ebusalmoner.baseapi.BaseFragment;
import com.xpro.ebusalmoner.baseapi.BaseHandler;
import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.entity.BreakdownData_M;
import com.xpro.ebusalmoner.entity.BreakdownRoot_M;
import com.xpro.ebusalmoner.entity.TrailerTaskData_S;
import com.xpro.ebusalmoner.logic.MainLogic;
import com.xpro.ebusalmoner.logic.TaskLogic;
import com.xpro.ebusalmoner.utils.DateUtil;
import com.xpro.ebusalmoner.utils.MobilePhoneUtils;
import com.xpro.ebusalmoner.utils.PreferencesUtils;
import com.xpro.ebusalmoner.utils.ProgressDialogUtils;
import com.xpro.ebusalmoner.utils.ToastUtils;
import com.xpro.ebusalmoner.widget.ScrollTextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


/**
 * 实施人员-->故障
 *
 * @author huangjh
 */
public class ShiftFragment_S1 extends BaseFragment implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View rootView;
    private TextView transferApplication, task_sign_go_in_line, task_date_now, task_date, task_weather_textview,
            task_sign_go_in_buscode;
    private TextView taskText;
    private ImageView task_weather;
    private Button attendance_btn;
    private ListView taskFragmentListview;
    private SwipeRefreshLayout taskFragment_swipe;
    private RelativeLayout task_signRelative;
    private ScrollTextView scrollTextView;
    private List<BreakdownData_M> dataList;
    private List<TrailerTaskData_S> dataList1;
    Handler handler;
    List<String> data;
    RouteAdapter taskAdapter;
    BaseActivity x;
    TaskLogic taskLogic;
    MainLogic mainLogic;
    //屏幕宽度
    int width;

    Activity context;
    private String json = "{\"success\": \"true\", " + "\"body\": {\"data\":[{\"id\": " +
            "\"100\", " + "\"plateNumber\": \"1号拖车\",\"hitchLatitude\": " +
            "\"31.9813440513\", \"hitchLongitude\": \"118.7626883184\", " + "\"driverName\": \"科比\", \"driverTel\": " +
            "\"12345678910\", " + "\"lineName\": \"108路\",\"hitchTime\": \"2013.02.12\",\"plateNumber\": " +
            "\"JN-0039\"},]}, " +
            "\"errorCode\": \"-1\",\"msg\": \"成功\"}";

    //扫码常量
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private final static int SCANNIN_GREQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup group = (ViewGroup) rootView.getParent();
            if (group != null) {
                group.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_task, container, false);
        }
//        ProgressDialogUtils.showProgressDialog("获取任务中", getActivity(), false);

        setViews();//控件初始化
        initData();//数据初始化
        setOthers();//其他设置
        setOnClickListener();//点击初始化

        /**
         * 本地假数据,因为任务接口中需要传救济车的trailerCode，只有在扫码绑定之后才会有
         */
//        getData();

        /**
         * 开启webSocket连接
         */
        connectSocket();

        return rootView;
    }

    private void getData() {
        BreakdownRoot_M root0 = JSON.parseObject(json, BreakdownRoot_M.class);
        dataList = root0.getBody().getData();
        Log.e("hh", dataList.size() + "");
        taskAdapter = new RouteAdapter(getActivity(), dataList);
        taskFragmentListview.setAdapter(taskAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();

        // 天气接口
        taskLogic.getWeatherChina();

        // 任务接口
//        taskLogic.getTask(trailerCode);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scrollTextView.stopScroll();
    }

    /**
     * 控件初始化
     */
    public void setViews() {
        transferApplication = (TextView) rootView.findViewById(R.id.transferApplication);
        task_sign_go_in_line = (TextView) rootView.findViewById(R.id.task_sign_go_in_line);
        task_date_now = (TextView) rootView.findViewById(R.id.task_date_now);
        task_date = (TextView) rootView.findViewById(R.id.task_date);
        task_weather_textview = (TextView) rootView.findViewById(R.id.task_weather_textview);
        task_weather = (ImageView) rootView.findViewById(R.id.task_weather);
        attendance_btn = (Button) rootView.findViewById(R.id.attendance_btn);
        taskFragmentListview = (ListView) rootView.findViewById(R.id.taskFragmentListview);
        taskFragment_swipe = (SwipeRefreshLayout) rootView.findViewById(R.id.taskFragment_swipe);
        task_signRelative = (RelativeLayout) rootView.findViewById(R.id.task_signRelative);
        task_sign_go_in_buscode = (TextView) rootView.findViewById(R.id.task_sign_go_in_buscode);
        scrollTextView = (ScrollTextView) rootView.findViewById(R.id.scrollTextView);
        taskText = (TextView) rootView.findViewById(R.id.taskText);
    }

    /**
     * 数据初始化
     */
    public void initData() {
        context = getActivity();
        data = new ArrayList<>();
        handler = new Myhandler(getActivity());
        taskLogic = new TaskLogic(handler, getActivity());
        mainLogic = new MainLogic(handler, getActivity());
        x = (BaseActivity) getActivity();
        width = PreferencesUtils.getInt(getActivity(), "WindowsWidth");
        taskFragment_swipe.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    /**
     * 其他设置
     */
    public void setOthers() {
        //列表设置不显示分割线
        taskFragmentListview.setDivider(null);
        task_date_now.setText(DateUtil.getY() + "年" + DateUtil.getM() + "月" + DateUtil.getDate() + "日");
        task_date.setText(DateUtil.getDay());

        //滚动条跑马灯设置
        scrollTextView.setText("雪天路滑,请谨慎驾驶");
        scrollTextView.init(getActivity().getWindowManager());
        scrollTextView.startScroll();
        scrollTextView.setEnabled(false);

    }

    public void setOnClickListener() {
        attendance_btn.setOnClickListener(this);
        taskText.setOnClickListener(this);
        transferApplication.setOnClickListener(this);
        taskFragment_swipe.setOnRefreshListener(this);
    }

    /**
     * 签到考勤
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //签到考勤
            case R.id.attendance_btn:
                //获得网路偶读mac地址
                String mac = MobilePhoneUtils.getIsConnectionRouterInfo(getActivity());
                Log.e("router", "router:" + MobilePhoneUtils.getAllWifiRouterInfo(getActivity()));
                ToastUtils.showToast(getActivity(), "手机已经连接的网络mac地址：" + mac);

                /**
                 * （暂时屏蔽，直接跳转到SigninActivity）
                 */
                //发送签到请求
//                taskLogic.sign(PreferencesUtils.getString(getActivity(), "LoginId"), "0");

                Intent intent1 = new Intent(getActivity(), SigninActivity.class);
                intent1.putExtra("trailerCode", "yz-0035");
                Toast.makeText(getActivity(), "yz-0035", Toast.LENGTH_SHORT).show();
                startActivity(intent1);
                break;

            case R.id.taskText: //走签到扫码步骤
                //发送签到请求
                taskLogic.sign(PreferencesUtils.getString(getActivity(), "LoginId"), "0");
                break;
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        //停留两秒发送message
        handler.sendEmptyMessageDelayed(Constants.REFRESH_COMPLETE, 2000);
    }


    class Myhandler extends BaseHandler {

        public Myhandler(Context context) {
            super(context);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void doHandle(Message msg) {
            super.doHandle(msg);

            switch (msg.what) {
                //下拉刷新完成
                case Constants.REFRESH_COMPLETE:
                    if (taskFragment_swipe.isRefreshing()) {
                        //下拉刷新消失
                        taskFragment_swipe.setRefreshing(false);
                    }
                    break;

                //任务接收数据成功
                case Constants.HANDLER_TODAY_TASK_SUCCESS:
                    ToastUtils.showToast(getActivity(), "暂无新数据");//单机数据操作
                    break;

                //任务接收数据失败
                case Constants.HANDLER_TODAY_TASK_FAIL:
                    ToastUtils.showToast(getActivity(), "刷新失败");
                    break;

                //获得天气数据成功
                case Constants.HANDLER_GETWEATHER_SUCCESS:
                    //获得天气
                    String weather = (String) msg.obj;
                    task_weather_textview.setText(weather);
                    //获取天气之后匹配相对应的图片
                    if (weather.equals("晴")) {
                        task_weather.setImageResource(R.mipmap.sunny);
                    } else if (weather.equals("阴")) {
                        task_weather.setImageResource(R.mipmap.cloudy2);
                    } else if (weather.equals("多云")) {
                        task_weather.setImageResource(R.mipmap.cloudy);
                    } else if (weather.equals("阵雨")) {
                        task_weather.setImageResource(R.mipmap.rain5);
                    } else if (weather.equals("小雨")) {
                        task_weather.setImageResource(R.mipmap.rain1);
                    } else if (weather.equals("中雨")) {
                        task_weather.setImageResource(R.mipmap.rain2);
                    } else if (weather.equals("大雨")) {
                        task_weather.setImageResource(R.mipmap.rain3);
                    } else if (weather.equals("暴雨")) {
                        task_weather.setImageResource(R.mipmap.rain4);
                    } else if (weather.equals("小雪")) {
                        task_weather.setImageResource(R.mipmap.snow);
                    } else if (weather.equals("中雪")) {
                        task_weather.setImageResource(R.mipmap.snow2);
                    } else if (weather.equals("大雪")) {
                        task_weather.setImageResource(R.mipmap.snow3);
                    } else if (weather.equals("暴雪")) {
                        task_weather.setImageResource(R.mipmap.snow4);
                    }
                    break;

                //获得天气数据失败
                case Constants.HANDLER_GETWEATHER_FAIL:
                    ToastUtils.showToast(context, "" + msg.obj);
                    break;

//                //获得任务列表成功
//                case Constants.HANDLER_TASKINFO_SUCCESS:
//
//                    ProgressDialogUtils.dismissProgressBar();
//
//                    break;
//
//                //获得任务列表失败
//                case Constants.HANDLER_TASKINFO_FAIL:
//                    ProgressDialogUtils.dismissProgressBar();
//                    x.showTip("" + msg.obj);
//                    break;

                case Constants.GET_TASK_SUCCESS:
                    ProgressDialogUtils.dismissProgressBar();
                    dataList1 = (List<TrailerTaskData_S>) msg.obj;
                    Log.e("----", dataList1.get(0).getHitchLatitude());
                    // getData(dataList1);
                    break;
                case Constants.GET_TASK_FAIL:
                    ProgressDialogUtils.dismissProgressBar();
                    ToastUtils.showToast(getActivity(), "" + msg.obj);
                    break;

                //签到返回成功
                case Constants.HANDLER_SIGN_SUCCESS:

//                    //蓝牙获取距离
//                    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//                    if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
//                        Intent intent = new Intent(
//                                BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                        startActivity(intent);
//                    }
//                    mBluetoothAdapter.startLeScan(BluetoothUtils.mLeScanCallback);


//                    //跳转扫码
//                    Intent intent = new Intent(getActivity(),
//                            CaptureActivity.class);
//                    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);


//                    Intent intent = new Intent(getActivity(), SigninActivity.class);
                    Intent intent = new Intent(getActivity(), AnimationActivity.class);
                    startActivity(intent);
                    break;

                //签到返回失败
                case Constants.HANDLER_SIGN_FAIL:
                    ToastUtils.showToast(getActivity(), "" + msg.obj);
                    break;
            }
        }
    }


    /**
     * 判断wifi是否连接，连接wifi返回true，否则返回false
     */
    public boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }


    /**
     * 测试网络是否可用
     *
     * @return
     */
    public boolean isNetworkOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("ping -c 1 www.baidu.com");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (Exception e) {
        }
        return false;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        if (isWifiConnected(getActivity().getApplicationContext())) {
//            if (isNetworkOnline()) {
//                Toast.makeText(getActivity(), "当前wifi网络可用！", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(getActivity(), "当前wifi网络不可用，请重新连接wifi！", Toast.LENGTH_LONG).show();
//            }
//        } else {
//            Toast.makeText(getActivity(), "当前没有连接wifi！", Toast.LENGTH_LONG).show();
//        }
//    }

    public void connectSocket() {
        try {
            client = new WebSocketClient(new URI(address), selectDraft.draft) {
                @Override
                public void onOpen(final ServerHandshake serverHandshakeData) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            etDetails.append("已经连接到服务器【" + getURI() + "】\n");

                            Log.e("wlf", "已经连接到服务器【" + getURI() + "】");
                        }
                    });
                }

                @Override
                public void onMessage(final String message) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            etDetails.append("获取到服务器信息【" + message + "】\n");

//                            logic.faultInformation();// 待分配
                            Log.e("wlf", "获取到服务器信息【" + message + "】");
                        }
                    });
                }

                @Override
                public void onClose(final int code, final String reason, final boolean remote) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            etDetails.append("断开服务器连接【" + getURI() + "，状态码： " + code + "，断开原因：" + reason +
//                                    "】\n");

                            Log.e("wlf", "断开服务器连接【" + getURI() + "，状态码： " + code + "，断开原因：" + reason + "】");
                        }
                    });
                }

                @Override
                public void onError(final Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            etDetails.append("连接发生了异常【异常原因：" + e + "】\n");

                            Log.e("wlf", "连接发生了异常【异常原因：" + e + "】");
                        }
                    });
                }
            };
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
