package com.shtoone.glhnt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.COM_ZuzhiJiegou;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.treenode.FileBean;
import com.shtoone.glhnt.treenode.SimpleTreeAdapter;
import com.shtoone.glhnt.treenode.TreeListViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CommZuhijiegou extends Activity {

    private List<FileBean> mDatas = new ArrayList<FileBean>();
    private ListView mTree;
    private int biaoduanID;
    private int returnType = -1;
    @SuppressWarnings("rawtypes")
    private TreeListViewAdapter mAdapter;
    private COM_ZuzhiJiegou data;             //组织结构实体
    private ServiceDao service;              //数据服务类
    private static Handler handler;          //数据加载句柄
    private String RootNode;                 //根ID
    HashMap map;

    private ImageButton btn_back;             //顶部回退按钮
    private String type;
    private String userGroupID = "";
    private String userRole = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_comm_zuhijiegou);
        this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.theme_hntqd);
        biaoduanID = getIntent().getIntExtra("biaoduanID", 0);
        type = getIntent().getStringExtra("type");
        userGroupID = getIntent().getStringExtra("userGroupID");
        userRole = getIntent().getStringExtra("userRole");
        service = new ServiceDao();
        mTree = (ListView) findViewById(R.id.id_tree);
        btn_back = (ImageButton) this.findViewById(R.id.back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createHandler();
        startGetDataThread();

//        //后退按钮按下
//        btn_back = (ImageButton) this.findViewById(R.id.back);                               //顶部回退按钮
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                returnType = 2;
//                finish();
//            }
//        });
    }
    private void initData(COM_ZuzhiJiegou list) {
        Log.d("组织结构长度：", String.valueOf(list.getdata().size()));
        map = new HashMap();

        //全部分配一个数字ID，同时标记根节点
        for(int i=0; i<list.getdata().size();i++){
            //获取当前要处理的节点
            COM_ZuzhiJiegou.DataEntity tmpNode = list.getdata().get(i);
            map.put(tmpNode.getID(),i+1);
            if(tmpNode.getParentdepartid() == "") {
                RootNode = tmpNode.getID();
            }
//            if(tmpNode.getID() == userGroupID) {
//                RootNode = tmpNode.getID();
//            }
        }

        FileBean tmpFeileBean;

        //再循环一次，创建FileBean
        for(int i=0; i<list.getdata().size();i++){
            COM_ZuzhiJiegou.DataEntity tmpNode = list.getdata().get(i);
            int _id = (int)map.get(tmpNode.getID());
            if(tmpNode.getParentdepartid()==""){
                tmpFeileBean = new FileBean(_id, 0, tmpNode.getDepartname(),tmpNode.getID());
            }else{
                int _pid = (int)map.get(tmpNode.getParentdepartid());
                tmpFeileBean = new FileBean(_id, _pid, tmpNode.getDepartname(),tmpNode.getID());
            }
           mDatas.add(tmpFeileBean);
//            if(tmpNode.getID()==userGroupID){
//                tmpFeileBean = new FileBean(_id, 0, tmpNode.getDepartname(),tmpNode.getID());
//                mDatas.add(tmpFeileBean);
//            }else{
//                if(map.get(tmpNode.getParentdepartid()) != null) {
//                    int _pid = (int) map.get(tmpNode.getParentdepartid());
//                    tmpFeileBean = new FileBean(_id, _pid, tmpNode.getDepartname(), tmpNode.getID());
//                    mDatas.add(tmpFeileBean);
//                }
//            }
        }

//        mDatas.add(new FileBean(999, 0, "川南城际铁路有限责任公司",""));
//        mDatas.add(new FileBean(102, 1, "川南铁路",""));
//        mDatas.add(new FileBean(3, 999, "渝黔铁路",""));
//        mDatas.add(new FileBean(4, 999, "合南铁路",""));
//        mDatas.add(new FileBean(5, 102, "一标段",""));
//        mDatas.add(new FileBean(6, 102, "二标段",""));
//        mDatas.add(new FileBean(7, 102, "三标段",""));
//
//        mDatas.add(new FileBean(8, 5, "1号拌合站",""));
//        mDatas.add(new FileBean(9, 5, "2号拌合站",""));

        try {
            mAdapter = new SimpleTreeAdapter<FileBean>(biaoduanID , mTree, this, mDatas, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTree.setAdapter(mAdapter);
    }

    private void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    initData(data);
                } else {
                    Toast.makeText(getApplicationContext(), "获取数据失败！", Toast.LENGTH_SHORT).show();
                    Log.d("失败在这里：", "----------------------------------------");
                }
            }
        };
    }

    private void startGetDataThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //***** 日期初期化 BEGIN *****
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                    Calendar rld = Calendar.getInstance();
                    String endTime = sdf.format(rld.getTime());
                    //***** 日期初期化 END *****
                    Log.d("祝鹏辉：",endTime + " " + type + " " + userGroupID + " " + userRole );
                    data = service.getZuzhijiegou(endTime,type,userGroupID,userRole);
                } catch (Exception e) {
                }
                if (data != null) {
                    handler.sendEmptyMessage(1);
                } else {
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("biaoduanID", biaoduanID);
            setResult(2, intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comm_zuhijiegou, menu);
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
}
