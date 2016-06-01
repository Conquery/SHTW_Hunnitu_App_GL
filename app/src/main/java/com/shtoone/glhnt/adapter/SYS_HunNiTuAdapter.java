package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.SYS_HunNiTu_Entity;

import java.util.List;

public class SYS_HunNiTuAdapter extends BaseAdapter {

    private List<SYS_HunNiTu_Entity.DataEntity> items;
    private LayoutInflater inflater;

    public SYS_HunNiTuAdapter(Context context, List<SYS_HunNiTu_Entity.DataEntity> items) {
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
            convertView = inflater.inflate(R.layout.sys_hntqd_item, null);
            cache = new ItemCache();
            cache.time = (TextView) convertView.findViewById(R.id.hntqd_item_time);
            cache.nameMc = (TextView) convertView.findViewById(R.id.hntqd_item_name);
            cache.hege = (ImageView) convertView.findViewById(R.id.hntqd_item_hege_iv);
            cache.sjQiangdu = (TextView) convertView.findViewById(R.id.hntqd_item_sjQiangdu);
            cache.qdDaibiao = (TextView) convertView.findViewById(R.id.hntqd_item_qiangduDaibiao);
            cache.namePro = (TextView) convertView.findViewById(R.id.hntqd_item_projectName);
            cache.namePart = (TextView) convertView.findViewById(R.id.hntqd_item_partName);
            cache.testType = (TextView) convertView.findViewById(R.id.hntqd_item_testType);
            convertView.setTag(cache);
        } else {
            cache = (ItemCache) convertView.getTag();
        }
        SYS_HunNiTu_Entity.DataEntity item = items.get(position);
        cache.time.setText(item.getSYRQ());
        cache.nameMc.setText(item.getShebeiname());
        if ("合格".equals(item.getPDJG()) || "有效".equals(item.getPDJG())) {
            cache.hege.setImageResource(R.drawable.ok);
        } else {
            cache.hege.setImageResource(R.drawable.ng);
        }
        cache.sjQiangdu.setText(item.getSJQD());
        cache.qdDaibiao.setText(item.getQDDBZ());
        cache.namePro.setText(item.getGCMC());
        cache.namePart.setText(item.getSGBW());
        cache.testType.setText(item.getTestName());
        return convertView;
    }

    private class ItemCache {
        public TextView time;
        public TextView nameMc;
        public TextView weituoNum;
        public ImageView hege;
        public TextView sjQiangdu;
        public TextView qdDaibiao;
        public TextView namePro;
        public TextView namePart;
        public TextView testType;
    }

}
