package com.hlhdidi.shop.pojo.sys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SysRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer sys_rid;
	private String sys_rname;
	private String sys_des;
	private List<SysModule> modules=new ArrayList<>();
	public Integer getSys_rid() {
		return sys_rid;
	}
	public void setSys_rid(Integer sys_rid) {
		this.sys_rid = sys_rid;
	}
	public String getSys_rname() {
		return sys_rname;
	}
	public void setSys_rname(String sys_rname) {
		this.sys_rname = sys_rname;
	}
	public String getSys_des() {
		return sys_des;
	}
	public void setSys_des(String sys_des) {
		this.sys_des = sys_des;
	}
	public List<SysModule> getModules() {
		return modules;
	}
	public void setModules(List<SysModule> modules) {
		this.modules = modules;
	}
	
}
