package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.SYS_TongJiFengXi_Item_Entity;

import java.util.List;

/**
 * Created by Administrator on 2015/11/22.
 */
public class SYS_TongJiFengXiAdapter extends BaseAdapter {
    private List<SYS_TongJiFengXi_Item_Entity> items;
    private int resource;
    private LayoutInflater inflater;

    public SYS_TongJiFengXiAdapter(Context context, int resource, List<SYS_TongJiFengXi_Item_Entity> items) {
        this.items = items;
        this.resource = resource;
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
        ItemCache cache = null;
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
            cache = new ItemCache();
            cache.t_biaoduan = (TextView) convertView.findViewById(R.id.tjfx_item_biaoduan);
            cache.t_cishu = (TextView) convertView.findViewById(R.id.tjfx_item_cishu);
            cache.t_hege = (TextView) convertView.findViewById(R.id.tjfx_item_hege);
            cache.t_youxiao = (TextView) convertView.findViewById(R.id.tjfx_item_youxiao);
            cache.t_buhege = (TextView) convertView.findViewById(R.id.tjfx_item_buhege);
            cache.t_hegelv = (TextView) convertView.findViewById(R.id.tjfx_item_hegelv);
            convertView.setTag(cache);
        } else {
            cache = (ItemCache) convertView.getTag();
        }
        SYS_TongJiFengXi_Item_Entity item = items.get(position);
        cache.t_biaoduan.setText(item.getBiaoduan());
        cache.t_cishu.setText(item.getCishu());
        cache.t_hege.setText(item.getHege());
        cache.t_youxiao.setText(item.getYouxiao());
        cache.t_buhege.setText(item.getBuhege());
        cache.t_hegelv.setText(item.getHegelv());
        return convertView;
    }

    private class ItemCache {
        public TextView t_biaoduan;
        public TextView t_cishu;
        public TextView t_hege;
        public TextView t_youxiao;
        public TextView t_buhege;
        public TextView t_hegelv;
    }
}
