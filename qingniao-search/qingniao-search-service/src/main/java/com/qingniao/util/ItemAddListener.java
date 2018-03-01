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
package com.qingniao.util;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.qingniao.search.service.ItemSearchService;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月20日 下午12:47:35 
* 类说明 
* 
*监听器 当接收到添加的消息后 将数据添加到solr 
*/
public class ItemAddListener implements MessageListener{

	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	
	@Autowired
	private ItemSearchService itemSearchService;
	
	@Override
	public void onMessage(Message mes) {
		try {
			TextMessage message=(TextMessage) mes;
			long id=Long.parseLong(message.getText());
			//监听到后台添加商品的时候，将商品添加至索引库 
			//先让线程休息半秒 避免数据库添加的事务没提交所引发的异常
			Thread.sleep(500);
			itemSearchService.saveByItem(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
 