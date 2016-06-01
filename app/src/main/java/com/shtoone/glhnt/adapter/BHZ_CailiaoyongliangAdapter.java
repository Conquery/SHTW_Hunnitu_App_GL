package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.BHZ_CaiLiaoYongLiang_Item;

import java.util.List;

/**
 * Created by Administrator on 2015/11/26.
 */
public class BHZ_CailiaoyongliangAdapter extends BaseAdapter {
    private int resource;
    private List<BHZ_CaiLiaoYongLiang_Item> items;
    private LayoutInflater inflater;

    public BHZ_CailiaoyongliangAdapter(Context context,int resource, List<BHZ_CaiLiaoYongLiang_Item> items) {
        this.resource = resource;
        this.items = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            cache.t1 = (TextView) convertView.findViewById(R.id.cailiao_item_mingcheng);
            cache.t2 = (TextView) convertView.findViewById(R.id.cailiao_item_shiji);
            cache.t3 = (TextView) convertView.findViewById(R.id.cailiao_item_peibi);
            cache.t4 = (TextView) convertView.findViewById(R.id.cailiao_item_wuchazhi);
            convertView.setTag(cache);
        } else {
            cache = (ItemCache) convertView.getTag();
        }
        BHZ_CaiLiaoYongLiang_Item item = items.get(position);
        cache.t1.setText(item.getName());
        cache.t2.setText(item.getShiji());
        cache.t3.setText(item.getPeibi());
        cache.t4.setText(item.getWuchazhi());
//        if (position % 2 == 0) {
//            convertView.setBackgroundColor(Color.argb(250, 78, 100, 132)); // 颜色设置
//        } else {
//            convertView.setBackgroundColor(Color.argb(255, 66, 90, 126));// 颜色设置
//        }
        return convertView;
    }

    private class ItemCache {
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
    }
}
