package com.shtoone.glhnt.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/11/25.
 */
public class BHZ_ChaobiaoTongji {


    /**
     * data : [{"departName":"MHSS3标试验室","hczlv":"0.00","hcblv":"0.00","cbpanshu":"0","totalPanshu":"0","hczpanshu":"0","bhjCount":"0","hcbpanshu":"0","mcblv":"0.00","czlv":"0.00","mcbpanshu":"0","departId":"4028b8814ed22bc2014ed235e6750002","cblv":"0.00","cczpanshu":"0","mczlv":"0.00","totalFangliang":"0","mczpanshu":"0"}]
     * success : true
     */

    private boolean success;
    /**
     * departName : MHSS3标试验室
     * hczlv : 0.00
     * hcblv : 0.00
     * cbpanshu : 0
     * totalPanshu : 0
     * hczpanshu : 0
     * bhjCount : 0
     * hcbpanshu : 0
     * mcblv : 0.00
     * czlv : 0.00
     * mcbpanshu : 0
     * departId : 4028b8814ed22bc2014ed235e6750002
     * cblv : 0.00
     * cczpanshu : 0
     * mczlv : 0.00
     * totalFangliang : 0
     * mczpanshu : 0
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
        private String departName;
        private String hczlv;
        private String hcblv;
        private String cbpanshu;
        private String totalPanshu;
        private String hczpanshu;

        public String getBhzCount() {
            return bhzCount;
        }

        public void setBhzCount(String bhzCount) {
            this.bhzCount = bhzCount;
        }

        private String bhzCount;
        private String bhjCount;
        private String hcbpanshu;
        private String mcblv;
        private String czlv;
        private String mcbpanshu;
        private String departId;
        private String cblv;
        private String cczpanshu;
        private String mczlv;
        private String totalFangliang;
        private String mczpanshu;

        public void setDepartName(String departName) {
            this.departName = departName;
        }

        public void setHczlv(String hczlv) {
            this.hczlv = hczlv;
        }

        public void setHcblv(String hcblv) {
            this.hcblv = hcblv;
        }

        public void setCbpanshu(String cbpanshu) {
            this.cbpanshu = cbpanshu;
        }

        public void setTotalPanshu(String totalPanshu) {
            this.totalPanshu = totalPanshu;
        }

        public void setHczpanshu(String hczpanshu) {
            this.hczpanshu = hczpanshu;
        }

        public void setBhjCount(String bhjCount) {
            this.bhjCount = bhjCount;
        }

        public void setHcbpanshu(String hcbpanshu) {
            this.hcbpanshu = hcbpanshu;
        }

        public void setMcblv(String mcblv) {
            this.mcblv = mcblv;
        }

        public void setCzlv(String czlv) {
            this.czlv = czlv;
        }

        public void setMcbpanshu(String mcbpanshu) {
            this.mcbpanshu = mcbpanshu;
        }

        public void setDepartId(String departId) {
            this.departId = departId;
        }

        public void setCblv(String cblv) {
            this.cblv = cblv;
        }

        public void setCczpanshu(String cczpanshu) {
            this.cczpanshu = cczpanshu;
        }

        public void setMczlv(String mczlv) {
            this.mczlv = mczlv;
        }

        public void setTotalFangliang(String totalFangliang) {
            this.totalFangliang = totalFangliang;
        }

        public void setMczpanshu(String mczpanshu) {
            this.mczpanshu = mczpanshu;
        }

        public String getDepartName() {
            return departName;
        }

        public String getHczlv() {
            return hczlv;
        }

        public String getHcblv() {
            return hcblv;
        }

        public String getCbpanshu() {
            return cbpanshu;
        }

        public String getTotalPanshu() {
            return totalPanshu;
        }

        public String getHczpanshu() {
            return hczpanshu;
        }

        public String getBhjCount() {
            return bhjCount;
        }

        public String getHcbpanshu() {
            return hcbpanshu;
        }

        public String getMcblv() {
            return mcblv;
        }

        public String getCzlv() {
            return czlv;
        }

        public String getMcbpanshu() {
            return mcbpanshu;
        }

        public String getDepartId() {
            return departId;
        }

        public String getCblv() {
            return cblv;
        }

        public String getCczpanshu() {
            return cczpanshu;
        }

        public String getMczlv() {
            return mczlv;
        }

        public String getTotalFangliang() {
            return totalFangliang;
        }

        public String getMczpanshu() {
            return mczpanshu;
        }
    }
}
