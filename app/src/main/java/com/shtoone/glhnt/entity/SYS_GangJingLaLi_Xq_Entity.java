package com.shtoone.glhnt.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class SYS_GangJingLaLi_Xq_Entity implements Parcelable {

	/**
	 * WTBH : W-SJJ1-GY-20140423-5
	 * SYRQ : 2014-05-13
	 * SYLX : 100047
	 * SJCC : null
	 * LZ : 136.043&135.757
	 * chuli :
	 * SBBH : XKXC01SG01SZ01WN01
	 * LZQD : 676&675
	 * ZDLZSCL : 24&24
	 * ZZRQ : null
	 * WSBZ : 130&135
	 * SJBH : W-SJJ1-GY-20140423-5
	 * SCL : 11.5&8
	 * CJMC : 施工部位1719
	 * f_LZ :
	 * GGZL : 16
	 * WY : null&null
	 * f_SJ :
	 * DHBZ : 145.3&145.8
	 * QFLZ : 106.514&105.803
	 * QDDBZ : null
	 * QFQD : 530&526
	 * PZBM : HRB400E
	 */

	private DataEntity data;
	/**
	 * data : {"WTBH":"W-SJJ1-GY-20140423-5","SYRQ":"2014-05-13","SYLX":"100047","SJCC":null,"LZ":"136.043&135.757","chuli":"","SBBH":"XKXC01SG01SZ01WN01","LZQD":"676&675","ZDLZSCL":"24&24","ZZRQ":null,"WSBZ":"130&135","SJBH":"W-SJJ1-GY-20140423-5","SCL":"11.5&8","CJMC":"施工部位1719","f_LZ":"","GGZL":"16","WY":"null&null","f_SJ":"","DHBZ":"145.3&145.8","QFLZ":"106.514&105.803","QDDBZ":null,"QFQD":"530&526","PZBM":"HRB400E"}
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
		private String WTBH;
		private String SYRQ;
		private String SYLX;
		private String SJCC;
		private String LZ;
		private String chuli;
		private String SBBH;
		private String LZQD;
		private String ZDLZSCL;
		private String ZZRQ;
		private String WSBZ;
		private String SJBH;
		private String SCL;
		private String CJMC;
		private String f_LZ;
		private String GGZL;
		private String WY;
		private String f_SJ;
		private String DHBZ;
		private String QFLZ;
		private String QDDBZ;
		private String QFQD;
		private String PZBM;
		private String PDJG;
		private String WTID;
		private String GCMC;

		public void setSGBW(String SGBW) {
			this.SGBW = SGBW;
		}

		public void setGCMC(String GCMC) {
			this.GCMC = GCMC;
		}

		private String SGBW;

		public String getGCMC() {
			return GCMC;
		}

		public String getSGBW() {
			return SGBW;
		}

		public String getPDJG() {
			return PDJG;
		}

		public String getWTID() {
			return WTID;
		}

		public void setWTID(String WTID) {
			this.WTID = WTID;
		}

		public void setPDJG(String PDJG) {
			this.PDJG = PDJG;
		}

		public void setWTBH(String WTBH) {
			this.WTBH = WTBH;
		}

		public void setSYRQ(String SYRQ) {
			this.SYRQ = SYRQ;
		}

		public void setSYLX(String SYLX) {
			this.SYLX = SYLX;
		}

		public void setSJCC(String SJCC) {
			this.SJCC = SJCC;
		}

		public void setLZ(String LZ) {
			this.LZ = LZ;
		}

		public void setChuli(String chuli) {
			this.chuli = chuli;
		}

		public void setSBBH(String SBBH) {
			this.SBBH = SBBH;
		}

		public void setLZQD(String LZQD) {
			this.LZQD = LZQD;
		}

		public void setZDLZSCL(String ZDLZSCL) {
			this.ZDLZSCL = ZDLZSCL;
		}

		public void setZZRQ(String ZZRQ) {
			this.ZZRQ = ZZRQ;
		}

		public void setWSBZ(String WSBZ) {
			this.WSBZ = WSBZ;
		}

		public void setSJBH(String SJBH) {
			this.SJBH = SJBH;
		}

		public void setSCL(String SCL) {
			this.SCL = SCL;
		}

		public void setCJMC(String CJMC) {
			this.CJMC = CJMC;
		}

		public void setF_LZ(String f_LZ) {
			this.f_LZ = f_LZ;
		}

		public void setGGZL(String GGZL) {
			this.GGZL = GGZL;
		}

		public void setWY(String WY) {
			this.WY = WY;
		}

		public void setF_SJ(String f_SJ) {
			this.f_SJ = f_SJ;
		}

		public void setDHBZ(String DHBZ) {
			this.DHBZ = DHBZ;
		}

		public void setQFLZ(String QFLZ) {
			this.QFLZ = QFLZ;
		}

		public void setQDDBZ(String QDDBZ) {
			this.QDDBZ = QDDBZ;
		}

		public void setQFQD(String QFQD) {
			this.QFQD = QFQD;
		}

		public void setPZBM(String PZBM) {
			this.PZBM = PZBM;
		}

		public String getWTBH() {
			return WTBH;
		}

		public String getSYRQ() {
			return SYRQ;
		}

		public String getSYLX() {
			return SYLX;
		}

		public String getSJCC() {
			return SJCC;
		}

		public String getLZ() {
			return LZ;
		}

		public String getChuli() {
			return chuli;
		}

		public String getSBBH() {
			return SBBH;
		}

		public String getLZQD() {
			return LZQD;
		}

		public String getZDLZSCL() {
			return ZDLZSCL;
		}

		public String getZZRQ() {
			return ZZRQ;
		}

		public String getWSBZ() {
			return WSBZ;
		}

		public String getSJBH() {
			return SJBH;
		}

		public String getSCL() {
			return SCL;
		}

		public String getCJMC() {
			return CJMC;
		}

		public String getF_LZ() {
			return f_LZ;
		}

		public String getGGZL() {
			return GGZL;
		}

		public String getWY() {
			return WY;
		}

		public String getF_SJ() {
			return f_SJ;
		}

		public String getDHBZ() {
			return DHBZ;
		}

		public String getQFLZ() {
			return QFLZ;
		}

		public String getQDDBZ() {
			return QDDBZ;
		}

		public String getQFQD() {
			return QFQD;
		}

		public String getPZBM() {
			return PZBM;
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(this.WTBH);
			dest.writeString(this.SYRQ);
			dest.writeString(this.SYLX);
			dest.writeString(this.SJCC);
			dest.writeString(this.LZ);
			dest.writeString(this.chuli);
			dest.writeString(this.SBBH);
			dest.writeString(this.LZQD);
			dest.writeString(this.ZDLZSCL);
			dest.writeString(this.ZZRQ);
			dest.writeString(this.WSBZ);
			dest.writeString(this.SJBH);
			dest.writeString(this.SCL);
			dest.writeString(this.CJMC);
			dest.writeString(this.f_LZ);
			dest.writeString(this.GGZL);
			dest.writeString(this.WY);
			dest.writeString(this.f_SJ);
			dest.writeString(this.DHBZ);
			dest.writeString(this.QFLZ);
			dest.writeString(this.QDDBZ);
			dest.writeString(this.QFQD);
			dest.writeString(this.PZBM);
			dest.writeString(this.PDJG);
			dest.writeString(this.WTID);
			dest.writeString(this.GCMC);
			dest.writeString(this.SGBW);
		}

		public DataEntity(Parcel in) {
			this.WTBH= in.readString();
			this.SYRQ= in.readString();
			this.SYLX= in.readString();
			this.SJCC= in.readString();
			this.LZ= in.readString();
			this.chuli= in.readString();
			this.SBBH= in.readString();
			this.LZQD= in.readString();
			this.ZDLZSCL= in.readString();
			this.ZZRQ= in.readString();
			this.WSBZ= in.readString();
			this.SJBH= in.readString();
			this.SCL= in.readString();
			this.CJMC= in.readString();
			this.f_LZ = in.readString();
			this.GGZL = in.readString();
			this.WY= in.readString();
			this.f_SJ = in.readString();
			this.DHBZ= in.readString();
			this.QFLZ= in.readString();
			this.QDDBZ= in.readString();
			this.QFQD= in.readString();
			this.PZBM= in.readString();
			this.PDJG = in.readString();
			this.WTID = in.readString();
			this.GCMC = in.readString();
			this.SGBW = in.readString();
		}

		private DataEntity() {
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

	public SYS_GangJingLaLi_Xq_Entity() {
	}

	private SYS_GangJingLaLi_Xq_Entity(Parcel in) {
		this.data = in.readParcelable(DataEntity.class.getClassLoader());
		this.success = in.readByte() != 0;
	}

	public static final Parcelable.Creator<SYS_GangJingLaLi_Xq_Entity> CREATOR = new Parcelable.Creator<SYS_GangJingLaLi_Xq_Entity>() {
		public SYS_GangJingLaLi_Xq_Entity createFromParcel(Parcel source) {
			return new SYS_GangJingLaLi_Xq_Entity(source);
		}

		public SYS_GangJingLaLi_Xq_Entity[] newArray(int size) {
			return new SYS_GangJingLaLi_Xq_Entity[size];
		}
	};
}
