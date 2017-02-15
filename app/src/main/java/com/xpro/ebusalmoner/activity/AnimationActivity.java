package com.xpro.ebusalmoner.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.baseapi.BaseActivity;
import com.xpro.ebusalmoner.baseapi.BaseHandler;
import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.logic.TaskLogic;
import com.xpro.ebusalmoner.utils.DateUtil;
import com.xpro.ebusalmoner.utils.PreferencesUtils;
import com.xpro.ebusalmoner.utils.ProgressDialogUtils;
import com.xpro.ebusalmoner.utils.ToastUtils;
import com.xpro.ebusalmoner.widget.scancode.android.CaptureActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.xpro.ebusalmoner.constants.Constants.BOUNDTRAILER_FAIL;
import static com.xpro.ebusalmoner.constants.Constants.BOUNDTRAILER_SUCCESS;


public class AnimationActivity extends BaseActivity {
    private ImageView wifiImageView, bluetoothImageView, jiantouImageView;
    private AnimationDrawable wifi, jiantou, buletooth;
    String taskid, buscode, token, planShiftNo, navigLat, navigLng;
    //蓝牙
    private BluetoothAdapter mBluetoothAdapter;
    TaskLogic taskLogic;
    Handler handler;

    String ibeaconName, mac, uuid, content;
    int major, minor, txPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        taskid = getIntent().getStringExtra("taskid");
        buscode = getIntent().getStringExtra("buscode");
        token = getIntent().getStringExtra("token");
        planShiftNo = getIntent().getStringExtra("planShiftNo");
        setViews();

        handler = new Myhandler(this);
        taskLogic = new TaskLogic(handler, this);


        /**
         * 箭头动画
         */
        jiantou = (AnimationDrawable) jiantouImageView.getBackground();
        jiantou.start();

        /**
         * 蓝牙动画
         */
        buletooth = (AnimationDrawable) bluetoothImageView.getBackground();
        buletooth.start();

        /**
         * wifi动画
         */
        wifi = (AnimationDrawable) wifiImageView.getBackground();
        wifi.start();


        //发送蓝牙请求
        //蓝牙获取距离
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(intent);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        }

        //发送蓝牙验证请求(type:1.蓝牙，2.wifi,3.二维码)
//        taskLogic.codeAndCar(uuid, uuid, "1");
//        taskLogic.bluetoothAndCar(uuid, uuid, "1");

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                    handler.sendEmptyMessage(111);
//                    Thread.sleep(4000);
//                    BluetoothUtils.turnOffBluetooth();//关闭蓝牙
//                    turnOffBluetooth();//关闭蓝牙
//                    handler.sendEmptyMessage(222);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wifi.stop();
        buletooth.stop();
        jiantou.stop();
        finish();
    }


    public void setViews() {
        wifiImageView = (ImageView) findViewById(R.id.wifiImageView);
        bluetoothImageView = (ImageView) findViewById(R.id.bluetoothImageView);
        jiantouImageView = (ImageView) findViewById(R.id.jiantouImageView);
    }

    //扫码常量
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private final static int SCANNIN_GREQUEST_CODE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫码完成
        if (requestCode == SCANNIN_GREQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                //扫码得到的字段结果
                content = data.getStringExtra(DECODED_CONTENT_KEY);
                Log.e("content", ":" + content);
//                //证明返回的任务中存在没有完成的任务（即当前有任务可取）
//                if (getPosition() != taskListData.size()) {
//                    taskLogic.codeAndCar(taskListData.get(getPosition()).getTaskid(), content);
//                } else {
//                    //返回的任务列表中不存在么有完成的任务
//                }

                //发送二维码验证请求(type:1.蓝牙，2.wifi,3.二维码)
                taskLogic.codeAndCar(content, content, "3");

            } else {
                ToastUtils.showToast(AnimationActivity.this, "取消");
            }
        } else {
            ToastUtils.showToast(AnimationActivity.this, "取消");
        }
    }


    class Myhandler extends BaseHandler {

        public Myhandler(Context context) {
            super(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //扫描二维码返回成功
                case Constants.HANDLER_CODEANDCAR_SUCCESS:
                    //验证成功绑定拖车
                    String personId = PreferencesUtils.getString(AnimationActivity.this, "LoginId");
                    String name = PreferencesUtils.getString(AnimationActivity.this, "name");
                    String phone = PreferencesUtils.getString(AnimationActivity.this, "phone");
                    Log.e("mainEntity", personId+"/"+name+"/"+phone);
                    taskLogic.boundTrailer(personId,name,phone,content);

//                    Intent intent1 = new Intent(AnimationActivity.this, SigninActivity.class);
//                    startActivity(intent1);
//                    finish();
                    break;

                //扫描二维码返回失败
                case Constants.HANDLER_CODEANDCAR_FAIL:
                    ToastUtils.showToast(AnimationActivity.this, "二维码验证失败");
                    break;

                //蓝牙验证成功
                case Constants.HANDLER_BLUETOOTHANDCAR_SUCCESS:

                    break;

                //蓝牙验证失败
                case Constants.HANDLER_BLUETOOTHANDCAR_FAIL:
                    ToastUtils.showToast(AnimationActivity.this, "蓝牙验证失败");
                    break;

                //人和救济车绑定成功,跳转任务页面
                case BOUNDTRAILER_SUCCESS:

                    Intent intent1 = new Intent(AnimationActivity.this, SigninActivity.class);
                    intent1.putExtra("trailerCode", content);
                    Log.e("trailerCode",content);
                    Toast.makeText(AnimationActivity.this, content, Toast.LENGTH_SHORT).show();
                    startActivity(intent1);
                    finish();
                    break;

                //人和救济车绑定失败
                case BOUNDTRAILER_FAIL:
                    ToastUtils.showToast(AnimationActivity.this, "暂无此车辆信息");
                    break;

                case 111:
                    bluetoothImageView.setVisibility(View.GONE);
                    wifiImageView.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(AnimationActivity.this,
                            CaptureActivity.class);
                    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                    break;
//                case 222:
//                    //跳转扫码
//                    Intent intent = new Intent(AnimationActivity.this,
//                        CaptureActivity.class);
//                    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
//                    break;
            }
        }
    }

    /**
     * 蓝牙信息
     */
    public BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi,
                             final byte[] scanRecord) {
            int startByte = 2;
            boolean patternFound = false;
            // 寻找ibeacon
            while (startByte <= 5) {
                if (((int) scanRecord[startByte + 2] & 0xff) == 0x02 && // Identifies
                        // an
                        // iBeacon
                        ((int) scanRecord[startByte + 3] & 0xff) == 0x15) { // Identifies
                    // correct
                    // data
                    // length
                    patternFound = true;
                    break;
                }
                startByte++;
            }
            // 如果找到了的话
            if (patternFound) {
                // 转换为16进制
                byte[] uuidBytes = new byte[16];
                System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
                String hexString = bytesToHex(uuidBytes);

                // ibeacon的UUID值
                uuid = hexString.substring(0, 8) + "-"
                        + hexString.substring(8, 12) + "-"
                        + hexString.substring(12, 16) + "-"
                        + hexString.substring(16, 20) + "-"
                        + hexString.substring(20, 32);

                // ibeacon的Major值
                major = (scanRecord[startByte + 20] & 0xff) * 0x100
                        + (scanRecord[startByte + 21] & 0xff);

                // ibeacon的Minor值
                minor = (scanRecord[startByte + 22] & 0xff) * 0x100
                        + (scanRecord[startByte + 23] & 0xff);

                ibeaconName = device.getName();
                mac = device.getAddress();
                txPower = (scanRecord[startByte + 24]);
                Log.e("Bluetooth", bytesToHex(scanRecord));
                Log.e("Bluetooth", "Name：" + ibeaconName + "\nMac：" + mac
                        + " \nUUID：" + uuid + "\nMajor：" + major + "\nMinor："
                        + minor + "\nTxPower：" + txPower + "\nrssi：" + rssi);

                Log.e("Bluetooth", "distance：" + calculateAccuracy(txPower, rssi));

            }
        }
    };
    static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    protected double calculateAccuracy(int txPower, double rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine accuracy, return -1.
        }

        double ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return accuracy;
        }
    }

    /**
     * 关闭蓝牙
     */
    public boolean turnOffBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.disable();
        }
        return false;
    }


    private SurfaceView mySurfaceView;
    private SurfaceHolder myHolder;
    private Camera myCamera;

    /**
     * 初始化surface
     */
    @SuppressWarnings("deprecation")
    private void initSurface() {
        //初始化surfaceview
        mySurfaceView = (SurfaceView) findViewById(R.id.camera_surfaceview);

        //初始化surfaceholder
        myHolder = mySurfaceView.getHolder();
        myHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    /**
     * 初始化摄像头
     */
    private void initCamera() {
        ProgressDialogUtils.showProgressDialog("正在解析二维码", AnimationActivity.this, false);
        //如果存在摄像头
        if (checkCameraHardware(this)) {
            //获取摄像头（首选前置，无前置选后置）
            if (openFacingFrontCamera()) {
                Log.e("carema", "openCameraSuccess");
                //进行对焦
                autoFocus();
            } else {
                Log.e("carema", "openCameraFailed");
            }

        }
    }

    /**
     * 判断是否存在摄像头
     */
    private boolean checkCameraHardware(Context context) {

        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // 设备存在摄像头
            return true;
        } else {
            // 设备不存在摄像头
            return false;
        }

    }


    /**
     * 得到后置摄像头
     */
    private boolean openFacingFrontCamera() {

        //尝试开启前置摄像头
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int camIdx = 0, cameraCount = Camera.getNumberOfCameras(); camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    Log.e("carema", "tryToOpenCamera");
                    myCamera = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        //如果开启前置失败（无前置）则开启后置
        if (myCamera == null) {
            for (int camIdx = 0, cameraCount = Camera.getNumberOfCameras(); camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    try {
                        myCamera = Camera.open(camIdx);
                    } catch (RuntimeException e) {
                        return false;
                    }
                }
            }
        }

        try {
            //这里的myCamera为已经初始化的Camera对象
            myCamera.setPreviewDisplay(myHolder);
        } catch (IOException e) {
            e.printStackTrace();
            myCamera.stopPreview();
            myCamera.release();
            myCamera = null;
        }

        myCamera.startPreview();

        return true;
    }

    /**
     * 对焦并拍照
     */
    private void autoFocus() {

//        try {
//            //因为开启摄像头需要时间，这里让线程睡两秒
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //自动对焦
        myCamera.autoFocus(myAutoFocus);

        //对焦后拍照
        myCamera.takePicture(null, null, myPicCallback);
    }


    //自动对焦回调函数(空实现)
    private Camera.AutoFocusCallback myAutoFocus = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
        }
    };


    //拍照成功回调函数
    private Camera.PictureCallback myPicCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //完成拍照后关闭Activity
//            getActivity().finish();

            //将得到的照片进行270°旋转，使其竖直
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            matrix.preRotate(270);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            //创建并保存图片文件
            File pictureFile = new File(Constants.SDCARDEBUSPHOTOGRAPH, DateUtil.getYMDDate() + ".jpg");
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception error) {
//                Toast.makeText(CameraActivity.this, "拍照失败", Toast.LENGTH_SHORT).show();
                Log.e("carema", "保存照片失败" + error.toString());
                error.printStackTrace();
                myCamera.stopPreview();
                myCamera.release();
                myCamera = null;
            }


            Log.e("carema", "获取照片成功");
            myCamera.stopPreview();
            myCamera.release();
            myCamera = null;
            ProgressDialogUtils.dismissProgressBar();
            //隐藏拍照完成，跳转例保界面
            Intent intent = new Intent(AnimationActivity.this, SigninActivity.class);
            intent.putExtra("taskid", "" + taskid);
            intent.putExtra("buscode", "" + buscode);
            intent.putExtra("token", "" + token);
            intent.putExtra("planShiftNo", "" + planShiftNo);
            intent.putExtra("navigLat", getIntent().getStringExtra("navigLat"));
            intent.putExtra("navigLng", getIntent().getStringExtra("navigLng"));
            startActivity(intent);
            finish();
        }
    };

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
}
