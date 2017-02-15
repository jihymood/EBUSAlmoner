package com.xpro.ebusalmoner.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.baseapi.BaseHandler;
import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.entity.TrailerTaskBody_S;
import com.xpro.ebusalmoner.entity.TrailerTaskData_S;
import com.xpro.ebusalmoner.entity.TrailerTaskRoot_S;
import com.xpro.ebusalmoner.logic.TaskLogic;
import com.xpro.ebusalmoner.utils.DrivingRouteOverlay;
import com.xpro.ebusalmoner.utils.ToastUtils;
import com.xpro.ebusalmoner.widget.MyCommonDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 救济实施--> 签到后跳转的任务页面
 */
public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView line, lineNumber, driverName, lineTime, location;
    private MapView mMapView = null;// 百度地图控件,专门显示地图用的控件
    private BaiduMap mBaiduMap;// 百度地图对象,抽象的地图对象
    private MyLocationConfiguration.LocationMode currentMode;// 定位模式
    private LocationClient locClient;// 定位客户端
    private double latitude, longitude; // 经纬度
    private boolean isFirstLoc = true;// 记录是否第一次定位
    private InfoWindow mInfoWindow;
    private ImageView driverTel, back;
    private Button button, history, button_false;
    private String[] trailers;// 建立数据源
    private List<String> list;
    // 实时路况交通图
    private Button traffic_blur, traffic_focus;
    private List<List<LatLng>> tuoche;
    private List<LatLng> latlngs1;// 拖车1经纬度集合
    private List<LatLng> latlngs2;// 拖车2经纬度集合
    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private static final int TIME_INTERVAL = 80;
    private static final double DISTANCE = 0.00009;
    private Handler mHandler;

    private LatLng cenpt;// 自身位置
    private LatLng cenpt1;// 故障车位置
//    private LatLng cenpt2;// 拖车位置

    private String cenpt1Addr = "";
    private String telStr;// 电话
    private String lineStr;// 路线
    private String numberStr;// 自编号
    private String driverNameStr;// 司机姓名
    private String timeStr;// 故障时间
    private String faultId;// faultId
    private String driverId;// 司机personId
    private String trailerCode; //yz-0035

    private Marker mMarkerB;
    private Marker mMarkerC;
    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.mipmap.icon_marka);
    BitmapDescriptor bdB = BitmapDescriptorFactory
            .fromResource(R.mipmap.gzcbus);
    BitmapDescriptor bdC = BitmapDescriptorFactory
            .fromResource(R.mipmap.trailerbusy);

    private RoutePlanSearch mSearch;
    private MyOrientationListener myOrientationListener;// 方向传感器的监听器
    private int mXDirection;// 方向传感器X方向的值
    private float mCurrentAccracy;// 当前的精度

    private LinearLayout linear1;
    private RelativeLayout linear2;
    private TrailerTaskRoot_S root;
    private TrailerTaskBody_S body;
    private List<TrailerTaskData_S> dataList;
    private MyHandler handler1;
    private TaskLogic logic;

    private String json = "{\"success\": \"true\", "
            + "\"body\": {\"data\":[{\"id\": \"100\", "
            + "\"plateNumber\": \"1号拖车\",\"hitchLatitude\": "
            + "\"31.97506596513\", \"hitchLongitude\": \"118.758275333184\", "
            + "\"driverName\": \"科比\", \"driverTel\": \"18769029715\", "
            + "\"line\": \"108路\",\"hitchTime\": \"2016.12.12 12:23:23\","
            + "\"number\": \"JN-0039\",\"trailerLatitude\":\"31.96366596\","
            + "\"trailerLongitude\":\"118.75327533\"},"
            + "]}, \"errorCode\": \"-1\",\"msg\": \"成功\"}";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shift_s);

        SDKInitializer.initialize(getApplicationContext());
        mHandler = new Handler(Looper.getMainLooper());

        initView();
        init();
//        getData(); //获取本地假数据

        Intent intent = getIntent();
        trailerCode = intent.getStringExtra("trailerCode");

        handler1 = new MyHandler(this);
        logic = new TaskLogic(handler1, this);
        logic.getTask(trailerCode);

//        boolean isCheck = MapUtils.isLocation(cenpt1, cenpt2, 2000);
//        if (isCheck) { // 直线距离大于500,为true不在范围内,故障按钮不可点击
//            button.setVisibility(View.GONE);
//            button_false.setVisibility(View.VISIBLE);
//        } else {// 直线距离小于500,为false在范围内,故障按钮可点击
//            button.setVisibility(View.VISIBLE);
//            button_false.setVisibility(View.GONE);
//            // button.setVisibility(View.GONE);
//            // button_false.setVisibility(View.VISIBLE);
//        }

        initOritationListener();
        Log.e("onCreateView", "onCreateView");

    }

    @Override
    public void onStart() {
        // if(null!=dataList && dataList.size()>0){
        // 开启图层定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!locClient.isStarted()) {
            locClient.start();
        }
        // 开启方向传感器
        myOrientationListener.start();
        // }else{
        //
        // }
        super.onStart();
        Log.e("onStart", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

        // handler1 = new MyHandler(getActivity());
        // logic = new TaskLogic(handler1, getActivity());
        // logic.getTask();

        // if(cenpt1!=null && cenpt2!=null){
        // MarkerOptions ooB = new MarkerOptions().position(cenpt1).icon(bdB);
        // mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
        // MarkerOptions ooC = new MarkerOptions().position(cenpt2).icon(bdC);
        // mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));
        // }else{
        // linear1.setVisibility(View.GONE);
        // linear2.setVisibility(View.GONE);
        // return;
        // }

        Log.e("onResume", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        // if(null!=dataList && dataList.size()>0){
        mMapView.onPause();
        isFirstLoc = true;
        // }else{
        //
        // }
        Log.e("onPause", "onPause");
    }

    @Override
    public void onStop() {
        // if(null!=dataList && dataList.size()>0){
        // 关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
        locClient.stop();

        // 关闭方向传感器
        myOrientationListener.stop();
        // }else{
        //
        // }
        super.onStop();
        Log.e("onStop", "onStop");
    }

    @Override
    public void onDestroy() {
        // if(null!=dataList && dataList.size()>0){
        mMapView.onDestroy();
        mMapView = null;
        // }else{
        //
        // }
        super.onDestroy();
        Log.e("onDestroy", "onDestroy");
    }


    private void getData(TrailerTaskData_S data) {
//    private void getData() {
        String hLat = data.getHitchLatitude();
        String hLng = data.getHitchLongitude();
        lineStr = data.getLineName();// 路线
        numberStr = data.getCode();// 自编号
        driverNameStr = data.getDriverName();// 司机姓名
        timeStr = data.getHitchTime();// 故障时间
        telStr = data.getDriverTel();
        faultId = data.getId();
        driverId = data.getPersonId();

        cenpt1 = new LatLng(Double.parseDouble(hLat),
                Double.parseDouble(hLng));
        reverseGeoCode(cenpt1);
        line.setText(lineStr);
        lineNumber.setText(numberStr);
        driverName.setText("驾驶员：" + driverNameStr);
        lineTime.setText(timeStr);
        location.setText(cenpt1Addr);
//            }
//        }
    }

    /**
     * 根据经纬度获得车辆位置信息
     */
    public void initOverlay() {

        if (cenpt1 != null) {
            MarkerOptions ooB = new MarkerOptions().position(cenpt1).icon(bdB);
            mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
        } else {
//            linear1.setVisibility(View.GONE);
//            linear2.setVisibility(View.GONE);

            return;
        }
    }

    public void initView() {
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (RelativeLayout) findViewById(R.id.linear2);
        mMapView = (MapView) findViewById(R.id.mapView);
        line = (TextView) findViewById(R.id.line);
        lineNumber = (TextView) findViewById(R.id.lineNumber);
        driverName = (TextView) findViewById(R.id.driverName);
        lineTime = (TextView) findViewById(R.id.lineTime);
        location = (TextView) findViewById(R.id.location);
        back = (ImageView) findViewById(R.id.back);
        button = (Button) findViewById(R.id.button);
        history = (Button) findViewById(R.id.history);
        button_false = (Button) findViewById(R.id.button_false);
        driverTel = (ImageView) findViewById(R.id.driverTel);
        traffic_focus = (Button) findViewById(R.id.traffic_focus);
        traffic_blur = (Button) findViewById(R.id.traffic_blur);
        driverTel.setOnClickListener(this);
        back.setOnClickListener(this);
        button.setOnClickListener(this);
        history.setOnClickListener(this);
        traffic_blur.setOnClickListener(this);
        traffic_focus.setOnClickListener(this);

    }

    /**
     * 初始化方向传感器
     */
    private void initOritationListener() {
        myOrientationListener = new MyOrientationListener(this);
        myOrientationListener
                .setOnOrientationListener(new OnOrientationListener() {
                    @Override
                    public void onOrientationChanged(float x) {
                        mXDirection = (int) x;

                        // 构造定位数据
                        MyLocationData locData = new MyLocationData.Builder()
                                .accuracy(mCurrentAccracy)
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                                .direction(mXDirection).latitude(latitude)//
                                .longitude(longitude)//
                                .build();
                        // 设置定位数据
                        mBaiduMap.setMyLocationData(locData);
                    }
                });
    }

    /**
     * 初始化定位相关代码
     */
    private void init() {
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap = mMapView.getMap();
        // 普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mBaiduMap.setMapStatus(msu);
        currentMode = MyLocationConfiguration.LocationMode.NORMAL;
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位服务的客户端
        locClient = new LocationClient(this);
        // 注册监听函数
        locClient.registerLocationListener(locListener);
        // 配置LocationClient这个定位客户端
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll");// 设置坐标类型
        option.setAddrType("all");
        option.setScanSpan(1000);//
        // 配置客户端
        locClient.setLocOption(option);
        // 启动定位
        locClient.start();

        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                currentMode, true, null));
    }

    BDLocationListener locListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null)
                return;
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())//
                    .direction(mXDirection)// 方向
                    .latitude(latitude)//
                    .longitude(longitude)//
                    .build();
            // 设置定位数据
            mCurrentAccracy = location.getRadius();
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            mBaiduMap.setMyLocationData(locData);
            // 第一次定位的时候，那地图中心店显示为定位到的位置
            cenpt = new LatLng(latitude, longitude);
            Log.e("MainActivity", latitude + "/" + longitude);

            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                // MapStatusUpdate描述地图将要发生的变化
                // MapStatusUpdateFactory生成地图将要反生的变化
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(msu);
                Log.e("故障车经纬度--", latitude + "/" + longitude);

                initOverlay();
//                routePlan(); //发起路线规划

            }
        }

    };

    class MyOrientationListener implements SensorEventListener {
        private Context context;
        private SensorManager sensorManager;
        private Sensor sensor;
        private float lastX;
        private OnOrientationListener onOrientationListener;

        public MyOrientationListener(Context context) {
            this.context = context;
        }

        // 开始
        public void start() {
            // 获得传感器管理器
            sensorManager = (SensorManager) context
                    .getSystemService(Context.SENSOR_SERVICE);
            if (sensorManager != null) {
                // 获得方向传感器
                sensor = sensorManager
                        .getDefaultSensor(Sensor.TYPE_ORIENTATION);
            }
            // 注册
            if (sensor != null) {// SensorManager.SENSOR_DELAY_UI
                sensorManager.registerListener(this, sensor,
                        SensorManager.SENSOR_DELAY_UI);
            }

        }

        // 停止检测
        public void stop() {
            sensorManager.unregisterListener(this);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // 接受方向感应器的类型
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                // 这里我们可以得到数据，然后根据需要来处理
                float x = event.values[SensorManager.DATA_X];

                if (Math.abs(x - lastX) > 1.0) {
                    onOrientationListener.onOrientationChanged(x);
                }
                lastX = x;

            }
        }

        public void setOnOrientationListener(
                OnOrientationListener onOrientationListener) {
            this.onOrientationListener = onOrientationListener;
        }

    }

    public interface OnOrientationListener {
        void onOrientationChanged(float x);
    }

    /**
     * 反地理编码得到地址信息
     *
     * @return
     */
    private void reverseGeoCode(LatLng latLng) {
        // 创建地理编码检索实例
        GeoCoder geoCoder = GeoCoder.newInstance();
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                // TODO Auto-generated method stub
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                    Toast.makeText(SigninActivity.this, "抱歉，未能找到结果",
                            Toast.LENGTH_LONG).show();
                }
                // Toast.makeText(this, "位置：" + result.getAddress(),
                // Toast.LENGTH_LONG).show();
                cenpt1Addr = result.getAddress();
                location.setText(cenpt1Addr);
                Log.e("ShiftFragment_S", cenpt1Addr);
            }

            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                // TODO Auto-generated method stub
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                }
            }
        });
    }


    // 拖车经纬度集合
    public void initData() {
        tuoche = new ArrayList<List<LatLng>>();
        latlngs1 = new ArrayList<LatLng>(); // 第一辆车经纬度集合
        latlngs2 = new ArrayList<LatLng>();// 第二辆车经纬度集合

        latlngs1.add(new LatLng(31.9697048489, 118.7748744881));
        latlngs1.add(new LatLng(31.9702354581, 118.7747304881));
        latlngs1.add(new LatLng(31.9874731546, 118.7744525592));
        latlngs1.add(new LatLng(31.9961700713, 118.7757586264));
        latlngs1.add(new LatLng(32.0048694174, 118.7774854183));
        latlngs1.add(new LatLng(32.0037307114, 118.7824144492));
        latlngs1.add(new LatLng(31.9962609086, 118.7872986406));
        latlngs1.add(new LatLng(31.9944540133, 118.7914938260));
        latlngs1.add(new LatLng(31.9906570133, 118.7968118260));

        latlngs2.add(new LatLng(31.9733642960, 118.7662912632));
        latlngs2.add(new LatLng(31.9735350000, 118.7557740000));
        latlngs2.add(new LatLng(31.9714564110, 118.7504893514));
        latlngs2.add(new LatLng(31.9755604110, 118.7513513514));
        latlngs2.add(new LatLng(31.9767854110, 118.7518543514));
        latlngs2.add(new LatLng(31.9776434110, 118.7491953514));
        latlngs2.add(new LatLng(31.9812311339, 118.7433553933));

        tuoche.add(latlngs1);
        tuoche.add(latlngs2);
    }

    /**
     * 根据两点算取图标转的角度
     */
    private double getAngle(LatLng fromPoint, LatLng toPoint) {
        double slope = getSlope(fromPoint, toPoint);
        if (slope == Double.MAX_VALUE) {
            if (toPoint.latitude > fromPoint.latitude) {
                return 0;
            } else {
                return 180;
            }
        }
        float deltAngle = 0;
        if ((toPoint.latitude - fromPoint.latitude) * slope < 0) {
            deltAngle = 180;
        }
        double radio = Math.atan(slope);
        double angle = 180 * (radio / Math.PI) + deltAngle - 90;
        return angle;
    }

    /**
     * 根据点和斜率算取截距
     */
    private double getInterception(double slope, LatLng point) {

        double interception = point.latitude - slope * point.longitude;
        return interception;
    }

    /**
     * 算斜率
     */
    private double getSlope(LatLng fromPoint, LatLng toPoint) {
        if (toPoint.longitude == fromPoint.longitude) {
            return Double.MAX_VALUE;
        }
        double slope = ((toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude));
        return slope;

    }

    private MyCommonDialog dialog;

    public void showPhoneDatil(final Context context, final String phonenumber) {
        dialog = new MyCommonDialog(context, "提示", "确定拨打电话", "取消", "确认");
        dialog.setOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phonenumber));
                context.startActivity(intent);
            }
        });
        dialog.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 发起路线规划
     */
    public void routePlan() {
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(listener);
        // 起点与终点 拖车位置--故障车位置
//        PlanNode stNode = PlanNode.withLocation(cenpt2);
//        PlanNode enNode = PlanNode.withLocation(cenpt1);
        // 驾车路线规划
//        mSearch.drivingSearch(new DrivingRoutePlanOption().from(stNode).to(
//                enNode));
    }

    /**
     * 路线规划结果监听
     */
    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {

        public void onGetWalkingRouteResult(WalkingRouteResult result) {
            // 获取步行路径规划结果
        }

        public void onGetTransitRouteResult(TransitRouteResult result) {
            // 获取公交换乘路径规划结果
        }

        public void onGetDrivingRouteResult(DrivingRouteResult result) {
            // 获取驾车线路规划结果
            if (result == null) {
                Toast.makeText(SigninActivity.this, "抱歉，未找到导航结果", Toast.LENGTH_SHORT)
                        .show();
            }
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                // overlay.zoomToSpan();
            }
        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult arg0) {
            // TODO Auto-generated method stub

        }
    };

//    @Override
//    public void onMapClick(LatLng arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public boolean onMapPoiClick(MapPoi arg0) {
//        // TODO Auto-generated method stub
//        return false;
//    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.button:// 实施人员跳转到故障详情
                Intent intent = new Intent(this, BreakdownActivity_S.class);
                intent.putExtra("lineStr", lineStr);
                intent.putExtra("numberStr", numberStr);
                intent.putExtra("driverNameStr", driverNameStr);
                intent.putExtra("timeStr", timeStr);
                intent.putExtra("telStr", telStr);
                intent.putExtra("cenpt1Addr", cenpt1Addr);
                intent.putExtra("faultId", faultId);
                intent.putExtra("driverId", driverId);

                startActivity(intent);
                break;
            case R.id.history:// 历史
//                startActivity(new Intent(this, HistoryActivity.class));
                Intent intent1 = new Intent(this, HistoryActivity_M.class);
                intent1.putExtra("trailerCode", trailerCode);
                startActivity(intent1);
                break;
            case R.id.driverTel:
                // final String tel = "12839405849";
//                showConfirmTip("确定拨打电话", new BaseFragment.OnCustomDialogConfirmListener() {
//                    @Override
//                    public void onClick() {
//                        // TODO Auto-generated method stub
//                        Intent intent = new Intent(Intent.ACTION_CALL, Uri
//                                .parse("tel:" + telStr));
//                        startActivity(intent);
//                    }
//                }, new BaseFragment.OnCustomDialogConfirmListener() {
//                    @Override
//                    public void onClick() {
//                        // TODO Auto-generated method stub
//
//                    }
//                });
                break;
            case R.id.traffic_focus: // 打开路况
                mBaiduMap.setTrafficEnabled(true);
                break;
            case R.id.traffic_blur: // 关闭路况
                mBaiduMap.setTrafficEnabled(false);
                break;
            default:
                break;
        }
    }

    class MyHandler extends BaseHandler {
        public MyHandler(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void doHandle(Message msg) {
            // TODO Auto-generated method stub
            super.doHandle(msg);
            switch (msg.what) {
                case Constants.GET_TASK_SUCCESS:
                    TrailerTaskData_S data = (TrailerTaskData_S) msg.obj;
                    if (null != data) {
                        Log.e("TrailerTaskData_S", data.toString());
                        getData(data);
                    } else {
                        ToastUtils.showToast(SigninActivity.this, "暂无数据");
                    }
                    break;
                case Constants.GET_TASK_FAIL:
                    ToastUtils.showToast(SigninActivity.this, "" + msg.obj);
                    break;
                default:
                    break;
            }
        }
    }

}
