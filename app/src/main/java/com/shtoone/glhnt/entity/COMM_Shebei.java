package com.shtoone.glhnt.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/11/27.
 */
public class COMM_Shebei {

    /**
     * data : [{"departid":"4028b8814ed22bc2014ed23bd0b8000e","banhezhanminchen":"沪通大桥试验室","gprsbianhao":"XKXC01SG03SZ03WN01"}]
     * success : true
     */

    private boolean success;
    /**
     * departid : 4028b8814ed22bc2014ed23bd0b8000e
     * banhezhanminchen : 沪通大桥试验室
     * gprsbianhao : XKXC01SG03SZ03WN01
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
        private String departid;
        private String banhezhanminchen;
        private String gprsbianhao;

        public void setDepartid(String departid) {
            this.departid = departid;
        }

        public void setBanhezhanminchen(String banhezhanminchen) {
            this.banhezhanminchen = banhezhanminchen;
        }

        public void setGprsbianhao(String gprsbianhao) {
            this.gprsbianhao = gprsbianhao;
        }

        public String getDepartid() {
            return departid;
        }

        public String getBanhezhanminchen() {
            return banhezhanminchen;
        }

        public String getGprsbianhao() {
            return gprsbianhao;
        }
    }
}
