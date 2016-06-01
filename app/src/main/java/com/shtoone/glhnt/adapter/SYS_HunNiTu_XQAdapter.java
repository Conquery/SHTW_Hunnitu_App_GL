package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.activity.SYS_HuNiTuQiangDu_XqActivity;
import com.shtoone.glhnt.entity.COM_O2O;

import java.util.List;

public class SYS_HunNiTu_XQAdapter extends BaseAdapter {

    private List<COM_O2O> items;
    private LayoutInflater inflater;
    private Context context;

    public SYS_HunNiTu_XQAdapter(Context context, List<COM_O2O> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemCache cache;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.sys_hntqd_xq_item, null);
            cache = new ItemCache();
            cache.t1 = (TextView) convertView.findViewById(R.id.hntqd_xq_hezai);
            cache.t2 = (TextView) convertView.findViewById(R.id.hntqd_xq_qddanzhi);
            cache.t3 = (TextView) convertView.findViewById(R.id.hntqd_xq_chakan);
            convertView.setTag(cache);
        } else {
            cache = (ItemCache) convertView.getTag();
        }
        final COM_O2O item = items.get(position);
        cache.t1.setText(item.getName1());
        cache.t2.setText(item.getName2());
        cache.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SYS_HuNiTuQiangDu_XqActivity)context).updateChartView(item.getListXYs());
            }
        });
        return convertView;
    }

    private class ItemCache {
        private TextView t1;
        private TextView t2;
        private TextView t3;
    }

}
