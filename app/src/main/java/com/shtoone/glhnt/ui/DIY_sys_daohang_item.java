package com.shtoone.glhnt.ui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shtoone.glhnt.R;

/**
 * Created by Administrator on 2016/5/21 0021.
 */
public class DIY_sys_daohang_item extends LinearLayout {

    private TextView tv_sys1;
    private TextView tv_sysdh_item1_1;
    private TextView tv_sysdh_item1_2;
    private TextView tv_sysdh_item1_3;
    private TextView tv_sysdh_item1_4;

    public DIY_sys_daohang_item(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.diy_sys_daohang_item, this);
        tv_sys1 = (TextView) findViewById(R.id.tv_sys1);
        tv_sysdh_item1_1 = (TextView) findViewById(R.id.tv_sysdh_item1_1);
        tv_sysdh_item1_2 = (TextView) findViewById(R.id.tv_sysdh_item1_2);
        tv_sysdh_item1_3 = (TextView) findViewById(R.id.tv_sysdh_item1_3);
        tv_sysdh_item1_4 = (TextView) findViewById(R.id.tv_sysdh_item1_4);
    }

    public DIY_sys_daohang_item setSysTestType(String testType) {
        tv_sys1.setText(testType);
        return this;
    }

    public DIY_sys_daohang_item setItem1(String item1) {
        tv_sysdh_item1_1.setText(item1);
        return this;
    }

    public DIY_sys_daohang_item setItem2(String item2) {
        tv_sysdh_item1_2.setText(item2);
        return this;
    }

    public DIY_sys_daohang_item setItem3(String item3) {
        tv_sysdh_item1_3.setText(item3);
        return this;
    }

    public DIY_sys_daohang_item setItem4(String item4) {
        tv_sysdh_item1_4.setText(item4);
        return this;
    }

}
