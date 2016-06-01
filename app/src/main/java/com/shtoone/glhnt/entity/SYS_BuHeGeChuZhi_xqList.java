package com.shtoone.glhnt.entity;

import java.io.Serializable;

public class SYS_BuHeGeChuZhi_xqList implements Serializable {

	private static final long serialVersionUID = -6605662994298747L;

	private String id;
	private String lizhi;
	private String qufulizhi;
	private String qiangdu;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLizhi() {
		return lizhi;
	}

	public void setLizhi(String lizhi) {
		this.lizhi = lizhi;
	}

	public String getQufulizhi() {
		return qufulizhi;
	}

	public void setQufulizhi(String qufulizhi) {
		this.qufulizhi = qufulizhi;
	}

	public String getQiangdu() {
		return qiangdu;
	}

	public void setQiangdu(String qiangdu) {
		this.qiangdu = qiangdu;
	}

}
