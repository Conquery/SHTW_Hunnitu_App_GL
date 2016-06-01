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
    private MenuFrame_SYS menu_sys;    //ʩ����λʵ���Ҳ˵�
    private MenuFrame_BHZ menu_bhz;    //ʩ����λ���վ�˵�
    private SYS_LingdaoFragment lingdao_sys;  //�쵼ʵ���Ҳ˵�
    private BHZ_LingdaoFragment lingdao_bhz;  //�쵼���վ�˵�
    private RelativeLayout sys_layout, bhz_layout;
    private ImageButton menu;
    private UserInfo userInfo;
    private ServiceDao service;
    private static Handler handler;
    private MainInfo mainInfo;

    static DataPropertiy dp; // ��дdata.properties����
    Message msg = null; // ����ʱ����Ϣ
    private String[] xg_tags; // ��Ѷ�Ÿ��ǩ
    private NotificationService notificationService;// ��ȡ֪ͨ���ݷ���

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
            xgRegiste();    //ע���Ÿ�
            setListener();
            setChoiceItem(defaultChoice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * �������
     */
    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                } else {
                    Toast.makeText(getApplicationContext(), "��ȡ����ʧ�ܣ�", Toast.LENGTH_SHORT).show();
                    Log.d("ʧ�������","++++++++++++++++++++++++++++++++++");
                }
            }
        };
    }

    /**
     * �����߳���������
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
     * Ԥ����
     * @throws IOException
     */
    private void beforeStart() throws IOException {
        dp = new DataPropertiy(this);
        service = new ServiceDao();
        userInfo = getIntent().getParcelableExtra("userInfo");
        xg_tags = getIntent().getStringArrayExtra("tag");
        xg_tags = array_unique(xg_tags);
        //userInfo.setType("SG");     //DEBUG������
        userGroupID = userInfo.getDepartId();
        fManager = getSupportFragmentManager();
    }

    /**
     * ���ҿؼ�
     */
    private void findView() {
        sys_layout = (RelativeLayout) this.findViewById(R.id.menuframe_sys);
        bhz_layout = (RelativeLayout) this.findViewById(R.id.menuframe_bhz);
        menu = (ImageButton) this.findViewById(R.id.menu);
        if(userInfo.getUserRole().equals("1")) {
            //���վ
            sys_layout.setVisibility(View.GONE);
            defaultChoice = 1;
        }
        else if(userInfo.getUserRole().equals("3")) {
            //ʵ����
            bhz_layout.setVisibility(View.GONE);
            defaultChoice = 0;
        }
        else { }
    }

    /**
     * �ؼ��¼�����
     */
    private void setListener() {
        sys_layout.setOnClickListener(this);
        bhz_layout.setOnClickListener(this);

        //Menu�˵���ѡ��
        menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Menu", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, CommBHZStatusActivity.class);
//                intent.putExtra("userGroupID", userGroupID);
//                startActivityForResult(intent, 1);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.right);
                builder.setTitle("��������");

                final String[] bids = new String[4];
                bids[0] = "֪ͨ��¼";
                bids[1] = "���վ״̬";
                bids[2] = "ϵͳ������";
                bids[3] = "ע��";

                //    ����һ���������б�ѡ����
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
     * �Ÿ�ע��
     */
    private void xgRegiste(){
        // �������������������������������������� ��Ѷ�Ÿ�ע�� ����������������������������������������
        // �ػص�¼�ɹ�������Ѷ�������Ͷ�������������
        // ����logcat���������debug������ʱ��ر�
        XGPushConfig.enableDebug(this, false);
        // �����Ҫ֪��ע���Ƿ�ɹ�����ʹ��registerPush(getApplicationContext(),
        // XGIOperateCallback)��callback�汾
        // �����Ҫ���˺ţ���ʹ��registerPush(getApplicationContext(),account)�汾
        // ����ɲο���ϸ�Ŀ���ָ��
        // ���ݵĲ���ΪApplicationContext
        Context context = this;
        // 1.��ȡ�豸Token
        Handler handler = new HandlerExtension(MainActivity.this);
        msg = handler.obtainMessage();
        //tagע��
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
     * �Ÿ�ʹ�õľ��
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
                sys_layout.setBackgroundColor(getResources().getColor(R.color.blue));          //���õײ������˵�����ɫ ������ ��ɫ ѡ��
                bhz_layout.setBackgroundColor(getResources().getColor(R.color.lightgray));    //���õײ������˵�����ɫ ���վ ��ɫ δѡ��
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
                sys_layout.setBackgroundColor(getResources().getColor(R.color.lightgray));     //���õײ������˵�����ɫ ������ ��ɫ δѡ��
                bhz_layout.setBackgroundColor(getResources().getColor(R.color.blue));           //���õײ������˵�����ɫ ���վ ��ɫ ѡ��
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
                //TODO ��ֵ
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

    // ȥ���������ظ��ļ�¼
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
