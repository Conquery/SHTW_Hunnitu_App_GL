package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.BHZ_ZongheTongji_Entity;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2015/11/27.
 */
public class BHZ_zonghetongjiCLAdapter extends BaseAdapter {
    private List<BHZ_ZongheTongji_Entity> datas;
    private LayoutInflater inflater;
    private int resource;

    public BHZ_zonghetongjiCLAdapter(Context context,List<BHZ_ZongheTongji_Entity> p_datas, int resource){
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
        if (convertView == null){
            convertView = inflater.inflate(resource, null);
            cache = new ItemCache();
            cache.tv_txtZongchanliangDate = (TextView) convertView.findViewById(R.id.tjfx_item_shijian);
            cache.tv_txtZongchanliangPanshu = (TextView) convertView.findViewById(R.id.tjfx_item_panshu);
            cache.tv_txtZongchanliangChangliang = (TextView) convertView.findViewById(R.id.tjfx_item_fangliang);
            convertView.setTag(cache);
        }else{
            cache = (ItemCache) convertView.getTag();
        }
        BHZ_ZongheTongji_Entity data = datas.get(position);
        cache.tv_txtZongchanliangDate.setText(data.getDate());
        cache.tv_txtZongchanliangPanshu.setText(String.valueOf(data.getPanshu()));
        DecimalFormat format=new DecimalFormat("#,##0.00");
        cache.tv_txtZongchanliangChangliang.setText(format.format(data.getChangliang()));
//        if (position % 2 == 0) {
//            convertView.setBackgroundColor(Color.argb(250, 78, 100, 132)); // 颜色设置
//        } else {
//            convertView.setBackgroundColor(Color.argb(255, 66, 90, 126));// 颜色设置
//        }
        return convertView;
    }

    private final class ItemCache {
        public TextView tv_txtZongchanliangDate;
        public TextView tv_txtZongchanliangPanshu;
        public TextView tv_txtZongchanliangChangliang;
    }
}
