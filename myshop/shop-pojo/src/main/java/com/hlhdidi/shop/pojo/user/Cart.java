package com.hlhdidi.shop.pojo.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * 购物车放到cookie里面不要放所有的字段.
 * @author Administrator
 *
 */
public class Cart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CartItem> items=new ArrayList<>();
	private Float productPrice=0f;	//商品总金额
	private Float fee=10f;	//运费(采用固定值.)
	private Integer productAmount=0;//商品数量.[商品总数目.]
	private Float totalPrice=0f;//商品总计
	
	public List<CartItem> getItems() {
		return items;
	}
	public void setItems(List<CartItem> items) {
		this.items = items;
	}
	public Float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}
	public Float getFee() {
		return fee;
	}
	public void setFee(Float fee) {
		this.fee = fee;
	}
	public Integer getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(Integer productAmount) {
		this.productAmount = productAmount;
	}
	public Float getTotalPrice() {
		this.totalPrice=this.productPrice+this.fee;
		return totalPrice;
	}
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * 添加购物车.
	 * @param cartItem
	 */
	public void addItemToCart(CartItem cartItem) {
		//判断CartItem是否存在
		boolean isExists=false;
		if(items.size()>0) {
			//判断是否存在.
			for(CartItem item:items) {
				if(item.getSkuId().equals(cartItem.getSkuId())) {
					//存在
					this.productPrice=this.productPrice+(cartItem.getAmount())*(cartItem.getSku().getPrice());
					this.productAmount=this.getProductAmount()+cartItem.getAmount();
					item.setAmount(item.getAmount()+cartItem.getAmount());
					isExists=true;
					break;
				}
			}
			if(isExists==false) {
				items.add(cartItem);
				this.productPrice=this.productPrice+(cartItem.getAmount())*(cartItem.getSku().getPrice());
				this.productAmount=this.getProductAmount()+cartItem.getAmount();
			}
		}
		else {
			items.add(cartItem);
			this.productAmount=this.getProductAmount()+cartItem.getAmount();
			this.productPrice=this.productPrice+(cartItem.getAmount())*(cartItem.getSku().getPrice());
		}
	}
	
}
