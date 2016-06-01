package com.shtoone.glhnt.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/11/27.
 */
public class BHZ_ZongheTongji_Entity implements Serializable {
    private static final long serialVersionUID = 6098321351074036703L;
    private String Date;         //周期
    private Double changliang;   //产量
    private Double panshu;        //盘数
    private Double primarylv;    //低报警

    public Double getPrimaryps() {
        return primaryps;
    }

    public Double getMiddleps() {
        return middleps;
    }

    public Double getHighps() {
        return highps;
    }

    public void setPrimaryps(Double primaryps) {
        this.primaryps = primaryps;
    }

    public void setMiddleps(Double middleps) {
        this.middleps = middleps;
    }

    public void setHighps(Double highps) {
        this.highps = highps;
    }

    private Double primaryps;    //低报警盘数
    private Double middlelv;     //中报警
    private Double middleps;     //中报警盘数
    private Double highlv;       //高报警
    private Double highps;       //高报警盘数

    public Double getPanshu() {
        return panshu;
    }

    public void setPanshu(Double panshu) {
        this.panshu = panshu;
    }

    public Double getPrimarylv() {
        return primarylv;
    }
    public void setPrimarylv(Double primarylv) {
        this.primarylv = primarylv;
    }
    public Double getMiddlelv() {
        return middlelv;
    }
    public void setMiddlelv(Double middlelv) {
        this.middlelv = middlelv;
    }
    public Double getHighlv() {
        return highlv;
    }
    public void setHighlv(Double highlv) {
        this.highlv = highlv;
    }

    public String getDate() {
        return Date;
    }
    public void setDate(String date) {
        Date = date;
    }
    public Double getChangliang() {
        return changliang;
    }
    public void setChangliang(Double changliang) {
        this.changliang = changliang;
    }
}
