package com.shtoone.glhnt.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.adapter.BHZ_LingdaoAdapter;
import com.shtoone.glhnt.entity.BHZ_Lingdao;
import com.shtoone.glhnt.entity.MainInfo;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.util.CommFunctions;
import com.shtoone.glhnt.util.DataPropertiy;
import com.shtoone.glhnt.util.DatePicker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BHZ_Tongjifenxi2Activity extends Activity {

    private String startTime = "2015-09-01 12:12:12",endTime = "2015-09-30 12:12:12";
    private EditText et1,et2;
    private TextView btn_search,tv_zuzhijiegou;
    private ListView listView;
    private ServiceDao service;
    private Handler handler;
    private List<BHZ_Lingdao> items;
    private ProgressDialog mypDialog;

    public String userGroupID;
    //public UserInfo userInfo;
    public MainInfo mainInfo;

    private String selectedGroupId = "";
    private String selectedGroupName="";

    private ImageView weather_img;
    private ImageButton btn_back;
    private int returnType = -1;
    private String dengji = "SG",userRole = "";

    private DataPropertiy dp;
    private String xmmc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_bhz_tongjifenxi2);
        this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.theme_hntqd);
        setTheme(android.R.style.Theme_DeviceDefault_NoActionBar);

        userGroupID = getIntent().getStringExtra("userGroupID");
        selectedGroupId = userGroupID;
        xmmc = getIntent().getStringExtra("xmmc");
        selectedGroupName = xmmc;
        dengji = getIntent().getStringExtra("dengji");
        userRole = getIntent().getStringExtra("userRole");
        ((TextView) this.findViewById(R.id.project_title)).setText(selectedGroupName + getResources().getString(R.string.bhz) + " > 综合统分");
     //***** 日期初期化 BEGIN *****
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar rld = Calendar.getInstance();
        endTime = sdf.format(rld.getTime());
        rld.add(Calendar.MONTH, -3);
        startTime = sdf.format(rld.getTime());
        //***** 日期初期化 END *****
        service = new ServiceDao();
        try {
            dp = new DataPropertiy(BHZ_Tongjifenxi2Activity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        createHandler();
        setProgressDialog(); //创建加载特效
        findView();
        setListener();
        startGetDataThread();
    }

    @SuppressLint("HandlerLeak")
    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mypDialog.dismiss();
                if (msg.what == 0) {
                    Toast.makeText(BHZ_Tongjifenxi2Activity.this, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
                if (msg.what == 1) {
                    showView();
                }
            }
        };
    }

    private void startGetDataThread() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Log.d("祝鹏辉：","开启线程，采集数据");
                    items = service.getBhzLingdaoData(selectedGroupId,startTime,endTime);
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                if (items != null) {
                    handler.sendEmptyMessage(1);
                } else {
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    private void findView() {
        weather_img = (ImageView) this.findViewById(R.id.weather_img);
        if("GL".equals(dengji)) {
            weather_img.setBackgroundResource(0);
            weather_img.setBackgroundResource(R.drawable.wrap);
        }
        btn_back = (ImageButton) this.findViewById(R.id.back);
        weather_img = (ImageView) this.findViewById(R.id.weather_img);
        listView = (ListView) this.findViewById(R.id.bhzdh_listview);
        btn_search = (TextView) this.findViewById(R.id.bhz_ld_chaxun_btn);
        et1 = (EditText) this.findViewById(R.id.bhz_ld_first_choice);
        et2 = (EditText) this.findViewById(R.id.bhz_ld_second_choice);
        tv_zuzhijiegou = (TextView) this.findViewById(R.id.zuzhijiegou);                     //组织结构面板
    }

    protected void showView() {
        listView.setAdapter(new BHZ_LingdaoAdapter(BHZ_Tongjifenxi2Activity.this, R.layout.bhz_daohang_item, items, selectedGroupId, selectedGroupName));
    }

    private void setListener() {
        et1.setText(startTime);
        et2.setText(endTime);
        et1.setFocusableInTouchMode(false);
        et2.setFocusableInTouchMode(false);
        et1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker dateTimePicKDialog = new DatePicker(
                        BHZ_Tongjifenxi2Activity.this, startTime);
                dateTimePicKDialog.dateTimePicKDialog(et1);
            }
        });
        et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker dateTimePicKDialog = new DatePicker(
                        BHZ_Tongjifenxi2Activity.this, endTime);
                dateTimePicKDialog.dateTimePicKDialog(et2);
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(null);
                startTime = et1.getText().toString();
                endTime = et2.getText().toString();
                if(Integer.valueOf(CommFunctions.ChangeTimeToLong(startTime)) > Integer.valueOf(CommFunctions.ChangeTimeToLong(endTime)) ){
                    Toast.makeText(BHZ_Tongjifenxi2Activity.this, "开始日期不能大于结束日期！", Toast.LENGTH_SHORT).show();
                    mypDialog.dismiss();
                    return;
                }
                mypDialog.show();
                startGetDataThread();
            }
        });

        //组织结构面板weather_img  tv_zuzhijiegou
        weather_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("GL".equals(dengji)) {
                    Intent intent = new Intent(BHZ_Tongjifenxi2Activity.this, CommZuhijiegou.class);
                    intent.putExtra("userGroupID", dp.get("DEPART_ID"));
                    intent.putExtra("xmmc", xmmc);
                    intent.putExtra("type", "1");
                    intent.putExtra("userRole", userRole);
                    startActivityForResult(intent, 1);
                }
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

    private void setProgressDialog() {
        mypDialog = new ProgressDialog(BHZ_Tongjifenxi2Activity.this);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mypDialog.setMessage("加载中,请稍后...");
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(false);
        mypDialog.setCanceledOnTouchOutside(false);
        mypDialog.show();
    }

    @Override
    public void onActivityResult(int arg0, int arg1, Intent arg2) {
        try {
            super.onActivityResult(arg0, arg1, arg2);
            selectedGroupId = arg2.getStringExtra("selectedGroupId");
            selectedGroupName = arg2.getStringExtra("selectedGroupName");
            if(!"".equals(selectedGroupName) && selectedGroupName != null) {
                ((TextView) this.findViewById(R.id.project_title)).setText(selectedGroupName + " > " + getResources().getString(R.string.bhz) + " > 综合统分");
                //showToast(selectedUserGroupId);
                setProgressDialog();
                startGetDataThread();
            }
        }catch(Exception ex) {
            showToast(ex.getMessage());
        }
    }

    private void showToast(String msg) {
        Toast.makeText(BHZ_Tongjifenxi2Activity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("userGroupID", userGroupID);
        setResult(returnType, intent);
        super.finish();
    }
}
