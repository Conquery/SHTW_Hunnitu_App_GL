package com.shtoone.glhnt.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.COM_XY;
import com.shtoone.glhnt.entity.SYS_Tongjifenxi;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.ui.HistogramView;
import com.shtoone.glhnt.util.CommFunctions;
import com.shtoone.glhnt.util.DataPropertiy;
import com.shtoone.glhnt.util.DatePicker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SysTongjfenxiActivity extends Activity {
    private HistogramView histogramView1;
    private ProgressDialog mypDialog;
    private Handler handler;
    //private List<SYS_Lingdao> data;
    private SYS_Tongjifenxi data2;
    private ServiceDao service;

    private EditText dateFrom, dateTo;         //开始与结束日期
    private boolean isShown = false;          //当前是否弹出了日期选择框
    private ImageButton btn_back;               //顶部回退按钮
    private TextView chart_title,btn_search;  //检索按钮
    private Button tjfx_btn_countsy,tjfx_btn_hegelv;   //按数量，或者按不合格
    private TextView sysdh_item1_1,sysdh_item1_2,sysdh_item1_3,sysdh_item1_4,sysdh_item2_1,sysdh_item2_2,sysdh_item2_3,sysdh_item2_4,sysdh_item3_1,sysdh_item3_2,sysdh_item3_3,sysdh_item3_4,sysdh_item4_1,sysdh_item4_2,sysdh_item4_3,sysdh_item4_4;
    private TextView sysdh_item1_2_1,sysdh_item1_2_2,sysdh_item2_2_1,sysdh_item2_2_2,sysdh_item3_2_1,sysdh_item3_2_2,sysdh_item4_2_1,sysdh_item4_2_2;
    private String xmmc;
    private String userGroupID;
    private String startTime = "2015-09-01 12:12:12", endTime = "2015-09-30 12:12:12";
    private int returnType = -1;
    private int showType = 0;
    private ImageView weather_img;
    private String dengji="SG",userRole = "";
    private DataPropertiy dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_sys_tongjfenxi);
        this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.theme_hntqd);
        setTheme(android.R.style.Theme_DeviceDefault_NoActionBar);

        service = new ServiceDao();
        findView();
        try {
            beforeStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setListener();
        setProgressDialog();
        createHandler();
        setLayoutAndColor();
        startGetDataThread();
    }

    /**
     *  UI预处理
     */
    private void beforeStart() throws IOException {
        dengji = getIntent().getStringExtra("dengji");
        xmmc = getIntent().getStringExtra("xmmc");
        ((TextView) this.findViewById(R.id.project_title)).setText(xmmc + " > " + getResources().getString(R.string.sys) + " > 综合统分");
        service = new ServiceDao();
        xmmc = getIntent().getStringExtra("xmmc");
        userGroupID = getIntent().getStringExtra("userGroupID");
        userRole = getIntent().getStringExtra("userRole");
        dp = new DataPropertiy(SysTongjfenxiActivity.this);

        //***** 日期初期化 BEGIN *****
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar rld = Calendar.getInstance();
        endTime = sdf.format(rld.getTime());
        rld.add(Calendar.MONTH, -3);
        startTime = sdf.format(rld.getTime());
        //***** 日期初期化 END *****
    }

    /**
     * 交互性控件
     */
    private void findView() {
        weather_img = (ImageView) this.findViewById(R.id.weather_img);
        if("GL".equals(dengji)) {
            weather_img.setBackgroundResource(0);
            weather_img.setBackgroundResource(R.drawable.wrap);
        }
        dateFrom = (EditText) this.findViewById(R.id.systjfx_first_choice);                  //开始日期
        dateTo = (EditText) this.findViewById(R.id.systjfx_second_choice);                   //结束日期
        histogramView1 = (HistogramView) this.findViewById(R.id.tjfx_HistogramView1);       //chart1
        chart_title = (TextView) this.findViewById(R.id.chart_title);
        tjfx_btn_countsy = (Button) this.findViewById(R.id.tjfx_btn_countsy);
        tjfx_btn_hegelv = (Button) this.findViewById(R.id.tjfx_btn_hegelv);
        /****************************************************
         * TODO:统计表格绘制
        ****************************************************/
        sysdh_item1_1 = (TextView) this.findViewById(R.id.sysdh_item1_1);
        sysdh_item1_2 = (TextView) this.findViewById(R.id.sysdh_item1_2);
        sysdh_item1_2_1 = (TextView) this.findViewById(R.id.sysdh_item1_2_1);
        sysdh_item1_2_2 = (TextView) this.findViewById(R.id.sysdh_item1_2_2);
        sysdh_item1_3 = (TextView) this.findViewById(R.id.sysdh_item1_3);
        sysdh_item1_4 = (TextView) this.findViewById(R.id.sysdh_item1_4);

        sysdh_item2_1 = (TextView) this.findViewById(R.id.sysdh_item2_1);
        sysdh_item2_2 = (TextView) this.findViewById(R.id.sysdh_item2_2);
        sysdh_item2_2_1 = (TextView) this.findViewById(R.id.sysdh_item2_2_1);
        sysdh_item2_2_2 = (TextView) this.findViewById(R.id.sysdh_item2_2_2);
        sysdh_item2_3 = (TextView) this.findViewById(R.id.sysdh_item2_3);
        sysdh_item2_4 = (TextView) this.findViewById(R.id.sysdh_item2_4);

        sysdh_item3_1 = (TextView) this.findViewById(R.id.sysdh_item3_1);
        sysdh_item3_2 = (TextView) this.findViewById(R.id.sysdh_item3_2);
        sysdh_item3_2_1 = (TextView) this.findViewById(R.id.sysdh_item3_2_1);
        sysdh_item3_2_2 = (TextView) this.findViewById(R.id.sysdh_item3_2_2);
        sysdh_item3_3 = (TextView) this.findViewById(R.id.sysdh_item3_3);
        sysdh_item3_4 = (TextView) this.findViewById(R.id.sysdh_item3_4);

        sysdh_item4_1 = (TextView) this.findViewById(R.id.sysdh_item4_1);
        sysdh_item4_2 = (TextView) this.findViewById(R.id.sysdh_item4_2);
        sysdh_item4_2_1 = (TextView) this.findViewById(R.id.sysdh_item4_2_1);
        sysdh_item4_2_2 = (TextView) this.findViewById(R.id.sysdh_item4_2_2);
        sysdh_item4_3 = (TextView) this.findViewById(R.id.sysdh_item4_3);
        sysdh_item4_4 = (TextView) this.findViewById(R.id.sysdh_item4_4);

        btn_back = (ImageButton) this.findViewById(R.id.back);                                  //顶部回退按钮
        btn_search = (TextView) this.findViewById(R.id.systjfx_chaxun_btn);                  //检索按钮
    }

    /**
     * 再执行业务之前的事件
     */
    private void beforeStartLoadListThread() {
        mypDialog.show();
        startTime = dateFrom.getText().toString();
        endTime = dateTo.getText().toString();
    }

    /**
     * 开启线程，执行业务处理，检索数据
     */
    private void startGetDataThread() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    //data = service.getSysLingdaoData(userGroupID,startTime,endTime);
                    data2 = service.getSYS_Tongjifenxi(userGroupID,startTime,endTime);
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (data2 != null) {
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
                    Toast.makeText(SysTongjfenxiActivity.this, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
                if (msg.what == 1) {
                    showView(showType);
                }
            }
        };
    }

    /****************************************************
     * chart与数据列表展示
     ***************************************************/
    protected void showView(int type) {
        List<COM_XY> dataSeries = new ArrayList<COM_XY>();
        if(data2 == null) return;
        if(data2.getData().size() > 0){
            for(int i=0; i<data2.getData().size();i++ ){
                SYS_Tongjifenxi.DataEntity tmp = data2.getData().get(i);
                if(tmp.getTestType().equals("100014")){
                    //砼抗压强度
                    COM_XY item = new COM_XY();
                    sysdh_item1_1.setText(tmp.getTestCount());
                    sysdh_item1_2.setText(tmp.getQualifiedCount());
                    sysdh_item1_2_1.setText(tmp.getValidCount());
                    sysdh_item1_2_2.setText(tmp.getNotqualifiedCount());
                    sysdh_item1_3.setText(tmp.getQualifiedCount());
                    sysdh_item1_4.setText(tmp.getQualifiedPer());
                    item.setName1(getResources().getString(R.string.comm_hunnituqiangdu));
                    if(type == 0) {
                        // Log.d("转换1：", tmp.getTongkangya1());
                        if(tmp.getTestCount() != null)
                            item.setName2(Double.valueOf(tmp.getTestCount()));
                        else
                            item.setName2(0.00);
                    }else{
                        if(tmp.getNotqualifiedCount() != null) {
                            item.setName2(Double.valueOf(tmp.getNotqualifiedCount()));
                            Log.d("1不合格",tmp.getNotqualifiedCount());
                        }
                        else
                            item.setName2(0.00);
                    }
                    dataSeries.add(item);
                }else if(tmp.getTestType().equals("100047")){
                    //钢筋试验
                    COM_XY item = new COM_XY();
                    sysdh_item2_1.setText(tmp.getTestCount());
                    sysdh_item2_2.setText(tmp.getQualifiedCount());
                    sysdh_item2_2_1.setText(tmp.getValidCount());
                    sysdh_item2_2_2.setText(tmp.getNotqualifiedCount());
                    sysdh_item2_3.setText(tmp.getQualifiedCount());
                    sysdh_item2_4.setText(tmp.getQualifiedPer());
                    item.setName1(getResources().getString(R.string.comm_gangjingshiyan));
                    if(type == 0) {
                        // Log.d("转换1：", tmp.getTongkangya1());
                        if(tmp.getTestCount() != null)
                            item.setName2(Double.valueOf(tmp.getTestCount()));
                        else
                            item.setName2(0.00);
                    }else{
                        if(tmp.getNotqualifiedCount() != null) {
                            item.setName2(Double.valueOf(tmp.getNotqualifiedCount()));
                            Log.d("2不合格",tmp.getNotqualifiedCount());
                        }
                        else
                            item.setName2(0.00);
                    }
                    dataSeries.add(item);
                }else if(tmp.getTestType().equals("100048")){
                    //钢筋焊接接头试验
                    COM_XY item = new COM_XY();
                    sysdh_item3_1.setText(tmp.getTestCount());
                    sysdh_item3_2.setText(tmp.getQualifiedCount());
                    sysdh_item3_2_1.setText(tmp.getValidCount());
                    sysdh_item3_2_2.setText(tmp.getNotqualifiedCount());
                    sysdh_item3_3.setText(tmp.getQualifiedCount());
                    sysdh_item3_4.setText(tmp.getQualifiedPer());
                    item.setName1(getResources().getString(R.string.comm_hanjiejietou));
                    if(type == 0) {
                        // Log.d("转换1：", tmp.getTongkangya1());
                        if(tmp.getTestCount() != null)
                            item.setName2(Double.valueOf(tmp.getTestCount()));
                        else
                            item.setName2(0.00);
                    }else{
                        if(tmp.getNotqualifiedCount() != null) {
                            item.setName2(Double.valueOf(tmp.getNotqualifiedCount()));
                            Log.d("3不合格",tmp.getNotqualifiedCount());
                        }
                        else
                            item.setName2(0.00);
                    }
                    dataSeries.add(item);
                }else{
                    //钢筋机械连接接头试验
                    COM_XY item = new COM_XY();
                    sysdh_item4_1.setText(tmp.getTestCount());
                    sysdh_item4_2.setText(tmp.getQualifiedCount());
                    sysdh_item4_2_1.setText(tmp.getValidCount());
                    sysdh_item4_2_2.setText(tmp.getNotqualifiedCount());
                    sysdh_item4_3.setText(tmp.getQualifiedCount());
                    sysdh_item4_4.setText(tmp.getQualifiedPer());
                    item.setName1(getResources().getString(R.string.comm_jixiejietou));
                    if(type == 0) {
                        // Log.d("转换1：", tmp.getTongkangya1());
                        if(tmp.getTestCount() != null)
                            item.setName2(Double.valueOf(tmp.getTestCount()));
                        else
                            item.setName2(0.00);
                    }else{
                        if(tmp.getNotqualifiedCount() != null) {
                            item.setName2(Double.valueOf(tmp.getNotqualifiedCount()));
                            Log.d("4不合格",tmp.getNotqualifiedCount());
                        }
                        else
                            item.setName2(0.00);
                    }
                    dataSeries.add(item);
                }
            }
        }
//        if(data.size() > 0){
//            SYS_Lingdao tmp = data.get(0);
//            sysdh_item1_1.setText(tmp.getTongkangya1());
//            sysdh_item1_2.setText(tmp.getTongkangya2());
//            sysdh_item1_3.setText(tmp.getTongkangya3());
//            sysdh_item1_4.setText(tmp.getTongkangya4());
//            COM_XY data1 = new COM_XY();
//            data1.setName1(getResources().getString(R.string.comm_hunnituqiangdu));
//            if(type == 0) {
//               // Log.d("转换1：", tmp.getTongkangya1());
//                if(tmp.getTongkangya1() != null)
//                    data1.setName2(Double.valueOf(tmp.getTongkangya1()));
//                else
//                    data1.setName2(0.00);
//            }else{
//                if(tmp.getTongkangya2() != null)
//                    data1.setName2(Double.valueOf(tmp.getTongkangya2()));
//                else
//                    data1.setName2(0.00);
//            }
//            dataSeries.add(data1);
//
//            sysdh_item2_1.setText(tmp.getGangjinlali1());
//            sysdh_item2_2.setText(tmp.getGangjinlali2());
//            sysdh_item2_3.setText(tmp.getGangjinlali3());
//            sysdh_item2_4.setText(tmp.getGangjinlali4());
//
//            COM_XY data2 = new COM_XY();
//            data2.setName1(getResources().getString(R.string.comm_gangjingshiyan));
//            if(type == 0) {
//                //Log.d("转换2：",tmp.getGangjinlali1());
//                if(tmp.getGangjinlali1() != null)
//                    data2.setName2(Double.valueOf(tmp.getGangjinlali1()));
//                else
//                    data2.setName2(0.00);
//            }else{
//                if(tmp.getGangjinlali2() != null)
//                    data2.setName2(Double.valueOf(tmp.getGangjinlali2()));
//                else
//                    data2.setName2(0.00);
//            }
//            dataSeries.add(data2);
//
//            sysdh_item3_1.setText(tmp.getGangjinhjjt1());
//            sysdh_item3_2.setText(tmp.getGangjinhjjt2());
//            sysdh_item3_3.setText(tmp.getGangjinhjjt3());
//            sysdh_item3_4.setText(tmp.getGangjinhjjt4());
//
//            COM_XY data3 = new COM_XY();
//            data3.setName1(getResources().getString(R.string.comm_hanjiejietou));
//            if(type == 0) {
//                //Log.d("转换3：", String.valueOf(tmp.getGangjinhjjt1() == null));
//                if(tmp.getGangjinhjjt1() != null)
//                    data3.setName2(Double.valueOf(tmp.getGangjinhjjt1()));
//                else
//                    data3.setName2(0.00);
//            }else {
//                if(tmp.getGangjinhjjt2() != null)
//                    data3.setName2(Double.valueOf(tmp.getGangjinhjjt2()));
//                else
//                    data3.setName2(0.00);
//            }
//            dataSeries.add(data3);
//
//            sysdh_item4_1.setText(tmp.getGangjinjxljjt1());
//            sysdh_item4_2.setText(tmp.getGangjinjxljjt2());
//            sysdh_item4_3.setText(tmp.getGangjinjxljjt3());
//            sysdh_item4_4.setText(tmp.getGangjinjxljjt4());
//
//            COM_XY data4 = new COM_XY();
//            data4.setName1(getResources().getString(R.string.comm_jixiejietou));
//            if(type == 0) {
//                if(tmp.getGangjinjxljjt1() != null)
//                    data4.setName2(Double.valueOf(tmp.getGangjinjxljjt1()));
//                else
//                    data4.setName2(0.00);
//            }else{
//                if(tmp.getGangjinjxljjt2() != null)
//                    data4.setName2(Double.valueOf(tmp.getGangjinjxljjt2()));
//                else
//                    data4.setName2(0.00);
//            }
//            dataSeries.add(data4);
//        }

        histogramView1.Update(dataSeries);
    }

    /**
     *  控件交互性处理
     */
    private void setListener() {
        dateFrom.setText(startTime);
        dateTo.setText(endTime);
        dateFrom.setFocusableInTouchMode(false);
        dateTo.setFocusableInTouchMode(false);

        //选择开始日期
        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker dateTimePicKDialog = new DatePicker(
                        SysTongjfenxiActivity.this, startTime);
                dateTimePicKDialog.dateTimePicKDialog(dateFrom);
            }
        });

        //选择结束日期
        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker dateTimePicKDialog = new DatePicker(
                        SysTongjfenxiActivity.this, endTime);
                dateTimePicKDialog.dateTimePicKDialog(dateTo);
            }
        });

        //后退按钮按下
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnType = 2;
                finish();
            }
        });

        //检索按钮按下
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeStartLoadListThread();
                if(Integer.valueOf(CommFunctions.ChangeTimeToLong(startTime)) > Integer.valueOf(CommFunctions.ChangeTimeToLong(endTime)) ){
                    Toast.makeText(SysTongjfenxiActivity.this, "开始日期不能大于结束日期！", Toast.LENGTH_SHORT).show();
                    mypDialog.dismiss();
                    return;
                }
                startGetDataThread();
            }
        });

        //按总数
        tjfx_btn_countsy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tjfx_btn_countsy.setTextColor(Color.rgb(0, 0, 0));
                tjfx_btn_hegelv.setTextColor(Color.rgb(190, 190, 190));
                showView(0);
            }
        });

        //按不合格数
        tjfx_btn_hegelv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tjfx_btn_countsy.setTextColor(Color.rgb(190, 190, 190));
                tjfx_btn_hegelv.setTextColor(Color.rgb(0, 0, 0));
                showView(1);
            }
        });

        //组织结构面板weather_img  tv_zuzhijiegou
        weather_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("GL".equals(dengji)) {
                    Intent intent = new Intent(SysTongjfenxiActivity.this, CommZuhijiegou.class);
                    intent.putExtra("userGroupID", dp.get("DEPART_ID"));
                    intent.putExtra("xmmc", xmmc);
                    intent.putExtra("type", "3");
                    intent.putExtra("userRole", userRole);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sys_tongjfenxi, menu);
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

    /**
     * 界面布局
     */
    private void setLayoutAndColor() {
        // 图表显示范围在占屏幕大小的90%的区域内
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int scrWidth = (int) (dm.widthPixels * 1);
        int scrHeight = (int) (dm.heightPixels * 0.3);
        int lstHeight = (int) (dm.heightPixels * 0.05);

        // 容器属性
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(scrWidth, scrHeight);

        histogramView1.setLayoutParams(layoutParams);
        histogramView1.setTitle("试验结果统分");
        histogramView1.setColorPlotArea(getResources().getColor(R.color.deepSkyBlue));
        LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(scrWidth, lstHeight);
        /****************************************************
         * TODO:统计表格绘制
         ****************************************************/
    }

    /**
     * 加载特效
     */
    private void setProgressDialog() {
        mypDialog = new ProgressDialog(SysTongjfenxiActivity.this);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mypDialog.setMessage("加载中,请稍后...");
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(false);
        mypDialog.setCanceledOnTouchOutside(false);
        mypDialog.show();
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        try {
            super.onActivityResult(arg0, arg1, arg2);
            userGroupID = arg2.getStringExtra("selectedGroupId");
            if(!"".equals(arg2.getStringExtra("selectedGroupName")) && arg2.getStringExtra("selectedGroupName") != null) {
                ((TextView) this.findViewById(R.id.project_title)).setText(arg2.getStringExtra("selectedGroupName") + " > " + getResources().getString(R.string.sys) + " > 综合统分");
                //showToast(selectedUserGroupId);
                //setProgressDialog();
                beforeStartLoadListThread();
                startGetDataThread();
            }
        }catch(Exception ex) {
            //Toast.makeText(SysTongjfenxiActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("userGroupID", userGroupID);
        setResult(returnType, intent);
        super.finish();
    }
}
