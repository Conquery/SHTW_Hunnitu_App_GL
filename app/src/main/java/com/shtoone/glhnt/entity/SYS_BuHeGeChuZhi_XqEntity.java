package com.shtoone.glhnt.entity;

import java.io.Serializable;
import java.util.List;

public class SYS_BuHeGeChuZhi_XqEntity implements Serializable{

	private static final long serialVersionUID = -9101740768196102488L;
	
	private String sysName;
	private String syName;
	private String proName;
	private String partName;
	private String weituoNum;
	private String shijianNum;
	private String shiyanTime;
	private String shijianChicun;
	private String sjQiangdu;
	private String lingqi;
	private String qdDaibiao;
	private String pingzhongNum;
	private String guigeZhonglei;
	private String gongchengZhijing;
	private String caozuorenyuan;
	private String panduanjieguou;
	private List<SYS_BuHeGeChuZhi_xqList> items;

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSyName() {
		return syName;
	}

	public void setSyName(String syName) {
		this.syName = syName;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getWeituoNum() {
		return weituoNum;
	}

	public void setWeituoNum(String weituoNum) {
		this.weituoNum = weituoNum;
	}

	public String getShijianNum() {
		return shijianNum;
	}

	public void setShijianNum(String shijianNum) {
		this.shijianNum = shijianNum;
	}

	public String getShiyanTime() {
		return shiyanTime;
	}

	public void setShiyanTime(String shiyanTime) {
		this.shiyanTime = shiyanTime;
	}

	public String getShijianChicun() {
		return shijianChicun;
	}

	public void setShijianChicun(String shijianChicun) {
		this.shijianChicun = shijianChicun;
	}

	public String getSjQiangdu() {
		return sjQiangdu;
	}

	public void setSjQiangdu(String sjQiangdu) {
		this.sjQiangdu = sjQiangdu;
	}

	public String getLingqi() {
		return lingqi;
	}

	public void setLingqi(String lingqi) {
		this.lingqi = lingqi;
	}

	public String getQdDaibiao() {
		return qdDaibiao;
	}

	public void setQdDaibiao(String qdDaibiao) {
		this.qdDaibiao = qdDaibiao;
	}

	public String getPingzhongNum() {
		return pingzhongNum;
	}

	public void setPingzhongNum(String pingzhongNum) {
		this.pingzhongNum = pingzhongNum;
	}

	public String getGuigeZhonglei() {
		return guigeZhonglei;
	}

	public void setGuigeZhonglei(String guigeZhonglei) {
		this.guigeZhonglei = guigeZhonglei;
	}

	public String getGongchengZhijing() {
		return gongchengZhijing;
	}

	public void setGongchengZhijing(String gongchengZhijing) {
		this.gongchengZhijing = gongchengZhijing;
	}

	public String getCaozuorenyuan() {
		return caozuorenyuan;
	}

	public void setCaozuorenyuan(String caozuorenyuan) {
		this.caozuorenyuan = caozuorenyuan;
	}

	public String getPanduanjieguou() {
		return panduanjieguou;
	}

	public void setPanduanjieguou(String panduanjieguou) {
		this.panduanjieguou = panduanjieguou;
	}

	public List<SYS_BuHeGeChuZhi_xqList> getItems() {
		return items;
	}

	public void setItems(List<SYS_BuHeGeChuZhi_xqList> items) {
		this.items = items;
	}

}
