package com.xpro.ebusalmoner.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.baseapi.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_about)
public class AboutActivity extends BaseActivity implements OnClickListener {
    @ViewInject(R.id.about_describe)
    private TextView about_describe;
    @ViewInject(R.id.about_lianjie)
    private TextView about_lianjie;
    @ViewInject(R.id.about_company_logo)
    private ImageView logoImage;
    @ViewInject(R.id.back)
    private ImageView back;

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setTitle("关于");
        about_lianjie.setText(Html.fromHtml("<a href=\"http://wmdw.jswmw.com\">http://wmdw.jswmw.com</a> "));
        logoImage.setImageResource(R.mipmap.logo);
        about_describe.setOnClickListener(this);
        about_lianjie.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.about_describe: //app描述
                break;
            case R.id.about_lianjie://链接
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(about_lianjie.getText().toString());
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }


}
