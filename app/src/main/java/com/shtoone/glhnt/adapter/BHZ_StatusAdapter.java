package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.BHZ_Status;

import java.util.List;

/**
 * Created by Administrator on 2015/11/28.
 */
public class BHZ_StatusAdapter extends BaseAdapter {

    private List<BHZ_Status.DataEntity> items;
    private LayoutInflater inflater;

    public BHZ_StatusAdapter(Context context,List<BHZ_Status.DataEntity> items){
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
            convertView = inflater.inflate(R.layout.bhz_status_item, null);
            cache = new ItemCache();
            cache.bhz_sts_zname = (TextView) convertView.findViewById(R.id.bhz_sts_zname);
            cache.bhz_sts_jname = (TextView) convertView.findViewById(R.id.bhz_sts_jname);
            cache.bhz_sts = (TextView) convertView.findViewById(R.id.bhz_sts);
            cache.bhz_sts_chuliaoshijian_value = (TextView) convertView.findViewById(R.id.bhz_sts_chuliaoshijian_value);
            cache.bhz_sts_caijishijian_value = (TextView) convertView.findViewById(R.id.bhz_sts_caijishijian_value);
            cache.bhz_sts_baocunshijian_value = (TextView) convertView.findViewById(R.id.bhz_sts_baocunshijian_value);
            cache.bhz_sts_shangchuanshijian_value = (TextView) convertView.findViewById(R.id.bhz_sts_shangchuanshijian_value);
            convertView.setTag(cache);
        } else {
            cache = (ItemCache) convertView.getTag();
        }
        BHZ_Status.DataEntity item = items.get(position);
        cache.bhz_sts_zname.setText(item.getDepartname());
        cache.bhz_sts_jname.setText(item.getBanhezhanminchen());
        cache.bhz_sts.setText(item.getState());
        cache.bhz_sts_chuliaoshijian_value.setText(item.getChuliaoshijian());
        cache.bhz_sts_caijishijian_value.setText(item.getCaijishijian());
        cache.bhz_sts_baocunshijian_value.setText(item.getBaocunshijian());
        cache.bhz_sts_shangchuanshijian_value.setText(item.getShangchuanyanshi());
        return convertView;
    }

    private class ItemCache {
        TextView bhz_sts_zname;
        TextView bhz_sts_jname;
        TextView bhz_sts;
        TextView bhz_sts_chuliaoshijian_value;
        TextView bhz_sts_caijishijian_value;
        TextView bhz_sts_baocunshijian_value;
        TextView bhz_sts_shangchuanshijian_value;
    }
}
