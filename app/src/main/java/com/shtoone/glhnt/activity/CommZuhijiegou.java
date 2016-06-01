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
    private COM_ZuzhiJiegou data;             //��֯�ṹʵ��
    private ServiceDao service;              //���ݷ�����
    private static Handler handler;          //���ݼ��ؾ��
    private String RootNode;                 //��ID
    HashMap map;

    private ImageButton btn_back;             //�������˰�ť
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

//        //���˰�ť����
//        btn_back = (ImageButton) this.findViewById(R.id.back);                               //�������˰�ť
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                returnType = 2;
//                finish();
//            }
//        });
    }
    private void initData(COM_ZuzhiJiegou list) {
        Log.d("��֯�ṹ���ȣ�", String.valueOf(list.getdata().size()));
        map = new HashMap();

        //ȫ������һ������ID��ͬʱ��Ǹ��ڵ�
        for(int i=0; i<list.getdata().size();i++){
            //��ȡ��ǰҪ����Ľڵ�
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

        //��ѭ��һ�Σ�����FileBean
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

//        mDatas.add(new FileBean(999, 0, "���ϳǼ���·�������ι�˾",""));
//        mDatas.add(new FileBean(102, 1, "������·",""));
//        mDatas.add(new FileBean(3, 999, "��ǭ��·",""));
//        mDatas.add(new FileBean(4, 999, "������·",""));
//        mDatas.add(new FileBean(5, 102, "һ���",""));
//        mDatas.add(new FileBean(6, 102, "�����",""));
//        mDatas.add(new FileBean(7, 102, "�����",""));
//
//        mDatas.add(new FileBean(8, 5, "1�Ű��վ",""));
//        mDatas.add(new FileBean(9, 5, "2�Ű��վ",""));

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
                    Toast.makeText(getApplicationContext(), "��ȡ����ʧ�ܣ�", Toast.LENGTH_SHORT).show();
                    Log.d("ʧ�������", "----------------------------------------");
                }
            }
        };
    }

    private void startGetDataThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //***** ���ڳ��ڻ� BEGIN *****
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                    Calendar rld = Calendar.getInstance();
                    String endTime = sdf.format(rld.getTime());
                    //***** ���ڳ��ڻ� END *****
                    Log.d("ף���ԣ�",endTime + " " + type + " " + userGroupID + " " + userRole );
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
