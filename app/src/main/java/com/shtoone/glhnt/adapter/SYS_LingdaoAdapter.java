package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.SYS_Lingdao_ly;

/**
 * Created by Administrator on 2015/11/23.
 */
public class SYS_LingdaoAdapter extends BaseAdapter {
    private int resource;
    private SYS_Lingdao_ly items;
    private LayoutInflater inflater;
    private Context context;
    private String userGroupId;
    private String xmmc;

    public SYS_LingdaoAdapter(Context context, int resource, SYS_Lingdao_ly items, String userGroupId, String xmmc) {
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
            cache.sys1 = (TextView) convertView.findViewById(R.id.sys1);
            cache.sys2 = (TextView) convertView.findViewById(R.id.sys2);
            cache.sys3 = (TextView) convertView.findViewById(R.id.sys3);
            cache.sys4 = (TextView) convertView.findViewById(R.id.sys4);
            cache.t1 = (TextView) convertView.findViewById(R.id.sys_count);
            cache.t2 = (TextView) convertView.findViewById(R.id.syj_count);
            cache.t3 = (TextView) convertView.findViewById(R.id.sysdh_item1_1);
            cache.t4 = (TextView) convertView.findViewById(R.id.sysdh_item1_2);
            cache.t5 = (TextView) convertView.findViewById(R.id.sysdh_item1_3);
            cache.t6 = (TextView) convertView.findViewById(R.id.sysdh_item1_4);
            cache.t7 = (TextView) convertView.findViewById(R.id.sysdh_item2_1);
            cache.t8 = (TextView) convertView.findViewById(R.id.sysdh_item2_2);
            cache.t9 = (TextView) convertView.findViewById(R.id.sysdh_item2_3);
            cache.t10 = (TextView) convertView.findViewById(R.id.sysdh_item2_4);
            cache.t11 = (TextView) convertView.findViewById(R.id.sysdh_item3_1);
            cache.t12 = (TextView) convertView.findViewById(R.id.sysdh_item3_2);
            cache.t13 = (TextView) convertView.findViewById(R.id.sysdh_item3_3);
            cache.t14 = (TextView) convertView.findViewById(R.id.sysdh_item3_4);
            cache.t15 = (TextView) convertView.findViewById(R.id.sysdh_item4_1);
            cache.t16 = (TextView) convertView.findViewById(R.id.sysdh_item4_2);
            cache.t17 = (TextView) convertView.findViewById(R.id.sysdh_item4_3);
            cache.t18 = (TextView) convertView.findViewById(R.id.sysdh_item4_4);
//            cache.sys1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_HuNiTuQiangDu_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_HuNiTuQiangDu_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t4.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_HuNiTuQiangDu_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t5.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_BuHeGeSYChuZhi_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t6.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_BuHeGeSYChuZhi_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.sys2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_GangJingLaLi_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t7.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_GangJingLaLi_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t8.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_GangJingLaLi_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t9.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_BuHeGeSYChuZhi_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t10.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_BuHeGeSYChuZhi_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.sys3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_GangJingHJieTou_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t11.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_GangJingHJieTou_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t12.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_GangJingHJieTou_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t13.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_BuHeGeSYChuZhi_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t14.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_BuHeGeSYChuZhi_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.sys4.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_GangJingJXLJJieTou_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t15.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_GangJingJXLJJieTou_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t16.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_GangJingJXLJJieTou_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t17.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_BuHeGeSYChuZhi_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            cache.t18.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, SYS_BuHeGeSYChuZhi_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
            convertView.setTag(cache);
        } else {
            cache = (ItemCache) convertView.getTag();
        }

        if (items != null && items.isSuccess() && items.getData().size() > 0) {

            cache.name.setText(items.getData().get(position).get(0).getDepartName());
            //Log.d("实验室与试验机个数：",item.getSysCount() + " " + item.getSyjCount());
            cache.t1.setText(items.getData().get(position).get(0).getSysCount());     //拌合站总数
            cache.t2.setText(items.getData().get(position).get(0).getSyjCount());     //拌合机总数
            cache.t3.setText(Html.fromHtml(IsNull(items.getData().get(position).get(0).getTestCount())));
            cache.t4.setText(Html.fromHtml(IsNull(items.getData().get(position).get(0).getNotQualifiedCount())));
            cache.t5.setText(Html.fromHtml(IsNull(items.getData().get(position).get(0).getRealCount())));
            if (!"".equals(items.getData().get(position).get(0).getRealPer()) && items.getData().get(position).get(0).getRealPer() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
                cache.t6.setText(Html.fromHtml(items.getData().get(position).get(0).getRealPer() + "%"));
            } else {
                cache.t6.setText(Html.fromHtml("0.00%"));
            }
            cache.t7.setText(Html.fromHtml(IsNull(items.getData().get(position).get(1).getTestCount())));
            cache.t8.setText(Html.fromHtml(IsNull(items.getData().get(position).get(1).getNotQualifiedCount())));
            cache.t9.setText(Html.fromHtml(IsNull(items.getData().get(position).get(1).getRealCount())));
            if (!"".equals(items.getData().get(position).get(1).getRealPer()) && items.getData().get(position).get(1).getRealPer() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
                cache.t10.setText(Html.fromHtml(items.getData().get(position).get(1).getRealPer() + "%"));
            } else {
                cache.t10.setText(Html.fromHtml("0.00%"));
            }
            //暂时先写成0
            cache.t11.setText(Html.fromHtml("0"));
            cache.t12.setText(Html.fromHtml("0"));
            cache.t13.setText(Html.fromHtml("0"));
            cache.t14.setText(Html.fromHtml("0"));
            cache.t15.setText(Html.fromHtml("0"));
            cache.t16.setText(Html.fromHtml("0"));
            cache.t17.setText(Html.fromHtml("0"));
            cache.t18.setText(Html.fromHtml("0"));

//            cache.t11.setText(Html.fromHtml(IsNull(items.getData().get(position).get(2).getTestCount())));
//            cache.t12.setText(Html.fromHtml(IsNull(items.getData().get(position).get(2).getNotQualifiedCount())));
//            cache.t13.setText(Html.fromHtml(IsNull(items.getData().get(position).get(2).getRealCount())));
//            if (!"".equals(items.getData().get(position).get(2).getRealPer()) && items.getData().get(position).get(2).getRealPer() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
//                cache.t14.setText(Html.fromHtml(items.getData().get(position).get(2).getRealPer() + "%"));
//            } else {
//                cache.t14.setText(Html.fromHtml("0.00%"));
//            }
//            cache.t15.setText(Html.fromHtml(IsNull(items.getData().get(position).get(3).getTestCount())));
//            cache.t16.setText(Html.fromHtml(IsNull(items.getData().get(position).get(3).getNotQualifiedCount())));
//            cache.t17.setText(Html.fromHtml(IsNull(items.getData().get(position).get(3).getRealCount())));
//            if (!"".equals(items.getData().get(position).get(3).getRealPer()) && items.getData().get(position).get(3).getRealPer() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
//                cache.t18.setText(Html.fromHtml(items.getData().get(position).get(3).getRealPer() + "%"));
//            } else {
//                cache.t18.setText(Html.fromHtml("0.00%"));
//            }


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
        TextView sys1, sys2, sys3, sys4;
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        TextView t5;
        TextView t6;
        TextView t7;
        TextView t8;
        TextView t9;
        TextView t10;
        TextView t11;
        TextView t12;
        TextView t13;
        TextView t14;
        TextView t15;
        TextView t16;
        TextView t17;
        TextView t18;
    }
}
