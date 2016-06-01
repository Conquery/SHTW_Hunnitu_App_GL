package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.BHZ_SCshujuchaxun_Entity;

import java.util.List;

public class BHZ_ScshujuchaxunAdapter extends BaseAdapter {

    private List<BHZ_SCshujuchaxun_Entity> items;
    private LayoutInflater inflater;
    private int flag;

    public BHZ_ScshujuchaxunAdapter(Context context, List<BHZ_SCshujuchaxun_Entity> items, int flag) {
        this.items = items;
        this.flag = flag;
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
            convertView = inflater.inflate(R.layout.scshujuchaxun_item, null);
            cache = new ItemCache();
            cache.t1 = (TextView) convertView.findViewById(R.id.scshuju_item_time);
            cache.t2 = (TextView) convertView.findViewById(R.id.scshuju_item_name);
            cache.t3 = (TextView) convertView.findViewById(R.id.scshuju_item_proName);
            cache.t4 = (TextView) convertView.findViewById(R.id.scshuju_item_jzbw);
            cache.t5 = (TextView) convertView.findViewById(R.id.scshuju_item_ddlc);
            cache.t6 = (TextView) convertView.findViewById(R.id.scshuju_item_qddj);
            cache.t7 = (TextView) convertView.findViewById(R.id.scshuju_item_shuliang);
            convertView.setTag(cache);
        } else {
            cache = (ItemCache) convertView.getTag();
        }
        BHZ_SCshujuchaxun_Entity item = items.get(position);

        if (flag == 0) {
            cache.t1.setText(item.getDeviceName() + " " + item.getTime());
            cache.t2.setText("立即处置");
            cache.t2.setTextColor(Color.RED);
        } else if (flag == 1) {
            cache.t1.setText(item.getDeviceName() + " " + item.getTime());
            cache.t2.setText("已处置");
            cache.t2.setTextColor(Color.GREEN);
        }else{
            cache.t1.setText(item.getTime());
            cache.t2.setText(item.getDeviceName());
            cache.t2.setTextColor(Color.BLACK);
        }
        cache.t3.setText(item.getProName());
        cache.t4.setText(item.getJiaozhubuwei());
        cache.t5.setText(item.getDidianlicheng());
        cache.t6.setText(item.getQiangdudengji());
        cache.t7.setText(item.getShuliang());
        return convertView;
    }

    private class ItemCache {
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        TextView t5;
        TextView t6;
        TextView t7;
    }

}
