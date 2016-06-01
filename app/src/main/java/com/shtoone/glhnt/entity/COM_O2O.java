package com.shtoone.glhnt.entity;

import java.io.Serializable;
import java.util.List;

public class COM_O2O implements Serializable {

	private static final long serialVersionUID = 2699683591070649356L;

	private String name1;
	private String name2;

	public String getName3() {
		return name3;
	}

	public String getName4() {
		return name4;
	}

	private String name3;

	public void setName4(String name4) {
		this.name4 = name4;
	}

	public void setName3(String name3) {
		this.name3 = name3;
	}

	private String name4;
	private List<COM_XY> listXYs;
	
	public List<COM_XY> getListXYs() {
		return listXYs;
	}

	public void setListXYs(List<COM_XY> listXYs) {
		this.listXYs = listXYs;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

}
