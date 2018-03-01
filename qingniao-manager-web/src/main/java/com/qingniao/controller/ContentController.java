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
package com.qingniao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingniao.common.EasyUIDataGridResult;
import com.qingniao.common.QingNiaoResult;
import com.qingniao.content.service.ContentService;
import com.qingniao.pojo.TbContent;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月13日 下午10:47:40 
* 类说明 
*/
@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	
	@ResponseBody
	@RequestMapping("/content/query/list")
	public EasyUIDataGridResult getContentList(long categoryId,int page,int rows){
		return contentService.getContentList(categoryId, page, rows);
	}
	
	@RequestMapping("/content/save")
	@ResponseBody
	public QingNiaoResult save(TbContent content){
		return contentService.save(content);
	}
	
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public QingNiaoResult update(TbContent content){
		return contentService.update(content);
	}
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public QingNiaoResult delete(long[] ids){
		return contentService.delete(ids);
	}

}
 