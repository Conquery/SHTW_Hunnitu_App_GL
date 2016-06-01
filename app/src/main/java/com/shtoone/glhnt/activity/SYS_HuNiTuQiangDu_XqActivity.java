package com.shtoone.glhnt.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.adapter.SYS_HunNiTu_XQAdapter;
import com.shtoone.glhnt.entity.COM_O2O;
import com.shtoone.glhnt.entity.COM_XY;
import com.shtoone.glhnt.entity.SYS_HunNiTu_Xq_Entity;
import com.shtoone.glhnt.serviceDao.API;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.ui.ChartView;
import com.shtoone.glhnt.util.HttpUtil;
import com.shtoone.glhnt.util.ScrollWithListView;
import com.shtoone.glhnt.util.SplitUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SYS_HuNiTuQiangDu_XqActivity extends Activity {

    private TextView tv_title, tv_time, tv_proName, tv_proPart, tv_sylx, tv_shijianQd, tv_hege, tv_qiangduDaibiao,
            tv_lingqi, tv_shijianChicun;
    private ImageView iv_hege;
    private ListView listView;
    private ChartView chartView;
    private ServiceDao service;
    private ProgressDialog mypDialog;
    private Handler handler;
    private SYS_HunNiTu_Xq_Entity data;
    private SYS_HunNiTu_XQAdapter adapter;
    private String xqID, titleName;
    private ImageButton btn_back;

    private String source = "0";
    private LinearLayout buhegeContent = null;
    private EditText xq_yuanyin, xq_chuzhijieguo, xq_chulifangshi;
    private Button xq_submit, xq_unDone;
    private String userFullName = "";
    private String xmmc = "";
    private String reason = null, result = null, type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_hunnituqiangdu_xq);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.theme_hntqd);
        setTheme(android.R.style.Theme_DeviceDefault_NoActionBar);

        findView();
        beforeStart();
        setProgressDialog();
        setLayout();
        createHandler();
        startXqThread();
    }

    private void beforeStart() {
        xmmc = getIntent().getStringExtra("xmmc");
        ((TextView) this.findViewById(R.id.project_title)).setText(xmmc+ " > " + getResources().getString(R.string.sys) + " > 压力试验");
        service = new ServiceDao();
        xqID = getIntent().getStringExtra("xqID");
        Log.e("xqID", "xqID:" + xqID);
        titleName = getIntent().getStringExtra("TitleName");
        source = getIntent().getStringExtra("source");
        userFullName = getIntent().getStringExtra("userFullName");
        if (source.equals("0")) {
            buhegeContent.setVisibility(View.GONE);
        } else {
            buhegeContent.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("HandlerLeak")
    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mypDialog.dismiss();
                if (msg.what == 1) {
                    adapter = new SYS_HunNiTu_XQAdapter(SYS_HuNiTuQiangDu_XqActivity.this, SplitUtil.HNTQD_CurveSplit(data.getData().getKYLZ(), data.getData().getKYQD(), data.getData().getF_LZ(), data.getData().getF_SJ()));
                    showView();
                } else {
                    Toast.makeText(SYS_HuNiTuQiangDu_XqActivity.this, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void startXqThread() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    data = service.getHNTQDXqData(xqID);
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

    public void updateChartView(List<COM_XY> datas) {
        //Log.d("祝鹏辉", String.valueOf(datas.size()));
        //chartView.setChartTitle("力值曲线 x:时间(S) y:力值(KN)");
        chartView.setStep(2);
        chartView.Update(datas);
    }

    protected void showView() {
        tv_title.setText(titleName);
        tv_time.setText(data.getData().getSYRQ());
        tv_proName.setText(data.getData().getGCMC());
        tv_proPart.setText(data.getData().getSGBW());
        tv_sylx.setText(data.getData().getTestName());
        tv_shijianQd.setText(data.getData().getSJQD());
        //TODO
//        ***********修改************************

        if ("合格".equals(data.getData().getPDJG()) || "有效".equals(data.getData().getPDJG())) {
            iv_hege.setImageResource(R.drawable.ok);
        } else {
            iv_hege.setImageResource(R.drawable.ng);
        }
        //设置文本
        tv_hege.setText(data.getData().getPDJG());


        //        ***********************************
        tv_qiangduDaibiao.setText(data.getData().getQDDBZ());
        tv_lingqi.setText(data.getData().getLQ());
        tv_shijianChicun.setText(data.getData().getSJCC());
        xq_yuanyin.setText(data.getData().getChuli());
        listView.setAdapter(adapter);
        ScrollWithListView.setListViewHeightBasedOnChildren(listView);
        List<COM_O2O> datas = SplitUtil.HNTQD_CurveSplit(data.getData().getKYLZ(), data.getData().getKYQD(), data.getData().getF_LZ(), data.getData().getF_SJ());
        if (datas.size() > 0)
            updateChartView(datas.get(0).getListXYs());
    }

    private void findView() {
        tv_title = (TextView) this.findViewById(R.id.hntqd_xq_title);
        tv_time = (TextView) this.findViewById(R.id.hntqd_xq_time);
        tv_proName = (TextView) this.findViewById(R.id.hntqd_xq_projectName);
        tv_proPart = (TextView) this.findViewById(R.id.hntqd_xq_projectPart);
        tv_sylx= (TextView) this.findViewById(R.id.hntqd_xq_testType);
        tv_shijianQd = (TextView) this.findViewById(R.id.hntqd_xq_shijianQiangdu);
        tv_hege = (TextView) this.findViewById(R.id.hntqd_xq_tv_hege);
        iv_hege = (ImageView) this.findViewById(R.id.hntqd_xq_iv_hege);
        tv_qiangduDaibiao = (TextView) this.findViewById(R.id.hntqd_xq_qiangduDaibiao);
        tv_lingqi = (TextView) this.findViewById(R.id.hntqd_xq_lingqi);
        tv_shijianChicun = (TextView) this.findViewById(R.id.hntqd_xq_shijianchicun);
        listView = (ListView) this.findViewById(R.id.hntqd_xq_listView);
        chartView = (ChartView) this.findViewById(R.id.hntqd_xq_chartView);
        btn_back = (ImageButton) this.findViewById(R.id.back);
        //以下不合格处置内容private EditText xq_yuanyin,xq_chuzhijieguo,xq_chulifangshi;    Button xq_submit,xq_unDone;
        buhegeContent = (LinearLayout) this.findViewById(R.id.buhegeContent);
        xq_yuanyin = (EditText) this.findViewById(R.id.xq_yuanyin);
        xq_chuzhijieguo = (EditText) this.findViewById(R.id.xq_chuzhijieguo);
        xq_chulifangshi = (EditText) this.findViewById(R.id.xq_chulifangshi);
        xq_submit = (Button) this.findViewById(R.id.xq_submit);
        xq_unDone = (Button) this.findViewById(R.id.xq_unDone);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //点击提交
        xq_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SYS_HuNiTuQiangDu_XqActivity.this)
                        .setMessage("是否提交?").setCancelable(true);
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            reason = xq_yuanyin.getText().toString().trim();
                            result = URLEncoder.encode(xq_chuzhijieguo.getText().toString().trim(), "utf-8");
                            type = URLEncoder.encode(xq_chulifangshi.getText().toString().trim(), "utf-8");
                        } catch (UnsupportedEncodingException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            if ("".equals(reason)) { //超标原因没有内容 提示并返回
                                Toast.makeText(SYS_HuNiTuQiangDu_XqActivity.this, "请输入不合格原因！", Toast.LENGTH_LONG).show();
                                return;
                            }
//                            if ("".equals(result)) { //处置结果没有内容 提示并返回
//                                Toast.makeText(SYS_HuNiTuQiangDu_XqActivity.this, "请输入处置结果！", Toast.LENGTH_LONG).show();
//                                return;
//                            }
//                            if ("".equals(type)) { //处置原因没有内容 提示并返回
//                                Toast.makeText(SYS_HuNiTuQiangDu_XqActivity.this, "请输入处置方式！", Toast.LENGTH_LONG).show();
//                                return;
//                            }
//                            final String url = API.SYS_CHAOBIAO_DO_URL.replace("%1", xqID).replace("%2",data.getData().getSBBH())
//                                    .replace("%3", reason).replace("%4", type).replace("%5", result)
//                                    .replace("%6", userFullName);
                            final String url = API.SYS_CHAOBIAO_DO_URL;
                            Log.d("实验室不合格处置URL：", url);
                            Toast.makeText(SYS_HuNiTuQiangDu_XqActivity.this, "提交中，请稍后。。。", Toast.LENGTH_SHORT).show();
                            final Handler mhandler = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    super.handleMessage(msg);
                                    if (msg.what == 1) {
                                        Toast.makeText(SYS_HuNiTuQiangDu_XqActivity.this, "处置成功！", Toast.LENGTH_LONG).show();
                                        setResult(1);
                                        finish();
                                    } else {
                                        Toast.makeText(SYS_HuNiTuQiangDu_XqActivity.this, "处置失败！", Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            new Thread(new Runnable() {
                                public void run() {
                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("SYJID", xqID);
                                    map.put("chaobiaoyuanyin", reason);
                                    try {
                                        String request = HttpUtil.postRequest(url, map);
                                        Log.d("混凝土抗压不合格处置结果：", request);
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (true) {
                                        mhandler.sendEmptyMessage(1);  // 上传成功 发送消息到 handler 关闭详情页并提示上传成功
                                    } else {
                                        mhandler.sendEmptyMessage(2);  // 上传失败 则什么都不做 停留在此页面
                                    }
                                }
                            }).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setPositiveButton("取消", null);
                builder.create().show();
            }
        });

        //点击重置
        xq_unDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SYS_HuNiTuQiangDu_XqActivity.this)
                        .setMessage("是否重置输入的内容?").setCancelable(true);
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() { //点击确定清空三个输入框的内容
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        xq_yuanyin.setText("");
                        xq_chuzhijieguo.setText("");
                        xq_chulifangshi.setText("");
                    }
                });
                builder.setPositiveButton("取消", null); //点击取消什么都不做
                builder.create().show();
            }
        });
    }

    private void setLayout() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int scrWidth = (int) (dm.widthPixels * 0.9);
        int scrHeight = (int) (dm.heightPixels * 0.3);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(scrWidth, scrHeight);
        layoutParams.gravity = Gravity.CENTER;
        chartView.setStep(5);
        chartView.setLayoutParams(layoutParams);
    }

    private void setProgressDialog() {
        mypDialog = new ProgressDialog(SYS_HuNiTuQiangDu_XqActivity.this);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mypDialog.setMessage("加载中，请稍后...");
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(false);
        mypDialog.setCanceledOnTouchOutside(false);
        mypDialog.show();
    }
}
