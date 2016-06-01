package com.shtoone.glhnt.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.adapter.SYS_LingdaoAdapter;
import com.shtoone.glhnt.entity.MainInfo;
import com.shtoone.glhnt.entity.SYS_Lingdao;
import com.shtoone.glhnt.entity.SYS_Lingdao_ly;
import com.shtoone.glhnt.entity.UserInfo;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.util.CommFunctions;
import com.shtoone.glhnt.util.DatePicker;
import com.shtoone.glhnt.adapter.SYS_LingdaoAdapter_ly;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SYS_LingdaoFragment extends Fragment {

    private String startTime = "2015-09-01 00:00:00", endTime = "2015-09-30 00:00:00";
    private EditText et1, et2;
    private TextView btn_search, tv_zuzhijiegou;
    private ListView listView;
    private ServiceDao service;
    private Handler handler;
    private SYS_Lingdao_ly items;
    private ProgressDialog mypDialog;

    public String userGroupID;
    public UserInfo userInfo;
    public MainInfo mainInfo;

    private String selectedGroupId = "";
    private String selectedGroupName = "";
    private ImageView weather_img;

    private View view;
    private PopupWindow pop = null;
    private LayoutInflater inflater;
    private ViewGroup container;

    private String clickGroupId = "";
    private String clickGroupName = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), android.R.style.Theme_DeviceDefault_NoActionBar);
        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_sys_lingdao, container, false);
        this.inflater = inflater;
        this.container = container;
        //***** 日期初期化 BEGIN *****
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar rld = Calendar.getInstance();
        endTime = sdf.format(rld.getTime());
        rld.add(Calendar.MONTH, -3);
        startTime = sdf.format(rld.getTime());
        //***** 日期初期化 END *****

        initPopMenu2();
        selectedGroupId = userGroupID;
        selectedGroupName = userInfo.getDepartName();
        service = new ServiceDao();
        createHandler();
        setProgressDialog(); //创建加载特效
        findView();
        setListener();
        startGetDataThread();
        return view;
    }

    @SuppressLint("HandlerLeak")
    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mypDialog.dismiss();
                if (msg.what == 0) {
                    Toast.makeText(getActivity(), "暂无数据！", Toast.LENGTH_SHORT).show();
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
                    items = service.getSysLingdaoData_ly(selectedGroupId, startTime, endTime);

                    Thread.sleep(500);
                } catch (Exception e) {
                }
                if (items != null) {
                    //通过items是否为空，返回1，来判断是否有数据
                    handler.sendEmptyMessage(1);
                } else {
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    private void findView() {
        weather_img = (ImageView) view.findViewById(R.id.weather_img);
        if (!"".equals(selectedGroupName))
            ((TextView) view.findViewById(R.id.project_title)).setText(selectedGroupName + " > 试验室管理");
        else
            ((TextView) view.findViewById(R.id.project_title)).setText(userInfo.getXmmc() + " > 试验室管理");
        listView = (ListView) view.findViewById(R.id.sysdh_listview);
        btn_search = (TextView) view.findViewById(R.id.sysdh_chaxun_btn);
        et1 = (EditText) view.findViewById(R.id.sysdh_first_choice);
        et2 = (EditText) view.findViewById(R.id.sysdh_second_choice);
        tv_zuzhijiegou = (TextView) view.findViewById(R.id.zuzhijiegou);                     //组织结构面板
    }

    protected void showView() {
        listView.setAdapter(new SYS_LingdaoAdapter_ly(getActivity(), R.layout.sys_daohang_item_ly, items, selectedGroupId, selectedGroupName));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickGroupId = items.getData().get(position).get(0).getUserGroupId();
                clickGroupName = items.getData().get(position).get(0).getDepartName();
                pop.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });
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
                        getActivity(), startTime);
                dateTimePicKDialog.dateTimePicKDialog(et1);
            }
        });
        et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker dateTimePicKDialog = new DatePicker(
                        getActivity(), endTime);
                dateTimePicKDialog.dateTimePicKDialog(et2);
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(null);
                mypDialog.show();
                startTime = et1.getText().toString();
                endTime = et2.getText().toString();
                Log.d("开始日期:", startTime);
                if (Integer.valueOf(CommFunctions.ChangeTimeToLong(startTime)) > Integer.valueOf(CommFunctions.ChangeTimeToLong(endTime))) {
                    Toast.makeText(getActivity(), "开始日期不能大于结束日期！", Toast.LENGTH_SHORT).show();
                    mypDialog.dismiss();
                    return;
                }
                startGetDataThread();
            }
        });

        //组织结构面板weather_img  tv_zuzhijiegou
        weather_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommZuhijiegou.class);
                intent.putExtra("userGroupID", userInfo.getDepartId());
                intent.putExtra("xmmc", userInfo.getDepartName());
                intent.putExtra("userRole", userInfo.getUserRole());
                intent.putExtra("type", "3");
                startActivityForResult(intent, 1);
            }
        });
    }

    private void setProgressDialog() {
        mypDialog = new ProgressDialog(getActivity());
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
            if (!"".equals(selectedGroupName) && selectedGroupName != null)
                ((TextView) view.findViewById(R.id.project_title)).setText(selectedGroupName + " > 试验室管理");
            setProgressDialog();
            startGetDataThread();
        } catch (Exception ex) {
            Log.e("异常", ex.getStackTrace().toString());
        }
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void initPopMenu2() {
        pop = new PopupWindow(getActivity());

        View view_pop = inflater.inflate(R.layout.popwindowcycle2, container, false);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(-00000);
        pop.setBackgroundDrawable(dw);
        pop.update();
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view_pop);

//        pop.setTouchInterceptor(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                // TODO Auto-generated method stub
//
//                if (event.getY() < 300) {  //这里处理，当点击gridview以外区域的时候，菜单关闭
//                    if (pop.isShowing())
//                        pop.dismiss();
//                }
//                Log.d("Demo", "popupWindow::onTouch >>> view: "
//                        + v + ", event: " + event);
//                return false;
//            }
//        });

        ImageView img1 = (ImageView) view_pop.findViewById(R.id.item_image1);
        ImageView img2 = (ImageView) view_pop.findViewById(R.id.item_image2);
//        ImageView img3 = (ImageView) view_pop.findViewById(R.id.item_image3);
        ImageView img4 = (ImageView) view_pop.findViewById(R.id.item_image4);
        ImageView img5 = (ImageView) view_pop.findViewById(R.id.item_image5);
        ImageView img6 = (ImageView) view_pop.findViewById(R.id.item_image6);

        //压力机实验
        img1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SYS_HuNiTuQiangDu_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userGroupID", clickGroupId);
                bundle.putString("xmmc", clickGroupName);
                bundle.putString("dengji", "GL");
                bundle.putString("userRole", userInfo.getUserRole());
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                pop.dismiss();
            }
        });

        //万能机实验
        img2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SYS_Wannengji_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userGroupID", clickGroupId);
                bundle.putString("xmmc", clickGroupName);
                bundle.putString("dengji", "GL");
                bundle.putString("userRole", userInfo.getUserRole());
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                pop.dismiss();
            }
        });

/*        //钢筋焊接头
        img3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SYS_GangJingHJieTou_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userGroupID", clickGroupId);
                bundle.putString("xmmc", clickGroupName);
                bundle.putString("dengji", "GL");
                bundle.putString("userRole", userInfo.getUserRole());
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                pop.dismiss();
            }
        });*/

        //万能机不合格
        img4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SYS_Wannengji_BuHeGeSYChuZhi_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userGroupID", clickGroupId);
                bundle.putString("xmmc", clickGroupName);
                bundle.putString("dengji", "GL");
                bundle.putString("role", userInfo.getQuanxian().isSyschaobiaoReal() ? "CZ" : "NO");
                bundle.putString("userRole", userInfo.getUserRole());
                intent.putExtra("userFullName", userInfo.getUserFullName());
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                pop.dismiss();
            }
        });

        //压力机不合格
        img5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SYS_Yaliji_BuHeGeSYChuZhi_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userGroupID", clickGroupId);
                bundle.putString("xmmc", clickGroupName);
                bundle.putString("dengji", "GL");
                bundle.putString("role", userInfo.getQuanxian().isSyschaobiaoReal() ? "CZ" : "NO");
                bundle.putString("userRole", userInfo.getUserRole());
                intent.putExtra("userFullName", userInfo.getUserFullName());
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                pop.dismiss();
            }
        });

        //综合统计
        img6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SysTongjfenxiActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userGroupID", clickGroupId);
                bundle.putString("xmmc", clickGroupName);
                bundle.putString("dengji", "GL");
                bundle.putString("userRole", userInfo.getUserRole());
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                pop.dismiss();
            }
        });
    }

}
