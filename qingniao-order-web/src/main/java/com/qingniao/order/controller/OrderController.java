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
package com.qingniao.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qingniao.common.utils.CookieUtils;
import com.qingniao.common.utils.JsonUtils;
import com.qingniao.pojo.TbItem;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2018年1月3日 下午4:23:48 
* 类说明 
*/
@Controller
public class OrderController {
	
	@Value("${TT_CART}")
	private String TT_CART;
	
	
	@RequestMapping("/order/order-cart")
	public String orderCart(HttpServletRequest request){
		request.setAttribute("cartList", getCartList(request));
		return "order-cart";
	}
	
	
	//从cookie取出商品列表
	/**
	 * @return
	 */
	public List<TbItem> getCartList(HttpServletRequest request){
		String cookieValue = CookieUtils.getCookieValue(request, TT_CART);
		if(StringUtils.isNotBlank(cookieValue)){
			return JsonUtils.jsonToList(cookieValue, TbItem.class);
		}
		return new ArrayList<>();
	}

}
 