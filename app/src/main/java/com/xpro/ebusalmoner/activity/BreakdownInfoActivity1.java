package com.xpro.ebusalmoner.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.baseapi.BaseActivity;
import com.xpro.ebusalmoner.baseapi.BaseHandler;
import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.entity.BreakdownInfoData_M;
import com.xpro.ebusalmoner.logic.TaskLogic;
import com.xpro.ebusalmoner.utils.MapUtils;
import com.xpro.ebusalmoner.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理人员-->故障详情（待分配）
 *
 * @author huangjh
 */
public class BreakdownInfoActivity1 extends BaseActivity implements OnClickListener {
    private static final String TAG = "BreakdownInfoActivity1";
    private static final int CHANGEITEM = 0xaaaa;
    private TextView locationText;
    private MapView mMapView = null;// 百度地图控件,专门显示地图用的控件
    private BaiduMap mBaiduMap;// 百度地图对象,抽象的地图对象
    private LocationMode currentMode;// 定位模式
    private LocationClient locClient;// 定位客户端
    private double latitude, longitude; // 经纬度
    private boolean isFirstLoc = true;// 记录是否第一次定位

    private InfoWindow mInfoWindow;
    private Button set;
    private ImageView driverTel;
    private LinearLayout back;
    private TextView spinner, lineText, lineNumber, driverName, lineTime;
    private ArrayAdapter<String> adapter;
    private String[] trailers;// 建立数据源
    private List<String> list;
    private PopupWindow popupWindow;
    private View view;
    private Button button;
    private String cenpt1Addr, line, number, name, tel, time, lat, lng,driverId;
    private String code;
    private LatLng latLng;//故障车位置
    private List<List<LatLng>> tuoche;
    private List<LatLng> latlngs1;// 拖车1经纬度集合
    private List<LatLng> latlngs2;// 拖车2经纬度集合
    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private static final int TIME_INTERVAL = 80;
    private static final double DISTANCE = 0.00009;
    private Handler mHandler;
    private MyHandler handler;
    private TaskLogic logic;
    private Intent intent;

    private String json = "{\"success\": \"true\", " + "\"body\":  {\"list\":[{\"trailerId\": \"100\", " + "\"trailerCode\": \"1号拖车\"," +
            "\"trailerLatitude\": " + "\"31.9813440513\", \"trailerLongitude\": \"118.7626883184\", " + "\"driverName\": \"科比\", " + "\"driverTel\": \"18769029715\", " + "\"trailerState\": \"1\"},  {" + "\"trailerId\": \"100\", " + "\"trailerCode\": \"2号拖车\", " + "\"trailerLatitude\": \"31.9837630513\", " + "\"trailerLongitude\": \"118.7663173184\", " + "\"driverName\": \"科比\", " + "\"driverTel\": \"18769029715\", " + "\"trailerState\": \"1\"},{\"trailerId\": \"100\", " + "\"trailerCode\": \"3号拖车\",\"trailerLatitude\": " + "\"31.9829411546\", \"trailerLongitude\": \"118.7701045592\", " + "\"driverName\": \"科比\", " + "\"driverTel\": \"18769029715\", " + "\"trailerState\": \"0\"},{\"trailerId\": \"100\", " + "\"trailerCode\": \"4号拖车\",\"trailerLatitude\": " + "\"31.9783108489\", \"trailerLongitude\": \"118.7694844881\", " + "\"driverName\": \"科比\", " + "\"driverTel\": \"18769029715\", " + "\"trailerState\": \"0\"},{\"trailerId\": \"100\", " + "\"trailerCode\": \"5号拖车\",\"trailerLatitude\": " + "\"31.97506596\", \"trailerLongitude\": \"118.75827533\", " + "\"driverName\": \"科比\", " + "\"driverTel\": \"18769029715\", " + "\"trailerState\": \"0\"},{\"trailerId\": \"100\", " + "\"trailerCode\": \"6号拖车\",\"trailerLatitude\": " + "\"31.96566596\", \"trailerLongitude\": \"118.75627533\", " + "\"driverName\": \"科比\", " + "\"driverTel\": \"18769029715\", " + "\"trailerState\": \"0\"}]}, \"errorCode\": \"-1\",\"msg\": \"成功\"}";

    BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.gzcbus);
    float[] results;
    // 定位监听器
    // BDLocationListener locListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_breakdown_info1);

//        Log.e(TAG, json);
//        getList();// 拖车数据源
//        initData();// 经纬度数据源

        initView();
        init();

        handler = new MyHandler(BreakdownInfoActivity1.this);
        logic = new TaskLogic(handler, BreakdownInfoActivity1.this);
        logic.getTrailers();


//        getdata();//本地假数据

//        initOverlay();
////        多辆移动的小拖车
//        for (int i = 0; i < tuoche.size(); i++) {
//            drawPolyLine(tuoche.get(i));
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        logic.getTrailers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }


        private void getdata(List<BreakdownInfoData_M> dataList) {
//    private void getdata() {
//        BreakdownInfoRoot_M root3 = JSON.parseObject(json, BreakdownInfoRoot_M.class);
//        List<BreakdownInfoData_M> dataList = root3.getBody().getList();
        // TODO Auto-generated method stub
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                final BreakdownInfoData_M subBody = dataList.get(i);
                double lat = Double.valueOf(subBody.getTrailerLatitude());
                double lng = Double.valueOf(subBody.getTrailerLongitude());
                LatLng latLng = new LatLng(lat, lng);

                if (subBody.getTrailerState().equals("0")) {// 空闲中拖车
                    BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.mipmap.trailerfree);
                    MarkerOptions oo = new MarkerOptions().position(latLng).icon(bd).zIndex(5);
                    final Marker mMarker = (Marker) (mBaiduMap.addOverlay(oo));

                    mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            // 创建标注对象(就是地图上的那些图标等等)
                            if (marker == mMarker) {
                                final Button button = new Button(BreakdownInfoActivity1.this);
                                button.setBackgroundResource(R.mipmap.popup);
                                button.setPadding(10, 10, 10, 10);
//                                        button.setTextColor(R.color.new_green);
                                button.setTextColor(Color.parseColor("#84C555"));
                                button.setTextSize(12);
                                String name = subBody.getTrailerCode();//拖车名字
                                code=subBody.getTrailerCode();//拖车编号
                                button.setText(name + "　空闲中");
                                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(button);
                                // 获得当前这个点的位置
                                LatLng ll = marker.getPosition();
                                OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick() {
                                        // 隐藏infowindow
                                        mBaiduMap.hideInfoWindow();
                                    }
                                };
                                // 显示这个button
                                mInfoWindow = new InfoWindow(bitmapDescriptor, ll, -100, listener);
                                mBaiduMap.showInfoWindow(mInfoWindow);
                                // spinner.setSelection(1);
                                spinner.setText(name + "　空闲中");
                                return true;
                            }
                            return false;
                        }
                    });
                } else if(subBody.getTrailerState().equals("1")){
                    BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.mipmap.trailerbusy);
                    MarkerOptions oo = new MarkerOptions().position(latLng).icon(bd).zIndex(5);
                    final Marker mMarker = (Marker) (mBaiduMap.addOverlay(oo));
                    // 忙碌中拖车不给选择拖车
                    /*mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            // TODO Auto-generated method stub
                            if (marker == mMarker) {
                                Button button = new Button(BreakdownInfoActivity1.this);
                                button.setBackgroundResource(R.mipmap.popup);
                                button.setPadding(10, 10, 10, 10);
                                button.setTextColor(Color.RED);
                                // button.setTextColor(Color.BLACK);
                                button.setTextSize(12);
                                String name = subBody.getTrailerCode();
                                button.setText(name + "　忙碌中");
                                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(button);
                                // 获得当前这个点的位置
                                LatLng ll = marker.getPosition();
                                OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick() {
                                        // 隐藏infowindow
                                        mBaiduMap.hideInfoWindow();
                                    }
                                };
                                // 显示这个button
                                mInfoWindow = new InfoWindow(bitmapDescriptor, ll, -100, listener);
                                mBaiduMap.showInfoWindow(mInfoWindow);
                                spinner.setText(name + "　忙碌中");
                                return true;
                            }
                            return false;
                        }
                    });*/
                }
            }
        }
    }

    /**
     * 动态获取数据源
     *
     * @return
     */
    public List<String> getList() {
        list = new ArrayList<String>();
        list.add("请选择拖车");
        list.add("1号拖车");
        list.add("1号拖车");
        list.add("1号拖车");
        list.add("1号拖车");
        return list;
    }

    public void initView() {
        mMapView = (MapView) findViewById(R.id.mapView);
        locationText = (TextView) findViewById(R.id.location);
        back = (LinearLayout) findViewById(R.id.back);
        lineText = (TextView) findViewById(R.id.line);
        lineNumber = (TextView) findViewById(R.id.lineNumber);
        driverName = (TextView) findViewById(R.id.driverName);
        lineTime = (TextView) findViewById(R.id.lineTime);

        spinner = (TextView) findViewById(R.id.spinner);
        spinner = (TextView) findViewById(R.id.spinner);
        driverTel = (ImageView) findViewById(R.id.driverTel);
        set = (Button) findViewById(R.id.button);
        back.setOnClickListener(this);
        set.setOnClickListener(this);
        driverTel.setOnClickListener(this);

        setTitle("故障详情");

        intent = getIntent();
        driverId = intent.getStringExtra("id");
        line = intent.getStringExtra("line");
        number = intent.getStringExtra("number");
        name = intent.getStringExtra("name");
        tel = intent.getStringExtra("tel");
        time = intent.getStringExtra("time");
        lat = intent.getStringExtra("lat");
        lng = intent.getStringExtra("lng");
        latLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));//故障车经纬度
        reverseGeoCode(latLng);

        lineText.setText(line);
        lineNumber.setText(number);
        driverName.setText("驾驶员：" + name);
        lineTime.setText(time);

    }

    private void init() {
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap = mMapView.getMap();
        // 普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mBaiduMap.setMapStatus(msu);
        currentMode = LocationMode.NORMAL;
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

        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(currentMode, true, null));


    }

    /**
     * 根据值, 设置spinner默认选中:
     *
     * @param spinner
     * @param value
     */
    public static void setSpinnerItemSelectedByValue(Spinner spinner, String value) {
        SpinnerAdapter apsAdapter = spinner.getAdapter(); // 得到SpinnerAdapter对象
        int k = apsAdapter.getCount();
        for (int i = 0; i < k; i++) {
            if (value.equals(apsAdapter.getItem(i).toString())) {
                spinner.setSelection(i, true);// 默认选中项
                break;
            }
        }
    }

    BDLocationListener locListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null) return;
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())//
                    .direction(100)// 方向
                    .latitude(location.getLatitude())//
                    .longitude(location.getLongitude())//
                    .build();
            // 设置定位数据

            latitude = location.getLatitude();
            longitude = location.getLongitude();
            mBaiduMap.setMyLocationData(locData);
            // 第一次定位的时候，那地图中心店显示为定位到的位置
            LatLng cenpt = new LatLng(latitude, longitude);
            Log.e("MainActivity", latitude + "/" + longitude);

            if (isFirstLoc) {
                isFirstLoc = false;
                Double latDouble=Double.valueOf(lat);
                Double lngDouble=Double.valueOf(lng);
                Double llLatDouble=location.getLatitude();
                Double llLngDouble=location.getLongitude();

                results = new float[1];
                Location.distanceBetween(latDouble, lngDouble, llLatDouble, llLngDouble, results);
                LatLng ll = new LatLng((latDouble+llLatDouble)/2, (lngDouble+llLngDouble)/2);//两点间的中心点坐标
                double distance=MapUtils.distanceStr(latLng,ll);//故障车和定位到的位置的距离
                float zoom = MapUtils.getZoom(results[0], Constants.SHIFTDETAILDISTANCE);
                Log.e(TAG, "缩放等级zoom:"+zoom+"\n两点间距离distance："+results[0]);

//                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
//                mBaiduMap.animateMapStatus(msu);
                MarkerOptions oo = new MarkerOptions().position(latLng).icon(bitmap).zIndex(5);
                final Marker mMarker = (Marker) (mBaiduMap.addOverlay(oo));

                //地图显示中心点以及缩放等级
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(zoom);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                // initOverlay();
            }
        }

    };


    @Override
    public void onClick(View v) {
//        String str = "1号拖车"; // 拿到被选择项的值
//        String str = (String) spinner.getSelectedItem(); // 拿到被选择项的值
//        String str = (String) spinner.getHint();
        String str = spinner.getText().toString();
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back:
                if ("".equals(str)) {
                    Toast.makeText(this, "请选择拖车", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
                break;
            case R.id.button:// 确定
                if ("".equals(str)) {
                    Toast.makeText(this, "请选择拖车", Toast.LENGTH_SHORT).show();
                } else {
                    showConfirmTip("确定选择拖车", new OnCustomDialogConfirmListener() {
                        @Override
                        public void onClick() {
                            // 选定拖车接口
                            logic.bindingTrailer(driverId,code);

                        }
                    }, new OnCustomDialogConfirmListener() {
                        @Override
                        public void onClick() {
                        }
                    });
                }
                break;
            case R.id.driverTel:// 拨打电话
                showConfirmTip("确定拨打电话", new OnCustomDialogConfirmListener() {
                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + tel));
                        startActivity(intent);
                    }
                }, new OnCustomDialogConfirmListener() {
                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub
                    }
                });
                break;
            default:
                break;
        }
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
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                    Toast.makeText(BreakdownInfoActivity1.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
                }
                // Toast.makeText(BreakdownInfoActivity1.this, "位置：" +
                // result.getAddress(),
                // Toast.LENGTH_LONG).show();
                cenpt1Addr = result.getAddress();
                Log.e("cenpt1Addr", cenpt1Addr);
                locationText.setText(cenpt1Addr);
            }

            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                // TODO Auto-generated method stub
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                }
            }
        });
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // mMapView.onSaveInstanceState(outState);
    }

    /**
     * 计算x方向每次移动的距离
     */
    private double getXMoveDistance(double slope) {
        if (slope == Double.MAX_VALUE) {
            return DISTANCE;
        }
        return Math.abs((DISTANCE * slope) / Math.sqrt(1 + slope * slope));
    }

    private void drawPolyLine(List<LatLng> polylines) {

        // polylines.add(polylines.get(0));//将轨迹连成一个完整的环形

        // PolylineOptions polylineOptions = new
        // PolylineOptions().points(polylines).width(10).color(Color.RED);
        // mPolyline = (Polyline) mBaiduMap.addOverlay(polylineOptions);
        OverlayOptions markerOptions;
        // markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f)
        // .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow)).position(polylines.get(0))
        // .rotate((float) getAngle(0));

        markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.fromResource(R.mipmap.arrow))
                // .icon(BitmapDescriptorFactory.fromResource(R.drawable.evacuator))
                .position(polylines.get(0));

        // mMoveMarker局部变量，一辆小车一个移动箭头
        Marker mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);

        moveLooper(polylines, mMoveMarker);
    }

    /**
     * 循环进行移动逻辑
     */
    // public void moveLooper(final List<LatLng> polylines,final Marker
    // mMoveMarker,
    // final OverlayOptions options) {
    public void moveLooper(final List<LatLng> polylines, final Marker mMoveMarker) {
        new Thread() {
            public void run() {
                while (true) {
                    for (int i = 0; i < polylines.size() - 1; i++) {
                        final LatLng startPoint = polylines.get(i);
                        final LatLng endPoint = polylines.get(i + 1);
                        Log.e("--", startPoint.latitude + "/" + startPoint.longitude);
                        mMoveMarker.setPosition(startPoint);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // refresh marker's rotate
                                if (mMapView == null) {
                                    return;
                                }
                                mMoveMarker.setRotate((float) getAngle(startPoint, endPoint));
                                // mBaiduMap.addOverlay(options);
                            }
                        });
                        double slope = getSlope(startPoint, endPoint);
                        // 是不是正向的标示
                        boolean isReverse = (startPoint.latitude > endPoint.latitude);

                        double intercept = getInterception(slope, startPoint);

                        double xMoveDistance = isReverse ? getXMoveDistance(slope) : -1 * getXMoveDistance(slope);

                        for (double j = startPoint.latitude; !((j > endPoint.latitude) ^ isReverse); j = j - xMoveDistance) {
                            LatLng latLng = null;
                            if (slope == Double.MAX_VALUE) {
                                latLng = new LatLng(j, startPoint.longitude);
                            } else {
                                latLng = new LatLng(j, (j - intercept) / slope);
                            }

                            final LatLng finalLatLng = latLng;
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (mMapView == null) {
                                        return;
                                    }
                                    mMoveMarker.setPosition(finalLatLng);// 走完一圈回到起点
                                    // mBaiduMap.addOverlay(options);
                                }
                            });
                            try {
                                Thread.sleep(TIME_INTERVAL);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }

        }.start();
    }

    @SuppressLint("InflateParams")
    @SuppressWarnings("deprecation")
    public void iniPopWindow() {
        view = LayoutInflater.from(this).inflate(R.layout.my_mapview_dialog, null);
        popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        backgroundAlpha(0.8f);
        // 点击外部区域popwindow消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        // 显示(靠中间)
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            // popupWindow.showAsDropDown(mMarkerA, 0, 0, Gravity.BOTTOM);
        }

        /**
         * 添加popupWindow窗口关闭事件
         */
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(1f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        // 让窗口背景后面的任何东西变暗,针对部分华为手机背景不变暗
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    @SuppressLint("HandlerLeak")
    class MyHandler extends BaseHandler {

        public MyHandler(Context context) {
            super(context);
        }

        @Override
        public void doHandle(Message msg) {
            super.doHandle(msg);
            switch (msg.what) {
                //管理人员拖车数据获取成功
                case Constants.GET_TRAILERS_SUCCESS:
                    List<BreakdownInfoData_M> dataList = (List<BreakdownInfoData_M>) msg.obj;
                    Log.e("BreakdownRoot", "dataList:" + dataList.size());
                    getdata(dataList);
//                    getdata();//本地假数据
                    break;

                //管理人员拖车数据获取失败
                case Constants.GET_TRAILERS_FAIL:
                    ToastUtils.showToast(BreakdownInfoActivity1.this, "" + msg.obj);
                    break;

                //救济管理：故障公交车和拖车绑定成功
                case Constants.BUS_BOUNDTRAILER_SUCCESS:
                    ToastUtils.showToast(BreakdownInfoActivity1.this, "" + msg.obj);

                    setResult(RESULT_OK, intent);
                    finish();

                    break;

                //救济管理：故障公交车和拖车绑定失败
                case Constants.BUS_BOUNDTRAILER_FAIL:
                    ToastUtils.showToast(BreakdownInfoActivity1.this, "" + msg.obj);
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 根据经纬度获得车辆位置信息
     */
    public void initOverlay() {

        LatLng llA = new LatLng(31.97506596f, 118.75827533f);
        LatLng llB = new LatLng(31.96566596f, 118.75627533f);
        LatLng llC = new LatLng(31.96366596f, 118.75327533f);
        LatLng llD = new LatLng(31.97368596f, 118.75223533f);
        LatLng ll = new LatLng(31.96363596f, 118.76223533f);
    }

}
