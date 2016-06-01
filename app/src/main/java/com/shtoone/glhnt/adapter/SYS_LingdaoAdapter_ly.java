package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.SYS_Lingdao_ly;
import com.shtoone.glhnt.ui.DIY_sys_daohang_item;

/**
 * Created by Administrator on 2015/11/23.
 */
public class SYS_LingdaoAdapter_ly extends BaseAdapter {
    private int resource;
    private SYS_Lingdao_ly items;
    private LayoutInflater inflater;
    private Context context;
    private String userGroupId;
    private String xmmc;

    public SYS_LingdaoAdapter_ly(Context context, int resource, SYS_Lingdao_ly items, String userGroupId, String xmmc) {
        this.resource = resource;
        this.items = items;
        this.context = context;
        this.userGroupId = userGroupId;
        this.xmmc = xmmc;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return items.getData().get(position);
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
            cache.name = (TextView) convertView.findViewById(R.id.sys_zuzhiName);
            cache.sys = (TextView) convertView.findViewById(R.id.sys_count);
            cache.syj = (TextView) convertView.findViewById(R.id.syj_count);
            cache.ll_diy = (LinearLayout) convertView.findViewById(R.id.ll_sys_daohang_item_ly);
            convertView.setTag(cache);
        } else {
            cache = (ItemCache) convertView.getTag();
        }

        if (items != null && items.isSuccess() && items.getData().size() > 0) {

            Log.e("items","items:"+items.getData().get(position).size());
            cache.name.setText(items.getData().get(position).get(0).getDepartName());
            cache.sys.setText(items.getData().get(position).get(0).getSysCount());     //拌合站总数
            cache.syj.setText(items.getData().get(position).get(0).getSyjCount());     //拌合机总数xx
            for (int i = 0; i < items.getData().get(position).size(); i++) {

                Log.e("items","items:"+items.getData().get(position).size());


                DIY_sys_daohang_item diy_item = new DIY_sys_daohang_item(context);
                diy_item.setSysTestType(items.getData().get(position).get(i).getTestName());
                diy_item.setItem1(items.getData().get(position).get(i).getTestCount());
                diy_item.setItem2(items.getData().get(position).get(i).getNotQualifiedCount());
                diy_item.setItem3(items.getData().get(position).get(i).getRealCount());
                if (TextUtils.isEmpty(items.getData().get(position).get(i).getRealPer())) {  //将""写在前头，这样，不管name是否为null，都不会出错。
                    diy_item.setItem4(items.getData().get(position).get(i).getRealPer() + "%");
                } else {
                    diy_item.setItem4("0.00%");
                }
                cache.ll_diy.addView(diy_item);
            }
        }
        return convertView;
    }

    private String IsNull(String value) {
        if ("".equals(value) || null == value)
            return "0";
        else
            return value;
    }

    private class ItemCache {
        TextView name;
        TextView sys;
        TextView syj;
        LinearLayout ll_diy;
    }
}
