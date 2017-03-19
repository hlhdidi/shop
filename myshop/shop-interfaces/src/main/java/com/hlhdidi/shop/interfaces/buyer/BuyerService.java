package com.hlhdidi.shop.interfaces.buyer;

import java.util.List;

import com.hlhdidi.shop.pojo.order.Order;
import com.hlhdidi.shop.pojo.user.Buyer;
import com.hlhdidi.shop.pojo.user.Cart;

public interface BuyerService {
	public Buyer findBuyerById(Integer userId);

	public Cart findCartFromRedis(String username);

	public void addCartToRedis(String username, Cart cart);

	public Cart convertCart(Cart cart);

	public List<Object> selectCart(Long[] skuIds, String username);

	public Order addOrder(Order order, Long[] skuIds, String username);

	public void updateOrderState(Long orderId);

	public boolean isUsernameExists(String username);

	public void regist(Buyer buyer)throws Exception;

	public void active(String username);
}
