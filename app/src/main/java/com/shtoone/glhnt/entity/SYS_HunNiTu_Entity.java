package com.shtoone.glhnt.entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 试验室混泥土强度列表数据
 */
public class SYS_HunNiTu_Entity implements Parcelable {

    private List<DataEntity> data;
    private boolean success;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public static class DataEntity implements Parcelable {

        private String SYJID;
        private String SYRQ;
        private String PDJG;
        private String ZZRQ;
        private String SJBH;
        private String LQ;
        private String SJMJ;
        private String WTID;
        private String CJMC;
        private String SJQD;
        private String shebeiname;
        private String QDDBZ;
        private String GCMC;
        private String SGBW;
        private String testName;

        public String getSGBW() {
            return SGBW;
        }

        public void setSGBW(String SGBW) {
            this.SGBW = SGBW;
        }

        public void setGCMC(String GCMC) {
            this.GCMC = GCMC;
        }

        public String getGCMC() {
            return GCMC;
        }

        public void setSYJID(String SYJID) {
            this.SYJID = SYJID;
        }

        public void setSYRQ(String SYRQ) {
            this.SYRQ = SYRQ;
        }

        public void setPDJG(String PDJG) { this.PDJG = PDJG;}

        public void setZZRQ(String ZZRQ) { this.ZZRQ = ZZRQ;}

        public void setSJBH(String SJBH) {
            this.SJBH = SJBH;
        }

        public void setLQ(String LQ) {
            this.LQ = LQ;
        }

        public void setSJMJ(String SJMJ) {
            this.SJMJ = SJMJ;
        }

        public void setWTID(String WTID) {
            this.WTID = WTID;
        }

        public void setCJMC(String CJMC) {
            this.CJMC = CJMC;
        }

        public void setSJQD(String SJQD) {
            this.SJQD = SJQD;
        }

        public void setShebeiname(String shebeiname) {
            this.shebeiname = shebeiname;
        }

        public void setQDDBZ(String QDDBZ) {
            this.QDDBZ = QDDBZ;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public String getSYJID() {
            return SYJID;
        }

        public String getSYRQ() {
            return SYRQ;
        }

        public String getPDJG() {
            return PDJG;
        }

        public String getZZRQ() {
            return ZZRQ;
        }

        public String getSJBH() {
            return SJBH;
        }

        public String getLQ() {
            return LQ;
        }

        public String getSJMJ() {
            return SJMJ;
        }

        public String getWTID() {
            return WTID;
        }

        public String getCJMC() {
            return CJMC;
        }

        public String getSJQD() {
            return SJQD;
        }

        public String getShebeiname() {
            return shebeiname;
        }

        public String getQDDBZ() {
            return QDDBZ;
        }

        public String getTestName() {
            return testName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.SYJID);
            dest.writeString(this.SYRQ);
            dest.writeString(this.PDJG);
            dest.writeString(this.ZZRQ);
            dest.writeString(this.SJBH);
            dest.writeString(this.LQ);
            dest.writeString(this.SJMJ);
            dest.writeString(this.WTID);
            dest.writeString(this.CJMC);
            dest.writeString(this.SJQD);
            dest.writeString(this.shebeiname);
            dest.writeString(this.QDDBZ);
            dest.writeString(this.GCMC);
            dest.writeString(this.SGBW);
            dest.writeString(this.testName);
        }

        public DataEntity() {
        }

        private DataEntity(Parcel in) {
            this.SYJID = in.readString();
            this.SYRQ = in.readString();
            this.PDJG = in.readString();
            this.ZZRQ = in.readString();
            this.SJBH = in.readString();
            this.LQ = in.readString();
            this.SJMJ = in.readString();
            this.WTID = in.readString();
            this.CJMC = in.readString();
            this.SJQD = in.readString();
            this.shebeiname = in.readString();
            this.QDDBZ = in.readString();
            this.GCMC = in.readString();
            this.SGBW = in.readString();
            this.testName = in.readString();
        }

        public static final Parcelable.Creator<DataEntity> CREATOR = new Parcelable.Creator<DataEntity>() {
            public DataEntity createFromParcel(Parcel source) {
                return new DataEntity(source);
            }

            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
        dest.writeByte(success ? (byte) 1 : (byte) 0);
    }

    public SYS_HunNiTu_Entity() {
    }

    private SYS_HunNiTu_Entity(Parcel in) {
        in.readTypedList(data, DataEntity.CREATOR);
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SYS_HunNiTu_Entity> CREATOR = new Parcelable.Creator<SYS_HunNiTu_Entity>() {
        public SYS_HunNiTu_Entity createFromParcel(Parcel source) {
            return new SYS_HunNiTu_Entity(source);
        }

        public SYS_HunNiTu_Entity[] newArray(int size) {
            return new SYS_HunNiTu_Entity[size];
        }
    };
}
