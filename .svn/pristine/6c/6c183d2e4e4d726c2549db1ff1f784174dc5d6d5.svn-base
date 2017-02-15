package com.xpro.ebusalmoner.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.entity.MessageDaily;


public class MessageAdapter_S extends BaseAdapter {

	private List<MessageDaily> list;
	private Context context;
	private LayoutInflater inflater;
	
	public MessageAdapter_S(List<MessageDaily> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.message_daily_item, null);
			holder.content=(TextView) convertView.findViewById(R.id.content);
			holder.time=(TextView) convertView.findViewById(R.id.time);
//			holder.line=(TextView) convertView.findViewById(R.id.line);
//			holder.address=(TextView) convertView.findViewById(R.id.address);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		MessageDaily message=(MessageDaily) getItem(position);
		holder.content.setText(message.getContent());
		holder.time.setText(message.getTime());
//		holder.plateNumber.setText(message.getPlateNumber());
//		holder.address.setText(message.getAddress());
		
		return convertView;
	}

	class ViewHolder{
		TextView content;
		TextView time;
//		TextView line;
//		TextView address;
	}
	
}
