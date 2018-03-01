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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingniao.common.EasyUITreeNode;
import com.qingniao.common.QingNiaoResult;
import com.qingniao.content.service.ContentCategoryService;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月13日 下午3:40:01 
* 类说明 
*/
@Controller
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@ResponseBody
	@RequestMapping("/content/category/list")
	public List<EasyUITreeNode> getContentCategory(@RequestParam(value="id",defaultValue="0")long parentId){
		return contentCategoryService.getContentCategory(parentId);
	}
	
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public QingNiaoResult saveNode(long parentId,String name){
		return contentCategoryService.saveNodes(parentId, name);
	}
	
	@RequestMapping("/content/category/update")
	@ResponseBody
	public QingNiaoResult updateNode(long id,String name){
		return contentCategoryService.updateNode(id, name);
	}
	
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public QingNiaoResult deleteNode(long id){
		return contentCategoryService.deleteNode(id);
	}

}
 