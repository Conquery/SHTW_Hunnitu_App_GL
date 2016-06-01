package com.shtoone.glhnt.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/11/27.
 */
public class BHZ_ZongheTj {

    /**
     * data : [{"midcbps":"0","highcbper":"0.00","pangshu":"56","gujifangshu":"55.541","midcbper":"0.00","xb":"3","xa":"2015","highcbps":"0","lowcbper":"0.00","lowcbps":"0"}]
     * success : true
     */

    private boolean success;
    /**
     * midcbps : 0
     * highcbper : 0.00
     * pangshu : 56
     * gujifangshu : 55.541
     * midcbper : 0.00
     * xb : 3
     * xa : 2015
     * highcbps : 0
     * lowcbper : 0.00
     * lowcbps : 0
     */

    private List<DataEntity> data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String midcbps;
        private String highcbper;
        private String pangshu;
        private String gujifangshu;
        private String midcbper;
        private String xb;
        private String xa;
        private String highcbps;
        private String lowcbper;
        private String lowcbps;

        public void setMidcbps(String midcbps) {
            this.midcbps = midcbps;
        }

        public void setHighcbper(String highcbper) {
            this.highcbper = highcbper;
        }

        public void setPangshu(String pangshu) {
            this.pangshu = pangshu;
        }

        public void setGujifangshu(String gujifangshu) {
            this.gujifangshu = gujifangshu;
        }

        public void setMidcbper(String midcbper) {
            this.midcbper = midcbper;
        }

        public void setXb(String xb) {
            this.xb = xb;
        }

        public void setXa(String xa) {
            this.xa = xa;
        }

        public void setHighcbps(String highcbps) {
            this.highcbps = highcbps;
        }

        public void setLowcbper(String lowcbper) {
            this.lowcbper = lowcbper;
        }

        public void setLowcbps(String lowcbps) {
            this.lowcbps = lowcbps;
        }

        public String getMidcbps() {
            return midcbps;
        }

        public String getHighcbper() {
            return highcbper;
        }

        public String getPangshu() {
            return pangshu;
        }

        public String getGujifangshu() {
            return gujifangshu;
        }

        public String getMidcbper() {
            return midcbper;
        }

        public String getXb() {
            return xb;
        }

        public String getXa() {
            return xa;
        }

        public String getHighcbps() {
            return highcbps;
        }

        public String getLowcbper() {
            return lowcbper;
        }

        public String getLowcbps() {
            return lowcbps;
        }
    }
}
