package com.shtoone.glhnt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.MainInfo;
import com.shtoone.glhnt.entity.Menu_BHZ;
import com.shtoone.glhnt.entity.UserInfo;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.util.DataPropertiy;

import java.io.IOException;


public class MenuFrame_BHZ extends Fragment {
    private static DataPropertiy dp;               // 读写缓存data.properties的类
    private Menu_BHZ data_list;           //本界面服务器端的数据
    private ServiceDao apiDao;            //API请求类
    private static Handler handler;      //全局通知句柄

    public String userGroupID;
    public UserInfo userInfo;
    public MainInfo mainInfo;

    private View view;
    private LinearLayout sc_chaxun,daichuzhi;
    private RelativeLayout  zonghetongji, cailiaoyongliang;
    private TextView tv_daichuzhi, tv_leijichuzhi, tv_chuzhilv,tv_sc_today, tv_sc_leiji, tv_sc_auth, tv_daichuzhi_auth, tv_tongji_auth, tv_cailiao_auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menuframe_bhz, container, false);
        //创建数据缓存类
        try {
            apiDao = new ServiceDao();
            dp = new DataPropertiy(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        findView();
        setLayoutParams();
        setOnClickListener();
        createHandler();      //数据加载消息监听事件
        startLoadListThread();   //开启加载列表数据请求线程
        return view;
    }

    /**
     * 展示数据
     * @param data
     */
    public void showView(Menu_BHZ data) {
        tv_daichuzhi.setText(data.getData().getWaitRealCount());
        tv_leijichuzhi.setText(data.getData().getLeijiRealCount());
        tv_chuzhilv.setText(data.getData().getRealPer());
        tv_sc_today.setText(data.getData().getTodayCount());
        tv_sc_leiji.setText(data.getData().getLeijiRealCount());
        tv_sc_auth.setText("");
        tv_daichuzhi_auth.setText("");
        tv_tongji_auth.setText("");
        tv_cailiao_auth.setText("");
    }

    private void findView() {
        userGroupID = userInfo.getDepartId();
        ((TextView) view.findViewById(R.id.project_title)).setText(userInfo.getDepartName() + " > " + getResources().getString(R.string.project_bhz));
        sc_chaxun = (LinearLayout) view.findViewById(R.id.bhz_scsjchaxun);
        daichuzhi = (LinearLayout) view.findViewById(R.id.bhz_daichuzhibaojing);
        zonghetongji = (RelativeLayout) view.findViewById(R.id.bhz_zonghetongji);
        cailiaoyongliang = (RelativeLayout) view.findViewById(R.id.bhz_cailiaoyongliang);

        tv_daichuzhi = (TextView) view.findViewById(R.id.main_tv_daichuzhi);
        tv_leijichuzhi = (TextView) view.findViewById(R.id.main_tv_leijichuzhi);
        tv_chuzhilv = (TextView) view.findViewById(R.id.main_tv_chuzhilv);
        tv_sc_today = (TextView) view.findViewById(R.id.txtBhzScToday);
        tv_sc_leiji = (TextView) view.findViewById(R.id.txtBhzDoLeiji);
        tv_sc_auth = (TextView) view.findViewById(R.id.menuscchaxunNoAuth);
        tv_daichuzhi_auth = (TextView) view.findViewById(R.id.bhz_daichuzhi_auth);
        tv_tongji_auth = (TextView) view.findViewById(R.id.sys_tongji_auth);
        tv_cailiao_auth = (TextView) view.findViewById(R.id.sys_buhege_auth);
    }

    private void setLayoutParams() {
        DisplayMetrics dm = getResources().getDisplayMetrics();

        int menuWidth = (int)((dm.widthPixels * 0.9 / 2));

        TableRow.LayoutParams pram = new TableRow.LayoutParams(menuWidth, menuWidth);
        pram.leftMargin = 4;

        sc_chaxun.setLayoutParams(pram);
        daichuzhi.setLayoutParams(pram);
        zonghetongji.setLayoutParams(pram);
        cailiaoyongliang.setLayoutParams(pram);
    }

    /************************************************************
     * 3.1开启加载列表数据请求线程
     ***********************************************************/
    private void startLoadListThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    data_list = apiDao.getBhzMenuData(userGroupID);
                    if (data_list != null) handler.sendEmptyMessage(1);
                    else handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /************************************************************
     * 4.创建句柄：监听数据加载后的消息
     ***********************************************************/
    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //dialog.dismiss();
                switch (msg.what) {
                    case 0:
                        showToast(getResources().getString(R.string.tip_requestdatafalse));
                        break;
                    case 1:
                        showView(data_list);
                        break;
                }
            }
        };
    }

    /************************************************************
     * 4.1无数据时的提示
     ***********************************************************/
    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /************************************************************
     * 事件监听程序
     ***********************************************************/
    private void setOnClickListener() {
        //生产数据查询
        sc_chaxun.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
				Intent intent = new Intent(getActivity(), BHZ_SCshujuchaxun_Activity.class);
				intent.putExtra("userGroupID", userGroupID);
                intent.putExtra("xmmc",userInfo.getDepartName());
                intent.putExtra("dengji", "SG");
                intent.putExtra("userRole", userInfo.getUserRole());
				startActivityForResult(intent, 1);
            }
        });
        //超标处置
        if(!userInfo.getQuanxian().isHntchaobiaoReal() && !userInfo.getQuanxian().isHntchaobiaoSp()){
            tv_daichuzhi_auth.setText(getResources().getString(R.string.txt_noSecurity));
        }else{
            daichuzhi.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), BHZ_ChaobiaoChuZHi_Activity.class);
                    intent.putExtra("userGroupID", userGroupID);
                    intent.putExtra("xmmc", userInfo.getDepartName());
                    intent.putExtra("dengji", "SG");
                    intent.putExtra("userRole", userInfo.getUserRole());
                    //审批还是超标权限
                    if(userInfo.getQuanxian().isHntchaobiaoSp()) {
                        intent.putExtra("role", "SP");
                    }else if(userInfo.getQuanxian().isHntchaobiaoReal()) {
                        intent.putExtra("role", "CZ");
                    } else {
                        intent.putExtra("role", "NO");
                    }
                    //Log.d("用户全名：", userInfo.getUserFullName());
                    intent.putExtra("chuzhi",userInfo.getQuanxian().isHntchaobiaoReal());
                    intent.putExtra("userFullName",userInfo.getUserFullName());
                    startActivityForResult(intent, 1);
                }
            });
        }

        //综合统计分析
        zonghetongji.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BHZ_Tongjifenxi2Activity.class);
                intent.putExtra("userGroupID", userGroupID);
                intent.putExtra("xmmc",userInfo.getDepartName());
                intent.putExtra("dengji", "SG");
                intent.putExtra("userRole", userInfo.getUserRole());
                startActivityForResult(intent, 1);
            }
        });

        //材料用量
        cailiaoyongliang.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BHZ_CailiaoyongliangActivity.class);
                intent.putExtra("userGroupID", userGroupID);
                intent.putExtra("xmmc",userInfo.getDepartName());
                intent.putExtra("dengji", "SG");
                intent.putExtra("userRole", userInfo.getUserRole());
                startActivityForResult(intent, 1);
            }
        });
    }
}
