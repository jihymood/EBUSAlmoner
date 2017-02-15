package com.xpro.ebusalmoner.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.activity.BreakdownInfoActivity1;
import com.xpro.ebusalmoner.activity.BreakdownInfoActivity2;
import com.xpro.ebusalmoner.activity.HistoryActivity;
import com.xpro.ebusalmoner.adapter.RouteAdapter;
import com.xpro.ebusalmoner.adapter.RouteAdapter2;
import com.xpro.ebusalmoner.baseapi.BaseFragment;
import com.xpro.ebusalmoner.baseapi.BaseHandler;
import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.entity.BreakdownData_M;
import com.xpro.ebusalmoner.entity.BreakdownRoot_M;
import com.xpro.ebusalmoner.logic.TaskLogic;
import com.xpro.ebusalmoner.utils.ToastUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 管理人员-->监控/故障
 *
 * @author huangjh
 */
@SuppressLint("InflateParams")
public class MonitorFragment extends BaseFragment implements OnClickListener, OnCheckedChangeListener {
    private static final String TAG = "MonitorFragment";
    private static final int REFRESH_COMPLETE = 0X110;
    private View view;
    private RouteAdapter adapter1;
    private RouteAdapter2 adapter2;
    private List<BreakdownData_M> data;
    private List<BreakdownData_M> data1;// 待完成
    private List<BreakdownData_M> data2;// 已完成
    private ListView listView;
    private Button set;
    private TextView textView, timeText;
    private RadioButton driverRadio, lineRadio;// 待分配
    private Myhandler handler;
    private TaskLogic logic;
    private LinearLayout blankLinear;
    private SimpleDateFormat sdf;
    private RelativeLayout relativelayout1;
    private String personId;

    private String json = "{\"success\": \"true\", " + "\"body\": {\"data\":[{\"id\": " +
            "\"100\", " + "\"plateNumber\": \"1号拖车\",\"hitchLatitude\": " +
            "\"31.9813440513\", \"hitchLongitude\": \"118.7626883184\", " + "\"driverName\": \"科比\", \"driverTel\": " +
            "\"18769029715\", " + "\"line\": \"108路\",\"hitchTime\": \"2013.02.12\",\"number\": " +
            "\"JN-0039\"}," + "{\"id\": \"100\", " + "\"plateNumber\": " +
            "\"1号拖车\",\"hitchLatitude\": " + "\"31.9813440513\", " +
            "\"hitchLongitude\": \"118.7626883184\", " + "\"driverName\": " +
            "\"科比\", \"driverTel\": \"18769029715\", " + "\"line\": \"108路\"," +
            "\"hitchTime\": \"2013.02.12\",\"number\": \"JN-0039\"}," + "{\"id\": \"100\", " + "\"plateNumber\": " +
            "\"1号拖车\",\"hitchLatitude\": " + "\"31.9813440513\", \"hitchLongitude\": \"118.7626883184\", " +
            "\"driverName\": \"科比\", \"driverTel\": \"18769029715\", " + "\"line\": \"108路\",\"hitchTime\": \"2013.02" +
            ".12\",\"number\": \"JN-0039\"},{\"id\": \"100\", " + "\"plateNumber\": \"1号拖车\",\"hitchLatitude\": " +
            "\"31.9813440513\", \"hitchLongitude\": \"118.7626883184\", " + "\"driverName\": \"科比\", \"driverTel\": " +
            "\"18769029715\", " + "\"line\": \"108路\",\"hitchTime\": \"2013.02.12\",\"number\": \"JN-0039\"}]}, " +
            "\"errorCode\": \"-1\",\"msg\": \"成功\"}";

    private String json1 = "{\"success\": \"true\", " + "\"body\": {\"data\":[]}, \"errorCode\": \"-1\",\"msg\": " +
            "\"成功\"}";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_monitor, null);

        initView();
        handler = new Myhandler(getActivity());
        logic = new TaskLogic(handler, getActivity());
        /**
         * 待分配 , 不能再这里使用, 从下一个页面返回的时候会重新走这个方法
         * 会导致"已分配"数据被覆盖
         */
//        logic.faultInformation();

//        personId = PreferencesUtils.getString(getActivity(), "LoginId");
//        logic.faultInformation2();//已分配，不能在这里使用，会将data1的数据覆盖

//        getData();
        getTodayDate();

        /**
         * 开启webSocket连接
         */
        connectSocket();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 任务接口,这里不能使用isSelected
        if (driverRadio.isChecked()) {//待分配
            getData1();
        }else if(lineRadio.isChecked()) { //已分配
            getData2();
        }
    }

    public void initView() {
        listView = (ListView) view.findViewById(R.id.listView);
        textView = (TextView) view.findViewById(R.id.textView);
        timeText = (TextView) view.findViewById(R.id.timeText);
        set = (Button) view.findViewById(R.id.set);
        driverRadio = (RadioButton) view.findViewById(R.id.driverRadio);
        lineRadio = (RadioButton) view.findViewById(R.id.lineRadio);
        blankLinear = (LinearLayout) view.findViewById(R.id.blankLinear);
        relativelayout1 = (RelativeLayout) view.findViewById(R.id.relativelayout1);

        set.setOnClickListener(this);
        driverRadio.setOnCheckedChangeListener(this);
        lineRadio.setOnCheckedChangeListener(this);
//        listView.setOnItemClickListener(this);
        relativelayout1.setOnClickListener(this);

        data = new ArrayList<BreakdownData_M>();
        data1 = new ArrayList<BreakdownData_M>();
        data2 = new ArrayList<BreakdownData_M>();
    }

    @SuppressLint("SimpleDateFormat")
    public void getTodayDate() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String dateStr = sdf.format(date);
        timeText.setText(dateStr);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub
        if (isChecked) {
            if (R.id.driverRadio == buttonView.getId()) {
                getData1();//待分配
            } else if (R.id.lineRadio == buttonView.getId()) {
//                logic.faultInformation2();//已分配，不能在这里使用，会将data1的数据覆盖
                getData2(); //已分配
            }
        }
    }

    public void getData() {
        BreakdownRoot_M root0 = JSON.parseObject(json, BreakdownRoot_M.class);
        List<BreakdownData_M> dataList = root0.getBody().getData();
        if (dataList != null && dataList.size() > 0) {
            data1 = dataList;
            adapter1 = new RouteAdapter(getActivity(), data1);
            listView.setAdapter(adapter1);
        } else { // 显示空白页
            data1 = dataList;
            listView.setVisibility(View.GONE);
            blankLinear.setVisibility(View.VISIBLE);
        }
    }

    private void getData1() {
        // TODO Auto-generated method stub
        logic.faultInformation();// 待分配

        data.clear();
        data.addAll(data1);// 待分配
        adapter1 = new RouteAdapter(getActivity(), data);
        listView.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                BreakdownData_M breakdown = data.get(position);
                Intent intent = new Intent(getActivity(), BreakdownInfoActivity1.class);
                String line = breakdown.getLineName();
                String name = breakdown.getDriverName();
                String number = breakdown.getCode();
                String tel = breakdown.getDriverTel();
                String time = breakdown.getHitchTime();
                String lat = breakdown.getHitchLatitude();
                String lng = breakdown.getHitchLongitude();
                String driverId = breakdown.getId();
                intent.putExtra("id", driverId);
                intent.putExtra("line", line);
                intent.putExtra("name", name);
                intent.putExtra("tel", tel);
                intent.putExtra("time", time);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("number", number);

//                startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void getData2() {
        // TODO Auto-generated method stub
        logic.faultInformation2();//已分配,走这个接口，会线走完下面几行代码(此时data2.size=0)，然后才会跳转到case Constants
        // .manager_complete_allot_success这行
//        Log.d(TAG, "getData2()");
//        data.clear();
//        data.addAll(data2);// 已分配,暂时先用待分配数据和接口,当data1获取成功但是没有数据时（即空白页），这里会报空指针
//        adapter2 = new RouteAdapter2(getActivity(), data);
//        listView.setAdapter(adapter2);
//        adapter2.notifyDataSetChanged();

//        listView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // TODO Auto-generated method stub
//                BreakdownData_M subData = data.get(position);
//                Bundle bundle = new Bundle();
//                Intent intent = new Intent(getActivity(), BreakdownInfoActivity2.class);
//                bundle.putParcelable("BreakdownData_M", subData);
//                intent.putExtra("bundle", bundle);
//                startActivity(intent);
//            }
//        });
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.set:// 历史任务
                startActivity(new Intent(getActivity(), HistoryActivity.class));
                break;
//            case R.id.relativelayout1://查询消息接口
//                logic.faultInformation();// 待分配
//                Toast.makeText(getActivity(), "查询消息接口", Toast.LENGTH_SHORT).show();
//                break;
            default:
                break;
        }
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        // TODO Auto-generated method stub
//        BreakdownData_M breakdown = data1.get(position);
//        String line = breakdown.getLineName();
//        String name = breakdown.getDriverName();
//        String number = breakdown.getCode();
//        String tel = breakdown.getDriverTel();
//        String time = breakdown.getHitchTime();
//        String lat = breakdown.getHitchLatitude();
//        String lng = breakdown.getHitchLongitude();
//        String driverId = breakdown.getId();
//        Intent intent = new Intent(getActivity(), BreakdownInfoActivity1.class);
//        intent.putExtra("id", driverId);
//        intent.putExtra("line", line);
//        intent.putExtra("name", name);
//        intent.putExtra("tel", tel);
//        intent.putExtra("time", time);
//        intent.putExtra("lat", lat);
//        intent.putExtra("lng", lng);
//        intent.putExtra("number", number);
//
//        startActivity(intent);
//    }

    /**
     * handler用于接收返回消息
     */
    class Myhandler extends BaseHandler {
        public Myhandler(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @SuppressWarnings("unchecked")
        @Override
        public void doHandle(Message msg) {
            super.doHandle(msg);
            switch (msg.what) {
                case Constants.MANAGER_WAIT_ALLOT_SUCCESS:// 待分配成功
                    List<BreakdownData_M> dataList = (List<BreakdownData_M>) msg.obj;
                    if (dataList != null && dataList.size() > 0) {
                        listView.setVisibility(View.VISIBLE);
                        blankLinear.setVisibility(View.GONE);

                        data1.clear();
                        data1 = dataList;
                        adapter1 = new RouteAdapter(getActivity(), data1);
                        listView.setAdapter(adapter1);

                        listView.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // TODO Auto-generated method stub
                                BreakdownData_M breakdown = data1.get(position);
                                Intent intent = new Intent(getActivity(), BreakdownInfoActivity1.class);
                                String line = breakdown.getLineName();
                                String name = breakdown.getDriverName();
                                String number = breakdown.getCode();
                                String tel = breakdown.getDriverTel();
                                String time = breakdown.getHitchTime();
                                String lat = breakdown.getHitchLatitude();
                                String lng = breakdown.getHitchLongitude();
                                String driverId = breakdown.getId();
                                intent.putExtra("id", driverId);
                                intent.putExtra("line", line);
                                intent.putExtra("name", name);
                                intent.putExtra("tel", tel);
                                intent.putExtra("time", time);
                                intent.putExtra("lat", lat);
                                intent.putExtra("lng", lng);
                                intent.putExtra("number", number);

//                                startActivity(intent);
                                startActivityForResult(intent, 1);
                            }
                        });

                    } else { // 显示空白页
                        data1 = dataList;
                        listView.setVisibility(View.GONE);
                        blankLinear.setVisibility(View.VISIBLE);
                    }
                    break;
                case Constants.MANAGER_WAIT_ALLOT_FAIL:// 待分配失败
                    listView.setVisibility(View.GONE);
                    blankLinear.setVisibility(View.VISIBLE);
                    ToastUtils.showToast(getActivity(), "" + msg.obj);
                    break;
                case Constants.MANAGER_COMPLETE_ALLOT_SUCCESS:// 获得已分配数据成功
                    List<BreakdownData_M> dataList2 = (List<BreakdownData_M>) msg.obj;
                    if (dataList2 != null && dataList2.size() > 0) {
                        listView.setVisibility(View.VISIBLE);
                        blankLinear.setVisibility(View.GONE);

                        data2.clear();
                        data2 = dataList2;
                        adapter2 = new RouteAdapter2(getActivity(), data2, logic);
                        listView.setAdapter(adapter2);
                        Log.d(TAG, "manager_complete_allot_success");

                        listView.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // TODO Auto-generated method stub
                                BreakdownData_M subData = data2.get(position);
                                Bundle bundle = new Bundle();
                                Intent intent = new Intent(getActivity(), BreakdownInfoActivity2.class);
                                bundle.putParcelable("BreakdownData_M", subData);
                                intent.putExtra("bundle", bundle);
                                startActivity(intent);
                            }
                        });

                    } else { // 显示空白页
                        data2 = dataList2;
                        listView.setVisibility(View.GONE);
                        blankLinear.setVisibility(View.VISIBLE);
                    }
                    break;
                case Constants.MANAGER_COMPLETE_FAIL:// 获得已分配数据失败
                    listView.setVisibility(View.GONE);
                    blankLinear.setVisibility(View.VISIBLE);
                    ToastUtils.showToast(getActivity(), "" + msg.obj);
                    break;
                //取消救济任务成功
                case Constants.CANCELFAULTVEHICLE_SUCCESS:
                    ToastUtils.showToast(getActivity(), "取消任务成功");
                    break;

                //取消救济任务失败
                case Constants.CANCELFAULTVEHICLE_FAIL:
                    ToastUtils.showToast(getActivity(), "" + msg.obj);
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 处理BreakdownInfoActivity1返回的结果
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1://来自按钮1的请求，作相应业务处理
                switch (resultCode) {
                    case RESULT_OK:
                        /**
                         * 刷新listview，从上一个页面返回的时候重新走了onResume方法，更新了数据源
                         * 这里的notifyDataSetChanged()不确定起不起作用
                         */
                        adapter1.notifyDataSetChanged();
                        break;
                }

            case 2://来自按钮2的请求，作相应业务处理
                switch (resultCode) {
                    case RESULT_OK:
                        /**
                         * 刷新listview，从上一个页面返回的时候没有重新走onResume方法
                         */
                        adapter1.notifyDataSetChanged();
                        break;
                }
        }
    }


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

                            logic.faultInformation();// 待分配
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
