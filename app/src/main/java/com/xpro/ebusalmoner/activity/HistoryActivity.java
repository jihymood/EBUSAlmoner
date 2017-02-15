package com.xpro.ebusalmoner.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.adapter.HistoryAdapter;
import com.xpro.ebusalmoner.baseapi.BaseActivity;
import com.xpro.ebusalmoner.baseapi.BaseHandler;
import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.entity.CharacterParser;
import com.xpro.ebusalmoner.entity.HistoryBody_M;
import com.xpro.ebusalmoner.entity.HistoryData_M;
import com.xpro.ebusalmoner.entity.HistoryManagerEntity;
import com.xpro.ebusalmoner.entity.HistoryRoot_M;
import com.xpro.ebusalmoner.entity.PinyinComparator;
import com.xpro.ebusalmoner.entity.SortModel;
import com.xpro.ebusalmoner.logic.TaskLogic;
import com.xpro.ebusalmoner.utils.ToastUtils;
import com.xpro.ebusalmoner.widget.CommonDatePickerDialog;

import org.xutils.view.annotation.ContentView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 救济管理-->历史详情页
 * 接口里不需要传trailerCode
 *
 * @author huangjh
 */

@ContentView(R.layout.fragment_history)
public class HistoryActivity extends BaseActivity implements OnClickListener,
        OnCheckedChangeListener {

    private View view;
    private ListView listView;
    private ArrayAdapter<SortModel> adapter;
    private HistoryAdapter adapter1;
    private List<HistoryData_M> data;
    private List<HistoryData_M> data0;// 已完成
    private List<HistoryData_M> data1;// 待完成
    private List<HistoryData_M> data2;// 待完成
    private CharacterParser characterParser;// 汉字转换成拼音的类
    private PinyinComparator pinyinComparator;
    private TextView nameText, dateText;
    private RadioButton finishRadio, jcRadio, cancelRadio;
    private Calendar calendar;
    private SimpleDateFormat date, sdf;
    private Button set;
    private ImageView back;
    private String dateStr;
    private MyHandler handler;
    private TaskLogic logic;

    private String json = "{\"success\": \"true\", "
            + "\"body\": {\"data\":[{\"id\": \"100\", "
            + "\"plateNumber\": \"1号拖车\",\"hitchLatitude\": "
            + "\"31.9813440513\", \"hitchLongitude\": \"118.7626883184\", "
            + "\"driverName\": \"科比代笔\", \"driverTel\": \"18769029715\", "
            + "\"line\": \"108路\",\"state\":0,\"images_driver\":[{\"url\": \"http://img5.duitang" +
            ".com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"},"
            + "{\"url\": \"http://img5.duitang.com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"}],"
            + "\"cause\":\"轮胎爆破\",\"images_trailer\":[{\"url\": \"http://img5.duitang" +
            ".com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"},"
            + "{\"url\": \"http://img5.duitang.com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"}],"
            + "\"hitchTime\": \"2013.02.12\",\"number\": \"JN-0039\"},{\"id\": \"100\", "
            + "\"plateNumber\": \"1号拖车\",\"hitchLatitude\": "
            + "\"31.9813440513\", \"hitchLongitude\": \"118.7626883184\", "
            + "\"driverName\": \"科比呆逼\", \"driverTel\": \"18769029715\", "
            + "\"line\": \"118路\",\"state\":1,\"images_driver\":[{\"url\": \"http://img5.duitang" +
            ".com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"},"
            + "{\"url\": \"http://img5.duitang.com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"}],"
            + "\"cause\":\"轮胎爆破\",\"images_trailer\":[{\"url\": \"http://img5.duitang" +
            ".com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"},"
            + "{\"url\": \"http://img5.duitang.com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"}],"
            + "\"hitchTime\": \"2014.02.12\",\"number\": \"JN-0039\"},{\"id\": \"100\", "
            + "\"plateNumber\": \"1号拖车\",\"hitchLatitude\": "
            + "\"31.9813440513\", \"hitchLongitude\": \"118.7626883184\", "
            + "\"driverName\": \"科比代币\", \"driverTel\": \"18769029715\", "
            + "\"line\": \"128路\",\"state\":2,\"images_driver\":[{\"url\": \"http://img5.duitang" +
            ".com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"},"
            + "{\"url\": \"http://img5.duitang.com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"}],"
            + "\"cause\":\"轮胎爆破\",\"images_trailer\":[{\"url\": \"http://img5.duitang" +
            ".com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"},"
            + "{\"url\": \"http://img5.duitang.com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"}],"
            + "\"hitchTime\": \"2015.02.12\",\"number\": \"JN-0039\"}]}, \"errorCode\": \"-1\",\"msg\": \"成功\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
		Log.e("HistoryActivity", json);

        initView();

        handler = new MyHandler(HistoryActivity.this);
        logic = new TaskLogic(handler, HistoryActivity.this);
        HistoryManagerEntity entity = new HistoryManagerEntity();
        entity.setState("2");
        entity.setDetailType("1");
        entity.setHitchTime(dateStr);
        entity.setTrailerCode("");
        logic.hasCompleteTask(entity);

        adapter1 = new HistoryAdapter(data, this);//一进来默认填充state=0的数据，可以是data/data0
        listView.setAdapter(adapter1);

        onListener();
    }

    public void initView() {
        nameText = (TextView) findViewById(R.id.name);
        set = (Button) findViewById(R.id.set);
        back = (ImageView) findViewById(R.id.back);
        listView = (ListView) findViewById(R.id.listView);
        finishRadio = (RadioButton) findViewById(R.id.finishRadio);
        jcRadio = (RadioButton) findViewById(R.id.jcRadio);
        cancelRadio = (RadioButton) findViewById(R.id.cancelRadio);
        dateText = (TextView) findViewById(R.id.dateText);
        // if(ismanager){
        // nameText.setText("历史故障");
        // }else{
        // nameText.setText("历史任务");
        // }

        data = new ArrayList<HistoryData_M>();
        data0 = new ArrayList<HistoryData_M>();
        data1 = new ArrayList<HistoryData_M>();
        data2 = new ArrayList<HistoryData_M>();

        finishRadio.setOnCheckedChangeListener(this);
        jcRadio.setOnCheckedChangeListener(this);
        cancelRadio.setOnCheckedChangeListener(this);
        set.setOnClickListener(this);
        back.setOnClickListener(this);

        getTodayDate();
    }

    public void getTodayDate() {
        sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        dateStr = sdf.format(date);
        Log.e("HistoryActivity", "date:" + dateStr);
        dateText.setText(dateStr);
    }


    public void getData() {
        data = new ArrayList<HistoryData_M>();
        data0 = new ArrayList<HistoryData_M>();
        data1 = new ArrayList<HistoryData_M>();
        data2 = new ArrayList<HistoryData_M>();

        HistoryRoot_M root = JSON.parseObject(json, HistoryRoot_M.class);
        HistoryBody_M body = root.getBody();
        List<HistoryData_M> dataList = body.getData();
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                HistoryData_M subData = dataList.get(i);

                int state = subData.getState();
                if ("0".equals(state + "")) { // 已修好
                    data0.add(subData);
                    data.addAll(data0);
                    adapter1 = new HistoryAdapter(data, this);
                    listView.setAdapter(adapter1);
                } else if ("1".equals(state + "")) { // 进场维修
                    data1.add(subData);
                } else { // 已取消
                    data2.add(subData);
                }
            }
        }
    }

    /**
     * listview列表点击事件
     */
    public void onListener() {
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                HistoryData_M subData = data.get(position);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(HistoryActivity.this,
                        HistoryInfoActivity.class);
                bundle.putParcelable("HistoryData_M", subData);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    // 日期选择
    @SuppressLint("SimpleDateFormat")
    public void dateSelectBtn() {
        calendar = Calendar.getInstance();
        date = new SimpleDateFormat("yyyy年MM月dd日");// 月份选择

        CommonDatePickerDialog dialog = new CommonDatePickerDialog(this,
                AlertDialog.THEME_HOLO_LIGHT, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month,
                                  int day) {
                if (year == calendar.get(Calendar.YEAR)
                        && month == calendar.get(Calendar.MONTH)
                        && day == calendar.get(Calendar.DAY_OF_MONTH)) {
                    calendar.set(year, month, day);
                } else {
                    calendar.set(year, month, day); // 默认当月1号
                }
                // 确定按钮后查询接口，得到某一天的json串
                dateText.setText(date.format(calendar.getTime()));
                String date = sdf.format(calendar.getTime());
                Log.e("date",date);
                //查询某一天的数据
                data.clear();
                data0.clear();
                data1.clear();
                data2.clear();

                if (finishRadio.isChecked()) {
                    HistoryManagerEntity entity = new HistoryManagerEntity();
                    entity.setState("2");
                    entity.setDetailType("1");
                    entity.setHitchTime(date);
                    entity.setTrailerCode("");
                    logic.hasCompleteTask(entity);
                } else if (jcRadio.isChecked()) {
                    HistoryManagerEntity entity1 = new HistoryManagerEntity();
                    entity1.setState("2");
                    entity1.setDetailType("2");
                    entity1.setHitchTime(date);
                    entity1.setTrailerCode("");
                    logic.hasCompleteTask(entity1);
                } else if (cancelRadio.isChecked()) {
                    HistoryManagerEntity entity2 = new HistoryManagerEntity();
                    entity2.setState("3");
                    entity2.setDetailType("");
                    entity2.setHitchTime(dateStr);
                    entity2.setTrailerCode("");
                    logic.hasCompleteTask(entity2);
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.set:
                dateSelectBtn();
                break;
            case R.id.back:
                finish();
                break;

            default:
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.finishRadio: //切换到"已完成",清空data数据，将data0重新填充

                    HistoryManagerEntity entity = new HistoryManagerEntity();
                    entity.setState("2");
                    entity.setDetailType("1");
                    entity.setHitchTime(dateStr);
                    entity.setTrailerCode("");
                    logic.hasCompleteTask(entity);

//                    data.clear();
//                    data.addAll(data0);
//                    adapter1.notifyDataSetChanged();
                    break;
                case R.id.jcRadio:

                    HistoryManagerEntity entity1 = new HistoryManagerEntity();
                    entity1.setState("2");
                    entity1.setDetailType("2");
                    entity1.setHitchTime(dateStr);
                    entity1.setTrailerCode("");
                    logic.hasCompleteTask(entity1);

//                    data.clear();
//                    data.addAll(data1);
//                    adapter1.notifyDataSetChanged();
                    break;
                case R.id.cancelRadio:

                    HistoryManagerEntity entity2 = new HistoryManagerEntity();
                    entity2.setState("3");
                    entity2.setDetailType("");
                    entity2.setHitchTime(dateStr);
                    entity2.setTrailerCode("");
                    logic.hasCompleteTask(entity2);

//                    data.clear();
//                    data.addAll(data2);
//                    adapter1.notifyDataSetChanged();
                    break;

                default:
                    break;
            }

        }
    }

    public class MyHandler extends BaseHandler {
        public MyHandler(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void doHandle(Message msg) {
            // TODO Auto-generated method stub
            super.doHandle(msg);
            switch (msg.what) {
                case Constants.MANAGER_COMPLETE_SUCCESS:
                    List<HistoryData_M> dataList = (List<HistoryData_M>) msg.obj;
                    if (dataList != null && dataList.size() > 0) {
                        data.clear();
                        for (int i = 0; i < dataList.size(); i++) {
                            HistoryData_M subData = dataList.get(i);
                            data.add(subData);
                            adapter1.notifyDataSetChanged();
                        }
                    } else {
                        data.clear();
                        adapter1.notifyDataSetChanged();
                    }

                    break;

                case Constants.MANAGER_COMPLETE_FAIL:
                    ToastUtils.showToast(HistoryActivity.this, "" + msg.obj);
                    break;
                default:
                    break;
            }
        }
    }
}
