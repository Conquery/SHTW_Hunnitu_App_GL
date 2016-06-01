package com.shtoone.glhnt.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/22.
 */
public class SYS_TongJiFengXi_Item_Entity  implements Serializable {

    private static final long serialVersionUID = 2058003515913720031L;
    private String biaoduan;
    private String cishu;
    private String hege;
    private String youxiao;
    private String buhege;
    private String hegelv;

    public String getBiaoduan() {
        return biaoduan;
    }

    public void setBiaoduan(String biaoduan) {
        this.biaoduan = biaoduan;
    }

    public String getCishu() {
        return cishu;
    }

    public void setCishu(String cishu) {
        this.cishu = cishu;
    }

    public String getHege() {
        return hege;
    }

    public void setHege(String hege) {
        this.hege = hege;
    }

    public String getYouxiao() {
        return youxiao;
    }

    public void setYouxiao(String youxiao) {
        this.youxiao = youxiao;
    }

    public String getBuhege() {
        return buhege;
    }

    public void setBuhege(String buhege) {
        this.buhege = buhege;
    }

    public String getHegelv() {
        return hegelv;
    }

    public void setHegelv(String hegelv) {
        this.hegelv = hegelv;
    }
}
