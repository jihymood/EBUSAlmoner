package com.xpro.ebusalmoner.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.baseapi.BaseActivity;
import com.xpro.ebusalmoner.baseapi.BaseHandler;
import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.entity.LoginDriverEntity;
import com.xpro.ebusalmoner.logic.MainLogic;
import com.xpro.ebusalmoner.utils.DateUtil;
import com.xpro.ebusalmoner.utils.MobilePhoneUtils;
import com.xpro.ebusalmoner.utils.NetUtil;
import com.xpro.ebusalmoner.utils.PreferencesUtils;
import com.xpro.ebusalmoner.utils.ToastUtils;

import butterknife.ButterKnife;


/**
 * @ClassName: WelcomeActivity
 * @Description: 欢迎界面
 * @author: houyang
 * @date: 2016年9月26日 下午3:50:23
 */
public class WelcomeActivity extends BaseActivity implements View.OnClickListener {
    private MainLogic mainLogic;
    private Handler handler;
    private EditText editIp;
    private Button btnIp, btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        editIp = (EditText) findViewById(R.id.editIp);
        btnIp = (Button) findViewById(R.id.btnIp);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        editIp.setOnClickListener(this);
        btnIp.setOnClickListener(this);
//        btn1.setOnClickListener(this);
//        btn2.setOnClickListener(this);

        ButterKnife.bind(this);

        handler = new Myhandler(this);
        mainLogic = new MainLogic(handler, this);

        //判断网路是否开启
        if (!NetUtil.isNetworkOnline()) {//网络不可用
            showTip("网络不可用，请开启网络后再试", new OnCustomDialogConfirmListener() {
                @Override
                public void onClick() {
                    finish();
                }
            });
        } else {
            //网络可用
            Log.e("timeString", "time:" + DateUtil.getYMDate());
            //验证登陆接口
            mainLogic.login(MobilePhoneUtils.getMiei(this), MobilePhoneUtils.getSerialNumber());
        }

        /**
         * 开启webSocket连接
         */
//        connectSocket();

        /**
         * 开启消息服务
         */
//        Intent intent = new Intent();
//        intent.setAction("ymw.MY_SERVICE");
//        startService(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIp:
                String str = editIp.getText().toString();
                if (null == str || "".equals(str)) {

                } else {
                    PreferencesUtils.putString(WelcomeActivity.this, "changeip", str);
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
//                    HttpUrls.baseUrl(getActivity(), editIp.getText() + "");//修改ip地址
                }
                break;
        }
    }


    String type;

    class Myhandler extends BaseHandler {
        public Myhandler(Context context) {
            super(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //登录成功
                case Constants.HANDLER_LOGIN_SUCCESS:
                    ToastUtils.showToast(WelcomeActivity.this, "手机登陆验证成功");
                    LoginDriverEntity loginDeviceEntity = (LoginDriverEntity) msg.obj;
                    //将LoginID保存为文件
                    PreferencesUtils.putString(WelcomeActivity.this, "LoginId", "" + loginDeviceEntity.getPersonId());
                    PreferencesUtils.putString(WelcomeActivity.this, "name", "" + loginDeviceEntity.getName());
                    PreferencesUtils.putString(WelcomeActivity.this, "phone", "" + loginDeviceEntity.getPhone());
                    Log.e("loginDeviceEntity", "PersonId():" + loginDeviceEntity.getPersonId()
                            + "\nname:" + loginDeviceEntity.getName());

                    //登陆跳转
//                    String type = loginDeviceEntity.getType(); //身份id
//                    if ("4".equals(type)) {  //管理人员
//                        startActivity(new Intent(WelcomeActivity.this, ManagerMainActivity.class));
//                    } else if ("5".equals(type)) {  //实施人员
//                        startActivity(new Intent(WelcomeActivity.this, ActualizerMainActivity.class));
//                    }
//                    finish();

                    type = loginDeviceEntity.getType(); //身份id
                    changeType();//在不修改表里type类型的时候，切换到管理人员和救济人员

                    break;

                //登录失败
                case Constants.HANDLER_LOGIN_FAIL:
                    ToastUtils.showToast(WelcomeActivity.this, "" + msg.obj);
                    break;

            }
        }
    }

    /**
     * 在不修改表里type类型的时候，切换到管理人员和救济人员
     */
    public void changeType() {

        //跳转到管理人员
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登陆跳转
//                if ("4".equals(type)) {  //管理人员
//                    startActivity(new Intent(WelcomeActivity.this, ManagerMainActivity.class));
//                    finish();
//                }
                startActivity(new Intent(WelcomeActivity.this, ManagerMainActivity.class));
            }
        });

        //跳转到实施人员
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登陆跳转
//                if ("4".equals(type)) {  //管理人员
//                    startActivity(new Intent(WelcomeActivity.this, ActualizerMainActivity.class));
//                    finish();
//                }
                startActivity(new Intent(WelcomeActivity.this, ActualizerMainActivity.class));
            }
        });
    }


}