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
package com.qingniao.search.service; 
/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月19日 下午2:34:50 
* 类说明 
*/
import com.qingniao.common.QingNiaoResult;
import com.qingniao.common.pojo.SearchResult;
public interface ItemSearchService {
	
	QingNiaoResult saveItemInSolr();
	
	SearchResult selectSearch(String search,int page,int rows);
	
	QingNiaoResult saveByItem(long id);

}
 