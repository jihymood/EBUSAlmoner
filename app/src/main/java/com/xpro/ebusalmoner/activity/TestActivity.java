package com.xpro.ebusalmoner.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.baseapi.BaseActivity;
import com.xpro.ebusalmoner.data.HitchVehicle;
import com.xpro.ebusalmoner.data.HitchVehicleDB;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;


@ContentView(R.layout.activity_test)
public class TestActivity extends BaseActivity implements OnClickListener {
    @ViewInject(R.id.back)
    private Button back;
    @ViewInject(R.id.add)
    private Button add;
    @ViewInject(R.id.delete)
    private Button delete;
    @ViewInject(R.id.query)
    private Button query;
    @ViewInject(R.id.text)
    private TextView text;

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        query.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        HitchVehicleDB personDB = new HitchVehicleDB();
        switch (v.getId()) {
            case R.id.add:
                HitchVehicle person1 = new HitchVehicle();
                person1.setHitchLatitude("31.9697048489");
                person1.setHitchLongitude("118.7748744881");
                person1.setHitchTime("2012.02.12");
                person1.setLine("108路");
                person1.setPlateNumber("苏A 2334");
                person1.setDriverName("王大锤");
                person1.setDriverTel("13109302381");
                personDB.savePerson(person1);
                break;
            case R.id.delete:
                personDB.delete();
                break;
            case R.id.query:
                List<HitchVehicle> hitchList = personDB.loadPerson();
                if (hitchList == null) {
                    return;
                } else {
                    for (HitchVehicle hitchVehicle : hitchList) {
                        text.setText(hitchVehicle.toString());
                    }
                }
                break;
            default:
                break;
        }
    }

}
