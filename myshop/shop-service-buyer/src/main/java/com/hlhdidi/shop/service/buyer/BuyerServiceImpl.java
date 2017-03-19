package com.hlhdidi.shop.service.buyer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hlhdidi.shop.commons.cons.Constant;
import com.hlhdidi.shop.commons.utils.EncodingUtils;
import com.hlhdidi.shop.interfaces.buyer.BuyerService;
import com.hlhdidi.shop.mapper.order.DetailMapper;
import com.hlhdidi.shop.mapper.order.OrderMapper;
import com.hlhdidi.shop.mapper.product.ColorMapper;
import com.hlhdidi.shop.mapper.product.ProductMapper;
import com.hlhdidi.shop.mapper.product.SkuMapper;
import com.hlhdidi.shop.mapper.user.BuyerMapper;
import com.hlhdidi.shop.pojo.order.Detail;
import com.hlhdidi.shop.pojo.order.Order;
import com.hlhdidi.shop.pojo.product.Color;
import com.hlhdidi.shop.pojo.product.Product;
import com.hlhdidi.shop.pojo.product.Sku;
import com.hlhdidi.shop.pojo.user.Buyer;
import com.hlhdidi.shop.pojo.user.Cart;
import com.hlhdidi.shop.pojo.user.CartItem;

@Service("buyerService")
public class BuyerServiceImpl implements BuyerService{
	@Autowired
	private BuyerMapper buyerMapper;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private SkuMapper skuMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ColorMapper colorMapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private DetailMapper detailMapper;
	@Autowired
	private JavaMailSender sender;
	@Value("${spring.mail.username}")
	private String from;	//邮件的发送方.
	private MimeMessage mimeMessage;
	@Override
	public Buyer findBuyerById(Integer userId) {
		Buyer buyer = buyerMapper.selectByPrimaryKey(Long.parseLong(userId+""));
		return buyer;
	}

	@Override
	public Cart findCartFromRedis(String username) {
		Map<Object, Object> map = redisTemplate.opsForHash().entries(Constant.REDIS_CART+username);
		//以skuId作为键.以amount作为值.
		if(map!=null) {
			Cart cart=new Cart();
			for(Map.Entry<Object, Object> en:map.entrySet()) {
				Long skuId=Long.parseLong(en.getKey()+"");
				Integer amount=Integer.parseInt(en.getValue()+"");
				CartItem cartItem=new CartItem();
				Sku sku = skuMapper.selectByPrimaryKey(skuId);
				Product product = productMapper.selectByPrimaryKey(sku.getProductId());
				Product simpleProduct=new Product();
				simpleProduct.setImages(product.getImages());
				simpleProduct.setName(product.getName());
				sku.setProduct(simpleProduct);
				Color color=colorMapper.selectByPrimaryKey(sku.getColorId());
				Color simpleColor=new Color();
				simpleColor.setName(color.getName());
				sku.setColor(simpleColor);
				cartItem.setSku(sku);
				cartItem.setAmount(amount);
				cart.addItemToCart(cartItem);
				
			}
			return cart;
		}
		return null;
	}

	@Override
	public void addCartToRedis(String username,Cart cart) {
		Map<Object,Object> params=new HashMap<>();
		List<CartItem> items = cart.getItems();
		for(CartItem item:items) {
			params.put(item.getSkuId()+"", item.getAmount()+"");
		}
		redisTemplate.opsForHash().putAll(Constant.REDIS_CART+username, params);
	}

	@Override
	public Cart convertCart(Cart cart) {
		List<CartItem> items = cart.getItems();
		for(CartItem item:items) {
			Sku sku = item.getSku();
			Color color = colorMapper.selectByPrimaryKey(sku.getColorId());
			sku.getColor().setName(color.getName());
			sku.getProduct().setName(productMapper.selectByPrimaryKey(sku.getProductId()).getName());
		}
		return cart;
	}

	@Override
	public List<Object> selectCart(Long[] skuIds,String username) {
		List<Object> list =null;
		if(skuIds!=null&&skuIds.length>0) {
			list=new ArrayList<>();
			for(Long skuId:skuIds) {
				Object amounts = redisTemplate.opsForHash().get(Constant.REDIS_CART+username,skuId+"");
				list.add(amounts);
			}
		}
		return list;
	}

	@Override
	public Order addOrder(Order order, Long[] skuIds, String username) {
		//设置order的id
		Long orderId = redisTemplate.opsForValue().increment("orderId",1L);
		order.setId(orderId);
		// order封装了部分属性
		Cart cart=null;
		List<Object> amounts=selectCart(skuIds,username);
		List<Detail> details=null;
		List<Object> idParams=null;
		if(amounts!=null&&amounts.size()>0) {
			details=new ArrayList<>();
			cart=new Cart();
			idParams=new ArrayList<>();//方便清除用
			for(int i=0;i<skuIds.length;i++) {
				CartItem item=new CartItem();
				Long skuId=skuIds[i];
				Integer amount =Integer.parseInt(amounts.get(i)+""); 
				Sku sku = skuMapper.selectByPrimaryKey(skuId);
				Product product = productMapper.selectByPrimaryKey(sku.getProductId());
				sku.setProduct(product);
				Color color=colorMapper.selectByPrimaryKey(sku.getColorId());
				sku.setColor(color);
				item.setAmount(amount);
				item.setSku(sku);
				cart.addItemToCart(item);
				Detail detail=new Detail();
				detail.setAmount(amount);
				detail.setColor(color.getName());
				detail.setOrderId(order.getId());
				detail.setPrice(item.getSku().getPrice());
				detail.setProductId(product.getId());
				detail.setProductName(product.getName());
				detail.setSize(item.getSku().getSize());
				details.add(detail);
				idParams.add(skuId+"");
			}
		}
		order.setCreateDate(new Date());
		order.setDeliverFee(cart.getFee());
		order.setTotalPrice(cart.getTotalPrice());
		order.setOrderPrice(cart.getProductPrice());
		String buyerId=redisTemplate.opsForValue().get(username+"");
		order.setBuyerId(Long.parseLong(buyerId));//设置buyerId
		Integer paymentWay= order.getPaymentWay();
		if(paymentWay==1) {
			order.setIsPaiy(0);//货到付款
		}
		else {
			order.setIsPaiy(1);//待支付
		}
		order.setOrderState(0);//提交订单
		//插入order
		orderMapper.insertSelective(order);
		//插入OrderDetail
		for(Detail detail:details) {
			detailMapper.insertSelective(detail);
		}
		//清除对应的ids
		redisTemplate.opsForHash().delete(Constant.REDIS_CART+username,idParams.toArray());
		return order;
	}

	@Override
	public void updateOrderState(Long orderId) {
		// TODO Auto-generated method stub
		Order order = orderMapper.selectByPrimaryKey(orderId);
		order.setOrderState(1);	//修改订单状态为仓库配货
		order.setIsPaiy(2);	//设置为已经付款
		orderMapper.updateByPrimaryKeySelective(order);
	}

	@Override
	public boolean isUsernameExists(String username) {
		String id = redisTemplate.opsForValue().get(username);
		if(id!=null) {
			return true;//存在
		}
		return false;//不存在
	}

	@Override
	public void regist(Buyer buyer) throws Exception {
		// 对密码进行加密.
		buyer.setPassword(EncodingUtils.encodePassword(buyer.getPassword()));
		buyer.setIsDel(true);//设置未激活状态
		buyer.setRegisterTime(new Date());
		buyerMapper.insertSelective(buyer);
		//存放到redis缓存数据库中.
		redisTemplate.opsForValue().set(buyer.getUsername(), buyer.getId()+"");
	    mimeMessage = sender.createMimeMessage();
		MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage,true);
		messageHelper.setFrom(from);
		messageHelper.setTo(buyer.getEmail());
		messageHelper.setSubject("用户激活.");
		String content="<!DOCTYPE html><html><head><meta charset='UTF-8'><title>激活成功!</title>"+
		"</head><body><h1>恭喜您激活成功!</h1><h2><a href='http://127.0.0.1:8580/active.aspx?username="+buyer.getUsername()+"'>点我跳转主页!</a></h2>"
		+"</body></html>";
		messageHelper.setText(content,true);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				sender.send(mimeMessage);
			}
		}).start();
		
	}

	@Override
	public void active(String username) {
		String id = redisTemplate.opsForValue().get(username);
		Buyer buyer = buyerMapper.selectByPrimaryKey(Long.parseLong(id));
		buyer.setIsDel(false);//已经激活了
		buyerMapper.updateByPrimaryKeySelective(buyer);
	}
	
}
