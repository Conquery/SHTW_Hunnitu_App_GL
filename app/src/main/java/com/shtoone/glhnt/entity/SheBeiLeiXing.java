package com.shtoone.glhnt.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class SheBeiLeiXing {

    /**
     * data : [{"banhezhanminchen":"吴中1标中心2000型压力机","departid":"f89aff075317222c01531b8b25b0001b","gprsbianhao":"TWSYS00018-000018"},{"banhezhanminchen":"吴中1标中心300B万能机","departid":"f89aff075317222c01531b8b25b0001b","gprsbianhao":"XPCL01SG01SZ01WN01"},{"banhezhanminchen":"吴中1标中心水泥试验机","departid":"f89aff075317222c01531b8b25b0001b","gprsbianhao":"XPCL01SG01SZ01YL02"},{"banhezhanminchen":"吴中1标中心1000B万能机","departid":"f89aff075317222c01531b8b25b0001b","gprsbianhao":"XPCL01SG01SZ01WN02"},{"banhezhanminchen":"吴中1标中心钢绞线试验机","departid":"f89aff075317222c01531b8b25b0001b","gprsbianhao":"XPCL01SG01SZ01WN03"}]
     * success : true
     */

    private boolean success;
    /**
     * banhezhanminchen : 吴中1标中心2000型压力机
     * departid : f89aff075317222c01531b8b25b0001b
     * gprsbianhao : TWSYS00018-000018
     */

    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String banhezhanminchen;
        private String departid;
        private String gprsbianhao;

        public String getBanhezhanminchen() {
            return banhezhanminchen;
        }

        public void setBanhezhanminchen(String banhezhanminchen) {
            this.banhezhanminchen = banhezhanminchen;
        }

        public String getDepartid() {
            return departid;
        }

        public void setDepartid(String departid) {
            this.departid = departid;
        }

        public String getGprsbianhao() {
            return gprsbianhao;
        }

        public void setGprsbianhao(String gprsbianhao) {
            this.gprsbianhao = gprsbianhao;
        }
    }
}
