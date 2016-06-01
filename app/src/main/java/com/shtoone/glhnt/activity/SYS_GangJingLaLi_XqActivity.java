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
import com.shtoone.glhnt.adapter.SYS_GangjinLali_XQAdapter;
import com.shtoone.glhnt.entity.COM_O2O;
import com.shtoone.glhnt.entity.COM_XY;
import com.shtoone.glhnt.entity.SYS_GangJingLaLi_Entity;
import com.shtoone.glhnt.entity.SYS_GangJingLaLi_Xq_Entity;
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

public class SYS_GangJingLaLi_XqActivity extends Activity {

    private TextView tv_title, tv_time, tv_proName, tv_hege, tv_proPart, tv_weituoNum, tv_shijianNum, tv_shijianchicun, tv_zhijin, tv_gongchenzhijin, tv_pingzhong,
            tv_shijianmianji, tv_zhijianriqi, tv_shenzhanglv, qufu_lizhi, qufuli_qiangdu, gjll_xq_title2;
    private ImageView iv_hege;
    private ChartView chartView;
    private ServiceDao service;
    private ProgressDialog mypDialog;
    private Handler handler;
    private SYS_GangJingLaLi_Xq_Entity data;
    private String xqID, titleName;
    private ImageButton btn_back;

    private String sysType = "1";
    private String source = "0";
    private ListView listView;
    private SYS_GangjinLali_XQAdapter adapter;

    private LinearLayout buhegeContent;
    private EditText xq_yuanyin, xq_chuzhijieguo, xq_chulifangshi;
    private Button xq_submit, xq_unDone;
    private String userFullName = "";
    private String xmmc = "";
    private String reason = null, result = null, type = null;
    SYS_GangJingLaLi_Entity.DataEntity model = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_gangjinglali_xq);
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
        sysType = getIntent().getStringExtra("sysType");
        source = getIntent().getStringExtra("source");
        model = getIntent().getParcelableExtra("model");
        if (source.equals("0")) {
            buhegeContent.setVisibility(View.GONE);
        } else {
            buhegeContent.setVisibility(View.VISIBLE);
        }
        String sy_name = "";
        if ("1".equals(sysType)) {
            sy_name = " > " + getResources().getString(R.string.sys) + " > �ֽ�����";
            gjll_xq_title2.setText(getResources().getText(R.string.gjll_title_xq1));
            qufu_lizhi.setVisibility(View.VISIBLE);
            qufuli_qiangdu.setVisibility(View.VISIBLE);
        } else if ("2".equals(sysType)) {
            sy_name = " > " + getResources().getString(R.string.sys) + "  > �ֽ������ͷ";
            gjll_xq_title2.setText(getResources().getText(R.string.gjll_title_xq2));
        } else {
            sy_name = " > " + getResources().getString(R.string.sys) + "  > �ֽ��е���ӽ�ͷ";
            gjll_xq_title2.setText(getResources().getText(R.string.gjll_title_xq3));
        }

        xmmc = getIntent().getStringExtra("xmmc");
        ((TextView) this.findViewById(R.id.project_title)).setText(xmmc + sy_name);
        service = new ServiceDao();
        xqID = getIntent().getStringExtra("xqID");
        titleName = getIntent().getStringExtra("Title_Name");
    }

    @SuppressLint("HandlerLeak")
    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mypDialog.dismiss();
                if (msg.what == 1) {
                    adapter = new SYS_GangjinLali_XQAdapter(SYS_GangJingLaLi_XqActivity.this, SplitUtil.GJ_CurveSplit(data.getData().getLZ(), data.getData().getLZQD(), data.getData().getQFLZ(), data.getData().getQFQD(), data.getData().getF_LZ(), data.getData().getF_SJ()), sysType);
                    showView();
                } else {
                    Toast.makeText(SYS_GangJingLaLi_XqActivity.this, "�����������ݣ�", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void startXqThread() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    if ("1".equals(sysType)) {
                        data = service.getGangjinLaliDetail(xqID);
                    } else if ("2".equals(sysType)) {
                        data = service.getGangJinHanjietouDetail(xqID);
                    } else {
                        data = service.getGangjinJixietouDetail(xqID);
                    }
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                if (data != null) {
                    handler.sendEmptyMessage(1);
                } else {
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    protected void showView() {
        tv_title.setText(titleName);
        tv_time.setText(data.getData().getSYRQ());
        tv_proName.setText(data.getData().getGCMC());
        tv_hege.setText(data.getData().getPDJG());
        if ("�ϸ�".equals(tv_hege.getText())) {
            iv_hege.setImageResource(R.drawable.ok);
        } else {
            iv_hege.setImageResource(R.drawable.ng);
        }
        tv_proPart.setText(data.getData().getSGBW());
        tv_weituoNum.setText(data.getData().getWTBH());
        tv_shijianNum.setText(data.getData().getSJBH());
        tv_shijianchicun.setText(data.getData().getSJCC());
        tv_zhijin.setText("NoAPI");
        tv_gongchenzhijin.setText(data.getData().getGGZL());
        tv_pingzhong.setText(data.getData().getPZBM());
        tv_shijianmianji.setText(data.getData().getPZBM());
        tv_zhijianriqi.setText(data.getData().getZZRQ());
        tv_shenzhanglv.setText("NoAPI");
        xq_yuanyin.setText(data.getData().getChuli());

        listView.setAdapter(adapter);
        //chartView.Update(data.getLists());
        ScrollWithListView.setListViewHeightBasedOnChildren(listView);

        List<COM_O2O> xys = SplitUtil.GJ_CurveSplit(data.getData().getLZ(), data.getData().getLZQD(), data.getData().getQFLZ(), data.getData().getQFQD(), data.getData().getF_LZ(), data.getData().getF_SJ());
        if (xys.size() > 0)
            updateChartView(xys.get(0).getListXYs());
    }

    private void findView() {
        listView = (ListView) this.findViewById(R.id.listView1);
        tv_title = (TextView) this.findViewById(R.id.gjll_xq_title);
        gjll_xq_title2 = (TextView) this.findViewById(R.id.gjll_xq_title2);
        tv_time = (TextView) this.findViewById(R.id.gjll_xq_time);
        tv_proName = (TextView) this.findViewById(R.id.gjll_xq_projectName);
        tv_hege = (TextView) this.findViewById(R.id.gjll_xq_tv_hege);
        iv_hege = (ImageView) this.findViewById(R.id.gjll_xq_iv_hege);
        tv_proPart = (TextView) this.findViewById(R.id.gjll_xq_projectPart);
        tv_weituoNum = (TextView) this.findViewById(R.id.gjll_xq_weituoNum);
        tv_shijianNum = (TextView) this.findViewById(R.id.gjll_xq_shijianNum);
        tv_shijianchicun = (TextView) this.findViewById(R.id.gjll_xq_sjcc);
        tv_zhijin = (TextView) this.findViewById(R.id.gjll_xq_zhijing);
        tv_gongchenzhijin = (TextView) this.findViewById(R.id.gjll_xq_gczj);
        tv_pingzhong = (TextView) this.findViewById(R.id.gjll_xq_pingzong);
        tv_shijianmianji = (TextView) this.findViewById(R.id.gjll_xq_sjmj);
        tv_zhijianriqi = (TextView) this.findViewById(R.id.gjll_xq_zjrq);
        tv_shenzhanglv = (TextView) this.findViewById(R.id.gjll_xq_shenzhanglv);
        chartView = (ChartView) this.findViewById(R.id.gjll_xq_chartView);
        btn_back = (ImageButton) this.findViewById(R.id.back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buhegeContent = (LinearLayout) this.findViewById(R.id.buhegeContent);
        xq_yuanyin = (EditText) this.findViewById(R.id.xq_yuanyin);
        xq_chuzhijieguo = (EditText) this.findViewById(R.id.xq_chuzhijieguo);
        xq_chulifangshi = (EditText) this.findViewById(R.id.xq_chulifangshi);
        xq_submit = (Button) this.findViewById(R.id.xq_submit);
        xq_unDone = (Button) this.findViewById(R.id.xq_unDone);

        qufu_lizhi = (TextView) this.findViewById(R.id.qufu_lizhi);
        qufuli_qiangdu = (TextView) this.findViewById(R.id.qufuli_qiangdu);

        //����ύ
        xq_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SYS_GangJingLaLi_XqActivity.this)
                        .setMessage("�Ƿ��ύ?").setCancelable(true);
                builder.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            reason = xq_yuanyin.getText().toString();
                            result = URLEncoder.encode(xq_chuzhijieguo.getText().toString().trim(), "utf-8");
                            type = URLEncoder.encode(xq_chulifangshi.getText().toString().trim(), "utf-8");
                        } catch (UnsupportedEncodingException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            if ("".equals(reason)) { //����ԭ��û������ ��ʾ������
                                Toast.makeText(SYS_GangJingLaLi_XqActivity.this, "�����벻�ϸ�ԭ��", Toast.LENGTH_LONG).show();
                                return;
                            }
//                            if ("".equals(result)) { //���ý��û������ ��ʾ������
//                                Toast.makeText(SYS_GangJingLaLi_XqActivity.this, "�����봦�ý����", Toast.LENGTH_LONG).show();
//                                return;
//                            }
//                            if ("".equals(type)) { //����ԭ��û������ ��ʾ������
//                                Toast.makeText(SYS_GangJingLaLi_XqActivity.this, "�����봦�÷�ʽ��", Toast.LENGTH_LONG).show();
//                                return;
//                            }
//                            final String url = API.SYS_CHAOBIAO_DO_URL.replace("%1", xqID).replace("%2",data.getData().getSBBH())
//                                    .replace("%3", reason).replace("%4", type).replace("%5", result)
//                                    .replace("%6", userFullName);
                            final String url = API.SYS_CHAOBIAO_DO_URL;
                            Log.d("�ֽ����鲻�ϸ���URL��", url);
                            Toast.makeText(SYS_GangJingLaLi_XqActivity.this, "�ύ�У����Ժ󡣡���", Toast.LENGTH_SHORT).show();
                            final Handler mhandler = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    super.handleMessage(msg);
                                    if (msg.what == 1) {
                                        Toast.makeText(SYS_GangJingLaLi_XqActivity.this, "���óɹ���", Toast.LENGTH_LONG).show();
                                        setResult(1);
                                        finish();
                                    } else {
                                        Toast.makeText(SYS_GangJingLaLi_XqActivity.this, "����ʧ�ܣ�", Toast.LENGTH_LONG).show();
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
                                        Log.d("�ֽ����鲻�ϸ��ý����", request);
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (true) {
                                        mhandler.sendEmptyMessage(1);  // �ϴ��ɹ� ������Ϣ�� handler �ر�����ҳ����ʾ�ϴ��ɹ�
                                    } else {
                                        mhandler.sendEmptyMessage(2);  // �ϴ�ʧ�� ��ʲô������ ͣ���ڴ�ҳ��
                                    }
                                }
                            }).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setPositiveButton("ȡ��", null);
                builder.create().show();
            }
        });

        //�������
        xq_unDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SYS_GangJingLaLi_XqActivity.this)
                        .setMessage("�Ƿ��������������?").setCancelable(true);
                builder.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() { //���ȷ�������������������
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        xq_yuanyin.setText("");
                        xq_chuzhijieguo.setText("");
                        xq_chulifangshi.setText("");
                    }
                });
                builder.setPositiveButton("ȡ��", null); //���ȡ��ʲô������
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
        chartView.setLayoutParams(layoutParams);
    }

    private void setProgressDialog() {
        mypDialog = new ProgressDialog(SYS_GangJingLaLi_XqActivity.this);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mypDialog.setMessage("�����У����Ժ�...");
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(true);
        mypDialog.setCanceledOnTouchOutside(false);
        mypDialog.show();
    }

    public void updateChartView(List<COM_XY> datas) {
        Log.d("ף����", String.valueOf(datas.size()));
        chartView.setStep(10);
        //chartView.setChartTitle("��ֵ���� x:ʱ��(S) y:��ֵ(KN)");
        chartView.Update(datas);
    }
}
