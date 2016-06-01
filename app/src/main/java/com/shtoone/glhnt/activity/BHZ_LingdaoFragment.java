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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.adapter.BHZ_LingdaoAdapter;
import com.shtoone.glhnt.entity.BHZ_Lingdao;
import com.shtoone.glhnt.entity.MainInfo;
import com.shtoone.glhnt.entity.UserInfo;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.util.CommFunctions;
import com.shtoone.glhnt.util.DatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class BHZ_LingdaoFragment extends Fragment {

    private String startTime = "2015-09-01 00:00:00",endTime = "2015-09-30 00:00:00";
    private EditText et1,et2;
    private TextView btn_search,tv_zuzhijiegou;
    private ListView listView;
    private ServiceDao service;
    private Handler handler;
    private List<BHZ_Lingdao> items;
    private ProgressDialog mypDialog;

    public String userGroupID;
    public UserInfo userInfo;
    public MainInfo mainInfo;

    private String selectedGroupId = "";
    private String selectedGroupName="";
    private String clickGroupId = "";
    private String clickGroupName = "";

    private View view;
    private ImageView weather_img;

    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    private LayoutInflater inflater;
    private ViewGroup container;
    GridView menuGrid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), android.R.style.Theme_DeviceDefault_NoActionBar);
        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_bhz_lingdao, container, false);

        this.inflater = inflater;
        this.container = container;
        selectedGroupId = userGroupID;
        selectedGroupName = userInfo.getDepartName();
        if(!"".equals(selectedGroupName))
            ((TextView) view.findViewById(R.id.project_title)).setText(selectedGroupName + " > 拌和站管理");
        else
            ((TextView) view.findViewById(R.id.project_title)).setText(userInfo.getXmmc() + " > 拌和站管理");

        //***** 日期初期化 BEGIN *****
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar rld = Calendar.getInstance();
        endTime = sdf.format(rld.getTime());
        rld.add(Calendar.MONTH, -3);
        startTime = sdf.format(rld.getTime());
        //***** 日期初期化 END *****
        initPopMenu2();    //弹出菜单
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
        weather_img = (ImageView) view.findViewById(R.id.weather_img);
        listView = (ListView) view.findViewById(R.id.bhzdh_listview);
        btn_search = (TextView) view.findViewById(R.id.bhz_ld_chaxun_btn);
        et1 = (EditText) view.findViewById(R.id.bhz_ld_first_choice);
        et2 = (EditText) view.findViewById(R.id.bhz_ld_second_choice);
        tv_zuzhijiegou = (TextView) view.findViewById(R.id.zuzhijiegou);                     //组织结构面板
    }

    protected void showView() {
        listView.setAdapter(new BHZ_LingdaoAdapter(getActivity(), R.layout.bhz_daohang_item, items, selectedGroupId, selectedGroupName));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickGroupId = items.get(position).getDepartId();
                clickGroupName = items.get(position).getBiaoduan();
                Log.d("clickGroupId:",clickGroupId);
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
                if(Integer.valueOf(CommFunctions.ChangeTimeToLong(startTime)) > Integer.valueOf(CommFunctions.ChangeTimeToLong(endTime)) ){
                    Toast.makeText(getActivity(), "开始日期不能大于结束日期！", Toast.LENGTH_SHORT).show();
                    mypDialog.dismiss();
                    return;
                }
                startGetDataThread();
            }
        });

        //组织结构面板 weather_img tv_zuzhijiegou
        weather_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommZuhijiegou.class);
                intent.putExtra("userGroupID", userInfo.getDepartId());
                intent.putExtra("xmmc", userInfo.getDepartName());
                intent.putExtra("type", "1");
                intent.putExtra("userRole", userInfo.getUserRole());
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
            if(!"".equals(selectedGroupName) && selectedGroupName != null)
                ((TextView) view.findViewById(R.id.project_title)).setText(selectedGroupName + " > 拌和站管理");
            //showToast(selectedUserGroupId);
            setProgressDialog();
            startGetDataThread();
        }catch(Exception ex) {
            //showToast("异常");
        }
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void initPopMenu(){
        pop = new PopupWindow(getActivity());

        View view_pop = inflater.inflate(R.layout.popupwindow, container, false);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(-00000);
        pop.setBackgroundDrawable(dw);
        pop.update();
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view_pop);

        pop.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                if (event.getY() < 240) {  //这里处理，当点击gridview以外区域的时候，菜单关闭
                    if (pop.isShowing())
                        pop.dismiss();
                }
                Log.d("Demo", "popupWindow::onTouch >>> view: "
                        + v + ", event: " + event);
                return false;
            }
        });

        /** 菜单图片 **/
        int[] menu_image_array = { R.drawable.bhz_sc,
                R.drawable.bhz_cb, R.drawable.bhz_tj,
                R.drawable.bhz_cl };
        /** 菜单文字 **/
        String[] menu_name_array = { "生产查询", "超标处置", "综合统分", "材料用量"};

        menuGrid = (GridView) view_pop.findViewById(R.id.gridview);
        menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
    }

    /**
     * 构造菜单Adapter
     *
     * @param menuNameArray
     *            名称
     * @param imageResourceArray
     *            图片
     * @return SimpleAdapter
     */
    private SimpleAdapter getMenuAdapter(String[] menuNameArray,
                                         int[] imageResourceArray) {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < menuNameArray.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", imageResourceArray[i]);
            map.put("itemText", menuNameArray[i]);
            data.add(map);
        }
        SimpleAdapter simperAdapter = new SimpleAdapter(getActivity(), data,
                R.layout.item_menu, new String[] { "itemImage", "itemText" },
                new int[] { R.id.item_image, R.id.item_text });
        return simperAdapter;
    }

    private void initPopMenu2(){
        pop = new PopupWindow(getActivity());

        View view_pop = inflater.inflate(R.layout.popwindowcycle1, container, false);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(-00000);
        pop.setBackgroundDrawable(dw);
        pop.update();
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view_pop);

        ImageView img1 = (ImageView) view_pop.findViewById(R.id.item_image1);
        ImageView img2 = (ImageView) view_pop.findViewById(R.id.item_image2);
        ImageView img3 = (ImageView) view_pop.findViewById(R.id.item_image3);
        ImageView img4 = (ImageView) view_pop.findViewById(R.id.item_image4);

        //生产数据查询
        img1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), BHZ_SCshujuchaxun_Activity.class);
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

        //超标处置
        img2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BHZ_ChaobiaoChuZHi_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userGroupID", clickGroupId);
                bundle.putString("xmmc", clickGroupName);
                bundle.putString("dengji", "GL");
                bundle.putString("userRole", userInfo.getUserRole());
                intent.putExtra("role", userInfo.getQuanxian().isHntchaobiaoSp() ? "SP" : (userInfo.getQuanxian().isHntchaobiaoReal() ? "CZ" : "NO"));
                intent.putExtra("userFullName",userInfo.getUserFullName());
                intent.putExtra("chuzhi",userInfo.getQuanxian().isHntchaobiaoReal());
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                pop.dismiss();
            }
        });

        //综合统分
        img3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BHZ_Tongjifenxi2Activity.class);
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

        //材料用量
        img4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BHZ_CailiaoyongliangActivity.class);
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
