package com.shtoone.glhnt.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18 0018.
 */
public class SYS_Lingdao_ly {

    /**
     * data : [[{"realCount":"6","departName":"北京中铁诚业监理公司","testtype":"100047","syjCount":"9","realPer":"300.00","testCount":"2","notQualifiedCount":"0","sysCount":"1","userGroupId":"f89aff075317222c01531b866ec50010"},{"realCount":"0","departName":"北京中铁诚业监理公司","testtype":"100048","syjCount":"9","realPer":"0.00","testCount":"2","notQualifiedCount":"0","sysCount":"1","userGroupId":"f89aff075317222c01531b866ec50010"},{"realCount":"0","departName":"北京中铁诚业监理公司","testtype":"100135","syjCount":"9","realPer":"0.00","testCount":"6","notQualifiedCount":"0","sysCount":"1","userGroupId":"f89aff075317222c01531b866ec50010"},{"realCount":"0","departName":"北京中铁诚业监理公司","testtype":"100136","syjCount":"9","realPer":"0.00","testCount":"2","notQualifiedCount":"0","sysCount":"1","userGroupId":"f89aff075317222c01531b866ec50010"}]]
     * success : true
     */

    private boolean success;
    /**
     * realCount : 6
     * departName : 北京中铁诚业监理公司
     * testtype : 100047
     * syjCount : 9
     * realPer : 300.00
     * testCount : 2
     * notQualifiedCount : 0
     * sysCount : 1
     * userGroupId : f89aff075317222c01531b866ec50010
     */

    private List<List<DataBean>> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        private String realCount;
        private String departName;
        private String testtype;
        private String syjCount;
        private String realPer;
        private String testCount;
        private String notQualifiedCount;
        private String sysCount;
        private String userGroupId;
        private String testName;

        public String getTestName() {
            return testName;
        }
        public void setTestName(String testName) {
            this.testName = testName;
        }

        public String getRealCount() {
            return realCount;
        }

        public void setRealCount(String realCount) {
            this.realCount = realCount;
        }

        public String getDepartName() {
            return departName;
        }

        public void setDepartName(String departName) {
            this.departName = departName;
        }

        public String getTesttype() {
            return testtype;
        }

        public void setTesttype(String testtype) {
            this.testtype = testtype;
        }

        public String getSyjCount() {
            return syjCount;
        }

        public void setSyjCount(String syjCount) {
            this.syjCount = syjCount;
        }

        public String getRealPer() {
            return realPer;
        }

        public void setRealPer(String realPer) {
            this.realPer = realPer;
        }

        public String getTestCount() {
            return testCount;
        }

        public void setTestCount(String testCount) {
            this.testCount = testCount;
        }

        public String getNotQualifiedCount() {
            return notQualifiedCount;
        }

        public void setNotQualifiedCount(String notQualifiedCount) {
            this.notQualifiedCount = notQualifiedCount;
        }

        public String getSysCount() {
            return sysCount;
        }

        public void setSysCount(String sysCount) {
            this.sysCount = sysCount;
        }

        public String getUserGroupId() {
            return userGroupId;
        }

        public void setUserGroupId(String userGroupId) {
            this.userGroupId = userGroupId;
        }
    }


}
