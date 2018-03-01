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
package com.qingniao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qingniao.item.pojo.Item;
import com.qingniao.pojo.TbItem;
import com.qingniao.pojo.TbItemDesc;
import com.qingniao.service.ItemService;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月22日 上午11:43:12 
* 类说明 
*/
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/item/{itemid}")
	public String item(@PathVariable long itemid,Model model){
		TbItem tbItem = itemService.selectByprimarKey(itemid);
		Item item=new Item(tbItem);
		TbItemDesc itemDesc = itemService.selectItemDescByKey(itemid);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
	

}
 