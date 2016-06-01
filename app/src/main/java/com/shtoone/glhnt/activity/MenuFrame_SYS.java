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
import com.shtoone.glhnt.entity.Menu_SYS;
import com.shtoone.glhnt.entity.UserInfo;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.util.DataPropertiy;

import java.io.IOException;

public class MenuFrame_SYS extends Fragment implements OnClickListener {

    static DataPropertiy dp;                // 读写缓存data.properties的类

    private Menu_SYS data_list;           //本界面服务器端的数据
    private ServiceDao apiDao;            //API请求类
    private static Handler handler;       //全局通知句柄
    private String selectedUserGroupId = "";

    public String userGroupID;
    public UserInfo userInfo;
    public MainInfo mainInfo;

    private View view;
    private LinearLayout hunNiTu, gangLaLi, gangHanJieTou, gangJiJieTou;
    private RelativeLayout zongHeTongJi, buHeGeChuLi;
    private TextView daichuzhiweituo, daichuzhibuhege, tv_daichuzhiweituo, tv_buhegeshiyan, tv_hntqd_leiji, tv_gjll_leiji, tv_ghjt_leiji, tv_gjjt_leiji,
            tv_hntqd_auth, tv_gjll_auth, tv_ghjt_auth, tv_gjjt_auth, tv_tj_auth, tv_buhege_auth, tv_zuzhijiegou;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menuframe_sys, container, false);
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
        createHandler();       //数据加载消息监听事件
        startLoadListThread(); //开启加载列表数据请求线程
        return view;
    }

    @Override
    public void onActivityResult(int arg0, int arg1, Intent arg2) {
        try {
            super.onActivityResult(arg0, arg1, arg2);
            selectedUserGroupId = arg2.getStringExtra("selectedUserGroupId");
            showToast(selectedUserGroupId);
        } catch (Exception ex) {
            showToast("异常");
        }
//        if (menu_sys != null) {
//            menu_sys.biaoduanID = biaoduanID;
//        }
//        if (menu_bhz != null) {
//            menu_bhz.biaoduanID = biaoduanID;
//        }
    }

    /*********************************************************
     * 找找交互性控件
     ********************************************************/
    private void findView() {
        //tv_zuzhijiegou = (TextView) view.findViewById(R.id.zuzhijiegou);                     //组织结构面板
        ((TextView) view.findViewById(R.id.project_title)).setText(userInfo.getDepartName() + " > " + getResources().getString(R.string.project_sys));
        daichuzhiweituo = (TextView) view.findViewById(R.id.main_tv_daichuzhi);
        daichuzhibuhege = (TextView) view.findViewById(R.id.main_tv_daichuzhibuhege);
        hunNiTu = (LinearLayout) view.findViewById(R.id.sys_hunnituqiangdu);
        gangLaLi = (LinearLayout) view.findViewById(R.id.sys_gangjinglali);
        gangHanJieTou = (LinearLayout) view.findViewById(R.id.sys_ganghanjietou);
        gangJiJieTou = (LinearLayout) view.findViewById(R.id.sys_gangjinjixiejietou);
        zongHeTongJi = (RelativeLayout) view.findViewById(R.id.sys_tongjifengxi);
        buHeGeChuLi = (RelativeLayout) view.findViewById(R.id.sys_buhegechuli);

        tv_daichuzhiweituo = (TextView) view.findViewById(R.id.main_tv_daichuzhi);         //顶部待处置委托
        tv_buhegeshiyan = (TextView) view.findViewById(R.id.main_tv_daichuzhibuhege);      //顶部不合格试验
        tv_hntqd_leiji = (TextView) view.findViewById(R.id.txtSysMenuhunnituqiangduCNT);   //混凝土强度试验累计
        tv_gjll_leiji = (TextView) view.findViewById(R.id.txtSysMenugangjinglaliCNT);      //钢筋拉力试验累计
        tv_ghjt_leiji = (TextView) view.findViewById(R.id.txtSysgangjinhanjiejietouCNT);   //钢筋焊接头试验累计
        tv_gjjt_leiji = (TextView) view.findViewById(R.id.txtSysjixiejietouCNT);            //钢筋连接街头试验累计

        //以下是各模块权限展示文字
        tv_hntqd_auth = (TextView) view.findViewById(R.id.menuhunnituqiangduNoAuth);       //混凝土强度试验菜单模块
        tv_gjll_auth = (TextView) view.findViewById(R.id.menugangjinglaliNoAuth);          //钢筋拉力试验菜单权限
        tv_ghjt_auth = (TextView) view.findViewById(R.id.menuGangHanJieTouNoAuth);         //钢筋焊接头试验菜单权限
        tv_gjjt_auth = (TextView) view.findViewById(R.id.menuGangJinJiXieNoAuth);          //钢筋连接街头试验菜单权限
        tv_tj_auth = (TextView) view.findViewById(R.id.sys_tongji_auth);                     //综合统计分析菜单权限
        tv_buhege_auth = (TextView) view.findViewById(R.id.sys_buhege_auth);                //不合格处置菜单权限
    }

    /*********************************************************
     * 显示动态加载的数据
     ********************************************************/
    public void showView(Menu_SYS data) {
        tv_daichuzhiweituo.setText(data.getData().getWaitWTcount());          //顶部待处置委托
        tv_buhegeshiyan.setText(data.getData().getWaitRealcount());            //顶部不合格试验
        tv_hntqd_leiji.setText(data.getData().getHntkangyacount());            //混凝土强度试验累计


        tv_ghjt_leiji.setText(data.getData().getYlnpSQL());   //压力机不合格数
        tv_gjjt_leiji.setText(data.getData().getWnnpSQL());  //万能机不合格数

        int wanNengJiTestCount = Integer.parseInt(data.getData().getGangjincount()) + Integer.parseInt(data.getData().getGangjinhanjiejietoucount()) + Integer.parseInt(data.getData().getGangjinlianjiejietoucount());

        tv_gjll_leiji.setText(wanNengJiTestCount + "");

//        //填充数字
//        for(int i=0; i< data.getData().size(); i++) {
//            SYS_Item.DataEntity item = data.getData().get(i);
//            if(item.getTesttype() == "100014")
//                tv_hntqd_leiji.setText(item.getTestCount());       //混凝土强度试验累计
//            else if(item.getTesttype() == "100047")
//                tv_gjll_leiji.setText(item.getTestCount());        //钢筋拉力试验累计
//            else if(item.getTesttype() == "100049")
//                tv_ghjt_leiji.setText(item.getTestCount());        //钢筋焊接头试验累计
//            else
//                tv_gjjt_leiji.setText(item.getTestCount());        //钢筋连接街头试验累计
//        }

        //以下是权限展示文字
        tv_hntqd_auth.setText("");     //混凝土强度试验菜单模块
        tv_gjll_auth.setText("");      //钢筋拉力试验菜单权限
        tv_ghjt_auth.setText("");      //钢筋焊接头试验菜单权限
        tv_gjjt_auth.setText("");      //钢筋连接街头试验菜单权限
        tv_tj_auth.setText("");         //综合统计分析菜单权限
        tv_buhege_auth.setText("");    //不合格处置菜单权限
    }

    /************************************************************
     * 3.交互按钮的监听事件
     ************************************************************/
    private void setOnClickListener() {
//        //组织结构面板
//        tv_zuzhijiegou.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), CommZuhijiegou.class);
//                intent.putExtra("userGroupID", userGroupID);
//                startActivityForResult(intent, 1);
//            }
//        });

        //顶部数字：待处置委托试验
        daichuzhiweituo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//				Intent intent = new Intent(getActivity(), OT_SYSZuZhiJieGouDH_Activity.class);
//				startActivity(intent);
            }
        });
        //顶部数字：待处置不合格试验
        daichuzhibuhege.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//				Intent intent = new Intent(getActivity(), OT_BHZZuZhiJieGouDH_Activity.class);
//				startActivity(intent);
            }
        });
        //混凝土强度试验
        hunNiTu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SYS_HuNiTuQiangDu_Activity.class);
                intent.putExtra("userGroupID", userInfo.getDepartId());
                intent.putExtra("xmmc", userInfo.getXmmc());
                intent.putExtra("dengji", "SG");
                intent.putExtra("userRole", userInfo.getUserRole());
                startActivityForResult(intent, 2);
            }
        });
        //万能机试验
        gangLaLi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SYS_Wannengji_Activity.class);
                intent.putExtra("userGroupID", userGroupID);
                intent.putExtra("xmmc", userInfo.getDepartName());
                intent.putExtra("dengji", "SG");
                intent.putExtra("userRole", userInfo.getUserRole());
                startActivityForResult(intent, 2);
            }
        });
        //压力机不合格
        gangHanJieTou.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SYS_Yaliji_BuHeGeSYChuZhi_Activity.class);
                intent.putExtra("userGroupID", userGroupID);
                intent.putExtra("xmmc", userInfo.getDepartName());
                intent.putExtra("dengji", "SG");
                intent.putExtra("userRole", userInfo.getUserRole());
                startActivityForResult(intent, 2);
            }
        });
        //万能机不合格
        gangJiJieTou.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SYS_Wannengji_BuHeGeSYChuZhi_Activity.class);
                intent.putExtra("userGroupID", userGroupID);
                intent.putExtra("xmmc", userInfo.getDepartName());
                intent.putExtra("dengji", "SG");
                intent.putExtra("userRole", userInfo.getUserRole());
                startActivityForResult(intent, 2);
            }
        });

        //试验综合统计分析
        zongHeTongJi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SysTongjfenxiActivity.class);
                intent.putExtra("userGroupID", userGroupID);
                intent.putExtra("xmmc", userInfo.getDepartName());
                intent.putExtra("dengji", "SG");
                intent.putExtra("userRole", userInfo.getUserRole());
                startActivityForResult(intent, 2);
            }
        });

        //不合格处置
        if (userInfo.getQuanxian().isSyschaobiaoReal()) {
            buHeGeChuLi.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SYS_BuHeGeSYChuZhi_Activity.class);
                    intent.putExtra("userGroupID", userGroupID);
                    intent.putExtra("xmmc", userInfo.getDepartName());
                    intent.putExtra("userFullName", userInfo.getUserFullName());
                    intent.putExtra("dengji", "SG");
                    intent.putExtra("userRole", userInfo.getUserRole());
                    intent.putExtra("role", "CZ");
                    startActivityForResult(intent, 2);
                }
            });
        } else {
            tv_buhege_auth.setText("无权限");
        }
    }

    /************************************************************
     * 通过以下代码动态设置按钮高度
     ************************************************************/
    private void setLayoutParams() {
        DisplayMetrics dm = getResources().getDisplayMetrics();

        int menuWidth = (int) ((dm.widthPixels * 0.9 / 2));

        TableRow.LayoutParams pram = new TableRow.LayoutParams(menuWidth, menuWidth);
        pram.leftMargin = 4;

        hunNiTu.setLayoutParams(pram);
        gangLaLi.setLayoutParams(pram);
        gangHanJieTou.setLayoutParams(pram);
        gangJiJieTou.setLayoutParams(pram);
        zongHeTongJi.setLayoutParams(pram);
        buHeGeChuLi.setLayoutParams(pram);
    }

    /************************************************************
     * 3.1开启加载列表数据请求线程
     ***********************************************************/
    private void startLoadListThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    data_list = apiDao.getSysMenuData(userInfo.getDepartId());
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
                        showToast("数据请求失败");
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

    @Override
    public void onClick(View v) {

    }
}
