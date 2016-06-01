package com.shtoone.glhnt.entity;

import java.io.Serializable;

public class SYS_BuHeGeChuZhi_Entity implements Serializable {

	private static final long serialVersionUID = 6811009335693384022L;

	private String id;

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
	private String type;
	private String shijianTime;
	private String shijianNum;
	private String shebeiName;
	private String projectName;
	private String shigongPart;
	private String testName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShijianTime() {
		return shijianTime;
	}

	public void setShijianTime(String shijianTime) {
		this.shijianTime = shijianTime;
	}

	public String getShebeiName() {
		return shebeiName;
	}

	public void setShebeiName(String shebeiName) {
		this.shebeiName = shebeiName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getShigongPart() {
		return shigongPart;
	}

	public void setShigongPart(String shigongPart) {
		this.shigongPart = shigongPart;
	}

	public String getTestName() {return testName;}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getShijianNum() {return shijianNum;}

	public void setShijianNum(String shijianNum) {
		this.shijianNum = shijianNum;
	}
}
