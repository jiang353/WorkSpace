//                            _ooOoo_  
//                           o8888888o  
//                           88" . "88  
//                           (| -_- |)  
//                            O\ = /O  
//                        ____/`---'\____  
//                      .   ' \\| |// `.  
//                       / \\||| : |||// \  
//                     / _||||| -:- |||||- \  
//                       | | \\\ - /// | |  
//                     | \_| ''\---/'' | |  
//                      \ .-\__ `-` ___/-. /  
//                   ___`. .' /--.--\ `. . __  
//                ."" '< `.___\_<|>_/___.' >'"".  
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//                 \ \ `-. \_ __\ /__ _/ .-` / /  
//         ======`-.____`-.___\_____/___.-`____.-'======  
//                            `=---='  
//  
//         .............................................  
//                  佛祖保佑             永无BUG 

/**
 * 
 */
package com.qingniao.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingniao.common.QingNiaoResult;
import com.qingniao.common.utils.CookieUtils;
import com.qingniao.common.utils.JsonUtils;
import com.qingniao.pojo.TbItem;
import com.qingniao.service.ItemService;

/**
 * @author 作者: Jiang Song
 * @version 创建时间：2017年12月29日 下午10:13:59 类说明
 */
@Controller
public class CartController {

	@Autowired
	private ItemService itemService;

	@Value("${TT_CART}")
	private String TT_CART;

	/*
	 * 添加商品
	 */
	@RequestMapping("/add/cart/{itemid}")
	public String addCart(@PathVariable long itemid, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 把商品加入到购物车
		// 1.从cookie里面取出购物车里面得商品列表
		List<TbItem> cartList = getCartList(request);
		// 2.判断商品列表里是否有要添加得商品
		boolean hasitem = false;
		// System.out.println(cartList);
		for (TbItem tbItem : cartList) {
			if (tbItem.getId() == itemid) {
				// 如果cooki里面有商品 则跟新数量
				hasitem = true;
				tbItem.setNum(tbItem.getNum() + num);
				break;
			}
		}
		// 3.跟新购物车商品熟练
		// cookie里不存在该商品 测将商品添加到cookie
		if (!hasitem) {
			TbItem item = itemService.selectByprimarKey(itemid);
			item.setNum(num);
			cartList.add(item);
		}
		// 4.把购物车列表重新写入cookie
		CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cartList));
		// 返回页面
		return "cartSuccess";
	}

	/*
	 * 购物车页面
	 */
	@RequestMapping("/cart/cart")
	public String cartList(HttpServletRequest request) {
		// 取出商品列表
		List<TbItem> cartList = getCartList(request);
		// 把商品添加到域
		request.setAttribute("cartList", cartList);
		return "cart";

	}

	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public QingNiaoResult updateCart(@PathVariable long itemId, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 取出所有商品
		List<TbItem> cartList = getCartList(request);
		// 更新购物车商品
		for (TbItem tbItem : cartList) {
			// 更新数量
			if (tbItem.getId() == itemId) {
				tbItem.setNum(num);
				break;
			}
		}
		// 更新完成后重新写入到cookie
		CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cartList));
		return QingNiaoResult.ok();
	}

	// 从cookie取出商品列表
	public List<TbItem> getCartList(HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, TT_CART);
		if (StringUtils.isNotBlank(cookieValue)) {
			return JsonUtils.jsonToList(cookieValue, TbItem.class);
		}
		return new ArrayList<>();
	}

	/*
	 * 删除购物车商品
	 * 
	 */
	@RequestMapping("/cart/delete/{cartid}")
	public String deleteCart(@PathVariable long cartid, HttpServletRequest request, HttpServletResponse response) {
		// 取出商品列表
		List<TbItem> cartList = getCartList(request);
		for (TbItem tbItem : cartList) {
			if (tbItem.getId() == cartid) {
				cartList.remove(tbItem);
				break;
			}
		}
		// 更新完成后重新写入到cookie
		CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cartList));
		return "redirect:/cart/cart";
	}
}
