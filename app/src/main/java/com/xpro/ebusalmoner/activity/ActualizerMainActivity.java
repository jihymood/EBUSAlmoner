package com.xpro.ebusalmoner.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.baseapi.BaseActivity;
import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.fragment.HistoryFragment;
import com.xpro.ebusalmoner.fragment.MessageFragment_S;
import com.xpro.ebusalmoner.fragment.SettingFragment;
import com.xpro.ebusalmoner.fragment.ShiftFragment_S1;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 实施人员-->进入主界面
 *
 * @ClassName: MainActivity
 * @Description: 主界面
 * @author: houyang
 * @date: 2016年9月20日 下午7:16:01
 */

@ContentView(R.layout.activity_main)
public class ActualizerMainActivity extends BaseActivity {
    @ViewInject(R.id.bottom_menu_group)
    private RadioGroup radioGroup;

    @ViewInject(R.id.bottom_menu_shift)
    private RadioButton radio_shift;

//	@ViewInject(R.id.bottom_menu_task)
//	private RadioButton radio_task;

    @ViewInject(R.id.bottom_menu_message)
    private RadioButton radio_message;

    @ViewInject(R.id.bottom_menu_setting)
    private RadioButton radio_setting;

    private final int MainContent = R.id.main_content;

    private ShiftFragment_S1 shiftFragment;
    private HistoryFragment historyFragment;
    private MessageFragment_S messageFragment;
    private SettingFragment settingFragment;

    private FragmentManager manager;
    private FragmentTransaction transaction;
    ;
    // 该变量用于接收推送过来接收的intent的传递值
    int push;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        super.initLayout(savedInstanceState);

        shiftFragment = new ShiftFragment_S1();
        historyFragment = new HistoryFragment();
        messageFragment = new MessageFragment_S();
        settingFragment = new SettingFragment();

        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        // 接收推送过来的值
        Intent intent = getIntent();
        push = intent.getIntExtra("push", 0);
        // 若是推送过来的消息跳转的mainactivity
        if (push == Constants.notice) {
            transaction.add(R.id.main_content, messageFragment);
            radio_shift.setChecked(false);
            radio_message.setChecked(true);
        } else {
            // 登录获取启动时跳转的mainactivity
            transaction.add(R.id.main_content, shiftFragment);
        }
        transaction.commit();
        initBottomMenu();

    }


    private void initBottomMenu() {
        WindowManager wm = getWindowManager();
//        int w = (int) (wm.getDefaultDisplay().getWidth() / 4 * 0.33);
//        int h = (int) (DensityUtil.dip2px(50) * 0.55);
        int w = (int) (wm.getDefaultDisplay().getWidth() / 5 * 0.28);
        int h = (int) (DensityUtil.dip2px(50) * 0.38);

        Drawable drawable_homepage = getResources().getDrawable(R.drawable.selector_bottom_menu_shift);
        drawable_homepage.setBounds(0, 0, w, h);

        radio_shift.setCompoundDrawables(null, drawable_homepage, null, null);

//        Drawable drawable_shoppingcart = getResources().getDrawable(R.drawable.selector_bottom_menu_task);
//        drawable_shoppingcart.setBounds(0, 0, w, h);
//        radio_task.setCompoundDrawables(null, drawable_shoppingcart, null, null);

        Drawable drawable_message = getResources().getDrawable(R.drawable.selector_bottom_menu_message);
        drawable_message.setBounds(0, 0, w, h);
        radio_message.setCompoundDrawables(null, drawable_message, null, null);

        Drawable drawable_mine = getResources().getDrawable(R.drawable.selector_bottom_menu_setting);
        drawable_mine.setBounds(0, 0, w, h);
        radio_setting.setCompoundDrawables(null, drawable_mine, null, null);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                transaction = manager.beginTransaction();
                switch (checkedId) {
                    case R.id.bottom_menu_shift:
                        transaction.replace(MainContent, shiftFragment);
                        break;

//                    case R.id.bottom_menu_task:
//                        transaction.replace(MainContent, historyFragment);
//                        break;

                    case R.id.bottom_menu_message:
                        transaction.replace(MainContent, messageFragment);
                        break;

                    case R.id.bottom_menu_setting:
                        transaction.replace(MainContent, settingFragment);
                        break;
                }
                transaction.commit();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String line = getIntent().getStringExtra("line");
                    Log.e("mm", line + "");
                }
                break;

            default:
                break;
        }
    }

}
