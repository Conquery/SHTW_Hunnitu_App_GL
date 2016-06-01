package com.shtoone.glhnt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 试验室混泥土详情数据
 */
public class SYS_HunNiTu_Xq_Entity implements Parcelable {
    /**
     * WTBH : SD3-MD-56KY-20140320-01
     * SYRQ : 2015-05-15
     * SYLX : 100014
     * SJCC : 150*150*150
     * chuli :
     * KYLZ : 904.55&893.71&873.1
     * SBBH : TWSYS00002-000005
     * ZZJJ : null&null&null
     * ZZRQ : 2015-03-20
     * PDJG : 合格
     * SJBH : SD3-MD-56KY-20140320-01
     * LQ : 56
     * SJMJ : 22500
     * f_LZ : 5.14,10.63,19……,
     * CJMC : 施工部位4
     * KYQD : 40.2&39.7&38.8
     * SJQD : C35
     * f_SJ : 3937,5546,7171,8781……,,
     * QDDBZ : 39.6
     */

    private DataEntity data;
    /**
     * data : {"WTBH":"SD3-MD-56KY-20140320-01","SYRQ":"2015-05-15","SYLX":"100014","SJCC":"150*150*150","chuli":"","KYLZ":"904.55&893.71&873.1","SBBH":"TWSYS00002-000005","ZZJJ":"null&null&null","ZZRQ":"2015-03-20","PDJG":"合格","SJBH":"SD3-MD-56KY-20140320-01","LQ":"56","SJMJ":"22500","f_LZ":"5.14,10.63,19.67,35.20,55.16,80.33,106.60,135.33,165.78,193.70,216.78,239.82,269.04,300.05,330.88,359.29,387.04,410.85,431.42,451.77,471.78,491.41,511.73,530.26,548.19,565.50,582.66,609.12,634.76,659.07,683.50,700.11,715.88,730.61,740.38,749.63,770.28,795.57,819.16,841.03,861.28,879.87,897.03,904.55,&6.10,7.40,8.86,10.93,15.71,22.91,31.29,41.06,52.07,64.17,77.08,90.75,105.06,120.93,144.13,167.49,191.65,216.67,242.75,272.86,300.78,326.20,349.98,370.17,391.07,412.73,433.85,454.27,476.76,498.56,519.38,540.51,559.35,577.19,594.02,609.91,624.86,638.87,652.48,673.54,696.30,715.75,735.95,753.09,768.75,786.14,802.99,822.18,840.06,856.04,870.49,884.29,893.71,893.71,&6.52,11.53,20.49,31.66,44.84,62.12,83.31,107.80,132.15,158.13,184.49,206.86,222.62,239.19,258.06,280.25,305.18,330.96,357.15,385.52,412.58,440.06,467.88,495.90,524.23,550.82,575.38,599.72,623.94,649.68,672.09,695.98,719.74,743.33,768.30,789.97,812.96,835.74,859.74,873.10,873.10,","CJMC":"施工部位4","KYQD":"40.2&39.7&38.8","SJQD":"C35","f_SJ":"3937,5546,7171,8781,10375,12000,13656,15281,16921,18500,20109,21703,23343,24953,26578,28203,29812,31406,33015,34656,36265,37906,39546,41156,42796,44406,46031,47656,49250,50875,52500,54125,55734,57375,59000,60625,62250,63875,65500,67093,68734,70359,71984,73609,&11687,13296,14921,16531,18156,19765,21375,23031,24625,26250,27875,29500,31093,32703,34343,35937,37578,39203,40859,42484,44078,45703,47328,48937,50515,52125,53750,55390,57031,58640,60250,61875,63500,65125,66734,68328,69953,71546,73140,74750,76375,77937,79562,81218,82843,84500,86125,87734,89359,90968,92625,94250,95859,97468,&8109,9719,11328,12922,14547,16156,17781,19406,21015,22656,24281,25922,27531,29094,30734,32328,33922,35531,37140,38781,40406,42015,43640,45250,46906,48484,50078,51703,53328,54953,56531,58125,59750,61375,62984,64578,66187,67797,69422,71015,72609,","QDDBZ":"39.6"}
     * success : true
     */

    private boolean success;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataEntity getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public static class DataEntity implements Parcelable {
        private String SYRQ;
        private String testName;
        private String SJCC;
        private String chuli;
        private String KYLZ;
        private String SBBH;
        private String ZZJJ;
        private String ZZRQ;
        private String PDJG;
        private String SJBH;
        private String LQ;
        private String SJMJ;
        private String f_LZ;
        private String CJMC;
        private String KYQD;
        private String SJQD;
        private String f_SJ;
        private String QDDBZ;
        private String GCMC;
        private String SGBW;

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

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public void setSYRQ(String SYRQ) {
            this.SYRQ = SYRQ;
        }

        public void setSJCC(String SJCC) {
            this.SJCC = SJCC;
        }

        public void setChuli(String chuli) {
            this.chuli = chuli;
        }

        public void setKYLZ(String KYLZ) {
            this.KYLZ = KYLZ;
        }

        public void setSBBH(String SBBH) {
            this.SBBH = SBBH;
        }

        public void setZZJJ(String ZZJJ) {
            this.ZZJJ = ZZJJ;
        }

        public void setZZRQ(String ZZRQ) {
            this.ZZRQ = ZZRQ;
        }

        public void setPDJG(String PDJG) {
            this.PDJG = PDJG;
        }

        public void setSJBH(String SJBH) {
            this.SJBH = SJBH;
        }

        public void setLQ(String LQ) {
            this.LQ = LQ;
        }

        public void setSJMJ(String SJMJ) {
            this.SJMJ = SJMJ;
        }

        public void setF_LZ(String f_LZ) {
            this.f_LZ = f_LZ;
        }

        public void setCJMC(String CJMC) {
            this.CJMC = CJMC;
        }

        public void setKYQD(String KYQD) {
            this.KYQD = KYQD;
        }

        public void setSJQD(String SJQD) {
            this.SJQD = SJQD;
        }

        public void setF_SJ(String f_SJ) {
            this.f_SJ = f_SJ;
        }

        public void setQDDBZ(String QDDBZ) {
            this.QDDBZ = QDDBZ;
        }

        public String getTestName() {
            return testName;
        }

        public String getSYRQ() {
            return SYRQ;
        }

        public String getSJCC() {
            return SJCC;
        }

        public String getChuli() {
            return chuli;
        }

        public String getKYLZ() {
            return KYLZ;
        }

        public String getSBBH() {
            return SBBH;
        }

        public String getZZJJ() {
            return ZZJJ;
        }

        public String getZZRQ() {
            return ZZRQ;
        }

        public String getPDJG() {
            return PDJG;
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

        public String getF_LZ() {
            return f_LZ;
        }

        public String getCJMC() {
            return CJMC;
        }

        public String getKYQD() {
            return KYQD;
        }

        public String getSJQD() {
            return SJQD;
        }

        public String getF_SJ() {
            return f_SJ;
        }

        public String getQDDBZ() {
            return QDDBZ;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.SYRQ);
            dest.writeString(this.testName);
            dest.writeString(this.SJCC);
            dest.writeString(this.chuli);
            dest.writeString(this.KYLZ);
            dest.writeString(this.SBBH);
            dest.writeString(this.ZZJJ);
            dest.writeString(this.ZZRQ);
            dest.writeString(this.PDJG);
            dest.writeString(this.SJBH);
            dest.writeString(this.LQ);
            dest.writeString(this.SJMJ);
            dest.writeString(this.f_LZ);
            dest.writeString(this.CJMC);
            dest.writeString(this.KYQD);
            dest.writeString(this.SJQD);
            dest.writeString(this.f_SJ);
            dest.writeString(this.QDDBZ);
            dest.writeString(this.GCMC);
            dest.writeString(this.SGBW);
        }

        public DataEntity() {
        }

        private DataEntity(Parcel in) {
            this.SYRQ = in.readString();
            this.testName = in.readString();
            this.SJCC = in.readString();
            this.chuli = in.readString();
            this.KYLZ = in.readString();
            this.SBBH = in.readString();
            this.ZZJJ = in.readString();
            this.ZZRQ = in.readString();
            this.PDJG = in.readString();
            this.SJBH = in.readString();
            this.LQ = in.readString();
            this.SJMJ = in.readString();
            this.f_LZ = in.readString();
            this.CJMC = in.readString();
            this.KYQD = in.readString();
            this.SJQD = in.readString();
            this.f_SJ = in.readString();
            this.QDDBZ = in.readString();
            this.GCMC = in.readString();
            this.SGBW = in.readString();
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
        dest.writeParcelable(this.data, 0);
        dest.writeByte(success ? (byte) 1 : (byte) 0);
    }

    public SYS_HunNiTu_Xq_Entity() {
    }

    private SYS_HunNiTu_Xq_Entity(Parcel in) {
        this.data = in.readParcelable(DataEntity.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SYS_HunNiTu_Xq_Entity> CREATOR = new Parcelable.Creator<SYS_HunNiTu_Xq_Entity>() {
        public SYS_HunNiTu_Xq_Entity createFromParcel(Parcel source) {
            return new SYS_HunNiTu_Xq_Entity(source);
        }

        public SYS_HunNiTu_Xq_Entity[] newArray(int size) {
            return new SYS_HunNiTu_Xq_Entity[size];
        }
    };
}
