package com.shtoone.glhnt.entity;

import java.io.Serializable;
import java.util.List;

public class BHZ_SCshujuchaxun_XqEntity implements Serializable {

	private static final long serialVersionUID = 3101610488452300359L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String id;
	private String time;
	private String proName;
	private String jbsj;
	private String shuliang;
	private String gdh;
	private String caozuozhe;
	private String didian;
	private String jzbw;
	private String wjjpz;
	private String snpz;
	private String sgpbbj;
	private String qddj;

	private String chulijieguo;
	private String wentiyuanyin;
	private String chulifangshi;

	public String getChulishijian() {
		return chulishijian;
	}

	public void setChulishijian(String chulishijian) {
		this.chulishijian = chulishijian;
	}

	private String chulishijian;

	public String getJianlishenpi() {
		return jianlishenpi;
	}

	public String getJianliresult() {
		return jianliresult;
	}

	public String getConfirmdate() {
		return confirmdate;
	}

	public String getShenpidate() {
		return shenpidate;
	}

	public String getChuliren() {
		return chuliren;
	}

	public String getShenpiren() {
		return shenpiren;
	}

	private String jianlishenpi;
	private String jianliresult;
	private String confirmdate;
	private String shenpidate;
	private String chuliren;
	private String shenpiren;

	public void setJianlishenpi(String jianlishenpi) {
		this.jianlishenpi = jianlishenpi;
	}

	public void setJianliresult(String jianliresult) {
		this.jianliresult = jianliresult;
	}

	public void setConfirmdate(String confirmdate) {
		this.confirmdate = confirmdate;
	}

	public void setShenpidate(String shenpidate) {
		this.shenpidate = shenpidate;
	}

	public void setChuliren(String chuliren) {
		this.chuliren = chuliren;
	}

	public void setShenpiren(String shenpiren) {
		this.shenpiren = shenpiren;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	private String filePath;

	public void setChulifangshi(String chulifangshi) {
		this.chulifangshi = chulifangshi;
	}

	public void setChulijieguo(String chulijieguo) {
		this.chulijieguo = chulijieguo;
	}

	public void setWentiyuanyin(String wentiyuanyin) {
		this.wentiyuanyin = wentiyuanyin;
	}

	public String getChulifangshi() {
		return chulifangshi;
	}

	public String getChulijieguo() {
		return chulijieguo;
	}

	public String getWentiyuanyin() {
		return wentiyuanyin;
	}

	private List<SC_chaxunItem_xq_data> items;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getJbsj() {
		return jbsj;
	}

	public void setJbsj(String jbsj) {
		this.jbsj = jbsj;
	}

	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
	}

	public String getGdh() {
		return gdh;
	}

	public void setGdh(String gdh) {
		this.gdh = gdh;
	}

	public String getCaozuozhe() {
		return caozuozhe;
	}

	public void setCaozuozhe(String caozuozhe) {
		this.caozuozhe = caozuozhe;
	}

	public String getDidian() {
		return didian;
	}

	public void setDidian(String didian) {
		this.didian = didian;
	}

	public String getJzbw() {
		return jzbw;
	}

	public void setJzbw(String jzbw) {
		this.jzbw = jzbw;
	}

	public String getWjjpz() {
		return wjjpz;
	}

	public void setWjjpz(String wjjpz) {
		this.wjjpz = wjjpz;
	}

	public String getSnpz() {
		return snpz;
	}

	public void setSnpz(String snpz) {
		this.snpz = snpz;
	}

	public String getSgpbbj() {
		return sgpbbj;
	}

	public void setSgpbbj(String sgpbbj) {
		this.sgpbbj = sgpbbj;
	}

	public String getQddj() {
		return qddj;
	}

	public void setQddj(String qddj) {
		this.qddj = qddj;
	}

	public List<SC_chaxunItem_xq_data> getItems() {
		return items;
	}

	public void setItems(List<SC_chaxunItem_xq_data> items) {
		this.items = items;
	}

}
