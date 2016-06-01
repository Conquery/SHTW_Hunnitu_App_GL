package com.shtoone.glhnt.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.adapter.BHZ_CailiaoyongliangAdapter;
import com.shtoone.glhnt.entity.BHZ_CaiLiaoYongLiang_Entity;
import com.shtoone.glhnt.entity.COMM_Shebei;
import com.shtoone.glhnt.entity.COM_XY;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.ui.HistogramView;
import com.shtoone.glhnt.util.CommFunctions;
import com.shtoone.glhnt.util.DataPropertiy;
import com.shtoone.glhnt.util.DatePicker;

import org.json.JSONArray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BHZ_CailiaoyongliangActivity extends Activity {

    private HistogramView histogramView1,histogramView2;
    private ListView listView;
    private ProgressDialog mypDialog;
    private Handler handler;
    private BHZ_CaiLiaoYongLiang_Entity data;
    private ServiceDao service;
    private String startTime = "2015-09-01 12:12:12",endTime = "2015-09-30 12:12:12";
    private EditText et1,et2;
    private TextView selectDevice,btn_search;
    private int returnType = -1;

    private String device = "";
    private COMM_Shebei dev_list = null;
    private int which_dev = 0;

    private ImageButton btn_back;

    private String xmmc;
    private String userGroupID;
    private ImageView weather_img;
    private String dengji = "SG",userRole="";
    private DataPropertiy dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_bhz_cailiaoyongliang);
        this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.theme_hntqd);
        setTheme(android.R.style.Theme_DeviceDefault_NoActionBar);
        //biaoduanID = getIntent().getIntExtra("biaoduanID", 0);
        //setTitle((biaoduanID+1)+"标      拌合站材料用量");
        try {
            beforeStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setProgressDialog();
        findView();
        setListener();
        createHandler();
        setLayoutAndColor();
        beforeStartGetDataThread();
        startGetDataThread();
    }

    /**
     * 预处理
     */
    private void beforeStart() throws IOException {
        xmmc = getIntent().getStringExtra("xmmc");
        dengji = getIntent().getStringExtra("dengji");
        userRole = getIntent().getStringExtra("userRole");
        ((TextView) this.findViewById(R.id.project_title)).setText(xmmc + " > " + getResources().getString(R.string.bhz) + " > 材料用量核算");
        service = new ServiceDao();

        userGroupID = getIntent().getStringExtra("userGroupID");
        //***** 日期初期化 BEGIN *****
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar rld = Calendar.getInstance();
        endTime = sdf.format(rld.getTime());
        rld.add(Calendar.MONTH, -3);
        startTime = sdf.format(rld.getTime());
        //***** 日期初期化 END *****
        dp = new DataPropertiy(BHZ_CailiaoyongliangActivity.this);
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
                DatePicker dateTimePicKDialog = new DatePicker(
                        BHZ_CailiaoyongliangActivity.this, startTime);
                dateTimePicKDialog.dateTimePicKDialog(et1);
            }
        });
        et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker dateTimePicKDialog = new DatePicker(
                        BHZ_CailiaoyongliangActivity.this, endTime);
                dateTimePicKDialog.dateTimePicKDialog(et2);
            }
        });
        btn_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeStartGetDataThread();
                if(Integer.valueOf(CommFunctions.ChangeTimeToLong(startTime)) > Integer.valueOf(CommFunctions.ChangeTimeToLong(endTime)) ){
                    Toast.makeText(BHZ_CailiaoyongliangActivity.this, "开始日期不能大于结束日期！", Toast.LENGTH_SHORT).show();
                    mypDialog.dismiss();
                    return;
                }
                startGetDataThread();
            }
        });

        //选择设备
        selectDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BHZ_CailiaoyongliangActivity.this);
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
                    bids[i] = devNames.get(i);
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

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnType = 2;
                finish();
            }
        });

        //组织结构面板weather_img  tv_zuzhijiegou
        weather_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("GL".equals(dengji)) {
                    Intent intent = new Intent(BHZ_CailiaoyongliangActivity.this, CommZuhijiegou.class);
                    intent.putExtra("userGroupID", dp.get("DEPART_ID"));
                    intent.putExtra("xmmc", xmmc);
                    intent.putExtra("type", "1");
                    intent.putExtra("userRole", userRole);
                    startActivityForResult(intent, 1);
                }

            }
        });
    }

    /**
     * 开启线程获取数据前
     */
    private void  beforeStartGetDataThread(){
        startTime = et1.getText().toString();
        endTime = et2.getText().toString();
        listView.setAdapter(null);
        histogramView1.Update(new ArrayList<COM_XY>());
        histogramView2.Update(new ArrayList<COM_XY>());
        mypDialog.show();
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
                    }
                    Thread.sleep(500);
                    if(dev_list != null)
                    {
                        if(dev_list.getData().size() > 0) {
                            device = dev_list.getData().get(which_dev).getGprsbianhao();
                            data = service.getBhzCailiaoyongliang(userGroupID, startTime, endTime, device);
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

    @SuppressLint("HandlerLeak")
    private void createHandler() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mypDialog.dismiss();
                if (msg.what == 0) {
                    Toast.makeText(BHZ_CailiaoyongliangActivity.this, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
                if (msg.what == 1) {
                    showView();
                }
            }
        };
    }

    protected void showView() {
        if(dev_list.getData().size() > 0) {
            selectDevice.setText(dev_list.getData().get(which_dev).getBanhezhanminchen());
        }
        BHZ_CailiaoyongliangAdapter listAdapter = new BHZ_CailiaoyongliangAdapter(BHZ_CailiaoyongliangActivity.this, R.layout.bhz_cailiaoyongliang_item, data.getItemsData());
        listView.setAdapter(listAdapter);
        //ScrollWithListView.setListViewHeightBasedOnChildren(listView);

        //以下设置listview的高度
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);

//        histogramView1.setTitle("材料用量");
//        histogramView1.setTitle("误差量");
        histogramView1.Update(data.getItemsChengben());
        histogramView2.Update(data.getItemsBaifenbi());
    }

    /**
     * 获取UI上面控件
     */
    private void findView() {
        weather_img = (ImageView) this.findViewById(R.id.weather_img);
        if("GL".equals(dengji)) {
            weather_img.setBackgroundResource(0);
            weather_img.setBackgroundResource(R.drawable.wrap);
        }
        btn_back = (ImageButton) this.findViewById(R.id.back);
        histogramView1 = (HistogramView) this.findViewById(R.id.cailiao_HistogramView1);
        histogramView2 = (HistogramView) this.findViewById(R.id.cailiao_HistogramView2);
        listView = (ListView) this.findViewById(R.id.cailiao_listView);
        et1 = (EditText) this.findViewById(R.id.cailiao_first_choice);
        et2 = (EditText) this.findViewById(R.id.cailiao_second_choice);
        btn_search = (TextView) this.findViewById(R.id.cailiao_chaxun_btn);
        selectDevice = (TextView) this.findViewById(R.id.selectDevice);
    }

    private void setLayoutAndColor() {
        // 图表显示范围在占屏幕大小的90%的区域内
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int scrWidth = (int) (dm.widthPixels * 1);
        int scrHeight = (int) (dm.heightPixels * 0.3);

        // 容器属性
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(scrWidth, scrHeight);

        histogramView1.setLayoutParams(layoutParams);
        histogramView1.setTitle("材料核算成本(单位：kg)");
        histogramView1.setMaxY(999999);
        histogramView1.setMinY(0);
        histogramView1.setStepY(100000);
        histogramView1.setColorPlotArea(getResources().getColor(R.color.deepSkyBlue));
        histogramView2.setLayoutParams(layoutParams);
        histogramView2.setTitle("材料误差(单位：kg)");
        histogramView2.setColorPlotArea(getResources().getColor(R.color.gray));
    }

    /**
     * 数据等待过程
     */
    private void setProgressDialog() {
        mypDialog = new ProgressDialog(BHZ_CailiaoyongliangActivity.this);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mypDialog.setMessage("加载中,请稍后...");
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(false);
        mypDialog.setCanceledOnTouchOutside(false);
        mypDialog.show();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("userGroupID", userGroupID);
        setResult(returnType, intent);
        super.finish();
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        try {
            super.onActivityResult(arg0, arg1, arg2);
            userGroupID = arg2.getStringExtra("selectedGroupId");
            if(!"".equals(arg2.getStringExtra("selectedGroupName")) && arg2.getStringExtra("selectedGroupName") != null) {
                ((TextView) this.findViewById(R.id.project_title)).setText(arg2.getStringExtra("selectedGroupName") + " > " + getResources().getString(R.string.bhz) + " > 材料用量核算");
                //showToast(selectedUserGroupId);
                //setProgressDialog();
                beforeStartGetDataThread();
                dev_list = null;
                startGetDataThread();
            }
        }catch(Exception ex) {
            //Toast.makeText(SYS_HuNiTuQiangDu_Activity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bhz_cailiaoyongliang, menu);
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
