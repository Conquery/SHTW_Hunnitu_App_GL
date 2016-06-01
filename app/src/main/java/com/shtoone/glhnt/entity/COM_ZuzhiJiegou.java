package com.shtoone.glhnt.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/11/23.
 */
public class COM_ZuzhiJiegou {

    /**
     * departTree : [{"departorderid":null,"departname":"中铁隧道局3标","description":"","lft":"1","parentdepartid":"","ID":"297ee90c4447f8a4014447fbba1e0015","rgt":"34","lng":"","type":"4","lat":""}]
     * success : true
     */

    private boolean success;
    /**
     * departorderid : null
     * departname : 中铁隧道局3标
     * description :
     * lft : 1
     * parentdepartid :
     * ID : 297ee90c4447f8a4014447fbba1e0015
     * rgt : 34
     * lng :
     * type : 4
     * lat :
     */

    private List<DataEntity> data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setdata(List<DataEntity> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<DataEntity> getdata() {
        return data;
    }

    public static class DataEntity {
        private Object departorderid;
        private String departname;
        private String description;
        private String lft;
        private String parentdepartid;
        private String ID;
        private String rgt;
        private String lng;
        private String type;
        private String lat;

        public void setDepartorderid(Object departorderid) {
            this.departorderid = departorderid;
        }

        public void setDepartname(String departname) {
            this.departname = departname;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setLft(String lft) {
            this.lft = lft;
        }

        public void setParentdepartid(String parentdepartid) {
            this.parentdepartid = parentdepartid;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public void setRgt(String rgt) {
            this.rgt = rgt;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public Object getDepartorderid() {
            return departorderid;
        }

        public String getDepartname() {
            return departname;
        }

        public String getDescription() {
            return description;
        }

        public String getLft() {
            return lft;
        }

        public String getParentdepartid() {
            return parentdepartid;
        }

        public String getID() {
            return ID;
        }

        public String getRgt() {
            return rgt;
        }

        public String getLng() {
            return lng;
        }

        public String getType() {
            return type;
        }

        public String getLat() {
            return lat;
        }
    }
}
