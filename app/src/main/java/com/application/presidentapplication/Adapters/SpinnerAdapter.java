package com.application.presidentapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.application.presidentapplication.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private List<String> lstData;
    private Activity activity;
    private LayoutInflater inflater;

    public SpinnerAdapter(List<String> lstData, Activity activity) {
        this.lstData = lstData;
        this.activity = activity;
        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lstData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView == null)
            view = inflater.inflate(R.layout.spinner1_item,null);
        TextView tv = view.findViewById(R.id.textView1);
        tv.setText(lstData.get(position));
        return view;
    }
}
