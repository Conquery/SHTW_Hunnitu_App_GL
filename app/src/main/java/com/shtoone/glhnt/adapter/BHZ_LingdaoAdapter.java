package com.shtoone.glhnt.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.BHZ_Lingdao;

import java.util.List;

/**
 * Created by Administrator on 2015/11/23.
 */
public class BHZ_LingdaoAdapter extends BaseAdapter {
    private int resource;
    private List<BHZ_Lingdao> items;
    private LayoutInflater inflater;
	private Context context;
    private String userGroupId;
    private String xmmc;

    public BHZ_LingdaoAdapter(Context context,int resource, List<BHZ_Lingdao> items,String userGroupId,String xmmc) {
        this.resource = resource;
        this.items = items;
		this.context = context;
        this.userGroupId = userGroupId;
        this.xmmc = xmmc;
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
            cache.name = (TextView) convertView.findViewById(R.id.bhz_zuzhiName);
            cache.t1 = (TextView) convertView.findViewById(R.id.bhz_count);                      //拌合站个数
            cache.t2 = (TextView) convertView.findViewById(R.id.bhj_count);                      //拌合机个数
            cache.t3 = (TextView) convertView.findViewById(R.id.sysdh_item_zongpanshu);         //总盘数
            cache.t4 = (TextView) convertView.findViewById(R.id.sysdh_item_zongfangliang);     //总方量
            cache.t5 = (TextView) convertView.findViewById(R.id.sysdh_item_chuji_1);            //初级超标盘数
            cache.t6 = (TextView) convertView.findViewById(R.id.sysdh_item_chuji_2);            //初级超标率
            cache.t7 = (TextView) convertView.findViewById(R.id.sysdh_item_chuji_3);            //初级处置盘数
            cache.t8 = (TextView) convertView.findViewById(R.id.sysdh_item_chuji_4);            //初级处置率
            cache.t9 = (TextView) convertView.findViewById(R.id.sysdh_item_zhongji_1);          //中级超标盘数
            cache.t10 = (TextView) convertView.findViewById(R.id.sysdh_item_zhongji_2);         //中级超标率
            cache.t11 = (TextView) convertView.findViewById(R.id.sysdh_item_zhongji_3);         //中级处置盘数
            cache.t12 = (TextView) convertView.findViewById(R.id.sysdh_item_zhongji_4);         //中级处置率
            cache.t13 = (TextView) convertView.findViewById(R.id.sysdh_item_gaoji_1);            //高级超标盘数
            cache.t14 = (TextView) convertView.findViewById(R.id.sysdh_item_gaoji_2);            //高级超标率
            cache.t15 = (TextView) convertView.findViewById(R.id.sysdh_item_gaoji_3);            //高级处置盘数
            cache.t16 = (TextView) convertView.findViewById(R.id.sysdh_item_gaoji_4);            //高级处置率

//            //跳转到拌合站综合统分
//            cache.t3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, BHZ_TongjifenxiActivity.class);
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
//                    Intent intent = new Intent(context, BHZ_TongjifenxiActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//
//            //初级
//            cache.t5.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, BHZ_SCshujuchaxun_Activity.class);
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
//                    Intent intent = new Intent(context, BHZ_SCshujuchaxun_Activity.class);
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
//                    Intent intent = new Intent(context, BHZ_ChaobiaoChuZHi_Activity.class);
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
//                    Intent intent = new Intent(context, BHZ_ChaobiaoChuZHi_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//            //中级
//            cache.t9.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, BHZ_SCshujuchaxun_Activity.class);
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
//                    Intent intent = new Intent(context, BHZ_SCshujuchaxun_Activity.class);
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
//                    Intent intent = new Intent(context, BHZ_ChaobiaoChuZHi_Activity.class);
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
//                    Intent intent = new Intent(context, BHZ_ChaobiaoChuZHi_Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userGroupID", userGroupId);
//                    bundle.putString("xmmc", xmmc);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
//
//            //高级
//            cache.t13.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, BHZ_SCshujuchaxun_Activity.class);
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
//                    Intent intent = new Intent(context, BHZ_SCshujuchaxun_Activity.class);
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
//                    Intent intent = new Intent(context, BHZ_ChaobiaoChuZHi_Activity.class);
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
//                    Intent intent = new Intent(context, BHZ_ChaobiaoChuZHi_Activity.class);
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
        BHZ_Lingdao item = items.get(position);
        cache.name.setText(item.getBiaoduan());                            //组织结构名称
        cache.t1.setText(item.getBanhezhanCount());                        //拌合站总数
        cache.t2.setText(item.getBanhejiCount());                          //拌合机总数
        cache.t3.setText(Html.fromHtml(IsNull(item.getZongpanshu())));
        cache.t4.setText(Html.fromHtml(IsNull(item.getZongfangliang())));
        cache.t5.setText(item.getChuji1());
        if (!"".equals(item.getChuji2()) && item.getChuji2() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
            cache.t6.setText(Html.fromHtml(item.getChuji2()+"%"));
        }else{
            cache.t6.setText(Html.fromHtml("0.00%"));
        }

        cache.t7.setText(Html.fromHtml(IsNull(item.getChuji3())));
        if (!"".equals(item.getChuji4()) && item.getChuji4() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
            cache.t8.setText(Html.fromHtml(item.getChuji4()+"%"));
        }else{
            cache.t8.setText(Html.fromHtml("0.00%"));
        }
        cache.t9.setText(Html.fromHtml(IsNull(item.getZhongji1())));
        if (!"".equals(item.getZhongji2()) && item.getZhongji2() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
            cache.t10.setText(Html.fromHtml(item.getZhongji2()+"%"));
        }else{
            cache.t10.setText(Html.fromHtml("0.00%"));
        }
        cache.t11.setText(Html.fromHtml(IsNull(item.getZhongji3())));
        if (!"".equals(item.getZhongji4()) && item.getZhongji4() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
            cache.t12.setText(Html.fromHtml(item.getZhongji4()+"%"));
        }else{
            cache.t12.setText(Html.fromHtml("0.00%"));
        }
        cache.t13.setText(Html.fromHtml(IsNull(item.getGaoji1())));
        if (!"".equals(item.getGaoji2()) && item.getGaoji2() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
            cache.t14.setText(Html.fromHtml(item.getGaoji2()+"%"));
        }else{
            cache.t14.setText(Html.fromHtml("0.00%"));
        }
        cache.t15.setText(Html.fromHtml(IsNull(item.getGaoji3())));
        if (!"".equals(item.getGaoji4()) && item.getGaoji4() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
            cache.t16.setText(Html.fromHtml(item.getGaoji4()+"%"));
        }else{
            cache.t16.setText(Html.fromHtml("0.00%"));
        }
        return convertView;
    }

    private String IsNull(String value){
        if("".equals(value) || null == value)
            return "0";
        else
            return value;
    }

    private class ItemCache {
        TextView name;
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
    }
}
