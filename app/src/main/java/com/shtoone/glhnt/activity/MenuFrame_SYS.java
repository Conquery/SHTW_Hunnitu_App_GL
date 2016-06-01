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

    static DataPropertiy dp;                // ��д����data.properties����

    private Menu_SYS data_list;           //������������˵�����
    private ServiceDao apiDao;            //API������
    private static Handler handler;       //ȫ��֪ͨ���
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
        //�������ݻ�����
        try {
            apiDao = new ServiceDao();
            dp = new DataPropertiy(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        findView();
        setLayoutParams();
        setOnClickListener();
        createHandler();       //���ݼ�����Ϣ�����¼�
        startLoadListThread(); //���������б����������߳�
        return view;
    }

    @Override
    public void onActivityResult(int arg0, int arg1, Intent arg2) {
        try {
            super.onActivityResult(arg0, arg1, arg2);
            selectedUserGroupId = arg2.getStringExtra("selectedUserGroupId");
            showToast(selectedUserGroupId);
        } catch (Exception ex) {
            showToast("�쳣");
        }
//        if (menu_sys != null) {
//            menu_sys.biaoduanID = biaoduanID;
//        }
//        if (menu_bhz != null) {
//            menu_bhz.biaoduanID = biaoduanID;
//        }
    }

    /*********************************************************
     * ���ҽ����Կؼ�
     ********************************************************/
    private void findView() {
        //tv_zuzhijiegou = (TextView) view.findViewById(R.id.zuzhijiegou);                     //��֯�ṹ���
        ((TextView) view.findViewById(R.id.project_title)).setText(userInfo.getDepartName() + " > " + getResources().getString(R.string.project_sys));
        daichuzhiweituo = (TextView) view.findViewById(R.id.main_tv_daichuzhi);
        daichuzhibuhege = (TextView) view.findViewById(R.id.main_tv_daichuzhibuhege);
        hunNiTu = (LinearLayout) view.findViewById(R.id.sys_hunnituqiangdu);
        gangLaLi = (LinearLayout) view.findViewById(R.id.sys_gangjinglali);
        gangHanJieTou = (LinearLayout) view.findViewById(R.id.sys_ganghanjietou);
        gangJiJieTou = (LinearLayout) view.findViewById(R.id.sys_gangjinjixiejietou);
        zongHeTongJi = (RelativeLayout) view.findViewById(R.id.sys_tongjifengxi);
        buHeGeChuLi = (RelativeLayout) view.findViewById(R.id.sys_buhegechuli);

        tv_daichuzhiweituo = (TextView) view.findViewById(R.id.main_tv_daichuzhi);         //����������ί��
        tv_buhegeshiyan = (TextView) view.findViewById(R.id.main_tv_daichuzhibuhege);      //�������ϸ�����
        tv_hntqd_leiji = (TextView) view.findViewById(R.id.txtSysMenuhunnituqiangduCNT);   //������ǿ�������ۼ�
        tv_gjll_leiji = (TextView) view.findViewById(R.id.txtSysMenugangjinglaliCNT);      //�ֽ����������ۼ�
        tv_ghjt_leiji = (TextView) view.findViewById(R.id.txtSysgangjinhanjiejietouCNT);   //�ֽ��ͷ�����ۼ�
        tv_gjjt_leiji = (TextView) view.findViewById(R.id.txtSysjixiejietouCNT);            //�ֽ����ӽ�ͷ�����ۼ�

        //�����Ǹ�ģ��Ȩ��չʾ����
        tv_hntqd_auth = (TextView) view.findViewById(R.id.menuhunnituqiangduNoAuth);       //������ǿ������˵�ģ��
        tv_gjll_auth = (TextView) view.findViewById(R.id.menugangjinglaliNoAuth);          //�ֽ���������˵�Ȩ��
        tv_ghjt_auth = (TextView) view.findViewById(R.id.menuGangHanJieTouNoAuth);         //�ֽ��ͷ����˵�Ȩ��
        tv_gjjt_auth = (TextView) view.findViewById(R.id.menuGangJinJiXieNoAuth);          //�ֽ����ӽ�ͷ����˵�Ȩ��
        tv_tj_auth = (TextView) view.findViewById(R.id.sys_tongji_auth);                     //�ۺ�ͳ�Ʒ����˵�Ȩ��
        tv_buhege_auth = (TextView) view.findViewById(R.id.sys_buhege_auth);                //���ϸ��ò˵�Ȩ��
    }

    /*********************************************************
     * ��ʾ��̬���ص�����
     ********************************************************/
    public void showView(Menu_SYS data) {
        tv_daichuzhiweituo.setText(data.getData().getWaitWTcount());          //����������ί��
        tv_buhegeshiyan.setText(data.getData().getWaitRealcount());            //�������ϸ�����
        tv_hntqd_leiji.setText(data.getData().getHntkangyacount());            //������ǿ�������ۼ�


        tv_ghjt_leiji.setText(data.getData().getYlnpSQL());   //ѹ�������ϸ���
        tv_gjjt_leiji.setText(data.getData().getWnnpSQL());  //���ܻ����ϸ���

        int wanNengJiTestCount = Integer.parseInt(data.getData().getGangjincount()) + Integer.parseInt(data.getData().getGangjinhanjiejietoucount()) + Integer.parseInt(data.getData().getGangjinlianjiejietoucount());

        tv_gjll_leiji.setText(wanNengJiTestCount + "");

//        //�������
//        for(int i=0; i< data.getData().size(); i++) {
//            SYS_Item.DataEntity item = data.getData().get(i);
//            if(item.getTesttype() == "100014")
//                tv_hntqd_leiji.setText(item.getTestCount());       //������ǿ�������ۼ�
//            else if(item.getTesttype() == "100047")
//                tv_gjll_leiji.setText(item.getTestCount());        //�ֽ����������ۼ�
//            else if(item.getTesttype() == "100049")
//                tv_ghjt_leiji.setText(item.getTestCount());        //�ֽ��ͷ�����ۼ�
//            else
//                tv_gjjt_leiji.setText(item.getTestCount());        //�ֽ����ӽ�ͷ�����ۼ�
//        }

        //������Ȩ��չʾ����
        tv_hntqd_auth.setText("");     //������ǿ������˵�ģ��
        tv_gjll_auth.setText("");      //�ֽ���������˵�Ȩ��
        tv_ghjt_auth.setText("");      //�ֽ��ͷ����˵�Ȩ��
        tv_gjjt_auth.setText("");      //�ֽ����ӽ�ͷ����˵�Ȩ��
        tv_tj_auth.setText("");         //�ۺ�ͳ�Ʒ����˵�Ȩ��
        tv_buhege_auth.setText("");    //���ϸ��ò˵�Ȩ��
    }

    /************************************************************
     * 3.������ť�ļ����¼�
     ************************************************************/
    private void setOnClickListener() {
//        //��֯�ṹ���
//        tv_zuzhijiegou.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), CommZuhijiegou.class);
//                intent.putExtra("userGroupID", userGroupID);
//                startActivityForResult(intent, 1);
//            }
//        });

        //�������֣�������ί������
        daichuzhiweituo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//				Intent intent = new Intent(getActivity(), OT_SYSZuZhiJieGouDH_Activity.class);
//				startActivity(intent);
            }
        });
        //�������֣������ò��ϸ�����
        daichuzhibuhege.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//				Intent intent = new Intent(getActivity(), OT_BHZZuZhiJieGouDH_Activity.class);
//				startActivity(intent);
            }
        });
        //������ǿ������
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
        //���ܻ�����
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
        //ѹ�������ϸ�
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
        //���ܻ����ϸ�
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

        //�����ۺ�ͳ�Ʒ���
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

        //���ϸ���
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
            tv_buhege_auth.setText("��Ȩ��");
        }
    }

    /************************************************************
     * ͨ�����´��붯̬���ð�ť�߶�
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
     * 3.1���������б����������߳�
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
     * 4.����������������ݼ��غ����Ϣ
     ***********************************************************/
    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //dialog.dismiss();
                switch (msg.what) {
                    case 0:
                        showToast("��������ʧ��");
                        break;
                    case 1:
                        showView(data_list);
                        break;
                }
            }
        };
    }

    /************************************************************
     * 4.1������ʱ����ʾ
     ***********************************************************/
    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

    }
}
