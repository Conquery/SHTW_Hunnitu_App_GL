package com.shtoone.glhnt.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 试验室钢筋拉力列表实体
 */
public class SYS_GangJingLaLi_Entity implements Parcelable {

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

        private String WTID;
        private String SYRQ;
        private String SYJID;
        private String CJMC;
        private String SJCC;
        private String GCZJ;
        private String ZZRQ;
        private String PDJG;
        private String shebeiname;
        private String SJBH;
        private String LZQD;
        private String QFLZ;
        private String PZBM;
        private String GCMC;
        private String SGBW;
        public String testName;

        public String getSGBW() {
            return SGBW;
        }

        public void setSGBW(String SGBW) {
            this.SGBW = SGBW;
        }

        public String getGCMC() {
            return GCMC;
        }

        public void setGCMC(String GCMC) {
            this.GCMC = GCMC;
        }

        public String getLZQD() {
            return LZQD;
        }

        public String getQFLZ() {
            return QFLZ;
        }

        public String getPZBM() {
            return PZBM;
        }

        public void setPZBM(String PZBM) {
            this.PZBM = PZBM;
        }

        public void setWTID(String WTID) {
            this.WTID = WTID;
        }

        public void setSYRQ(String SYRQ) {
            this.SYRQ = SYRQ;
        }

        public void setSYJID(String SYJID) {
            this.SYJID = SYJID;
        }

        public void setCJMC(String CJMC) {
            this.CJMC = CJMC;
        }

        public void setSJCC(String SJCC) {
            this.SJCC = SJCC;
        }

        public void setGCZJ(String GCZJ) {
            this.GCZJ = GCZJ;
        }

        public void setZZRQ(String ZZRQ) {
            this.ZZRQ = ZZRQ;
        }

        public void setPDJG(String PDJG) {
            this.PDJG = PDJG;
        }

        public void setShebeiname(String shebeiname) {
            this.shebeiname = shebeiname;
        }

        public void setSJBH(String SJBH) {
            this.SJBH = SJBH;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public String getWTID() {
            return WTID;
        }

        public String getSYRQ() {
            return SYRQ;
        }

        public String getSYJID() {
            return SYJID;
        }

        public String getCJMC() {
            return CJMC;
        }

        public String getSJCC() {
            return SJCC;
        }

        public String getGCZJ() {
            return GCZJ;
        }

        public String getZZRQ() {
            return ZZRQ;
        }

        public String getPDJG() {
            return PDJG;
        }

        public String getShebeiname() {
            return shebeiname;
        }

        public String getSJBH() {
            return SJBH;
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
            dest.writeString(this.WTID);
            dest.writeString(this.SYRQ);
            dest.writeString(this.SYJID);
            dest.writeString(this.CJMC);
            dest.writeString(this.SJCC);
            dest.writeString(this.GCZJ);
            dest.writeString(this.ZZRQ);
            dest.writeString(this.PDJG);
            dest.writeString(this.shebeiname);
            dest.writeString(this.SJBH);
            dest.writeString(this.LZQD);
            dest.writeString(this.QFLZ);
            dest.writeString(this.PZBM);
            dest.writeString(this.GCMC);
            dest.writeString(this.SGBW);
            dest.writeString(this.testName);
        }

        public DataEntity() {
        }

        private DataEntity(Parcel in) {
            this.WTID = in.readString();
            this.SYRQ = in.readString();
            this.SYJID = in.readString();
            this.CJMC = in.readString();
            this.SJCC = in.readString();
            this.GCZJ = in.readString();
            this.ZZRQ = in.readString();
            this.PDJG = in.readString();
            this.shebeiname = in.readString();
            this.SJBH = in.readString();
            this.LZQD = in.readString();
            this.QFLZ = in.readString();
            this.PZBM = in.readString();
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

    public SYS_GangJingLaLi_Entity() {
    }

    private SYS_GangJingLaLi_Entity(Parcel in) {
        in.readTypedList(data, DataEntity.CREATOR);
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SYS_GangJingLaLi_Entity> CREATOR = new Parcelable.Creator<SYS_GangJingLaLi_Entity>() {
        public SYS_GangJingLaLi_Entity createFromParcel(Parcel source) {
            return new SYS_GangJingLaLi_Entity(source);
        }

        public SYS_GangJingLaLi_Entity[] newArray(int size) {
            return new SYS_GangJingLaLi_Entity[size];
        }
    };
}
