package com.shtoone.glhnt.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.adapter.BHZ_StatusAdapter;
import com.shtoone.glhnt.entity.BHZ_Status;
import com.shtoone.glhnt.serviceDao.ServiceDao;

import java.io.IOException;

public class CommBHZStatusActivity extends Activity implements GestureDetector.OnGestureListener,View.OnTouchListener {

    private static final int FLING_MIN_DISTANCE = 100;
    private static final int FLING_MIN_VELOCITY = 200;
    private ViewFlipper mFlipper;

    private ListView listView;
    private BHZ_StatusAdapter adapter;
    private BHZ_Status data;
    private static Handler handler;
    private ServiceDao service;
    private TextView tv_title_page;

    private String current_PageNo = "1";
    private String userGroupID = "";
    private ProgressDialog mypDialog;
    private ImageButton btn_back;

    private int returnType = 3;
    private GestureDetector mGestureDetector;
    private String xmmc = "";
    //private DataPropertiy dp;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_bhz_status);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.theme_hntqd);
        beforeStart();
        try {
            findView();
        } catch (IOException e) {
            e.printStackTrace();
        }
        createHandler();
        setProgressDialog();
        beforeStartLoadListThread();
        startLoadListThread();
    }

    private void beforeStart(){
        service = new ServiceDao();
        userGroupID = getIntent().getStringExtra("userGroupID");
    }

    private void findView() throws IOException {
        xmmc = getIntent().getStringExtra("xmmc");
        //dp = new DataPropertiy(CommBHZStatusActivity.this);
        ((TextView) this.findViewById(R.id.project_title)).setText("拌和机状态");
        mFlipper = (ViewFlipper) this.findViewById(R.id.flipper1);
        listView = (ListView) this.findViewById(R.id.listview1);
        tv_title_page =  (TextView) this.findViewById(R.id.tv_title_page);
        btn_back = (ImageButton) this.findViewById(R.id.back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnType = 2;
                finish();
            }
        });
        mGestureDetector = new GestureDetector(this, this);
        mFlipper.setLongClickable(true);
        mFlipper.setOnTouchListener(this);
        listView.setOnTouchListener(this);
    }

    private void setProgressDialog() {
        mypDialog = new ProgressDialog(CommBHZStatusActivity.this);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mypDialog.setMessage("加载中,请稍后...");
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(true);
        mypDialog.setCanceledOnTouchOutside(false);
        mypDialog.show();
    }

    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    mypDialog.dismiss();
                    Toast.makeText(CommBHZStatusActivity.this, "暂无数据！", Toast.LENGTH_SHORT).show();
                    if (Integer.valueOf(current_PageNo) > 1) { //翻到最后一页
                        current_PageNo = String.valueOf(Integer.valueOf(current_PageNo) - 1);
                    }
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
    }

    private void startLoadListThread() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    data = service.getBhzStatus(userGroupID, current_PageNo);
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
        listView.setAdapter(new BHZ_StatusAdapter(CommBHZStatusActivity.this, data.getData()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bhz_status, menu);
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

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
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
                Toast.makeText(CommBHZStatusActivity.this, "当前已为第一页！", Toast.LENGTH_SHORT).show();
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
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("userGroupID", userGroupID);
        setResult(returnType, intent);
        super.finish();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
}
