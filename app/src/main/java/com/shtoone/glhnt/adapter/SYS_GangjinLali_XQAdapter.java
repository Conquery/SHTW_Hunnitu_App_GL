package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.activity.SYS_GangJingLaLi_XqActivity;
import com.shtoone.glhnt.entity.COM_O2O;

import java.util.List;

/**
 * Created by Administrator on 2015/11/27.
 */
public class SYS_GangjinLali_XQAdapter extends BaseAdapter {
    private List<COM_O2O> items;
    private LayoutInflater inflater;
    private Context context;
    private String type = "";

    public SYS_GangjinLali_XQAdapter(Context context, List<COM_O2O> items,String type) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.type = type;
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
            convertView = inflater.inflate(R.layout.sys_gangjin_lizhi_item, null);
            cache = new ItemCache();
            cache.t1 = (TextView) convertView.findViewById(R.id.txtData1);
            cache.t2 = (TextView) convertView.findViewById(R.id.txtData2);
            cache.t3 = (TextView) convertView.findViewById(R.id.txtData3);
            cache.t4 = (TextView) convertView.findViewById(R.id.txtData4);
            if("1".equals(type)){
                cache.t3.setVisibility(View.VISIBLE);
                cache.t4.setVisibility(View.VISIBLE);
            }
            cache.btn = (TextView) convertView.findViewById(R.id.chakan);
            convertView.setTag(cache);
        } else {
            cache = (ItemCache) convertView.getTag();
        }
        final COM_O2O item = items.get(position);
        cache.t1.setText(item.getName1());
        cache.t2.setText(item.getName2());
        cache.t3.setText(item.getName3());
        cache.t4.setText(item.getName4());
        cache.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SYS_GangJingLaLi_XqActivity) context).updateChartView(item.getListXYs());
            }
        });
        return convertView;
    }

    private class ItemCache {
        private TextView t1;
        private TextView t2;
        private TextView t3;
        private TextView t4;
        private TextView btn;
    }
}
