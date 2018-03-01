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
package com.qingniao.service; 
/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月29日 上午11:33:32 
* 类说明 
*/
import com.qingniao.common.QingNiaoResult;
import com.qingniao.pojo.TbUser;
public interface UserService {
	
	QingNiaoResult checkData(String parm,Integer type);
	
	QingNiaoResult register(TbUser user);
	
	
	QingNiaoResult loging(String username,String password);
	
	QingNiaoResult getUserByToken(String token);
}
 