package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.BHZ_ZongheTongji_Entity;

import java.util.List;

/**
 * Created by Administrator on 2015/11/27.
 */
public class BHZ_zonghetongjiWCAdapter extends BaseAdapter {
    private List<BHZ_ZongheTongji_Entity> datas;
    private LayoutInflater inflater;
    private int resource;

    public BHZ_zonghetongjiWCAdapter(Context context, List<BHZ_ZongheTongji_Entity> p_datas, int resource) {
        this.datas = p_datas;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ItemCache cache = null;
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
            cache = new ItemCache();
            cache.tv_txtZongchanliangDate = (TextView) convertView.findViewById(R.id.txtZongchanliangWCDate);
            cache.tv_txtZongchanliangGaobaojing = (TextView) convertView.findViewById(R.id.txtZongchanliangGaobaojing);
            cache.tv_txtZongchanliangZhongbaojing = (TextView) convertView.findViewById(R.id.txtZongchanliangZhongbaojing);
            cache.tv_txtZongchanliangDibaojing = (TextView) convertView.findViewById(R.id.txtZongchanliangDibaojing);
            convertView.setTag(cache);
        } else {
            cache = (ItemCache) convertView.getTag();
        }
        BHZ_ZongheTongji_Entity data = datas.get(position);
        cache.tv_txtZongchanliangDate.setText(data.getDate());
        cache.tv_txtZongchanliangGaobaojing.setText(String.valueOf(data.getHighlv()));
        cache.tv_txtZongchanliangZhongbaojing.setText(String.valueOf(data.getMiddlelv()));
        cache.tv_txtZongchanliangDibaojing.setText(String.valueOf(data.getPrimarylv()));
//        if (position % 2 == 0) {
//            convertView.setBackgroundColor(Color.argb(250, 78, 100, 132)); // 颜色设置
//        } else {
//            convertView.setBackgroundColor(Color.argb(255, 66, 90, 126));// 颜色设置
//        }
        return convertView;
    }


    private final class ItemCache {
        public TextView tv_txtZongchanliangDate;
        public TextView tv_txtZongchanliangGaobaojing;
        public TextView tv_txtZongchanliangZhongbaojing;
        public TextView tv_txtZongchanliangDibaojing;
    }
}
