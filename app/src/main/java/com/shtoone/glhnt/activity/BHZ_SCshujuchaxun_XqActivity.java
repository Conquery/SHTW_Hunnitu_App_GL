package com.shtoone.glhnt.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.adapter.SC_shengchanchaxunXqAdapter;
import com.shtoone.glhnt.entity.SC_Detail;
import com.shtoone.glhnt.entity.SC_chaxunItem_xq_data;
import com.shtoone.glhnt.serviceDao.ServiceDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BHZ_SCshujuchaxun_XqActivity extends Activity {

    private TextView tv_time, tv_proName, tv_jbsj, tv_shuliang, tv_gdNum, tv_caozuozhe, tv_didian, tv_jiaozhubuwei, tv_wjjpinzhong,
            tv_shuinipinzhong, tv_shigongpeibibianhao, tv_qiangdudengji, tv_title;
    private ListView listView;
    private ServiceDao service;
    private SC_Detail data;
    private ImageButton btn_back;
    private Handler handler;
    private int id = 0;
    //private DataPropertiy dp;
    private String xmmc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_scshujuxq);
        this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.theme_hntqd);
        try {
            beforeStart();
            findView();
            createHandler();
            startLoadListThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void beforeStart() throws IOException {
        xmmc = getIntent().getStringExtra("xmmc");
        //dp = new DataPropertiy(BHZ_SCshujuchaxun_XqActivity.this);
        ((TextView) this.findViewById(R.id.project_title)).setText(xmmc + " > " + getResources().getString(R.string.bhz) + " > 生产数据详情");
        id = getIntent().getIntExtra("xqID",id);
        service = new ServiceDao();
    }

    private void startLoadListThread() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    data = service.getBHZDetailItem(id);
                    Thread.sleep(500);
                } catch (Exception ignored) {
                }
                if (data != null) {
                    handler.sendEmptyMessage(1);
                } else {
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //mypDialog.dismiss();
                if (msg.what == 0) {
                    Toast.makeText(BHZ_SCshujuchaxun_XqActivity.this, "没有查询到记录！", Toast.LENGTH_SHORT).show();
                }
                if (msg.what == 1) {
                    showView();
                }
            }
        };
    }

    /**
     * 展示数据
     */
    private void showView() {
        tv_title.setText(getIntent().getStringExtra("Title_Name"));
        tv_time.setText(data.getData().getChuliaoshijian());            //出料时间
        tv_proName.setText(data.getData().getGongchengmingcheng());    //工程名称
        tv_jbsj.setText(data.getData().getJiaobanshijian());                      //搅拌时间
        tv_shuliang.setText(data.getData().getShuliang());             //数量
        tv_gdNum.setText(data.getData().getPeifanghao());                      //配方号
        tv_caozuozhe.setText(data.getData().getChaozuozhe());           //操作者
        tv_didian.setText(data.getData().getSigongdidian());                  //施工地点
        tv_jiaozhubuwei.setText(data.getData().getJiaozuobuwei());             //浇筑部位
        tv_wjjpinzhong.setText(data.getData().getWaijiajipingzhong());             //外加剂品种
        tv_shuinipinzhong.setText(data.getData().getShuinipingzhong());           //水泥品种
        tv_shigongpeibibianhao.setText(data.getData().getPeifanghao());   //施工配比编号
        tv_qiangdudengji.setText(data.getData().getQiangdudengji());            //强度等级

        /*****************************************************
         * 通过数据构筑材料用量对比列表
         ****************************************************/
        List<SC_chaxunItem_xq_data> items = new ArrayList<SC_chaxunItem_xq_data>();
        SC_chaxunItem_xq_data x = new SC_chaxunItem_xq_data();
        //shuini1
        x.setName(data.getHbfield().getShuini1_shijizhi());
        x.setShiji(data.getData().getShuini1_shijizhi());
        x.setPeibi(data.getData().getShuini1_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getFlw1()));
        x.setCb(String.valueOf(data.getData().getFlper1()));
        items.add(x);

        //shuini2
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getShuini2_shijizhi());
        x.setShiji(data.getData().getShuini2_shijizhi());
        x.setPeibi(data.getData().getShuini2_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getFlw2()));
        x.setCb(String.valueOf(data.getData().getFlper2()));
        items.add(x);

        //sha1
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getSha1_shijizhi());
        x.setShiji(data.getData().getSha1_shijizhi());
        x.setPeibi(data.getData().getSha1_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getGlw1()));
        x.setCb(String.valueOf(data.getData().getGlper1()));
        items.add(x);

        //sha2
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getSha2_shijizhi());
        x.setShiji(data.getData().getSha2_shijizhi());
        x.setPeibi(data.getData().getSha2_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getGlw3()));
        x.setCb(String.valueOf(data.getData().getGlper3()));
        items.add(x);

        //shi1
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getShi1_shijizhi());
        x.setShiji(data.getData().getShi1_shijizhi());
        x.setPeibi(data.getData().getShi1_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getGlw2()));
        x.setCb(String.valueOf(data.getData().getGlper2()));

        items.add(x);

        //shi2
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getShi2_shijizhi());
        x.setShiji(data.getData().getShi2_shijizhi());
        x.setPeibi(data.getData().getShi2_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getGlw4()));
        x.setCb(String.valueOf(data.getData().getGlper4()));
        items.add(x);

        //guliao5
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getGuliao5_shijizhi());
        x.setShiji(data.getData().getGuliao5_shijizhi());
        x.setPeibi(data.getData().getGuliao5_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getGlw5()));
        x.setCb(String.valueOf(data.getData().getGlper5()));
        items.add(x);

        //kuangfen3
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getKuangfen3_shijizhi());
        x.setShiji(data.getData().getKuangfen3_shijizhi());
        x.setPeibi(data.getData().getKuangfen3_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getFlw3()));
        x.setCb(String.valueOf(data.getData().getFlper3()));
        items.add(x);

        //Feimeihui4
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getFeimeihui4_shijizhi());
        x.setShiji(data.getData().getFeimeihui4_shijizhi());
        x.setPeibi(data.getData().getFeimeihui4_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getFlw4()));
        x.setCb(String.valueOf(data.getData().getFlper4()));
        items.add(x);

        //Fenliao5
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getFenliao5_shijizhi());
        x.setShiji(data.getData().getFenliao5_shijizhi());
        x.setPeibi(data.getData().getFenliao5_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getFlw5()));
        x.setCb(String.valueOf(data.getData().getFlper5()));
        items.add(x);

        //Fenliao6
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getFenliao6_shijizhi());
        x.setShiji(data.getData().getFenliao6_shijizhi());
        x.setPeibi(data.getData().getFenliao6_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getFlw6()));
        x.setCb(String.valueOf(data.getData().getFlper6()));
        items.add(x);

        //Waijiaji1
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getWaijiaji1_shijizhi());
        x.setShiji(data.getData().getWaijiaji1_shijizhi());
        x.setPeibi(data.getData().getWaijiaji1_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getWjw1()));
        x.setCb(String.valueOf(data.getData().getWjper1()));
        items.add(x);

        //Waijiaji2
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getWaijiaji2_shijizhi());
        x.setShiji(data.getData().getWaijiaji2_shijizhi());
        x.setPeibi(data.getData().getWaijiaji2_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getWjw2()));
        x.setCb(String.valueOf(data.getData().getWjper2()));
        items.add(x);

        //waijiaji3
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getWaijiaji3_shijizhi());
        x.setShiji(data.getData().getWaijiaji3_shijizhi());
        x.setPeibi(data.getData().getWaijiaji3_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getWjw3()));
        x.setCb(String.valueOf(data.getData().getWjper3()));
        items.add(x);

        //waijiaji4
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getWaijiaji4_shijizhi());
        x.setShiji(data.getData().getWaijiaji4_shijizhi());
        x.setPeibi(data.getData().getWaijiaji4_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getWjw4()));
        x.setCb(String.valueOf(data.getData().getWjper4()));
        items.add(x);

        //Shui1
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getShui1_shijizhi());
        x.setShiji(data.getData().getShui1_shijizhi());
        x.setPeibi(data.getData().getShui1_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getShw1()));
        x.setCb(String.valueOf(data.getData().getShper1()));
        items.add(x);

        //shui2
        x = new SC_chaxunItem_xq_data();
        x.setName(data.getHbfield().getShui2_shijizhi());
        x.setShiji(data.getData().getShui2_shijizhi());
        x.setPeibi(data.getData().getShui2_lilunzhi());
        x.setWucha(String.valueOf(data.getData().getShw2()));
        x.setCb(String.valueOf(data.getData().getShper2()));
        items.add(x);

        Log.d("材料种类长度：", String.valueOf(items.size()));
        SC_shengchanchaxunXqAdapter listAdapter = new SC_shengchanchaxunXqAdapter(BHZ_SCshujuchaxun_XqActivity.this, items);
        listView.setAdapter(listAdapter);
        //ScrollWithListView.setListViewHeightBasedOnChildren(listView);

//        // 图表显示范围在占屏幕大小的90%的区域内
//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        int scrHeight = (int) (dm.heightPixels * 0.45);
//        // 设置高度
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = scrHeight;
//        listView.setLayoutParams(params);

        int totalHeight = 0;
        int itemHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            itemHeight = listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + itemHeight;
        listView.setLayoutParams(params);
    }

    private void findView() {
        tv_title = (TextView) this.findViewById(R.id.scshuju_xq_title);
        tv_time = (TextView) this.findViewById(R.id.scshuju_xq_clsj);
        tv_proName = (TextView) this.findViewById(R.id.scshuju_xq_projectName);
        tv_jbsj = (TextView) this.findViewById(R.id.scshuju_xq_jbsj);
        tv_shuliang = (TextView) this.findViewById(R.id.scshuju_xq_shuliang);
        tv_gdNum = (TextView) this.findViewById(R.id.scshuju_xq_gdh);
        tv_caozuozhe = (TextView) this.findViewById(R.id.scshuju_xq_czz);
        tv_didian = (TextView) this.findViewById(R.id.scshuju_xq_ddlc);
        tv_jiaozhubuwei = (TextView) this.findViewById(R.id.scshuju_xq_jzbw);
        tv_wjjpinzhong = (TextView) this.findViewById(R.id.scshuju_xq_wjjpz);
        tv_shuinipinzhong = (TextView) this.findViewById(R.id.scshuju_xq_snpz);
        tv_shigongpeibibianhao = (TextView) this.findViewById(R.id.scshuju_xq_sgpbbh);
        tv_qiangdudengji = (TextView) this.findViewById(R.id.scshuju_xq_qddj);
        listView = (ListView) this.findViewById(R.id.scshuju_xq_listView);
        btn_back = (ImageButton) this.findViewById(R.id.back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
