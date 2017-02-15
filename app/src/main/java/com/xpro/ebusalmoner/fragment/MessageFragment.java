package com.xpro.ebusalmoner.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.adapter.MessageAdapter_S;
import com.xpro.ebusalmoner.baseapi.BaseFragment;
import com.xpro.ebusalmoner.entity.MessageDaily;

import java.util.ArrayList;
import java.util.List;


/**
 * 管理人员-->消息
 *
 * @author huangjh
 */

public class MessageFragment extends BaseFragment {
    private View view;
    private ListView listView;
    private MessageAdapter_S adapter;
    private List<MessageDaily> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_message_msg, null);

        initData();
        initView();

        return view;
    }

    public void initData() {
        data = new ArrayList<MessageDaily>();
        data.add(new MessageDaily("今日暴风暴雪，请注意防寒保暖", "2013-12-20 15:22"));
        data.add(new MessageDaily("今日暴风暴雪，请注意防寒保暖", "2016-09-17 08:00"));
        data.add(new MessageDaily("今日暴风暴雪，请注意防寒保暖", "2016-09-17 08:00"));
        data.add(new MessageDaily("今日世界末日，请于13：00登录诺亚方舟号", "2016-09-17 08:00"));
        data.add(new MessageDaily("今天雾霾，出门请带口罩", "2013-12-20 15:22"));

    }

    public void initView() {
        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new MessageAdapter_S(data, getActivity());
        listView.setAdapter(adapter);
    }

}
