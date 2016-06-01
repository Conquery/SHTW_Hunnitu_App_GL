package com.shtoone.glhnt.treenode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shtoone.glhnt.R;

import java.util.List;


public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T> {

	private int currentSelecet;
	private Context context;

	public SimpleTreeAdapter(int currentSelecet, ListView mTree, Context context, List<T> datas, int defaultExpandLevel)
			throws IllegalArgumentException, IllegalAccessException {
		super(mTree, context, datas, defaultExpandLevel);
		this.currentSelecet = currentSelecet;
		this.context = context;
	}

	@Override
	public View getConvertView(final Node node, int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = new ViewHolder();
		convertView = mInflater.inflate(R.layout.zuzhimianban_list_item, parent, false);
		viewHolder.icon = (ImageView) convertView.findViewById(R.id.id_treenode_icon);
		viewHolder.label = (TextView) convertView.findViewById(R.id.id_treenode_label);
		viewHolder.settting = (TextView) convertView.findViewById(R.id.id_treenode_setting);
		if (node.getIcon() == -1) {
			viewHolder.icon.setVisibility(View.VISIBLE);
			if (node.getLevel() == 3) {
				viewHolder.icon.setImageResource(R.drawable.tree_end);
			} else {
				viewHolder.icon.setImageResource(R.drawable.tree_ex);
			}
		} else {
			viewHolder.icon.setVisibility(View.VISIBLE);
			viewHolder.icon.setImageResource(node.getIcon());
		}
		if (currentSelecet != node.getId()) {
			viewHolder.settting.setText("选择");
			viewHolder.settting.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//Toast.makeText(mContext, "已选择" + node.getTag(), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.putExtra("selectedGroupId", node.getTag());
					intent.putExtra("selectedGroupName", node.getName());
					((Activity) mContext).setResult(2, intent);
					((Activity) mContext).finish();
				}
			});
		} else {
			viewHolder.settting.setText("√");
			viewHolder.settting.setTextColor(Color.WHITE);
			viewHolder.settting.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btnchakan_selector));
		}
		viewHolder.label.setText(node.getName());
		viewHolder.label.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(mContext, "已选择" + node.getTag(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("selectedGroupId", node.getTag());
				intent.putExtra("selectedGroupName", node.getName());
				((Activity) mContext).setResult(2, intent);
				((Activity) mContext).finish();
			}
		});
//		if (position % 2 == 0) {
//			convertView.setBackgroundColor(Color.argb(250, 78, 100, 132)); // 颜色设置
//		} else {
//			convertView.setBackgroundColor(Color.argb(255, 66, 90, 126));// 颜色设置
//		}

		return convertView;
	}

	private final class ViewHolder {
		ImageView icon;
		TextView label;
		TextView settting;
	}
}
