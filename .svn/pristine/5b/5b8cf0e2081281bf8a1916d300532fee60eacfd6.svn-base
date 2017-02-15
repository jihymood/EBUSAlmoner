package com.xpro.ebusalmoner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xpro.ebusalmoner.R;

import java.util.ArrayList;


public class CaptianSettingDrawAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<String> database;

    public CaptianSettingDrawAdapter(Context context, ArrayList<String> data) {
        super();
        this.context = context;
        this.database = data;
        this.database=data;
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return database.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub

        //settings_text

        LayoutInflater inflater = LayoutInflater.from(context);
        Viewholder holder;

        if (arg1 == null) {
            arg1 = inflater.inflate(R.layout.adapater_captain_mysetting_draw, null);
            holder = new Viewholder();

            holder.settings_textview = (TextView) arg1.findViewById(R.id.settings_text);

            arg1.setTag(holder);
        } else {
            holder = (Viewholder) arg1.getTag();
        }

        String parseString = database.get(arg0);
        holder.settings_textview.setText(parseString);


        return arg1;
    }

    class Viewholder {
        TextView settings_textview;
    }
}
