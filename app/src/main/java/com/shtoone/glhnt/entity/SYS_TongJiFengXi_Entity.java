package com.shtoone.glhnt.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/11/22.
 */
public class SYS_TongJiFengXi_Entity  implements Serializable {
    private static final long serialVersionUID = 722597196118398079L;

    private List<COM_XY> leijicishuList;  //累计次数 柱状图
    private List<SYS_TongJiFengXi_Item_Entity> leiijcishulist; //累计次数列表

    public List<COM_XY> getLeijicishuList() {
        return leijicishuList;
    }

    public void setLeijicishuList(List<COM_XY> leijicishuList) {
        this.leijicishuList = leijicishuList;
    }

    public List<SYS_TongJiFengXi_Item_Entity> getLeiijcishulist() {
        return leiijcishulist;
    }

    public void setLeiijcishulist(List<SYS_TongJiFengXi_Item_Entity> leiijcishulist) {
        this.leiijcishulist = leiijcishulist;
    }
}
