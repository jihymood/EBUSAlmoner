package com.xpro.ebusalmoner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.activity.HistoryInfoActivity;
import com.xpro.ebusalmoner.adapter.RouteAdapter;
import com.xpro.ebusalmoner.baseapi.BaseFragment;
import com.xpro.ebusalmoner.entity.BreakdownData_M;
import com.xpro.ebusalmoner.entity.CharacterParser;
import com.xpro.ebusalmoner.entity.PinyinComparator;
import com.xpro.ebusalmoner.entity.SortModel;

import java.util.ArrayList;
import java.util.List;


/**
 * 管理人员-->历史
 *
 * @author huangjh
 */
public class HistoryFragment extends BaseFragment implements OnClickListener {
    private View view;
    private ListView listView;
    private ArrayAdapter<SortModel> adapter;
    private RouteAdapter adapter1;
    private List<BreakdownData_M> data;
    private CharacterParser characterParser;// 汉字转换成拼音的类
    private PinyinComparator pinyinComparator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, null);

        initData();
        initView();
        onListener();

        return view;
    }

    public void initData() {
        data = new ArrayList<BreakdownData_M>();
//		data.add(new BreakdownBody_M("","","230路故障","2012.09.17","JN 0039", "江苏省南京市江宁区将军大道"));
//		data.add(new BreakdownBody_M("201路故障", "2012.09.17","JN 0039", "江苏省南京市江宁区将军大道"));
    }

    public void initView() {
        listView = (ListView) view.findViewById(R.id.listView);
        adapter1 = new RouteAdapter(getActivity(), data);
        listView.setAdapter(adapter1);
    }

    public void onListener() {
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                BreakdownData_M breakdown = data.get(position);
                String line = breakdown.getLineName();
                Intent intent = new Intent(getActivity(), HistoryInfoActivity.class);
                intent.putExtra("line", line);
                intent.putExtra("name", "王大锤");
                intent.putExtra("tel", "13851669521");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            default:
                break;
        }
    }

}
