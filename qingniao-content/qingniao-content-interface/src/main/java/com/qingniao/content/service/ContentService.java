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
import java.util.List;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月13日 下午7:56:24 
* 类说明 
*/
import com.qingniao.common.EasyUIDataGridResult;
import com.qingniao.common.QingNiaoResult;
import com.qingniao.common.utils.Ad1;
import com.qingniao.pojo.TbContent;
public interface ContentService {
	
	EasyUIDataGridResult  getContentList(long categoryId, int page, int rows);
	
	QingNiaoResult save(TbContent content);
	
	QingNiaoResult update(TbContent content);
	
	QingNiaoResult delete(long[] ids);
	
	List<Ad1> getContentList(long categoryId);

}
 