package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.SYS_BuHeGeChuZhi_xqList;

import java.util.List;

public class SYS_BuhegechuzhiXQAdapter extends BaseAdapter {

    private List<SYS_BuHeGeChuZhi_xqList> items;
    private LayoutInflater inflater;

    public SYS_BuhegechuzhiXQAdapter(Context context, List<SYS_BuHeGeChuZhi_xqList> items) {
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
            cache = new ItemCache();
            convertView = inflater.inflate(R.layout.buhegechuzhixq_item, null);
            cache.t1 = (TextView) convertView.findViewById(R.id.bhgcz_xq_item_id);
            cache.t2 = (TextView) convertView.findViewById(R.id.bhgcz_xq_item_lizhi);
            cache.t3 = (TextView) convertView.findViewById(R.id.bhgcz_xq_item_qufulizhi);
            cache.t4 = (TextView) convertView.findViewById(R.id.bhgcz_xq_item_qiangdu);
            convertView.setTag(cache);
        } else {
            cache = (ItemCache) convertView.getTag();
        }
        SYS_BuHeGeChuZhi_xqList item = items.get(position);
        cache.t1.setText(item.getId());
        cache.t2.setText(item.getLizhi());
        cache.t3.setText(item.getQufulizhi());
        cache.t4.setText(item.getQiangdu());
        return convertView;
    }

    private class ItemCache {
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;

    }

}
