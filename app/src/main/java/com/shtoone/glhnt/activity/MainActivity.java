package com.shtoone.glhnt.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.MainInfo;
import com.shtoone.glhnt.entity.UserInfo;
import com.shtoone.glhnt.serviceDao.NotificationService;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.util.DataPropertiy;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends FragmentActivity implements OnClickListener {

    private FragmentManager fManager;
    private MenuFrame_SYS menu_sys;    //施工单位实验室菜单
    private MenuFrame_BHZ menu_bhz;    //施工单位拌合站菜单
    private SYS_LingdaoFragment lingdao_sys;  //领导实验室菜单
    private BHZ_LingdaoFragment lingdao_bhz;  //领导拌合站菜单
    private RelativeLayout sys_layout, bhz_layout;
    private ImageButton menu;
    private UserInfo userInfo;
    private ServiceDao service;
    private static Handler handler;
    private MainInfo mainInfo;

    static DataPropertiy dp; // 读写data.properties的类
    Message msg = null; // 订阅时的消息
    private String[] xg_tags; // 腾讯信鸽标签
    private NotificationService notificationService;// 获取通知数据服务

    private String userGroupID; // TODO
    private final String GL = "GL";
    private int defaultChoice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.theme_main);
        setTheme(android.R.style.Theme_DeviceDefault_NoActionBar);
        try {
            beforeStart();
            findView();
            xgRegiste();    //注册信鸽
            setListener();
            setChoiceItem(defaultChoice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建句柄
     */
    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                } else {
                    Toast.makeText(getApplicationContext(), "获取数据失败！", Toast.LENGTH_SHORT).show();
                    Log.d("失败在这里：","++++++++++++++++++++++++++++++++++");
                }
            }
        };
    }

    /**
     * 开启线程请求数据
     */
    private void startGetDataThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mainInfo = service.getData(userInfo.getDepartId());
                } catch (Exception e) {
                }
                if (mainInfo != null) {
                    handler.sendEmptyMessage(1);
                } else {
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    /**
     * 预处理
     * @throws IOException
     */
    private void beforeStart() throws IOException {
        dp = new DataPropertiy(this);
        service = new ServiceDao();
        userInfo = getIntent().getParcelableExtra("userInfo");
        xg_tags = getIntent().getStringArrayExtra("tag");
        xg_tags = array_unique(xg_tags);
        //userInfo.setType("SG");     //DEBUG测试用
        userGroupID = userInfo.getDepartId();
        fManager = getSupportFragmentManager();
    }

    /**
     * 查找控件
     */
    private void findView() {
        sys_layout = (RelativeLayout) this.findViewById(R.id.menuframe_sys);
        bhz_layout = (RelativeLayout) this.findViewById(R.id.menuframe_bhz);
        menu = (ImageButton) this.findViewById(R.id.menu);
        if(userInfo.getUserRole().equals("1")) {
            //拌合站
            sys_layout.setVisibility(View.GONE);
            defaultChoice = 1;
        }
        else if(userInfo.getUserRole().equals("3")) {
            //实验室
            bhz_layout.setVisibility(View.GONE);
            defaultChoice = 0;
        }
        else { }
    }

    /**
     * 控件事件监听
     */
    private void setListener() {
        sys_layout.setOnClickListener(this);
        bhz_layout.setOnClickListener(this);

        //Menu菜单的选择
        menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Menu", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, CommBHZStatusActivity.class);
//                intent.putExtra("userGroupID", userGroupID);
//                startActivityForResult(intent, 1);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.right);
                builder.setTitle("其他功能");

                final String[] bids = new String[4];
                bids[0] = "通知记录";
                bids[1] = "拌合站状态";
                bids[2] = "系统及升级";
                bids[3] = "注销";

                //    设置一个下拉的列表选择项
                builder.setItems(bids, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //mypDialog.show();
                        if(which == 0){
                            Intent intent = new Intent(MainActivity.this, PushMessageListActivity.class);
                            intent.putExtra("userGroupID", userGroupID);
                            startActivityForResult(intent, 1);
                        }else if(which == 1){
                            Intent intent = new Intent(MainActivity.this, CommBHZStatusActivity.class);
                            intent.putExtra("userGroupID", userGroupID);
                            startActivityForResult(intent, 1);
                        }else if(which == 2){
                            Intent intent = new Intent(MainActivity.this, OT_aboutActivity.class);
                            intent.putExtra("userGroupID", userGroupID);
                            startActivityForResult(intent, 1);
                        }else{
                            dp.put("USER_NAME","");
                            dp.put("PASSWORD","");
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
                builder.show();
            }
        });
    }

    /**
     * 信鸽注册
     */
    private void xgRegiste(){
        // ■■■■■■■■■■■■■■■■■■■ 腾讯信鸽注册 ■■■■■■■■■■■■■■■■■■■■
        // 回回登录成功后向腾讯发起推送订阅则启动订阅
        // 开启logcat输出，方便debug，发布时请关闭
        XGPushConfig.enableDebug(this, false);
        // 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(),
        // XGIOperateCallback)带callback版本
        // 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
        // 具体可参考详细的开发指南
        // 传递的参数为ApplicationContext
        Context context = this;
        // 1.获取设备Token
        Handler handler = new HandlerExtension(MainActivity.this);
        msg = handler.obtainMessage();
        //tag注册
        for (int i = 0; i < xg_tags.length; i++) {
            XGPushManager.setTag(context, xg_tags[i]);
            //Log.d("tag",xg_tags[i]);
        }
        XGPushManager.registerPush(context, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                Log.w(Constants.LogTag, "+++ register push sucess. token:" + data);
                msg.obj = "+++ register push sucess. token:" + data;
                msg.sendToTarget();
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.w(Constants.LogTag,
                        "+++ register push fail. token:" + data + ", errCode:" + errCode + ",msg:" + msg);

                MainActivity.this.msg.obj = "+++ register push fail. token:" + data + ", errCode:" + errCode + ",msg:" + msg;
                MainActivity.this.msg.sendToTarget();
            }
        });
        notificationService = NotificationService.getInstance(this);
    }

    /**
     * 信鸽使用的句柄
     */
    private static class HandlerExtension extends Handler {
        WeakReference<MainActivity> mActivity;

        HandlerExtension(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity theActivity = mActivity.get();
            if (theActivity == null) {
                theActivity = new MainActivity();
            }
            if (msg != null) {
                Log.w(Constants.LogTag, msg.obj.toString());
                String token = XGPushConfig.getToken(theActivity);
                dp.put("TOKEN", token);
            }
            // XGPushManager.registerCustomNotification(theActivity,
            // "BACKSTREET", "BOYS", System.currentTimeMillis() + 5000, 0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuframe_sys:
                setChoiceItem(0);
                break;
            case R.id.menuframe_bhz:
                setChoiceItem(1);
                break;
            default:
                break;
        }
    }

    private void setChoiceItem(int index) {
        FragmentTransaction transaction = fManager.beginTransaction();
        clearChoice();
        hideFragments(transaction);
        switch (index) {
            case 0:
                sys_layout.setBackgroundColor(getResources().getColor(R.color.blue));          //设置底部导航菜单背景色 试验室 蓝色 选中
                bhz_layout.setBackgroundColor(getResources().getColor(R.color.lightgray));    //设置底部导航菜单背景色 拌合站 灰色 未选中
                if(userInfo.getType().equals(GL)) {
                    if (lingdao_sys == null) {
                        lingdao_sys = new SYS_LingdaoFragment();
                        lingdao_sys.userGroupID = userGroupID;
                        lingdao_sys.userInfo = userInfo;
                        lingdao_sys.mainInfo = mainInfo;
                        transaction.add(R.id.menuframecontent, lingdao_sys);
                    } else {
                        transaction.show(lingdao_sys);
                    }
                }else{
                    if (menu_sys == null) {
                        menu_sys = new MenuFrame_SYS();
                        menu_sys.userGroupID = userGroupID;
                        menu_sys.userInfo = userInfo;
                        menu_sys.mainInfo = mainInfo;
                        transaction.add(R.id.menuframecontent, menu_sys);
                    } else {
                        transaction.show(menu_sys);
                    }
                }

                break;

            case 1:
                sys_layout.setBackgroundColor(getResources().getColor(R.color.lightgray));     //设置底部导航菜单背景色 试验室 灰色 未选中
                bhz_layout.setBackgroundColor(getResources().getColor(R.color.blue));           //设置底部导航菜单背景色 拌合站 蓝色 选中
                if(userInfo.getType().equals(GL)) {
                    if (lingdao_bhz == null) {
                        lingdao_bhz = new BHZ_LingdaoFragment();
                        lingdao_bhz.userGroupID = userGroupID;
                        lingdao_bhz.userInfo = userInfo;
                        lingdao_bhz.mainInfo = mainInfo;
                        transaction.add(R.id.menuframecontent, lingdao_bhz);
                    } else {
                        transaction.show(lingdao_bhz);
                    }
                }else{
                    if (menu_bhz == null) {
                        menu_bhz = new MenuFrame_BHZ();
                        menu_bhz.userGroupID = userGroupID;
                        menu_bhz.userInfo = userInfo;
                        menu_bhz.mainInfo = mainInfo;
                        transaction.add(R.id.menuframecontent, menu_bhz);
                    } else {
                        transaction.show(menu_bhz);
                    }
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if(userInfo.getType().equals(GL)){
            if (lingdao_sys != null) {
                transaction.hide(lingdao_sys);
            }
            if (lingdao_bhz != null) {
                transaction.hide(lingdao_bhz);
            }
        }else{
            if (menu_sys != null) {
                transaction.hide(menu_sys);
            }
            if (menu_bhz != null) {
                transaction.hide(menu_bhz);
            }
        }
    }

    public void clearChoice() {
        sys_layout.setBackgroundColor(getResources().getColor(R.color.lightgray));
        bhz_layout.setBackgroundColor(getResources().getColor(R.color.lightgray));
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);

        if (arg1 <= 1 && arg1 >= 0) {
            setChoiceItem(arg1);
        }
        if(arg2 != null){
            userGroupID = arg2.getStringExtra("userGroupID");
            if(userInfo.getType().equals(GL)){
                //TODO 赋值
                if (lingdao_sys != null) {
                    lingdao_sys.userGroupID = userGroupID;
                }
                if (lingdao_bhz != null) {
                    lingdao_bhz.userGroupID = userGroupID;
                }
            }else{
                if (menu_sys != null) {
                    menu_sys.userGroupID = userGroupID;
                }
                if (menu_bhz != null) {
                    menu_bhz.userGroupID = userGroupID;
                }
            }
        }
    }

    // 去除数组中重复的记录
    public static String[] array_unique(String[] a) {
        // array_unique
        List<String> list = new LinkedList<String>();
        for (int i = 0; i < a.length; i++) {
            if (!list.contains(a[i])) {
                list.add(a[i]);
            }
        }
        return (String[]) list.toArray(new String[list.size()]);
    }
}
