package com.hlhdidi.shop.pojo.user;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.hlhdidi.shop.pojo.product.Sku;

/**
 * 购物车项
 * @author Administrator
 *
 */
public class CartItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Sku sku;
	private Integer amount;
	private Boolean isHave;	//是否有货!
	@JSONField(serialize=false)		//不被序列化
	private Long skuId;
	
	public Long getSkuId() {
		return this.sku.getId();
	}
	public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Boolean getIsHave() {
		return isHave;
	}
	public void setIsHave(Boolean isHave) {
		this.isHave = isHave;
	}
	
}
