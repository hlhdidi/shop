package com.hlhdidi.shop.pojo.product;

import java.io.Serializable;

/**
 * Brand的查询类.
 * @author Administrator
 *
 */
public class BrandQuery implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//String name, Integer isDisplay, Integer pageNo
	private String name;
	private Integer isDisplay;
	private Integer pageNo=1;
	private static final int PAGE_SIZE=10;
	private Integer pageSize=PAGE_SIZE;
	private Integer startIndex;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
		this.startIndex=(this.pageNo-1)*(this.pageSize);
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		this.startIndex=(this.pageNo-1)*(this.pageSize);
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
}
