package com.shtoone.glhnt.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/12/17.
 */
public class SYS_Tongjifenxi {


    /**
     * data : [{"qualifiedCount":"18","testType":"100049","notqualifiedCount":"0","testCount":"18","validCount":"0","userGroupId":"4028b8814ed22bc2014ed235e6750002","qualifiedPer":"100.00"},{"qualifiedCount":"43","testType":"100047","notqualifiedCount":"1","testCount":"44","validCount":"0","userGroupId":"4028b8814ed22bc2014ed235e6750002","qualifiedPer":"97.73"},{"qualifiedCount":"846","testType":"100014","notqualifiedCount":"1","testCount":"848","validCount":"0","userGroupId":"4028b8814ed22bc2014ed235e6750002","qualifiedPer":"99.76"},{"qualifiedCount":"35","testType":"100048","notqualifiedCount":"1","testCount":"36","validCount":"0","userGroupId":"4028b8814ed22bc2014ed235e6750002","qualifiedPer":"97.22"}]
     * success : true
     */

    private boolean success;
    /**
     * qualifiedCount : 18
     * testType : 100049
     * notqualifiedCount : 0
     * testCount : 18
     * validCount : 0
     * userGroupId : 4028b8814ed22bc2014ed235e6750002
     * qualifiedPer : 100.00
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
        private String qualifiedCount;
        private String testType;
        private String notqualifiedCount;
        private String testCount;
        private String validCount;
        private String userGroupId;
        private String qualifiedPer;

        public void setQualifiedCount(String qualifiedCount) {
            this.qualifiedCount = qualifiedCount;
        }

        public void setTestType(String testType) {
            this.testType = testType;
        }

        public void setNotqualifiedCount(String notqualifiedCount) {
            this.notqualifiedCount = notqualifiedCount;
        }

        public void setTestCount(String testCount) {
            this.testCount = testCount;
        }

        public void setValidCount(String validCount) {
            this.validCount = validCount;
        }

        public void setUserGroupId(String userGroupId) {
            this.userGroupId = userGroupId;
        }

        public void setQualifiedPer(String qualifiedPer) {
            this.qualifiedPer = qualifiedPer;
        }

        public String getQualifiedCount() {
            return qualifiedCount;
        }

        public String getTestType() {
            return testType;
        }

        public String getNotqualifiedCount() {
            return notqualifiedCount;
        }

        public String getTestCount() {
            return testCount;
        }

        public String getValidCount() {
            return validCount;
        }

        public String getUserGroupId() {
            return userGroupId;
        }

        public String getQualifiedPer() {
            return qualifiedPer;
        }
    }
}
