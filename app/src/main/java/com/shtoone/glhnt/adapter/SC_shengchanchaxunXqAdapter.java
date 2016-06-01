package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.SC_chaxunItem_xq_data;

import java.util.List;

public class SC_shengchanchaxunXqAdapter extends BaseAdapter {

	private List<SC_chaxunItem_xq_data> lists;
	private LayoutInflater inflater;

	public SC_shengchanchaxunXqAdapter(Context context,List<SC_chaxunItem_xq_data> lists) {
		this.lists = lists;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemCache cache;
		if(convertView == null) {
			convertView = inflater.inflate( R.layout.sc_shengchanchaxun_xq_item, null);
			cache = new ItemCache();
			cache.tv_mc = (TextView) convertView.findViewById(R.id.xq_item_mc);
			cache.tv_sj = (TextView) convertView.findViewById(R.id.xq_item_sj);
			cache.tv_pb = (TextView) convertView.findViewById(R.id.xq_item_pb);
			cache.tv_wc = (TextView) convertView.findViewById(R.id.xq_item_wc);
			cache.tv_wcl = (TextView) convertView.findViewById(R.id.xq_item_wcl);
			convertView.setTag(cache);
		}else {
			cache = (ItemCache) convertView.getTag();
		}
		SC_chaxunItem_xq_data d = lists.get(position);
		cache.tv_mc.setText(d.getName());
		cache.tv_sj.setText(d.getShiji());
		cache.tv_pb.setText(d.getPeibi());
		cache.tv_wc.setText(d.getWucha());
		cache.tv_wcl.setText(d.getCb());
//		if ("".equals(d.getCb())) {
//		} else if ("1".equals(d.getCb()) || "4".equals(d.getCb())) {
//			cache.tv_wcl.setTextColor(context.getResources().getColor(R.color.buttonBgBlue));
//		} else if ("2".equals(d.getCb()) || "5".equals(d.getCb())) {
//			cache.tv_wcl.setTextColor(context.getResources().getColor(R.color.green));
//		} else if ("3".equals(d.getCb()) || "6".equals(d.getCb())) {
//			cache.tv_wcl.setTextColor(context.getResources().getColor(R.color.red));
//		}
		return convertView;
	}
	
	private final class ItemCache {
		public TextView tv_mc;
		public TextView tv_sj;
		public TextView tv_pb;
		public TextView tv_wc;
		public TextView tv_wcl;
	}

}
