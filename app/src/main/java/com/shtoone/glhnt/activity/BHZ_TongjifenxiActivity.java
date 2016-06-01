package com.shtoone.glhnt.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.adapter.BHZ_zonghetongjiCLAdapter;
import com.shtoone.glhnt.adapter.BHZ_zonghetongjiWCAdapter;
import com.shtoone.glhnt.entity.BHZ_ZongheTongji_Entity;
import com.shtoone.glhnt.entity.COMM_Shebei;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.ui.BHZ_zongchanliang_cl_chart;
import com.shtoone.glhnt.ui.BHZ_zongchanliang_wc_chart;
import com.shtoone.glhnt.util.DateTimePicker;
import com.shtoone.glhnt.util.ScrollWithListView;

import org.json.JSONArray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BHZ_TongjifenxiActivity extends Activity {

    private ListView listView1,listView2;
    private LinearLayout sc_fl_content,sc_wc_content;
    private ProgressDialog mypDialog;
    private Handler handler;
    private List<BHZ_ZongheTongji_Entity> data;
    private ServiceDao service;
    private String startTime = "2015-09-01 12:12:12",endTime = "2015-09-30 12:12:12";
    private EditText et1,et2;
    private TextView tjfx_btn_ji,tjfx_btn_yue,tjfx_btn_zhou,selectDevice,btn_search;
    private int returnType = -1;
    private String cycle = "1";
    private String device = "";
    private COMM_Shebei dev_list = null;
    private int which_dev = 0;

    private ImageButton btn_back;

    private BHZ_zongchanliang_cl_chart chart_cl;
    private BHZ_zongchanliang_wc_chart chart_wc;

    private String xmmc;
    private String userGroupID;
    //private DataPropertiy dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_bhz_tongjifenxi);
        this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.theme_hntqd);
        try {
            findView();
            beforeStart();
            setProgressDialog();
            setListener();
            createHandler();
            setLayoutAndColor();
            startGetDataThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取UI上面控件
     */
    private void findView() {
        btn_back = (ImageButton) this.findViewById(R.id.back);
        sc_fl_content =  (LinearLayout) this.findViewById(R.id.sc_fl_content);
        sc_wc_content =  (LinearLayout) this.findViewById(R.id.sc_wc_content);
        listView1 = (ListView) this.findViewById(R.id.listView_fl);
        listView2 = (ListView) this.findViewById(R.id.listView_wc);
        et1 = (EditText) this.findViewById(R.id.bhztjfx_first_choice);
        et2 = (EditText) this.findViewById(R.id.bhztjfx_second_choice);
        btn_search = (TextView) this.findViewById(R.id.bhztjfx_chaxun_btn);
        tjfx_btn_ji = (TextView) this.findViewById(R.id.tjfx_btn_ji);
        tjfx_btn_yue = (TextView) this.findViewById(R.id.tjfx_btn_yue);
        tjfx_btn_zhou = (TextView) this.findViewById(R.id.tjfx_btn_zhou);
        selectDevice = (TextView) this.findViewById(R.id.selectDevice);
    }

    /**
     * 预处理
     */
    private void beforeStart() throws IOException {
        xmmc = getIntent().getStringExtra("xmmc");
        //dp = new DataPropertiy(BHZ_TongjifenxiActivity.this);
        ((TextView) this.findViewById(R.id.project_title)).setText(xmmc + " > 拌和站管理 > 综合统计分析");
        service = new ServiceDao();
        xmmc = getIntent().getStringExtra("xmmc");
        userGroupID = getIntent().getStringExtra("userGroupID");
        //***** 日期初期化 BEGIN *****
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Calendar rld = Calendar.getInstance();
        endTime = sdf.format(rld.getTime());
        rld.add(Calendar.MONTH, -3);
        startTime = sdf.format(rld.getTime());
        //***** 日期初期化 END *****
    }

    /**
     * 控件事件监听
     */
    private void setListener() {
        et1.setText(startTime);
        et2.setText(endTime);
        et1.setFocusableInTouchMode(false);
        et2.setFocusableInTouchMode(false);
        et1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker dateTimePicKDialog = new DateTimePicker(
                        BHZ_TongjifenxiActivity.this, startTime);
                dateTimePicKDialog.dateTimePicKDialog(et1);
            }
        });
        et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker dateTimePicKDialog = new DateTimePicker(
                        BHZ_TongjifenxiActivity.this, endTime);
                dateTimePicKDialog.dateTimePicKDialog(et2);
            }
        });

        //按季度
        tjfx_btn_ji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = et1.getText().toString();
                endTime = et2.getText().toString();
                tjfx_btn_ji.setTextColor(Color.rgb(0, 0, 0));
                tjfx_btn_yue.setTextColor(Color.rgb(190, 190, 190));
                tjfx_btn_zhou.setTextColor(Color.rgb(190, 190, 190));
                cycle = "1";
                mypDialog.show();
                startGetDataThread();
            }
        });

        //按月
        tjfx_btn_yue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = et1.getText().toString();
                endTime = et2.getText().toString();
                tjfx_btn_ji.setTextColor(Color.rgb(190, 190, 190));
                tjfx_btn_yue.setTextColor(Color.rgb(0, 0, 0));
                tjfx_btn_zhou.setTextColor(Color.rgb(190, 190, 190));
                cycle = "2";
                mypDialog.show();
                startGetDataThread();
            }
        });

        //按周
        tjfx_btn_zhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = et1.getText().toString();
                endTime = et2.getText().toString();
                tjfx_btn_ji.setTextColor(Color.rgb(190, 190, 190));
                tjfx_btn_yue.setTextColor(Color.rgb(190, 190, 190));
                tjfx_btn_zhou.setTextColor(Color.rgb(0, 0, 0));
                cycle = "3";
                mypDialog.show();
                startGetDataThread();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = et1.getText().toString();
                endTime = et2.getText().toString();

                mypDialog.show();
                startGetDataThread();
            }
        });

        //选择设备
        selectDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BHZ_TongjifenxiActivity.this);
                builder.setIcon(R.drawable.right);
                builder.setTitle("选择一个设备");

                JSONArray array = new JSONArray();
                //指定下拉列表的显示数据
                final List<String> devNames = new ArrayList<String>();
                final List<String> devIDs = new ArrayList<String>();

                for (int i = 0; i < dev_list.getData().size(); i++) {
                    COMM_Shebei.DataEntity tmp = dev_list.getData().get(i);
                    devNames.add(tmp.getBanhezhanminchen());
                    devIDs.add(tmp.getGprsbianhao());
                }
                //用数组存放标段
                final String[] bids = new String[devNames.size()];
                for (int i = 0; i < devNames.size(); i++) {
                    bids[i]= devNames.get(i);
                }
                //    设置一个下拉的列表选择项
                builder.setItems(bids, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //mypDialog.show();
                        device = devIDs.get(which);
                        selectDevice.setText(devNames.get(which));
                        which_dev = which;
                    }
                });
                builder.show();
            }
        });

        //后退
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnType = 2;
                finish();
            }
        });
    }

    /**
     * 开启线程，请求数据
     */
    private void startGetDataThread() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    //只获取一次
                    if(dev_list == null){
                        //TODO：祝鹏辉
                        dev_list = service.getCOMM_Shebei(userGroupID, "1","BHZ");
                        Thread.sleep(500);
                    }
                    if(dev_list != null)
                    {
                        if(dev_list.getData().size() > 0) {
                            device = dev_list.getData().get(which_dev).getGprsbianhao();
                            data = service.getBhzZongheTjjiClientData(userGroupID,startTime,endTime,device,cycle);
                        }
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

    /**
     * UI更新句柄
     */
    @SuppressLint("HandlerLeak")
    private void createHandler() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mypDialog.dismiss();
                if (msg.what == 0) {
                    Toast.makeText(BHZ_TongjifenxiActivity.this, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
                if (msg.what == 1) {
                    showView();
                }
            }
        };
    }

    /**
     * 根据数据刷新UI
     */
    protected void showView() {
        if(dev_list.getData().size() > 0) {
            selectDevice.setText(dev_list.getData().get(which_dev).getBanhezhanminchen());
        }
        listView1.setAdapter(new BHZ_zonghetongjiCLAdapter(BHZ_TongjifenxiActivity.this, data, R.layout.bhz_tjfx_item_fl));
        ScrollWithListView.setListViewHeightBasedOnChildren(listView1);
        listView2.setAdapter(new BHZ_zonghetongjiWCAdapter(BHZ_TongjifenxiActivity.this, data, R.layout.bhz_tjfx_item_wc));
        ScrollWithListView.setListViewHeightBasedOnChildren(listView2);
        //更新chart与数据
        chart_cl.Update(data);
        chart_wc.Update(data);
    }

    /**
     * 数据等待过程
     */
    private void setProgressDialog() {
        mypDialog = new ProgressDialog(BHZ_TongjifenxiActivity.this);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mypDialog.setMessage("加载中,请稍后...");
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(false);
        mypDialog.setCanceledOnTouchOutside(false);
        mypDialog.show();
    }

    /**
     * 存放chart的容器大小
     */
    private void setLayoutAndColor() {
        // 图表显示范围在占屏幕大小的90%的区域内
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int scrWidth = (int) (dm.widthPixels * 1);
        int scrHeight = (int) (dm.heightPixels * 0.45);

        chart_cl = new BHZ_zongchanliang_cl_chart(BHZ_TongjifenxiActivity.this);
        chart_wc = new BHZ_zongchanliang_wc_chart(BHZ_TongjifenxiActivity.this);

        // 容器属性
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(scrWidth, scrHeight);
        sc_fl_content.removeAllViews();
        sc_fl_content.addView(chart_cl, layoutParams);
        sc_wc_content.removeAllViews();
        sc_wc_content.addView(chart_wc, layoutParams);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("userGroupID", userGroupID);
        setResult(returnType, intent);
        super.finish();
    }


    /******************************************************
     * 以下自动生成的继承方法
     * @param menu
     * @return
     *****************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bhz_tongjifenxi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
