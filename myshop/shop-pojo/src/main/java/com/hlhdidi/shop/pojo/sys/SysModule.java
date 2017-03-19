package com.hlhdidi.shop.pojo.sys;

import java.io.Serializable;

public class SysModule  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer sys_mid;
	private String sys_mname;
	private String sys_desc;
	public Integer getSys_mid() {
		return sys_mid;
	}
	public void setSys_mid(Integer sys_mid) {
		this.sys_mid = sys_mid;
	}
	public String getSys_mname() {
		return sys_mname;
	}
	public void setSys_mname(String sys_mname) {
		this.sys_mname = sys_mname;
	}
	public String getSys_desc() {
		return sys_desc;
	}
	public void setSys_desc(String sys_desc) {
		this.sys_desc = sys_desc;
	}
	
	

}
