package com.shtoone.glhnt.entity;

import java.io.Serializable;

public class BHZ_SCshujuchaxun_Entity implements Serializable {

	private static final long serialVersionUID = -504884064895473662L;
	private int id;

	public String getBianhao() {
		return bianhao;
	}

	public String getShebeibianhao() {
		return shebeibianhao;
	}

	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}

	public void setShebeibianhao(String shebeibianhao) {
		this.shebeibianhao = shebeibianhao;
	}

	private String bianhao;
	private String shebeibianhao;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	private String deviceName;
	private String time;
	private String mName;
	private String proName;
	private String jiaozhubuwei;
	private String didianlicheng;
	private String qiangdudengji;
	private String shuliang;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getJiaozhubuwei() {
		return jiaozhubuwei;
	}

	public void setJiaozhubuwei(String jiaozhubuwei) {
		this.jiaozhubuwei = jiaozhubuwei;
	}

	public String getDidianlicheng() {
		return didianlicheng;
	}

	public void setDidianlicheng(String didianlicheng) {
		this.didianlicheng = didianlicheng;
	}

	public String getQiangdudengji() {
		return qiangdudengji;
	}

	public void setQiangdudengji(String qiangdudengji) {
		this.qiangdudengji = qiangdudengji;
	}

	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
	}

}
