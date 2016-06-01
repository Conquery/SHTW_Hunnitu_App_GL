package com.shtoone.glhnt.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.SYS_GangJingLaLi_Entity;

import java.util.List;

/**
 * 钢筋拉力界面 列表适配器
 *
 * @author colorful
 */
public class SYS_GangJingLaLiAdapter extends BaseAdapter {

    private List<SYS_GangJingLaLi_Entity.DataEntity> items;
    private LayoutInflater inflater;
    private String type = "";

    public SYS_GangJingLaLiAdapter(Context context, List<SYS_GangJingLaLi_Entity.DataEntity> items, String type) {
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
            convertView = inflater.inflate(R.layout.sys_gjll_item, null);
            cache = new ItemCache();
            cache.time = (TextView) convertView.findViewById(R.id.hntqd_item_time);
            cache.nameMc = (TextView) convertView.findViewById(R.id.hntqd_item_name);
            cache.hege = (ImageView) convertView.findViewById(R.id.hntqd_item_hege_iv);
            cache.shijianNum = (TextView) convertView.findViewById(R.id.hntqd_item_shijianNum);
            cache.sjQiangdu = (TextView) convertView.findViewById(R.id.hntqd_item_sjQiangdu);
            cache.qdDaibiao = (TextView) convertView.findViewById(R.id.hntqd_item_qiangduDaibiao);
            cache.namePro = (TextView) convertView.findViewById(R.id.hntqd_item_projectName);
            cache.namePart = (TextView) convertView.findViewById(R.id.hntqd_item_partName);
            cache.pingzhong = (TextView) convertView.findViewById(R.id.gjll_item_pingzong);
            cache.qufuli_row = (LinearLayout) convertView.findViewById(R.id.qufuli_row);
            cache.testType = (TextView) convertView.findViewById(R.id.gjll_item_testType);
            if (type.equals("1"))
                cache.qufuli_row.setVisibility(View.VISIBLE);
            convertView.setTag(cache);
        } else {
            cache = (ItemCache) convertView.getTag();
        }
        SYS_GangJingLaLi_Entity.DataEntity item = items.get(position);
        cache.time.setText(item.getSYRQ());
        cache.nameMc.setText(item.getShebeiname());
        if ("合格".equals(item.getPDJG())) {
            cache.hege.setImageResource(R.drawable.ok);
        } else {
            cache.hege.setImageResource(R.drawable.ng);
        }
        cache.shijianNum.setText(item.getSJBH());
        cache.sjQiangdu.setText(item.getLZQD());
        cache.qdDaibiao.setText(item.getQFLZ());
        cache.pingzhong.setText(item.getPZBM());
        cache.namePro.setText(item.getGCMC());
        cache.namePart.setText(item.getSGBW());
        cache.testType.setText(item.testName);
        return convertView;
    }

    private class ItemCache {
        public TextView time;
        public TextView nameMc;
        public ImageView hege;
        public TextView shijianNum;
        public TextView sjQiangdu;
        public TextView qdDaibiao;
        public TextView pingzhong;
        public TextView namePro;
        public TextView namePart;
        public LinearLayout qufuli_row;
        public TextView testType;
    }

}
