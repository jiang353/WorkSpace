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
package com.qingniao.portal.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qingniao.common.utils.JsonUtils;
import com.qingniao.content.service.ContentService;

/**
 * @author 作者: Jiang Song
 * @version 创建时间：2017年12月13日 下午1:03:10 类说明
 */
@Controller
public class IndexController {

	@Value("${AD1_CATEGORY_ID}")
	private long AD1_CATEGORY_ID;

	@Autowired
	private ContentService contentService;

	
	@RequestMapping("/{page}")
	public String show(@PathVariable String page, Model model) {
		if (page.equals("index")) {
			String json = JsonUtils.objectToJson(contentService.getContentList(AD1_CATEGORY_ID));
			model.addAttribute("ad1", json);
		}
		return page;
	}

}
