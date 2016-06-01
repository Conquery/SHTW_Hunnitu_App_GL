package com.shtoone.glhnt.entity;

/**
 * Created by Administrator on 2015/11/26.
 */
public class Menu_BHZ {

    /**
     * todayCount : 0
     * chaobiaoRealCount : 512
     * waitRealCount : 512
     * realPer :
     * leijiCount : 5583
     * leijiRealCount : 1
     */

    private DataEntity data;
    /**
     * data : {"todayCount":"0","chaobiaoRealCount":"512","waitRealCount":"512","realPer":"","leijiCount":"5583","leijiRealCount":"1"}
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
        private String todayCount;
        private String chaobiaoRealCount;
        private String waitRealCount;
        private String realPer;
        private String leijiCount;
        private String leijiRealCount;

        public void setTodayCount(String todayCount) {
            this.todayCount = todayCount;
        }

        public void setChaobiaoRealCount(String chaobiaoRealCount) {
            this.chaobiaoRealCount = chaobiaoRealCount;
        }

        public void setWaitRealCount(String waitRealCount) {
            this.waitRealCount = waitRealCount;
        }

        public void setRealPer(String realPer) {
            this.realPer = realPer;
        }

        public void setLeijiCount(String leijiCount) {
            this.leijiCount = leijiCount;
        }

        public void setLeijiRealCount(String leijiRealCount) {
            this.leijiRealCount = leijiRealCount;
        }

        public String getTodayCount() {
            return todayCount;
        }

        public String getChaobiaoRealCount() {
            return chaobiaoRealCount;
        }

        public String getWaitRealCount() {
            return waitRealCount;
        }

        public String getRealPer() {
            return realPer;
        }

        public String getLeijiCount() {
            return leijiCount;
        }

        public String getLeijiRealCount() {
            return leijiRealCount;
        }
    }
}
