package com.shtoone.glhnt.entity;

/**
 * Created by Administrator on 2015/11/26.
 */
public class Menu_SYS {

    /**
     * waitRealcount : 0
     * hntkangyacount : 1
     * gangjinhanjiejietoucount : 12
     * gangjincount : 6
     * waitWTcount : 2
     * gangjinlianjiejietoucount : 3
     */

    private DataEntity data;
    /**
     * data : {"waitRealcount":"0","hntkangyacount":"1","gangjinhanjiejietoucount":"12","gangjincount":"6","waitWTcount":"2","gangjinlianjiejietoucount":"3"}
     * success : true
     */

    private boolean success;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataEntity getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public static class DataEntity {
        private String waitRealcount;
        private String hntkangyacount;
        private String gangjinhanjiejietoucount;
        private String gangjincount;
        private String waitWTcount;
        private String gangjinlianjiejietoucount;
        private String ylnpSQL;  //压力机不合格总数

        public String getYlnpSQL() {
            return ylnpSQL;
        }

        public void setYlnpSQL(String ylnpSQL) {
            this.ylnpSQL = ylnpSQL;
        }

        public String getWnnpSQL() {
            return wnnpSQL;
        }

        public void setWnnpSQL(String wnnpSQL) {
            this.wnnpSQL = wnnpSQL;
        }

        private String wnnpSQL;  //万能机不合格总数

        public void setWaitRealcount(String waitRealcount) {
            this.waitRealcount = waitRealcount;
        }

        public void setHntkangyacount(String hntkangyacount) {
            this.hntkangyacount = hntkangyacount;
        }

        public void setGangjinhanjiejietoucount(String gangjinhanjiejietoucount) {
            this.gangjinhanjiejietoucount = gangjinhanjiejietoucount;
        }

        public void setGangjincount(String gangjincount) {
            this.gangjincount = gangjincount;
        }

        public void setWaitWTcount(String waitWTcount) {
            this.waitWTcount = waitWTcount;
        }

        public void setGangjinlianjiejietoucount(String gangjinlianjiejietoucount) {
            this.gangjinlianjiejietoucount = gangjinlianjiejietoucount;
        }

        public String getWaitRealcount() {
            return waitRealcount;
        }

        public String getHntkangyacount() {
            return hntkangyacount;
        }

        public String getGangjinhanjiejietoucount() {
            return gangjinhanjiejietoucount;
        }

        public String getGangjincount() {
            return gangjincount;
        }

        public String getWaitWTcount() {
            return waitWTcount;
        }

        public String getGangjinlianjiejietoucount() {
            return gangjinlianjiejietoucount;
        }
    }
}
