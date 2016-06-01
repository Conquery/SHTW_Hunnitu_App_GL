package com.shtoone.glhnt.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/26.
 */
public class BHZ_CaiLiaoYongLiang_Item implements Serializable {
    private static final long serialVersionUID = -8905596456645146809L;

    private String name;
    private String shiji;
    private String peibi;
    private String wuchazhi;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShiji() {
        return shiji;
    }

    public void setShiji(String shiji) {
        this.shiji = shiji;
    }

    public String getPeibi() {
        return peibi;
    }

    public void setPeibi(String peibi) {
        this.peibi = peibi;
    }

    public String getWuchazhi() {
        return wuchazhi;
    }

    public void setWuchazhi(String wuchazhi) {
        this.wuchazhi = wuchazhi;
    }
}
