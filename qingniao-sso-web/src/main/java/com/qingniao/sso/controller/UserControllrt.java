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
package com.qingniao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingniao.common.QingNiaoResult;
import com.qingniao.common.utils.CookieUtils;
import com.qingniao.common.utils.JsonUtils;
import com.qingniao.pojo.TbUser;
import com.qingniao.service.UserService;

/**
 * @author 作者: Jiang Song
 * @version 创建时间：2017年12月29日 上午11:46:27 类说明
 */

@Controller
public class UserControllrt {

	@Autowired
	private UserService userService;
	
	@Value("${TT_COOKIE}")
	private String TT_COOKIE;

	/*
	 * 显示注册
	 */
	@RequestMapping("/user/showRegister")
	public String showRegister() {
		return "register";
	}

	/*
	 * 显示登陆
	 */
	@RequestMapping("/user/showLogin")
	public String showLong(String redirect, Model model) {
		if (StringUtils.isNotBlank(redirect)) {
			// 从别的页面过来的地址 如果不为空就在登陆成功后跳转到该地址
			model.addAttribute("redirect", redirect);
		}
		return "login";
	}

	/**
	 * 
	 * @param parm
	 * @param type
	 * @return 参数效验
	 */
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public QingNiaoResult checkData(@PathVariable String parm, @PathVariable Integer type) {
		return userService.checkData(parm, type);
	}

	/**
	 * 注册
	 */
	@RequestMapping("/user/register")
	@ResponseBody
	public QingNiaoResult register(TbUser user) {
		return userService.register(user);
	}

	/**
	 * 登陆
	 */
	@RequestMapping("/user/login")
	@ResponseBody
	public QingNiaoResult login(String username, String password,HttpServletRequest request,HttpServletResponse response) {
		QingNiaoResult resou = userService.loging(username, password);
		//如果登陆成功 
		if(resou.getStatus()==200){
			//获取token
			String token=resou.getData().toString();
			//将token写入到客户端
			CookieUtils.setCookie(request, response, TT_COOKIE, token);
		}
		return resou;
	}
	
	/**
	 * jsonp 查询是否登陆
	 * @param token
	 * @param callback
	 * @return
	 * jQuery6651455({"status":200,"msg":"OK","data":{"id":7,"username":"zhangsan","password":null,
	 * "phone":"13488888888","email":"aa@a","created":1428311035000,"updated":1428311035000}});
	 */
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback){
		QingNiaoResult resou=userService.getUserByToken(token);
		//判断是否为jsonp请求  若为jsonp请求会有kallback参数
		//System.out.println("*****");
		if(StringUtils.isNotBlank(callback)){
			String strResult=callback+"("+JsonUtils.objectToJson(resou)+");";
			//System.out.println(strResult);
			return strResult;
		}
		return JsonUtils.objectToJson(resou);
	}

}
