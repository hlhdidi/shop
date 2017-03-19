package com.hlhdidi.shop.pojo.sys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SysUser  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer sys_uid;
	private String sys_username;
	private String sys_password;
	private List<SysRole> roles=new ArrayList<>();
	
	public Integer getSys_uid() {
		return sys_uid;
	}
	public void setSys_uid(Integer sys_uid) {
		this.sys_uid = sys_uid;
	}
	public String getSys_username() {
		return sys_username;
	}
	public void setSys_username(String sys_username) {
		this.sys_username = sys_username;
	}
	public String getSys_password() {
		return sys_password;
	}
	public void setSys_password(String sys_password) {
		this.sys_password = sys_password;
	}
	public List<SysRole> getRoles() {
		return roles;
	}
	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
	
}
