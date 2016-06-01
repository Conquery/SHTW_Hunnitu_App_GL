package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.SYS_BuHeGeChuZhi_Entity;

import java.util.List;

/**
 * 不合格处置界面 列表适配器
 * @author colorful
 */
public class SYS_BuHeGeChuZhiAdapter extends BaseAdapter {

	private List<SYS_BuHeGeChuZhi_Entity> items;
	private LayoutInflater inflater;
	private String type;
	
	public SYS_BuHeGeChuZhiAdapter(Context context, List<SYS_BuHeGeChuZhi_Entity> items,String type) {
		this.items = items;
		this.type = type;
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
			convertView = inflater.inflate(R.layout.buhegesychuzhi_item, null);
			cache = new ItemCache();
			cache.type = (TextView) convertView.findViewById(R.id.syTypeContent);
			cache.nameMc = (TextView) convertView.findViewById(R.id.bhgsy_shebeimingcheng);
			cache.time = (TextView) convertView.findViewById(R.id.bhgsy_syriqi);
			cache.namePro = (TextView) convertView.findViewById(R.id.bhgsycz_item_projectName);
			cache.namePart = (TextView) convertView.findViewById(R.id.bhgsycz_item_partName);
			cache.chuzhi = (TextView) convertView.findViewById(R.id.bhgsycz_chuzhi);
			cache.shijianNum = (TextView) convertView.findViewById(R.id.bhgsy_shijianNum);
			convertView.setTag(cache);
		} else {
			cache = (ItemCache) convertView.getTag();
		}
		SYS_BuHeGeChuZhi_Entity item = items.get(position);
		cache.type.setText(item.getType());
		cache.nameMc.setText(item.getShebeiName());
		cache.time.setText(item.getShijianTime());
		cache.namePro.setText(item.getProjectName());
		cache.namePart.setText(item.getShigongPart());
		if (type.equals("1")) {
			cache.chuzhi.setText("立即处置");
			cache.chuzhi.setTextColor(Color.RED);
		} else {
			cache.chuzhi.setText("已处置");
			cache.chuzhi.setTextColor(Color.GREEN);
		}
		cache.shijianNum.setText(item.getShijianNum());
		return convertView;
	}
	
	private class ItemCache{
		public TextView type;
		public TextView time;
		public TextView nameMc;
		public TextView namePro;
		public TextView namePart;
		public TextView chuzhi;
		public TextView shijianNum;
	}

}
