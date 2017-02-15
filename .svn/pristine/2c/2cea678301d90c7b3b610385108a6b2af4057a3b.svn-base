package com.xpro.ebusalmoner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.baseapi.BaseActivity;
import com.xpro.ebusalmoner.utils.PreferencesUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


/**
 * @ClassName: LoginActivity
 * @Description: 登录界面
 * @author: houyang
 * @date: 2016年9月26日 下午3:58:25
 */

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.edit_username)
    private EditText edit_username;

    @ViewInject(R.id.edit_password)
    private EditText edit_password;

    @ViewInject(R.id.clear_username)
    private ImageView clear_username;

    @ViewInject(R.id.clear_password)
    private ImageView clear_password;

    @ViewInject(R.id.btn_login)
    private Button btn_login;

    @ViewInject(R.id.changeip_btn)
    private TextView changeip_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.initLayout(savedInstanceState);

        initLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        edit_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    clear_username.setVisibility(View.VISIBLE);
                } else {
                    clear_username.setVisibility(View.GONE);
                }
            }
        });

        edit_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    clear_password.setVisibility(View.VISIBLE);
                } else {
                    clear_password.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initLogin() {
        clear_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_username.setText("");
            }
        });
        clear_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_password.setText("");
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edit_username.getText().toString().trim();
                String passWord = edit_password.getText().toString().trim();
                if (userName.equals("")) {
                    showTip("请输入用户名");
                    return;
                }
                if (passWord.equals("")) {
                    showTip("请输入密码");
                    return;
                }

                // //这边4行代码是单机测试，没有调用接口的时候用的
                // //自动存储用户名密码
                PreferencesUtils.putString(LoginActivity.this, "username", edit_username.getText().toString());
                PreferencesUtils.putString(LoginActivity.this, "password", edit_password.getText().toString());
                String name = edit_username.getText().toString();

                if ("1".equals(name)) {
                    startActivity(new Intent(LoginActivity.this, com.xpro.ebusalmoner.activity.ManagerMainActivity.class));
                } else {
                    startActivity(new Intent(LoginActivity.this, com.xpro.ebusalmoner.activity.ActualizerMainActivity.class));
                }

                finish();

            }
        });
    }

}
