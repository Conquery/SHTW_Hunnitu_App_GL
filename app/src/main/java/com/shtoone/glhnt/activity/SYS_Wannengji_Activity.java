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
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.adapter.SYS_GangJingLaLiAdapter;
import com.shtoone.glhnt.entity.COMM_GangJinTestType;
import com.shtoone.glhnt.entity.COMM_Shebei;
import com.shtoone.glhnt.entity.SYS_GangJingLaLi_Entity;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.util.CommFunctions;
import com.shtoone.glhnt.util.DataPropertiy;
import com.shtoone.glhnt.util.DatePicker;
import com.shtoone.glhnt.util.NetWorkingUtil;
import com.shtoone.glhnt.serviceDao.API;

import org.json.JSONArray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SYS_Wannengji_Activity extends Activity implements OnDoubleTapListener, OnGestureListener, OnTouchListener {

    private static final int FLING_MIN_DISTANCE = 100;
    private static final int FLING_MIN_VELOCITY = 200;

    private RelativeLayout relativeLayout;
    private LinearLayout layoutMain;
    private FrameLayout layoutDevice;
    private float offsetX;// 平移总量
    private float scale;// 每次平移的距离
    private static final int ANIMATION_DURATION = 20;// 每次移动的时间间隔，20ms
    private long mCurrentAnimationTime;// 当前时间，是SystemClock的时间
    private float mCurrentPosition = 0;// 当前的位置
    private boolean isShown = false;
    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2;
    private String xmmc;
    private String userGroupID;

    private ListView listView;
    private Handler handler;
    private ServiceDao service;
    //    private List<COM_SheBei_Entity> sysList;
//    private List<COM_SheBei_Entity> shebeiList;
    private SYS_GangJingLaLi_Entity data;
    private ProgressDialog mypDialog;
    private String current_PageNo = "1";
    private String startTime = "2015-09-01 12:12:12", endTime = "2015-09-30 12:12:12";
    private EditText et1, et2;
    private TextView btn_search;
    private String isQualified = "1";
    private ViewFlipper mFlipper;
    private TextView tv_title_page, tv_selectdevice, tv_selectTestType;
    private GestureDetector mGestureDetector;
    private int returnType = -1;
    private ImageButton btn_back;

    private String device = "";
    private COMM_Shebei dev_list = null;
    private COMM_GangJinTestType testType_list = null;
    private int which_dev = 0;
    private DataPropertiy dp;
    private ImageView weather_img;
    private String dengji = "SG", userRole = "";
    private String testType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_wannengji);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.theme_hntqd);
        setTheme(android.R.style.Theme_DeviceDefault_NoActionBar);

        try {
            beforeStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
        findView();
        setListener();
        createHandler();
        setProgressDialog();
        beforeStartLoadListThread();
        startLoadListThread();
    }

    private void beforeStart() throws IOException {
        xmmc = getIntent().getStringExtra("xmmc");
        dengji = getIntent().getStringExtra("dengji");
        dp = new DataPropertiy(SYS_Wannengji_Activity.this);
        ((TextView) this.findViewById(R.id.project_title)).setText(xmmc + " > " + getResources().getString(R.string.sys) + " > 万能机");
        service = new ServiceDao();

        userGroupID = getIntent().getStringExtra("userGroupID");
        Log.e("wgergeg", "userGroupID:" + userGroupID);
        userRole = getIntent().getStringExtra("userRole");

        //***** 日期初期化 BEGIN *****
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar rld = Calendar.getInstance();
        endTime = sdf.format(rld.getTime());
        rld.add(Calendar.MONTH, -3);
        startTime = sdf.format(rld.getTime());
        //***** 日期初期化 END *****
    }

    private void setProgressDialog() {
        mypDialog = new ProgressDialog(SYS_Wannengji_Activity.this);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mypDialog.setMessage("加载中，请稍后...");
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(false);
        mypDialog.setCanceledOnTouchOutside(false);
        mypDialog.show();
    }

    @SuppressLint("HandlerLeak")
    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    mypDialog.dismiss();
                    Toast.makeText(SYS_Wannengji_Activity.this, "暂无数据！", Toast.LENGTH_SHORT).show();
                    if (Integer.valueOf(current_PageNo) > 1) { //翻到最后一页
                        current_PageNo = String.valueOf(Integer.valueOf(current_PageNo) - 1);
                    }
                }
                if (msg.what == 2) {
                    doAnimation();
                }
                if (msg.what == 3) {
                    setAdapter();
                    mypDialog.dismiss();
                }
            }
        };
    }

    private void beforeStartLoadListThread() {
        mypDialog.show();
        current_PageNo = "1";
        tv_title_page.setText("第" + current_PageNo + "页");
        listView.setAdapter(null);
        startTime = et1.getText().toString();
        endTime = et2.getText().toString();
    }

    private void startLoadListThread() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    //只获取一次
                    if (dev_list == null) {
                        //TODO：祝鹏辉
                        dev_list = service.getCOMM_Shebei(userGroupID, "3", "SYS");
                        Thread.sleep(500);
                    }

                    if (testType_list == null) {
                        testType_list = service.getCOMM_GangJinTestType(API.WangnjTestType);
                        Thread.sleep(500);
                    }

                    data = service.getGJDataList(userGroupID, isQualified, startTime, endTime, current_PageNo, device, "0", testType);
                    Thread.sleep(500);
                } catch (Exception ignored) {
                }
                if (data != null) {
                    handler.sendEmptyMessage(3);
                } else {
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    protected void setAdapter() {
        tv_title_page.setText("第" + current_PageNo + "页");
        listView.setAdapter(new SYS_GangJingLaLiAdapter(SYS_Wannengji_Activity.this, data.getData(), "1"));
    }

    private void findView() {
        weather_img = (ImageView) this.findViewById(R.id.weather_img);
        if ("GL".equals(dengji)) {
            weather_img.setBackgroundResource(0);
            weather_img.setBackgroundResource(R.drawable.wrap);
        }
        listView = (ListView) this.findViewById(R.id.hntqd_listView);
        et1 = (EditText) this.findViewById(R.id.hntqd_first_choice);
        et2 = (EditText) this.findViewById(R.id.hntqd_second_choice);
        btn_search = (TextView) this.findViewById(R.id.hntqd_chaxun_btn);
        mFlipper = (ViewFlipper) this.findViewById(R.id.hntqd_Flipper);
        tv_title_page = (TextView) this.findViewById(R.id.hntqd_title_page);
        btn_back = (ImageButton) this.findViewById(R.id.back);

        tv_selectdevice = (TextView) this.findViewById(R.id.hntqd_selectDevice);
        tv_selectTestType = (TextView) this.findViewById(R.id.hntqd_selectTestType);
        radioGroup = (RadioGroup) this.findViewById(R.id.hege_radiogroup);
        radioButton1 = (RadioButton) this.findViewById(R.id.radio_hege);
        radioButton2 = (RadioButton) this.findViewById(R.id.radio_buhege);
        layoutMain = (LinearLayout) this.findViewById(R.id.main_view);
        layoutDevice = (FrameLayout) this.findViewById(R.id.frame_selectdevice);
        relativeLayout = (RelativeLayout) this.findViewById(R.id.relativeLayout);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        layoutDevice.setLayoutParams(new RelativeLayout.LayoutParams((int) (dm.widthPixels * 0.75), dm.heightPixels));
    }

    private void setListener() {
        et1.setText(startTime);
        et2.setText(endTime);
        et1.setFocusableInTouchMode(false);
        et2.setFocusableInTouchMode(false);
        et1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShown)
                    moveLayout();
                DatePicker dateTimePicKDialog = new DatePicker(
                        SYS_Wannengji_Activity.this, startTime);
                dateTimePicKDialog.dateTimePicKDialog(et1);
            }
        });
        et2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker dateTimePicKDialog = new DatePicker(
                        SYS_Wannengji_Activity.this, endTime);
                dateTimePicKDialog.dateTimePicKDialog(et2);
            }
        });
        mGestureDetector = new GestureDetector(this, this);
        mFlipper.setLongClickable(true);
        mFlipper.setOnTouchListener(this);
        listView.setOnTouchListener(this);
        btn_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeStartLoadListThread();
                if (Integer.valueOf(CommFunctions.ChangeTimeToLong(startTime)) > Integer.valueOf(CommFunctions.ChangeTimeToLong(endTime))) {
                    Toast.makeText(SYS_Wannengji_Activity.this, "开始日期不能大于结束日期！", Toast.LENGTH_SHORT).show();
                    mypDialog.dismiss();
                    return;
                }
                startLoadListThread();
            }
        });
        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                returnType = 2;
                finish();
            }
        });
        tv_selectdevice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //                layoutDevice.setVisibility(View.VISIBLE);
//                moveLayout();
                AlertDialog.Builder builder = new AlertDialog.Builder(SYS_Wannengji_Activity.this);
                builder.setIcon(R.drawable.right);
                builder.setTitle("选择一个设备");

                JSONArray array = new JSONArray();
                //指定下拉列表的显示数据
                final List<String> devNames = new ArrayList<String>();
                devNames.add("全部设备");
                final List<String> devIDs = new ArrayList<String>();
                devIDs.add("");
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
                        tv_selectdevice.setText(devNames.get(which));
                        which_dev = which;
                    }
                });
                builder.show();
            }
        });

        tv_selectTestType.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SYS_Wannengji_Activity.this);
                builder.setIcon(R.drawable.right);
                builder.setTitle("选择一个试验类型");

                JSONArray array = new JSONArray();
                //指定下拉列表的显示数据
                final List<String> testTypeNames = new ArrayList<String>();
                testTypeNames.add("全部类型");
                final List<String> testTypeIDs = new ArrayList<String>();
                testTypeIDs.add("");
                for (int i = 0; i < testType_list.getData().size(); i++) {
                    COMM_GangJinTestType.DataBean tmp = testType_list.getData().get(i);
                    testTypeNames.add(tmp.getTestName());
                    testTypeIDs.add(tmp.getTestId());
                }
                //用数组存放标段
                final String[] bids = new String[testTypeNames.size()];
                for (int i = 0; i < testTypeNames.size(); i++) {
                    bids[i] = testTypeNames.get(i);
                }
                //    设置一个下拉的列表选择项
                builder.setItems(bids, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //mypDialog.show();
                        testType = testTypeIDs.get(which);
                        Log.e("TestType", "testType:" + testType);
                        tv_selectTestType.setText(testTypeNames.get(which));
                        which_dev = which;
                    }
                });
                builder.show();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                beforeStartLoadListThread();
                if (checkedId == radioButton1.getId()) {
                    isQualified = "1";
                }
                if (checkedId == radioButton2.getId()) {
                    isQualified = "0";
                }
                startLoadListThread();
            }
        });

        //组织结构面板weather_img  tv_zuzhijiegou
        weather_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("GL".equals(dengji)) {
                    Intent intent = new Intent(SYS_Wannengji_Activity.this, CommZuhijiegou.class);
                    intent.putExtra("userGroupID", dp.get("DEPART_ID"));
                    intent.putExtra("xmmc", xmmc);
                    intent.putExtra("type", "3");
                    intent.putExtra("userRole", userRole);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }

    /**
     * 移动布局，偏移量为offsetX
     */
    private void moveLayout() {
        offsetX = layoutDevice.getWidth();
        scale = offsetX / 10;// 分成十次移动，形成动画 平移的距离
        long now = SystemClock.uptimeMillis();
        mCurrentAnimationTime = now + ANIMATION_DURATION;
        handler.sendEmptyMessageAtTime(2, mCurrentAnimationTime);
    }

    /**
     * 移动布局的指定的偏移量
     *
     * @param scale 位移量
     */
    private void moveLayout(float scale) {
        int left = layoutMain.getLeft();
        if (Math.abs(left + scale) < offsetX) {
            if ((left + scale > 0) || (left + scale < 0)) {
                layoutMain.offsetLeftAndRight((int) scale);
                mCurrentPosition += scale;
            } else {
                layoutMain.offsetLeftAndRight(0 - left);
                mCurrentPosition += 0 - left;
                isShown = false;
            }

        } else if (Math.abs(left + scale) >= offsetX) {
            layoutMain.offsetLeftAndRight((int) offsetX - left);
            mCurrentPosition += offsetX - Math.abs(left);
            isShown = true;
        }
        relativeLayout.invalidate();
    }

    private void doAnimation() {
        boolean isflag;
        if (isShown) {
            moveLayout(-scale);
            isflag = mCurrentPosition <= 0;
            if (isflag) {
                isShown = false;
                handler.removeMessages(2);
            } else {
                mCurrentAnimationTime += ANIMATION_DURATION;
                handler.sendMessageAtTime(handler.obtainMessage(2), mCurrentAnimationTime);
            }
        } else {
            moveLayout(scale);// 若是右移动，scale为负数
            if (Math.abs(mCurrentPosition) >= offsetX) {
                handler.removeMessages(2);
                isShown = true;
            } else {
                mCurrentAnimationTime += ANIMATION_DURATION;
                handler.sendMessageAtTime(handler.obtainMessage(2), mCurrentAnimationTime);
            }
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            mFlipper.setInAnimation(inFromRightAnimation());
            mFlipper.setOutAnimation(outToLeftAnimation());
            mFlipper.showNext();
            int t = Integer.valueOf(current_PageNo) + 1;
            current_PageNo = String.valueOf(t);
            mypDialog.show();
            startLoadListThread();
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            Integer index = Integer.valueOf(current_PageNo);
            if (index - 1 <= 0) {
                current_PageNo = "1";
                Toast.makeText(SYS_Wannengji_Activity.this, "当前已为第一页！", Toast.LENGTH_SHORT).show();
            } else {
                current_PageNo = String.valueOf(index - 1);
                mFlipper.setInAnimation(inFromLeftAnimation());
                mFlipper.setOutAnimation(outToRightAnimation());
                mFlipper.showPrevious();
                mypDialog.show();
                startLoadListThread();
            }
        }
        return false;
    }

    protected Animation inFromRightAnimation() {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(300);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    protected Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(300);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    protected Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(300);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    protected Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(300);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (NetWorkingUtil.isConn(SYS_Wannengji_Activity.this)) {
            int x = (int) e.getX();
            int y = (int) e.getY();
            int position = listView.pointToPosition(x, y);
            int firstVisiblePosition = listView.getFirstVisiblePosition();
            int itemIndex = listView.pointToPosition((int) e.getX(), (int) e.getY());
            SYS_GangJingLaLi_Entity.DataEntity item = (SYS_GangJingLaLi_Entity.DataEntity) listView.getItemAtPosition(itemIndex);
            Intent intent = new Intent(SYS_Wannengji_Activity.this, SYS_GangJingLaLi_XqActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("xqID", item.getSYJID());
            bundle.putString("source", "0");

            switch (item.testName) {
                case "钢筋试验":
                    bundle.putString("sysType", "1");
                    break;
                case "钢筋焊接接头试验":
                    bundle.putString("sysType", "2");
                    break;
                case "钢筋机械连接接头试验":
                    bundle.putString("sysType", "3");
                    break;
            }
            bundle.putString("xmmc", xmmc);
            bundle.putString("role", "NO");
            bundle.putString("Title_Name", xmmc + " > " + item.getShebeiname());
            bundle.putParcelable("model", item);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(SYS_Wannengji_Activity.this, "无网络连接！", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }


    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        try {
            super.onActivityResult(arg0, arg1, arg2);
            userGroupID = arg2.getStringExtra("selectedGroupId");
            if (!"".equals(arg2.getStringExtra("selectedGroupName")) && arg2.getStringExtra("selectedGroupName") != null) {
                ((TextView) this.findViewById(R.id.project_title)).setText(arg2.getStringExtra("selectedGroupName") + " > " + getResources().getString(R.string.sys) + " > 钢筋拉力");
                //showToast(selectedUserGroupId);
                //setProgressDialog();
                beforeStartLoadListThread();
                dev_list = null;
                startLoadListThread();
            }
        } catch (Exception ex) {
            //Toast.makeText(SYS_GangJingLaLi_Activity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
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
