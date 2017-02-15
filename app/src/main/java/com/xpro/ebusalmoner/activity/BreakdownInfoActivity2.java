package com.xpro.ebusalmoner.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
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
import com.xpro.ebusalmoner.entity.BreakdownData_M;

import java.util.List;

/**
 * 管理人员-->故障详情(已分配)
 *
 * @author huangjh
 */
public class BreakdownInfoActivity2 extends BaseActivity implements OnClickListener {
    private static final int CHANGEITEM = 0xaaaa;
    private TextView locationText;
    private MapView mMapView = null;// 百度地图控件,专门显示地图用的控件
    private BaiduMap mBaiduMap;// 百度地图对象,抽象的地图对象
    private LocationMode currentMode;// 定位模式
    private LocationClient locClient;// 定位客户端
    private double latitude, longitude; // 经纬度
    private boolean isFirstLoc = true;// 记录是否第一次定位

    private ImageView back;
    private TextView driverTel, trailerTel;
    private TextView spinner, lineText, driverNameText, lineTimeText, numberText;
    private TextView spinner1;
    private String lineStr, numberStr, nameStr, telStr, timeStr, latStr, lngStr, causeTrailerStr, causeDriverStr, cenpt1Addr;

    private List<List<LatLng>> tuoche;
    private List<LatLng> latlngs1;// 拖车1经纬度集合
    private List<LatLng> latlngs2;// 拖车2经纬度集合
    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private static final int TIME_INTERVAL = 80;
    private static final double DISTANCE = 0.00009;
    private Handler mHandler;
    private Marker mMarkerA;
    // 初始化全局 bitmap 信息，不用时及时 recycle, 拖车图标
    BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.mipmap.trailerfree);
    BitmapDescriptor bdB = BitmapDescriptorFactory.fromResource(R.mipmap.trailerfree);
    BitmapDescriptor bdC = BitmapDescriptorFactory.fromResource(R.mipmap.trailerbusy);
    BitmapDescriptor bdD = BitmapDescriptorFactory.fromResource(R.mipmap.trailerbusy);

    BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.gzcbus);

    // 定位监听器
    // BDLocationListener locListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_breakdown_info2);

        mHandler = new Handler(Looper.getMainLooper());
        initData();// 经纬度数据源
        initView();
        getFromIntent();
//		init();

    }

    public void getFromIntent() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = intent.getBundleExtra("bundle");
            BreakdownData_M subData = (BreakdownData_M) bundle.get("BreakdownData_M");
            lineStr = subData.getLineName();
            numberStr = subData.getCode();
            nameStr = subData.getDriverName();
            timeStr = subData.getHitchTime();
            telStr = subData.getDriverTel();
            latStr = subData.getHitchLatitude();
            lngStr = subData.getHitchLongitude();

            lineText.setText(lineStr);
            numberText.setText(numberStr);
            driverNameText.setText("驾驶员：" + nameStr);
            lineTimeText.setText(timeStr);

            LatLng latLng = new LatLng(Double.parseDouble(latStr), Double.parseDouble(lngStr));
            reverseGeoCode(latLng);
        }
    }

    public void initView() {
//		mMapView = (MapView) findViewById(R.id.mapView);
        locationText = (TextView) findViewById(R.id.location);
        back = (ImageView) findViewById(R.id.back);
        spinner = (TextView) findViewById(R.id.spinner);
        spinner1 = (TextView) findViewById(R.id.spinner1);
        lineText = (TextView) findViewById(R.id.line);
        numberText = (TextView) findViewById(R.id.lineNumber);
        driverNameText = (TextView) findViewById(R.id.driverName);
        lineTimeText = (TextView) findViewById(R.id.lineTime);
        driverTel = (TextView) findViewById(R.id.driverTel);
        trailerTel = (TextView) findViewById(R.id.trailerTel);
        back.setOnClickListener(this);
        driverTel.setOnClickListener(this);
        trailerTel.setOnClickListener(this);

        spinner.setVisibility(View.GONE);
        spinner1.setVisibility(View.VISIBLE);
        setTitle("故障详情");
        spinner1.setText("1号拖车");

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
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                // MapStatusUpdate描述地图将要发生的变化
                // MapStatusUpdateFactory生成地图将要反生的变化
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(msu);
                // locationText.setText(location.getAddrStr());
                // Log.e("故障车经纬度--", latitude + "/" + longitude);

                initOverlay();
            }
        }

    };

    /**
     * 根据经纬度获得车辆位置信息
     *
     * @param
     * @param
     */
    public void initOverlay() {

        LatLng llA = new LatLng(31.97506596f, 118.75827533f);
        LatLng ll = new LatLng(31.96363596f, 118.76223533f);
        MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA).zIndex(9).draggable(true);
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
        OverlayOptions options = new MarkerOptions().position(ll).icon(bitmap);
        mBaiduMap.addOverlay(options);
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
                    Toast.makeText(BreakdownInfoActivity2.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
                }
                cenpt1Addr = result.getAddress();
                locationText.setText(cenpt1Addr);
                Log.e("HistoryInfoActivity", cenpt1Addr);
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

    @Override
    protected void onResume() {
        super.onResume();
//		mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//		mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
//		mMapView.onDestroy();
//		mMapView = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        String str = "1号拖车"; // 拿到被选择项的值
        // String str = (String) spinner.getSelectedItem(); // 拿到被选择项的值
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.driverTel:// 拨打司机电话
                final String tel = "12839405849";
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
            case R.id.trailerTel:// 拨打拖车电话
                final String tell = "12839405849";
                showConfirmTip("确定拨打电话", new OnCustomDialogConfirmListener() {
                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + tell));
                        startActivity(intent);
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

}
