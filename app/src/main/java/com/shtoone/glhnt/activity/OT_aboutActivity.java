package com.shtoone.glhnt.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shtoone.glhnt.R;
import com.shtoone.glhnt.util.UpdateManager;

// 2015-09-11 ADD BY 郑兴泉 BEGIN
public class OT_aboutActivity extends Activity {

    private String userGroupID = "";
    private ImageButton btn_back;
    private int returnType = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_ot_about);
        this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.theme_hntqd);
        //setTitle(R.string.app_name);    // 设置标题
        ((TextView) this.findViewById(R.id.project_title)).setText(getResources().getString(R.string.app_name) + " > 关于系统&升级更新");
        userGroupID = getIntent().getStringExtra("userGroupID");
        TextView tv_banben= (TextView) findViewById(R.id.txtbanben);
        tv_banben.setText(getVersion());
        setLinter();
    }

    private void setLinter() {
        findViewById(R.id.but_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateManager manager = new UpdateManager(OT_aboutActivity.this);
                // 检查软件更新
                manager.checkUpdate();
            }
        });
        btn_back = (ImageButton) this.findViewById(R.id.back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnType = 2;
                finish();
            }
        });
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("userGroupID", userGroupID);
        setResult(returnType, intent);
        super.finish();
    }

    // 获取 版本号
    public String getVersion(){
        try {
            PackageManager packageManager = getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}

//2015-09-11 ADD BY 郑兴泉 END
