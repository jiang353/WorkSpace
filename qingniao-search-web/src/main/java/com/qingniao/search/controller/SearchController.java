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
package com.qingniao.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qingniao.search.service.ItemSearchService;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月19日 下午4:43:47 
* 类说明 
*/
@Controller
public class SearchController {
	
	@Autowired
	private ItemSearchService itemSearchService;
	
	@RequestMapping("/search")
	public String show(@RequestParam(value="q")String search,@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="rt",defaultValue="52")int rows,Model model){
		try {
			search=new String(search.getBytes("iso-8859-1"), "utf-8");
			model.addAttribute("itemList", itemSearchService.selectSearch(search, page, rows).getItemList());
			model.addAttribute("query", search);
			model.addAttribute("page", page);
			model.addAttribute("totalPages", itemSearchService.selectSearch(search, page, rows).getTotalPages());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "search";
	}

}
 