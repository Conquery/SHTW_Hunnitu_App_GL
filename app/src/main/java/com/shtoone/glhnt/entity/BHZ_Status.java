package com.shtoone.glhnt.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/11/28.
 */
public class BHZ_Status {

    /**
     * data : [{"chuliaoshijian":"2015-09-21 13:36:36","departname":"MHSS3��1�Ű��վ","shangchuanyanshi":"","caijishijian":"2015-09-21 18:49:42:763","baocunshijian":"2015-09-21 16:40:21","state":"δ����","banhezhanminchen":"MHSS3��1��վ1�Ż�"},{"chuliaoshijian":"2015-09-21 14:44:04","departname":"MHSS3��1�Ű��վ","shangchuanyanshi":"","caijishijian":"2015-09-21 17:53:27:193","baocunshijian":"2015-09-21 17:21:47","state":"δ����","banhezhanminchen":"MHSS3��1��վ2�Ż�"},{"chuliaoshijian":"2015-10-10 02:18:11","departname":"MHSS3��3�Ű��վ","shangchuanyanshi":"","caijishijian":"2015-10-10 02:18:41:664","baocunshijian":"2015-10-10 02:18:33","state":"δ����","banhezhanminchen":"MHSS3��3��վ3�Ż�"},{"chuliaoshijian":"2015-10-12 16:24:03","departname":"MHSS3��3�Ű��վ","shangchuanyanshi":"","caijishijian":"2015-10-12 16:24:23:396","baocunshijian":"2015-10-12 16:24:09","state":"δ����","banhezhanminchen":"MHSS3��3��վ1�Ż�"},{"chuliaoshijian":"2015-10-12 07:09:36","departname":"MHSS3��3�Ű��վ","shangchuanyanshi":"","caijishijian":"2015-10-12 07:09:47:910","baocunshijian":"2015-10-12 07:09:42","state":"δ����","banhezhanminchen":"MHSS3��3��վ2�Ż�"},{"chuliaoshijian":"2015-10-01 22:05:21","departname":"MHSS3��2�Ű��վ","shangchuanyanshi":"","caijishijian":"2015-10-01 22:05:36:134","baocunshijian":"2015-10-01 22:05:28","state":"δ����","banhezhanminchen":"MHSS3��2��վ2�Ż�"},{"chuliaoshijian":"2015-10-11 09:49:34","departname":"MHSS3��2�Ű��վ","shangchuanyanshi":"","caijishijian":"2015-10-11 09:49:13:974","baocunshijian":"2015-10-11 09:49:47","state":"δ����","banhezhanminchen":"MHSS3��2��վ1�Ż�"}]
     * success : true
     */

    private boolean success;
    /**
     * chuliaoshijian : 2015-09-21 13:36:36
     * departname : MHSS3��1�Ű��վ
     * shangchuanyanshi :
     * caijishijian : 2015-09-21 18:49:42:763
     * baocunshijian : 2015-09-21 16:40:21
     * state : δ����
     * banhezhanminchen : MHSS3��1��վ1�Ż�
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
        private String chuliaoshijian;
        private String departname;
        private String shangchuanyanshi;
        private String caijishijian;
        private String baocunshijian;
        private String state;
        private String banhezhanminchen;

        public void setChuliaoshijian(String chuliaoshijian) {
            this.chuliaoshijian = chuliaoshijian;
        }

        public void setDepartname(String departname) {
            this.departname = departname;
        }

        public void setShangchuanyanshi(String shangchuanyanshi) {
            this.shangchuanyanshi = shangchuanyanshi;
        }

        public void setCaijishijian(String caijishijian) {
            this.caijishijian = caijishijian;
        }

        public void setBaocunshijian(String baocunshijian) {
            this.baocunshijian = baocunshijian;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setBanhezhanminchen(String banhezhanminchen) {
            this.banhezhanminchen = banhezhanminchen;
        }

        public String getChuliaoshijian() {
            return chuliaoshijian;
        }

        public String getDepartname() {
            return departname;
        }

        public String getShangchuanyanshi() {
            return shangchuanyanshi;
        }

        public String getCaijishijian() {
            return caijishijian;
        }

        public String getBaocunshijian() {
            return baocunshijian;
        }

        public String getState() {
            return state;
        }

        public String getBanhezhanminchen() {
            return banhezhanminchen;
        }
    }
}
