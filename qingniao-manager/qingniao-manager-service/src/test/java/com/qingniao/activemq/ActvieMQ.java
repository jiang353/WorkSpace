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
package com.qingniao.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月20日 上午11:20:25 
* 类说明 生成产者
*/
/*@ContextConfiguration(locations="classpth:spring/applicationContext-actviemq.xml")
@RunWith(SpringJUnit4ClassRunner.class)*/
public class ActvieMQ {
	
	public void actvieTest(){
		//加载MQ配置文件
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		//获取模板
		JmsTemplate jms=(JmsTemplate) context.getBean("jmsTemplate");
		//获取队列对象
		Queue queue= (Queue) context.getBean("test-queue");
		//通过模板发送消息到队列
		jms.send(queue, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createTextMessage("this spring test message!");
			}
		});
	}

}
 