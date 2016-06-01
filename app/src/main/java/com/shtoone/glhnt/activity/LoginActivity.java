package com.shtoone.glhnt.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.entity.UserInfo;
import com.shtoone.glhnt.serviceDao.ServiceDao;
import com.shtoone.glhnt.serviceDao.UserConfig;
import com.shtoone.glhnt.util.DataPropertiy;
import com.shtoone.glhnt.util.NetWorkingUtil;

import java.io.IOException;

public class LoginActivity extends Activity {

    private ProgressDialog mypDialog;
    private Button btn_login;
    private EditText et_username, et_password;
    private ServiceDao mSerivce;
    private static Handler handler;
    private UserInfo userInfo;
    static DataPropertiy dp;                // 读写缓存data.properties的类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSerivce = new ServiceDao();
        String auto_login = "0";
        //创建数据缓存类
        try {
            dp = new DataPropertiy(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setProgressDialog();
        createHandler();
        findView();
        setLayoutParams();
        setListener();
        //是否自动登录
        autopLginOn();
    }

    private void setLayoutParams() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        et_username.setLayoutParams(new TableRow.LayoutParams((int) (dm.widthPixels * 0.65), 100));
        et_password.setLayoutParams(new TableRow.LayoutParams((int) (dm.widthPixels * 0.65), 100));
    }

    protected void createHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mypDialog.dismiss();
                if(NetWorkingUtil.isConn(LoginActivity.this)) {
                    if (msg.what == 0) {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                    }
                    if (msg.what == 1) {
                        UserConfig.UserName = et_username.getText().toString().trim();
                        UserConfig.PassWord = et_password.getText().toString().trim();
                        dp.put("USER_NAME", UserConfig.UserName);
                        dp.put("PASSWORD",UserConfig.PassWord);
                        dp.put("DEPART_NAME",userInfo.getDepartName());
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("userInfo", userInfo);
                        String[] tags = new String[userInfo.getSMSGroup().size()];
                        for (int i = 0; i < userInfo.getSMSGroup().size(); i++) {
                            tags[i] = userInfo.getSMSGroup().get(i).getName();
                        }
                        bundle.putStringArray("tag",tags);
                        dp.put("DEPART_ID", userInfo.getDepartId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "暂无网络连接！", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void setProgressDialog() {
        mypDialog = new ProgressDialog(LoginActivity.this);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mypDialog.setMessage("登录中，请稍后...");
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(true);
        mypDialog.setCanceledOnTouchOutside(false);
    }

    private void setListener() {
        et_username.setText(dp.get("USER_NAME"));
        et_password.setText(dp.get("PASSWORD"));
        btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mypDialog.show();
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (validate(username, password)) {
                    if (NetWorkingUtil.isConn(LoginActivity.this))
                        startCheckThread(username, password);
                    else
                        Toast.makeText(LoginActivity.this, "暂无网络连接！", Toast.LENGTH_SHORT).show();

                } else {
                    mypDialog.dismiss();
                }
            }
        });
    }

    protected void startCheckThread(final String username, final String password) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    userInfo = mSerivce.loginCheck(username, password);
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                if (userInfo != null) {
                    handler.sendEmptyMessage(1);
                } else {
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    protected boolean validate(String username, String password) {
        if ("".equals(username)) {
            Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ("".equals(password)) {
            Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void findView() {
        btn_login = (Button) this.findViewById(R.id.bnLogin);
        et_username = (EditText) this.findViewById(R.id.userEditText);
        et_password = (EditText) this.findViewById(R.id.pwdEditText);
    }

    private void autopLginOn(){
        if (!"".equals(et_username.getText())) {
            mypDialog.show();
            String username = et_username.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            if (validate(username, password)) {
                startCheckThread(username, password);
            } else {
                mypDialog.dismiss();
            }
        }
    }
}
