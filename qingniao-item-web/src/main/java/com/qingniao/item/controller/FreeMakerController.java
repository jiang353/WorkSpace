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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.qingniao.item.pojo.Item;
import com.qingniao.pojo.TbItem;
import com.qingniao.pojo.TbItemDesc;
import com.qingniao.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author 作者: Jiang Song
 * @version 创建时间：2017年12月24日 下午9:22:56 类说明
 */
@Controller
public class FreeMakerController {
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private ItemService itemService;


	@RequestMapping("/gethtml")
	@ResponseBody
	public String getHtml(long itemID) {

		// 1、从spring容器中获得FreeMarkerConfigurer对象。
		// 2、从FreeMarkerConfigurer对象中获得Configuration对象。
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		TbItem tbitem = itemService.selectByprimarKey(itemID);
		TbItemDesc itemDesc = itemService.selectItemDescByKey(itemID);
		Item item=new Item(tbitem);
		try {
			// 3、使用Configuration对象获得Template对象。
			Template template = configuration.getTemplate("item.ftl");
			// 4、创建数据集
			Map map=new HashMap<>();
			map.put("item", item);
			map.put("itemDesc", itemDesc);
			// 5、创建输出文件的Writer对象。
			Writer out=new FileWriter(new File("D:/out/"+itemID+".html"));
			// 6、调用模板对象的process方法，生成文件。
			template.process(map, out);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ok";
	}

}
