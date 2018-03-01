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
package com.qingniao.content.service; 
/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月13日 下午3:13:23 
* 类说明 
*/
import java.util.List;

import com.qingniao.common.EasyUITreeNode;
import com.qingniao.common.QingNiaoResult;

public interface ContentCategoryService {
	
	
	List<EasyUITreeNode> getContentCategory(long parentId);
	
	QingNiaoResult saveNodes(long parentId,String name);
	
	QingNiaoResult updateNode(long id,String name);

	QingNiaoResult deleteNode(long id);
}
 