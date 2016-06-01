package com.shtoone.glhnt.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/11/21.
 */
public class SYS_Item {

    /**
     * data : [{"realCount":"0","departName":"永广铁路试验室","testtype":"100014","realPer":"0.00","testCount":"111","notQualifiedCount":"0","userGroupId":"40288bf34e61585f014e6160c9fc0001"}]
     * success : true
     */

    private boolean success;
    /**
     * realCount : 0
     * departName : 永广铁路试验室
     * testtype : 100014
     * realPer : 0.00
     * testCount : 111
     * notQualifiedCount : 0
     * userGroupId : 40288bf34e61585f014e6160c9fc0001
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
        private String realCount;
        private String departName;
        private String testtype;

        public String getSyjCount() {
            return syjCount;
        }

        public String getSysCount() {
            return sysCount;
        }

        public void setSyjCount(String syjCount) {
            this.syjCount = syjCount;
        }

        public void setSysCount(String sysCount) {
            this.sysCount = sysCount;
        }

        private String syjCount;
        private String realPer;
        private String testCount;
        private String notQualifiedCount;
        private String sysCount;
        private String userGroupId;

        public void setRealCount(String realCount) {
            this.realCount = realCount;
        }

        public void setDepartName(String departName) {
            this.departName = departName;
        }

        public void setTesttype(String testtype) {
            this.testtype = testtype;
        }

        public void setRealPer(String realPer) {
            this.realPer = realPer;
        }

        public void setTestCount(String testCount) {
            this.testCount = testCount;
        }

        public void setNotQualifiedCount(String notQualifiedCount) {
            this.notQualifiedCount = notQualifiedCount;
        }

        public void setUserGroupId(String userGroupId) {
            this.userGroupId = userGroupId;
        }

        public String getRealCount() {
            return realCount;
        }

        public String getDepartName() {
            return departName;
        }

        public String getTesttype() {
            return testtype;
        }

        public String getRealPer() {
            return realPer;
        }

        public String getTestCount() {
            return testCount;
        }

        public String getNotQualifiedCount() {
            return notQualifiedCount;
        }

        public String getUserGroupId() {
            return userGroupId;
        }
    }
}
