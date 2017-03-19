package com.hlhdidi.shop.web.portal.buyer;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlhdidi.shop.interfaces.buyer.BuyerService;
import com.hlhdidi.shop.interfaces.product.SkuService;
import com.hlhdidi.shop.pojo.order.Order;
import com.hlhdidi.shop.pojo.product.Sku;
import com.hlhdidi.shop.pojo.user.Cart;
import com.hlhdidi.shop.pojo.user.CartItem;
import com.hlhdidi.shop.web.utils.PaymentUtil;

@RequestMapping("/portal/order")
@Controller
public class OrderController {
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private SkuService skuService;

	@RequestMapping("/toOrder")
	// 跳到订单页
	public String toOrder(Long[] skuIds, String username, Model model, Boolean isLogin, HttpServletResponse response) {
		// 已经登录了.
		Cart cart = null;
		List<Object> amounts = buyerService.selectCart(skuIds, username);
		boolean flag = true; // 判断是否有货.
		if (amounts != null && amounts.size() > 0) {
			cart = new Cart();
			for (int i = 0; i < skuIds.length; i++) {
				CartItem item = new CartItem();
				Long skuId = skuIds[i];
				Integer amount = Integer.parseInt(amounts.get(i) + "");
				Sku sku = skuService.findSkuById(skuId);
				if (sku.getStock() <= amount) {
					item.setIsHave(false);
					flag = false;
				}
				item.setAmount(amount);
				item.setSku(sku);
				cart.addItemToCart(item);
			}
		}
		model.addAttribute("buyerCart", cart);
		// 传入isLogin
		model.addAttribute("isLogin", isLogin);
		// 传入username,方便携带.
		model.addAttribute("username", username);
		if (cart == null || flag == false) {
			// 返回到页面
			return "cart";
		}
		return "order";
	}

	// 提交订单
	@RequestMapping("/submitOrder")
	public String submitOrder(Order order, Boolean isLogin, Long[] skuIds, String username, Model model) {
		order=buyerService.addOrder(order, skuIds, username);
		Integer way = order.getPaymentWay();
		// 根据way去选择.
		if (way == 1) {
			// 货到付款
			return "huodaofukuan";
		} else if (way == 2) {
			// 在线支付.
			model.addAttribute("username", username);
			model.addAttribute("isLogin", isLogin);
			model.addAttribute("orderId", order.getId());
			model.addAttribute("orderMoney", order.getTotalPrice());
			return "pay";
		}
		return null;
	}

	// 在线支付
	@RequestMapping("/pay")
	public String pay(HttpServletRequest request, Long orderid, String username, Double money, String pd_FrpId) {
		// 发给支付公司需要哪些数据
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
		String p2_Order = orderid + "";
		String p3_Amt = 0.5 + "";//测试使用.
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

		String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId=" + pd_FrpId + "&p0_Cmd=" + p0_Cmd
				+ "&p1_MerId=" + p1_MerId + "&p2_Order=" + p2_Order + "&p3_Amt=" + p3_Amt + "&p4_Cur=" + p4_Cur
				+ "&p5_Pid=" + p5_Pid + "&p6_Pcat=" + p6_Pcat + "&p7_Pdesc=" + p7_Pdesc + "&p8_Url=" + p8_Url
				+ "&p9_SAF=" + p9_SAF + "&pa_MP=" + pa_MP + "&pr_NeedResponse=" + pr_NeedResponse + "&hmac=" + hmac;

		// 重定向到第三方支付平台
		return "redirect:" + url;
	}

	public String callback(HttpServletRequest request,String p1_MerId,String r0_Cmd,String r1_Code
			,String r2_TrxId,String r3_Amt,String r4_Cur,String r5_Pid,String r6_Order,String r7_Uid,
			String r8_MP,String r9_BType,String rb_BankId,String ro_BankOrderId,
			String rp_PayDate,String rq_CardNo,String ru_Trxtime,String hmac,HttpServletResponse response) throws IOException {
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
		// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid,
				r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);

		if (isValid) {
			// 响应数据有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().println("<h1>付款成功！等待商城进一步操作！等待收货...</h1>");
			} else if (r9_BType.equals("2")) {
				// 服务器点对点 --- 支付公司通知你
				System.out.println("付款成功！");
				// 修改订单状态 为已付款
				Long orderId=Long.parseLong(r6_Order);
				buyerService.updateOrderState(orderId);
				// 回复支付公司
				response.getWriter().print("success");	//回复易宝的.支付成功.
			}
		} else {
			// 数据无效
			System.out.println("数据被篡改！");
		}
		return null;
	}
}
